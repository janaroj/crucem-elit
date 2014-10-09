(function() {
	var app = angular.module('crucem-elit');
	
	app.controller('GymsController', function($scope, $location, $filter, gymService, userService, ngTableParams, toaster) {
		var gymData = null;
		 $scope.tableParams = new ngTableParams({
		        page: 1,            // show first page
		        count: 10,          // count per page
		        sorting: {
		        }
		    }, {
		        total: 0,           // length of data
		        getData: function($defer, params) {
		        	if (gymData===null) {
						gymService.getGyms($scope.tableParams, $defer).then(function(data) {
							gymData = data;
							ui.util.table.prepareData($defer, $filter, params, gymData);
						});
					}
					else {
						ui.util.table.prepareData($defer, $filter, params,gymData);
						}
		        }
		    });
		 
		 $scope.inGym = function(gym) {
			 if (!$scope.user || !$scope.user.gym) {
				 return false;
			 }
			 return angular.equals($scope.user.gym, gym);
		 }
		 
		 $scope.joinGym = function(gym) {
			 if (confirm("Are you sure you wish to join " + gym.name )) {
				 userService.joinGym(gym.id).then(function(data) {
					 $scope.user.gym = gym;
					 toaster.pop('success', 'Gym', 'Joined gym successfully!');
				 }) ;
			 }
		 }
		 
		 $scope.leaveGym = function(gym) {
			 if (confirm("Are you sure you wish to leave from " + gym.name )) {
				 userService.leaveGym().then(function(data) {
					$scope.user.gym = null;
					toaster.pop('success', 'Gym', 'Left from gym successfully!');
				 });
			 }
		 }
		 
		 $scope.viewGym = function(id) {
				$location.path('/user/gyms/' + id);
			};
		    
	});


	app.controller('GymController', function($scope, $routeParams, gymService) {
		$scope.init = function() {
			gymService.getGymById($routeParams.id).then(function(data) {
				$scope.gym = data;
			});
		}
	});

}());