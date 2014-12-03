(function() {
	var app = angular.module('crucem-elit');

	app.controller('GymsController', function($scope, $location, $filter, gymService, userService, ngTableParams, toaster) {
		var gymData = null;
		var tempData = null;
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
						tempData = angular.copy(gymData);
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
			return $scope.user.gym.id === gym.id;
		};

		$scope.joinGym = function(gym) {
			if (confirm("Are you sure you wish to join " + gym.name + "? This will remove you from your current gym")) {
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
		
		$scope.newGym = function() {
			$location.path('/admin/gym');
		};

		$scope.deleteGym = function(gym) {
			if (confirm("Are you sure you wish to delete " + gym.name )) {
				gymService.deleteGym(gym.id).then(function() {
					gymData.splice( gymData.indexOf(gym), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', 'Gym' , 'Gym deleted successfully');
				}, 
				function(result) {
					toaster.pop('error', 'Gym', result.data.message);
				});
			}
		};
		
		$scope.updateGym = function(gym) {
			gymService.updateGym(gym).then(function(){
				toaster.pop('success', 'Gym' , 'Gym updated successfully');
				tempData[tempData.indexOf($filter("filter")(tempData, {id : gym.id}, true)[0])] = angular.copy(gym);
				gym.$edit = false;
			}, function(result) {
				toaster.pop('error', 'Gym', result.data.message);
				$scope.cancelEdit(gym);
			});
		};
		
		$scope.cancelEdit = function(gym) {
			var temp = $filter("filter")(tempData, {id : gym.id}, true)[0];
			temp.$edit = false;
			gymData[gymData.indexOf($filter("filter")(gymData, {id : gym.id}, true)[0])] = angular.copy(temp); //Temporary hack, do not try this at home
			$scope.tableParams.reload();
		};

	});

	app.controller('GymController', function($scope, $routeParams, $location, gymService, userService, toaster) {
		$scope.init = function() {
			gymService.getGymById($routeParams.id).then(function(result) {
				$scope.gym = result.data;
			}, function(result) {
				toaster.pop('error', 'Gym' , result.data.message);
			});

			gymService.getGymPicture($routeParams.id).then(function(result) {
				$scope.imageSrc = (!result.data) ? getDefaultImageSrc() : "data:image/png;base64," + result.data;
			}, function(result) {
				toaster.pop('error', 'Gym Picture' , result.data.message);
			});
			
			gymService.getComments($routeParams.id).then(function(result) {
				$scope.comments = result.data;
			}, function(result) {
				toaster.pop('error', 'Gym comments' , result.data.message);
			});
		};
		
		$scope.pictures = {};

		$scope.getPicture = function(id, gender) {
			userService.getProfilePicture(id).then(function(result) {
				$scope.pictures[id] = (!result.data) ? getDefaultUserImageSrc(gender) : "data:image/png;base64," + result.data;
			}, 
			function(result) {
				toaster.pop('error', 'Contact Picture' , result.data.message);
			});
		};

		var getDefaultUserImageSrc = function(gender) {
			return (gender === 'FEMALE') ? '../../images/jane_doe_test.png' : '../../images/john_doe_test.png';
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
		
		$scope.createComment = function() {
			$scope.comment.gym = $scope.gym;
			userService.createComment($scope.comment).then(function(result) {
				toaster.pop('success', 'Comment' , 'Comment created successfully');
			}, 
			function(result) {
				toaster.pop('error', 'Comment' , result.data.message);
			});
		};
		
		$scope.viewUser = function(id) {
			$location.path('/user/contacts/' + id);
		};
	});

	app.controller('AdminGymController', function($scope, $location, gymService, toaster) {

		$scope.createGym = function() {
			gymService.createGym($scope.gym).then(function(result) {
				$location.path('/user/gyms');
				toaster.pop('success', 'Gym' , 'Gym created successfully');
			}, 
			function(result) {
				toaster.pop('error', 'Gym' , result.data.message);
			});
		};

	});

}());