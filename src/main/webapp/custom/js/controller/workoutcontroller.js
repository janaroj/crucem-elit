(function() {
	var app = angular.module('crucem-elit');

	app.controller('WorkoutController', function($scope, $location, $filter, $timeout, userService, ngTableParams, toaster) {
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
	});
}());