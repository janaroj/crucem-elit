(function() {
	var app = angular.module('crucem-elit');
	
	app.controller('AdminExercisesController', function($scope, $q, $filter, $location, exerciseService, exerciseTypeService, ngTableParams, toaster) {

		var exerciseData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10,          // count per page
		}, {
			total: 0,           // length of data
			getData: function($defer, params) {
				if (exerciseData===null) {
					exerciseService.getExercises().then(function(result) {
						exerciseData = result.data;
						ui.util.table.prepareData($defer, $filter, params, exerciseData);
					}, function(result) {
						toaster.pop('error', 'Exercises' , result.data.message);
					});
				}
				else {
					ui.util.table.prepareData($defer, $filter, params, exerciseData);
				}
			}
		});

		$scope.tableParams.settings().$loading = true;
		
		$scope.newExercise = function() {
			$location.path('/admin/exercise');
		};

		$scope.deleteExercise = function(exercise) {
			if (confirm("Are you sure you wish to delete " + exercise.name )) {
				exerciseService.deleteExercise(exercise.id).then(function() {
					exerciseData.splice( exerciseData.indexOf(exercise), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', 'Exercise' , 'Exercise deleted successfully');
				}, 
				function(result) {
					toaster.pop('error', 'Exercise', result.data.message);
				});
			}
		};
		
		$scope.updateExercise = function(exerciseDto, exercise) {
			exerciseService.updateExercise(exerciseDto.id, exerciseDto).then(function(){
				toaster.pop('success', 'Exercise' , 'Exercise updated successfully');
				angular.copy(exerciseDto, exercise);
			}, function(result) {
				toaster.pop('error', 'Exercise', result.data.message);
			});
			exercise.$edit = false; 
		};
		
		$scope.editExercise = function(exercise) {
			$scope.exerciseDto = angular.copy(exercise);
			exercise.$edit = true;
		};
		
		var countWeightBooleans = [{'id' : 'true','title' : $scope.getTranslation('true')},{'id' : 'false','title' : $scope.getTranslation('false')}];
		$scope.countWeightBooleans = function(column) {
			var def = $q.defer();
			def.resolve(countWeightBooleans);
			return def;
		};
		
		var countTimeBooleans = [{'id' : 'true','title' : $scope.getTranslation('true')},{'id' : 'false','title' : $scope.getTranslation('false')}];
		$scope.countTimeBooleans = function(column) {
			var def = $q.defer();
			def.resolve(countTimeBooleans);
			return def;
		};
		
		var countRepeatsBooleans = [{'id' : 'true','title' : $scope.getTranslation('true')},{'id' : 'false','title' : $scope.getTranslation('false')}];
		$scope.countRepeatsBooleans = function(column) {
			var def = $q.defer();
			def.resolve(countRepeatsBooleans);
			return def;
		};
		
		$scope.$watch('language()', function(newLang, oldLang) {
			if (oldLang !== newLang) {
				angular.forEach(countWeightBooleans,function(val,key) {val.title = $scope.getTranslation(val.id);})
				angular.forEach(countTimeBooleans,function(val,key) {val.title = $scope.getTranslation(val.id);})
				angular.forEach(countRepeatsBooleans,function(val,key) {val.title = $scope.getTranslation(val.id);})
			}
		});
		
		$scope.exerciseTypeOptions = function(column) {
			var def = $q.defer();
			var typeOptions = [];
			exerciseTypeService.getExerciseTypes().then(function(result) {
				$scope.exerciseTypes = result.data;
				angular.forEach(result.data, function(item) {
					typeOptions.push({
						'id' : item.id,
						'title' : item.name
					});
				});
				def.resolve(typeOptions);
			});
			return def;
		};

	});

	app.controller('AdminExerciseController', function($scope, $location, exerciseService, exerciseTypeService, toaster) {

		$scope.init = function() {
			exerciseTypeService.getExerciseTypes().then(function(result) {
				$scope.types = result.data;
			},
			function(result) {
				toaster.pop('error', 'Exercise Types',  result.data.message);
			});
		};

		$scope.createExercise = function() {
			exerciseService.createExercise($scope.exercise).then(function(result) {
				$location.path('/admin/exercises');
				toaster.pop('success', 'Exercise' , 'Exercise created successfully');
			}, 
			function(result) {
				toaster.pop('error', 'Exercise', result.data.message);
			});
		};

	});
	app.controller('ExerciseController', function($scope, exerciseService, exerciseTypeService, toaster, $modalInstance) {
		$scope.ok = function () {
			$modalInstance.close();
		};

		$scope.cancel = function () {
			$modalInstance.dismiss('cancel');
		};

		$scope.exercises = [{name:"Jooks",countTime:false,countWeight:false,countRepeats:false,comment:"Best exercise EU",clicked:false},
		                    {name:"Kükk",countTime:false,countWeight:true,countRepeats:true,comment:"Best exercise RU", clicked:false}];
		$scope.showExerciseDetails = function (exercise) {
			exercise.clicked = !exercise.clicked;
		}
		
		
	});

}());