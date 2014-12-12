(function() {
	var app = angular.module('crucem-elit');

	app.controller('RecordsController', function($scope, $rootScope, $q, $location, $filter, $timeout, exerciseTypeService, userService, workoutService, ngTableParams, toaster) {
		var wodData = [];
		$scope.wodTableLoading = true;
		$scope.exerciseTableLoading = true;

		$scope.init = function() {
			workoutService.getWorkoutsWithResults().then(function(workouts) {
				for (var i = 0; i < workouts.data.length; i++) {
					wodData.push(workouts.data[i]);
				}

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
						ui.util.table.prepareData($defer, $filter, params, wodData);
						ui.util.initializeDateRangePicker('daterange1');
						$scope.wodTableLoading = false;
					}
				});

				$scope.tableParams2 = new ngTableParams({
					page: 1,            // show first page
					count: 10,          // count per page
					filter: {
						'user.name' : $scope.user.name
					},
					sorting: {
						'result.repeats' : 'desc'
					}
				},  {
					total: 0,           // length of data
					getData: function($defer, params) {
						ui.util.table.prepareData($defer, $filter, params, getExerciseData(wodData));
						ui.util.initializeDateRangePicker('daterange2');
						$scope.exerciseTableLoading = false;
					}
				});

			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('records'), result.data.message);
			});

		};
		
		var getExerciseData = function(workouts) {
			var exerciseData = [];
			angular.forEach(workouts, function(value) {
				for (var i = 0; i < value.exerciseGroups.length ; i++) {
					for (var j = 0; j < value.exerciseGroups[i].exercises.length; j++) {
						var ex = value.exerciseGroups[i].exercises[j];
						ex.user = value.user;
						ex.gym = value.gym;
						ex.date = value.date;
						exerciseData.push(ex);
					}
				}
			});
			return exerciseData;
		};
		
		$scope.clearDateFilterFirstTable = function() {
			delete($scope.tableParams.filter().date);
		};
		
		$scope.applyDateFilterFirstTable = function() {
			$timeout(function(){$scope.tableParams.filter()["date"] = $("input[name=daterange1]").val();});
		};
		
		$scope.clearDateFilterSecondTable = function() {
			delete($scope.tableParams2.filter().date);
		};
		
		$scope.applyDateFilterSecondTable = function() {
			$timeout(function(){$scope.tableParams2.filter()["date"] = $("input[name=daterange2]").val();});
		};
		
		$scope.viewWorkout = function(workout) {
			$location.path('/user/workout/view/' + workout.id);
		};

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
		
		$scope.deleteResult = function(record) {
			if (confirm("Are you sure you wish to delete " + record.name + " result?")) {
				userService.deleteResult(record.id).then(function() {
					wodData.splice( wodData.indexOf(record), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', 'Record' , 'Result deleted successfully');
				}, 
				function(result) {
					toaster.pop('error', 'Record', result.data.message);
				});
			}
		};

	});


	app.controller('RecordController', function($scope, $rootScope, $routeParams, $location, userService, workoutService, toaster) {
		$scope.workout = {};
		$scope.workout.exerciseGroups = [];
		$scope.workout.exerciseGroups.exercises = [];
		$scope.init = function() {
			userService.getWorkout($routeParams.id).then(function(result) {
				$scope.workout = result.data;
				$scope.finishedLoading = true;
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('workout'), result.data.message);
			});
		};

		$scope.saveFilledWorkout = function() {
			if (!isMissingWodResult()){
				$scope.workout.completed = true;
				delete $scope.workout.user;
				delete $scope.workout.result; // Tee dto hiljem
				workoutService.updateWorkout($scope.workout).then(
						function(result) {
							$location.path('/user/workouts');
							toaster.pop('success', $rootScope.getTranslation('workout'), $rootScope.getTranslation('workout.finished'));
						},
						function(result) {
							toaster.pop('error', $rootScope.getTranslation('workout'), result.data.message);
						}
				);
			}
			else {
				toaster.pop('error', $rootScope.getTranslation('submit.unsuccessful'), $rootScope.getTranslation('insert.at.least.one'));
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
					var exercise = $scope.workout.exerciseGroups[i].exercises[u].exerciseModel;
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