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
					gymService.getGyms().then(function(result) {
						gymData = result.data;
						ui.util.table.prepareData($defer, $filter, params, gymData);
					}, function(result) {
						toaster.pop('error', 'Gyms' , result.data.message);
					});
				}
				else {
					ui.util.table.prepareData($defer, $filter, params,gymData);
				}
			}
		});

		$scope.tableParams.settings().$loading = true;

		$scope.inGym = function(gym) {
			if (!$scope.user || !$scope.user.gym) {
				return false;
			}
			return angular.equals($scope.user.gym, gym);
		};

		$scope.joinGym = function(gym) {
			if (confirm("Are you sure you wish to join " + gym.name )) {
				userService.joinGym(gym.id).then(function() {
					$scope.user.gym = gym;
					toaster.pop('success', 'Gym', 'Joined gym successfully!');
				}, function(result) {
					toaster.pop('error', 'Gyms' , result.data.message);
				}) ;
			}
		};

		$scope.leaveGym = function(gym) {
			if (confirm("Are you sure you wish to leave from " + gym.name )) {
				userService.leaveGym().then(function() {
					$scope.user.gym = null;
					toaster.pop('success', 'Gym', 'Left from gym successfully!');
				}, function(result) {
					toaster.pop('error', 'Gym' , result.data.message);
				});
			}
		};

		$scope.viewGym = function(id) {
			$location.path('/user/gyms/' + id);
		};

	});

	app.controller('GymController', function($scope, $routeParams, gymService, toaster) {
		$scope.init = function() {
			gymService.getGymById($routeParams.id).then(function(result) {
				$scope.gym = result.data;
			}, function(result) {
				toaster.pop('error', 'Gym' , result.data.message);
			});

			gymService.getGymPicture($routeParams.id).then(function(result) {
				$scope.imageSrc = (!result.data) ? getDefaultImageSrc() : "data:image/png;base64," + result.data;
			});
		};

		var getDefaultImageSrc = function() {
			return '../../images/cf.jpg';
		};

		$scope.onFileSelect = function($files) {
			if ($files[0]) {
				gymService.uploadGymPicture($files[0], $routeParams.id).then(function(result) {
					$scope.imageSrc = "data:image/png;base64," + result.data;
					toaster.pop('success', 'Gym', 'File uploaded successfully!');
				}, function(result) {
					toaster.pop('error', 'Upload', 'Uploading file failed');
				});
			}
		};
	});

	app.controller('AdminGymController', function($scope, gymService, toaster) {

		$scope.createGym = function() {
			gymService.createGym($scope.gym).then(function(result) {
				toaster.pop('success', 'Gym' , 'Gym created successfully');
			}, 
			function(result) {
				toaster.pop('error', 'Gym' , result.data.message);
			});
		};

	});

}());