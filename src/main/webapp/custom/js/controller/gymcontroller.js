(function() {
	var app = angular.module('crucem-elit');
	
	app.controller('GymsController', function($scope, $location, $filter, gymService, ngTableParams) {
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
			 return angular.equals($scope.user.gym, gym);
		 }
		 
		 $scope.joinGym = function(id) {
			 alert("Not yet implemented");
		 }
		 
		 $scope.viewGym = function(id) {
				$location.path('/user/gyms/' + id);
			};
		    
	});


	app.controller('GymController', function($scope, $routeParams, gymService) {
		$scope.init = function() {
			gymService.getGymById($routeParams.id).then(function(data) {
				$scope.user = data;
			});
		}
	});

}());