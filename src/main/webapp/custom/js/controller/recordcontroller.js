(function() {
	var app = angular.module('crucem-elit');

	app.controller('RecordsController', function($scope, $q, $location, $filter, userService, workoutService, ngTableParams, toaster) {
		var recordData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10,          // count per page
			filter: {
				'user.name' : $scope.user.name
			},
			sorting: {
				'wod' : 'asc',
				'result.repeats' : 'desc'
			}
		}, {
			total: 0,           // length of data
			getData: function($defer, params) {
				if (recordData===null) {

					workoutService.getWorkouts().then(function(result) {
						recordData = result.data;
						ui.util.table.prepareData($defer, $filter, params, recordData);
					}, function(result) {
						toaster.pop('error', 'Records' , result.data.message);
					});
				}
				else {
					ui.util.table.prepareData($defer, $filter, params, recordData);
				}
			}
		});

		$scope.tableParams.settings().$loading = true;

		$scope.viewGym = function(id) {
			$location.path('/user/gyms/' + id);
		};

		$scope.viewUser = function(id) {
			$location.path('/user/contacts/' + id);
		};

		$scope.$watch('language()', function(newLang, oldLang) {
			if (oldLang !== newLang) {
				angular.forEach(genders,function(val,key) {val.title = $scope.getTranslation(val.id);})
			}
		});

		var genders = [];

		$scope.genders = function(column) {
			var def = $q.defer();
			userService.getGenders().then(function(result) {
				angular.forEach(result.data, function(item) {
					genders.push({
						'id' : item,
						'title' : $scope.getTranslation(item)
					});
				});
				def.resolve(genders);
			});
			return def;
		};
		
		$scope.deleteResult = function(record) {
			if (confirm("Are you sure you wish to delete " + record.name + " result?")) {
				userService.deleteResult(record.id).then(function() {
					recordData.splice( recordData.indexOf(record), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', 'Record' , 'Result deleted successfully');
				}, 
				function(result) {
					toaster.pop('error', 'Record', result.data.message);
				});
			}
		};

	});


	app.controller('RecordController', function($scope, $routeParams, $location, userService, workoutService, toaster) {
		$scope.workout = {};
		$scope.workout.exerciseGroups = [];
		$scope.workout.exerciseGroups.exercises = [];
		$scope.init = function() {
			userService.getWorkout($routeParams.id).then(function(result) {
				$scope.workout = result.data;
			}, function(result) {
				toaster.pop('error', 'Workout' , result.data.message);
			});
		};

		$scope.saveFilledWorkout = function() {
			if (!isMissingWodResult() && !isMissingExerciseResult()){
				userService.addWorkout($scope.workout).then(
						function(result) {
							$location.path('/user/workouts');
							toaster.pop('success', 'Workout' , 'Workout finished');
						},
						function(result) {
							toaster.pop('error', 'Workout' , result.data.message);
						}
				);
			}
			else {
				toaster.pop('error', 'Submit unsuccessful', 'Insert at least one result for every exercise!');
			}
		};

		var isMissingWodResult = function(){
			for (var i=0;i<$scope.workout.exerciseGroups.length;i++){
				var group = $scope.workout.exerciseGroups[i];
				if (group.wod) {

					if ( !group.record ||  !group.record.time && 
							!group.record.weight &&
							!group.record.repeats) {
						return true;
					}
					return false;
				}
			}
			return false;
		};

		var isMissingExerciseResult = function() {
			for (var i = 0; i < $scope.workout.exerciseGroups.length; i++) {
				for (var u = 0; u<$scope.workout.exerciseGroups[i].exercises.length; u++) {
					var exercise = $scope.workout.exerciseGroups[i].exercises[u];
					if (exercise.countWeight || exercise.countTime || exercise.countRepeats){
						if (!exercise.record || !exercise.record.time  && !record.weight && !record.repeats) {
							return true;
						}
					}
				}
			}
			return false;
		};

	});

}());