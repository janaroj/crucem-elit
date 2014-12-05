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
						$scope.doneTableLoading = false;
					}
				});
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('workouts'), result.data.message);
			});
		};
		
		$scope.fillWorkout = function(workout) {
			$location.path('/user/workout/fill/' + workout.id);
		}

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
			for (var i = 0; i < $scope.workout.exerciseGroups.length; i++) {
				if ( $scope.workout.exerciseGroups[i].wod) {
					return true;
				}
			}
			return false;
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

			modalInstance.result.then(
			function (exercises) {
				exerciseGroup.exercises.push.apply(exerciseGroup.exercises, exercises);
			});
		};


	});


}());