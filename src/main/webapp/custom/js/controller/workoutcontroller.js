(function() {
	var app = angular.module('crucem-elit');

	app.controller('WorkoutsController', function($scope, $rootScope, $location, $filter, $timeout, userService, ngTableParams, toaster) {
		var newWorkoutData = [];
		var doneWorkoutData = [];
		$scope.doneTableLoading = true;
		$scope.newTableLoading = true;

		$scope.init = function() {
			userService.getWorkouts().then(function(workouts) {
				for (var i = 0; i < workouts.data.length; i++) {
					var workout = workouts.data[i];
					if (workout.result) {doneWorkoutData.push(workout);}
					else {newWorkoutData.push(workout);}
				}

				$scope.tableParams1 = new ngTableParams({
					page: 1,            // show first page
					count: 10,          // count per page
					sorting: {'date' : 'asc'}
				}, {
					total: 0,           // length of data
					getData: function($defer, params) {
						ui.util.table.prepareData($defer, $filter, params,newWorkoutData);
						ui.util.initializeDateRangePicker('daterange1');
						$scope.newTableLoading = false;
					}
				});

				$scope.tableParams2 = new ngTableParams({
					page: 1,            // show first page
					count: 10,          // count per page
					sorting: {'date' : 'desc'}
				}, {
					total: 0,           // length of data
					getData: function($defer, params) {
						ui.util.table.prepareData($defer, $filter, params, doneWorkoutData);
						ui.util.initializeDateRangePicker('daterange2');
						$scope.doneTableLoading = false;
					}
				});
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('workouts'), result.data.message);
			});
		};
		
		$scope.clearDateFilterFirstTable = function() {
			delete($scope.tableParams1.filter().date);
		};
		
		$scope.applyDateFilterFirstTable = function() {
			$timeout(function(){$scope.tableParams1.filter()["date"] = $("input[name=daterange1]").val();});
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
		
		$scope.newWorkout = function() {
			$location.path('/user/workout/add');
		};
		
		$scope.deleteWorkout = function(workout) {
			if (confirm($rootScope.getTranslation('delete.workout.confirm') + " " + workout.name )) {
				userService.deleteWorkout(workout.id).then(function() {
					newWorkoutData.splice( newWorkoutData.indexOf(workout), 1 );
					$scope.tableParams1.reload();
					toaster.pop('success', $rootScope.getTranslation('workout'), $rootScope.getTranslation('workout.deleted.successfully'));
				}, 
				function(result) {
					toaster.pop('error', $rootScope.getTranslation('workout'), result.data.message);
				});
			}
		};

	});

	app.controller('WorkoutController', function($scope, $rootScope, $modal, $location, $timeout, userService, exerciseService, toaster) {

		$scope.open = function($event) {
			$event.preventDefault();
			$event.stopPropagation();
			$scope.opened = true;
		};

		$scope.format = 'yyyy-MM-dd';
		
        $scope.showExerciseDetails = function (exercise) {
        	exercise.clicked = !exercise.clicked;
        }
        
        $scope.createWorkout = function() {
        	userService.addWorkout($scope.workout).then(
        	  function(result) {
        		  $location.path('/user/workouts');
        		  toaster.pop('success', $rootScope.getTranslation('workout'), $rootScope.getTranslation('workout.added.successfully'));
        	  },
        	  function(result) {
        		  toaster.pop('error', $rootScope.getTranslation('workout'), result.data.message);
        	  }
        	);
        }

        $scope.workout = {};
		$scope.workout.exerciseGroups = [{name:"", isWod:false, exercises:[]}];
		$scope.newExerciseGroup = function(){
			$scope.workout.exerciseGroups.push({name:"", isWod:false, exercises:[]});
		};
		$scope.removeExerciseGroup = function(group) {
			$scope.workout.exerciseGroups.splice($scope.workout.exerciseGroups.indexOf(group), 1);
		};

		$scope.removeExercise = function(group,exercise){
			group.exercises.splice(group.exercises.indexOf(exercise), 1);
		};
		
		$scope.isWodSelected = function() {
			var wodAmount = 0;
			for (var i = 0; i < $scope.workout.exerciseGroups.length; i++) {
				if ( $scope.workout.exerciseGroups[i].wod) {
					wodAmount++;
				}
			}
			return wodAmount === 1;
		};
		
		$scope.isMissingExercises = function() {
			for (var i = 0; i < $scope.workout.exerciseGroups.length; i++) {
				if ( $scope.workout.exerciseGroups[i].exercises.length === 0) {
					return true;
				}
			}
			return false;
		};
		
		var exercises = [];
			exerciseService.getExercises().then(
			  function(result) {
				  exercises = result.data;
			  },
			  function(result) {
				  toaster.pop('error', $rootScope.getTranslation('exercises'), result.data.message);
			  }
			);
		
		$scope.openModal = function (exerciseGroup) {

			var modalInstance = $modal.open({
				templateUrl: 'partials/user/addExerciseModal.html',
				controller: 'ExerciseController',
				resolve: {
					exerciseData: function () {
						return exercises;
					}
				}
			});

			modalInstance.result.then(function (exercises) {
				exerciseGroup.exercises.push.apply(exerciseGroup.exercises, exercises);
			});
		};
		
		$scope.openWorkoutModal = function() {
			var modalInstance = $modal.open({
				templateUrl: 'partials/user/workoutsuggestions.html',
				controller: 'WorkoutSuggestionController',
				resolve: {
					workoutData: function () {
						return userService.getWorkoutSuggestions();
					}
				}
			});

			modalInstance.result.then(function (workout) {
				$scope.workout = workout;
			});
		};


	});
	
	app.controller('WorkoutSuggestionController', function($scope, $filter, $modalInstance, ngTableParams, workoutData, toaster) {
		
		$scope.ok = function (workout) {
			$modalInstance.close(getWorkoutDto(workout));
		};
		
		$scope.cancel = function () {
			$modalInstance.dismiss('cancel');
		};
		
		var getWorkoutDto = function(workout) {
			var workoutDto = {};
			workoutDto.comment = workout.comment;
			workoutDto.date = workout.date;
			workoutDto.name = workout.name;
			workoutDto.wod = workout.wod;
			workoutDto.exerciseGroups = getExerciseGroupsDto(workout.exerciseGroups);
			return workoutDto;
		};
		
		var getExerciseGroupsDto = function(groups) {
			var groupsDto = [];
			angular.forEach(groups, function(group) {
				var groupDto = {};
				groupDto.name = group.name;
				groupDto.wod = group.wod;
				groupDto.exercises = getExercisesDto(group.exercises);
				groupsDto.push(groupDto);
			});
			return groupsDto;
		};
		
		var getExercisesDto = function(exercises) {
			var exercisesDto = [];
			angular.forEach(exercises, function(exercise) {
				var exerciseDto = {};
				exerciseDto.exerciseModel = exercise.exerciseModel;
				exercisesDto.push(exerciseDto);
			});
			return exercisesDto;
		};
		
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10, 
			sorting: {
				name : 'asc'
			}
		}, {
			total: 0,           // length of data
			getData: function($defer, params) {
				ui.util.table.prepareData($defer, $filter, params, workoutData.data);
			}
		});
		
		$scope.tableParams.settings().$loading = true;
	});
	

}());