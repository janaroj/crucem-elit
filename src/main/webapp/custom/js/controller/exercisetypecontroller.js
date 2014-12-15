(function() {
	var app = angular.module('crucem-elit');

	app.controller('AdminExerciseTypesController', function($scope, $rootScope, $filter, $location, exerciseTypeService, ngTableParams, toaster) {

		var exerciseTypeData = null;
		var tempData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10,          // count per page
			sorting: {
				name : 'asc'
			}
		}, {
			total: 0,           // length of data
			getData: function($defer, params) {
				if (exerciseTypeData===null) {
					exerciseTypeService.getExerciseTypes().then(function(result) {
						exerciseTypeData = result.data;
						tempData = angular.copy(exerciseTypeData);
						ui.util.table.prepareData($defer, $filter, params, exerciseTypeData);
					}, function(result) {
						toaster.pop('error', $rootScope.getTranslation('exercise.type') , result.data.message);
					});
				}
				else {
					ui.util.table.prepareData($defer, $filter, params, exerciseTypeData);
				}
			}
		});

		$scope.tableParams.settings().$loading = true;
		
		$scope.newExerciseType = function() {
			$location.path('/admin/exercisetype');
		};

		$scope.deleteExerciseType = function(exerciseType) {
			if (confirm($rootScope.getTranslation('exercise.type.delete.confirm') + " " + exerciseType.name )) {
				exerciseTypeService.deleteExerciseType(exerciseType.id).then(function() {
					exerciseTypeData.splice( exerciseTypeData.indexOf(exerciseType), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', $rootScope.getTranslation('exercise.type') , $rootScope.getTranslation('exercise.type.delete.success'));
				}, 
				function(result) {
					toaster.pop('error', $rootScope.getTranslation('exercise.type'), result.data.message);
				});
			}
		};
		
		$scope.updateExerciseType = function(exerciseType) {
			exerciseTypeService.updateExerciseType(exerciseType).then(function(){
				toaster.pop('success', $rootScope.getTranslation('exercise.type') , $rootScope.getTranslation('exercise.type.update.success'));
				tempData[tempData.indexOf($filter("filter")(tempData, {id : exerciseType.id}, true)[0])] = angular.copy(exerciseType);
				exerciseType.$edit = false; 
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('exercise.type'), result.data.message);
				$scope.cancelEdit(exerciseType);
			});
		};
		
		$scope.cancelEdit = function(exerciseType) {
			var temp = $filter("filter")(tempData, {id : exerciseType.id}, true)[0];
			temp.$edit = false;
			exerciseTypeData[exerciseTypeData.indexOf($filter("filter")(exerciseTypeData, {id : exerciseType.id}, true)[0])] = angular.copy(temp); //Temporary hack, do not try this at home
			$scope.tableParams.reload();
		};

	});

	app.controller('AdminExerciseTypeController', function($scope, $rootScope, $location, exerciseTypeService, toaster) {

		$scope.createExerciseType = function() {
			exerciseTypeService.createExerciseType($scope.exerciseType).then(function(result) {
				$location.path('/admin/exercisetypes');
				toaster.pop('success', $rootScope.getTranslation('exercise.type') , $rootScope.getTranslation('exercise.type.create.success'));
			}, 
			function(result) {
				toaster.pop('error', $rootScope.getTranslation('exercise.type'), result.data.message);
			});
		};

	});

}());