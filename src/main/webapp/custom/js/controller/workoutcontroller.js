(function() {
	var app = angular.module('crucem-elit');
	
	app.controller('WorkoutController', function($scope, $location, $filter, workoutService, ngTableParams, toaster) {
		var workoutData = null;
		 $scope.tableParams = new ngTableParams({
		        page: 1,            // show first page
		        count: 10,          // count per page
		        sorting: {
		        }
		    }, {
		        total: 0,           // length of data
		        getData: function($defer, params) {
		        	if (workoutData===null) {
						workoutService.getWorkouts($scope.tableParams, $defer).then(function(result) {
							workoutData = result.data;
							ui.util.table.prepareData($defer, $filter, params, workoutData);
						}, function(result) {
							toaster.pop('error', 'Workouts' , result.data.message);
						});
					}
					else {
						ui.util.table.prepareData($defer, $filter, params,workoutData);
						}
		        }
		    });
		 
	});

}());