(function() {
	var app = angular.module('crucem-elit');

	app.controller('WorkoutsController', function($scope, $location, $filter, $timeout, userService, ngTableParams, toaster) {
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
					sorting: {'date' : 'desc'}
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
				toaster.pop('error', 'Workouts' , result.data.message);
			});
		};

		$scope.newWorkout = function() {
			$location.path('/user/workout/add');
		};

	});
	
	app.controller('WorkoutController', function($scope, $location, $timeout, userService, toaster) {
		$scope.showModal = false;
		$scope.open = function($event) {
			$event.preventDefault();
			$event.stopPropagation();
			$scope.opened = true;
		};

		$scope.format = 'yyyy-MM-dd';
		
		$scope.init = function() {
			
		};
		$scope.workoutgroups = [];
		$scope.newExerciseGroup = function(){
			$scope.workoutgroups.push({name:"", isWod:false});
		};
		$scope.removeExerciseGroup = function(group) {
			$scope.workoutgroups.splice($scope.workoutgroups.indexOf(group),1);
		};
		
		$scope.exercises = [{name:"test1"}];
		$scope.newExercise = function(){
			$scope.exercises.push({name:""});
		};
		
		$scope.removeExercise = function(exercise){
			$scope.exercises.splice($scope.exercises.indexOf(exercise),1);
		};
		
		
	});
	
	
}());