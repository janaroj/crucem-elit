(function() {
	var app = angular.module('crucem-elit');

	app.controller('GymsController', function($scope, $rootScope, $location, $filter, gymService, userService, ngTableParams, toaster) {
		var gymData = null;
		var tempData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10,          // count per page
			sorting: {
				name : 'asc'
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
						toaster.pop('error', $rootScope.getTranslation('gyms'), result.data.message);
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
			if (confirm($rootScope.getTranslation('join.new.gym.confirm') + " " + gym.name)) {
				userService.joinGym(gym.id).then(function() {
					$scope.user.gym = gym;
					toaster.pop('success', $rootScope.getTranslation('gyms'), $rootScope.getTranslation('gym.joined.successfully'));
				}, function(result) {
					toaster.pop('error', $rootScope.getTranslation('gyms'), result.data.message);
				}) ;
			}
		};

		$scope.leaveGym = function(gym) {
			if (confirm($rootScope.getTranslation('leave.gym.confirm') + " " + gym.name )) {
				userService.leaveGym().then(function() {
					$scope.user.gym = null;
					toaster.pop('success', $rootScope.getTranslation('gyms'), $rootScope.getTranslation('gym.left.successfully'));
				}, function(result) {
					toaster.pop('error', $rootScope.getTranslation('gyms'), result.data.message);
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
			if (confirm($rootScope.getTranslation('delete.gym.confirm')+ " " + gym.name )) {
				gymService.deleteGym(gym.id).then(function() {
					gymData.splice( gymData.indexOf(gym), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', $rootScope.getTranslation('gyms'), $rootScope.getTranslation('gym.deleted.successfully'));
				}, 
				function(result) {
					toaster.pop('error', $rootScope.getTranslation('gyms'), result.data.message);
				});
			}
		};
		
		$scope.updateGym = function(gym) {
			gymService.updateGym(gym).then(function(){
				toaster.pop('success',  $rootScope.getTranslation('gyms'), $rootScope.getTranslation('gym.updated.successfully'));
				tempData[tempData.indexOf($filter("filter")(tempData, {id : gym.id}, true)[0])] = angular.copy(gym);
				gym.$edit = false;
			}, function(result) {
				toaster.pop('error',  $rootScope.getTranslation('gyms'), result.data.message);
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

	app.controller('GymController', function($scope, $rootScope, $routeParams, $location, gymService, userService, toaster) {
		$scope.init = function() {
			gymService.getGymById($routeParams.id).then(function(result) {
				$scope.gym = result.data;
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('gyms'), result.data.message);
			});

			gymService.getGymPicture($routeParams.id).then(function(result) {
				$scope.imageSrc = (!result.data) ? getDefaultImageSrc() : "data:image/png;base64," + result.data;
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('gym.picture'), result.data.message);
			});
			
			gymService.getComments($routeParams.id).then(function(result) {
				$scope.commentQuantity = 5;
				$scope.comments = result.data;
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('gym.comments'), result.data.message);
			});
		};
		
		$scope.showMoreComments = function() {
			$scope.commentQuantity += $scope.hasMoreComments() ? 5 : 0;
		};
		
		$scope.hasMoreComments = function() {
			if (!$scope.comments) {
				return false;
			}
			return $scope.comments.length > $scope.commentQuantity;
		};
		
		$scope.pictures = {};

		$scope.getPicture = function(id, gender) {
			if (!$scope.pictures[id]) {
				$scope.pictures[id] = getDefaultUserImageSrc(gender);
				userService.getProfilePicture(id).then(function(result) {
					if (result.data) {
						$scope.pictures[id] = "data:image/png;base64," + result.data;
					}
				}, 
				function(result) {
					toaster.pop('error', $rootScope.getTranslation('contact.picture'), result.data.message);
				});
			}
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
					toaster.pop('success', $rootScope.getTranslation('gym'), $rootScope.getTranslation('file.uploaded.successfully'));
				}, function(result) {
					toaster.pop('error', $rootScope.getTranslation('upload'), $rootScope.getTranslation('file.upload.failed'));
				});
			}
		};
		
		$scope.refreshComments = function() {
			gymService.getComments($routeParams.id).then(function(result) {
				$scope.comments = result.data;
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('gym.comments'), result.data.message);
			});
		};
		
		$scope.createComment = function() {
			$scope.comment.gym = $scope.gym;
			userService.createComment($scope.comment).then(function(result) {
				$scope.comment.id = result.data;
				$scope.comment.user = $scope.user;
				$scope.comment.date = Date.parse(new Date());
				$scope.comments.push($scope.comment);
				$scope.comment = null;
				toaster.pop('success', $rootScope.getTranslation('comment'), $rootScope.getTranslation('comment.added.successfully'));
			}, 
			function(result) {
				toaster.pop('error', $rootScope.getTranslation('comment'), result.data.message);
			});
		};
		
		$scope.deleteComment = function(comment) {
			if (confirm($rootScope.getTranslation('delete.comment.confirmation'))) {
				userService.deleteComment(comment.id).then(function(result) {
					$scope.comments.splice( $scope.comments.indexOf(comment), 1 );
					toaster.pop('success', $rootScope.getTranslation('comment'), $rootScope.getTranslation('comment.deleted.successfully'));
				}, 
				function(result) {
					toaster.pop('error', $rootScope.getTranslation('comment'), result.data.message);
				});
			}
		};
		
		$scope.viewUser = function(id) {
			$location.path('/user/contacts/' + id);
		};
	});

	app.controller('AdminGymController', function($scope, $rootScope, $location, gymService, toaster) {

		$scope.createGym = function() {
			gymService.createGym($scope.gym).then(function(result) {
				$location.path('/user/gyms');
				toaster.pop('success', $rootScope.getTranslation('gym'), $rootScope.getTranslation('gym.created.successfully'));
			}, 
			function(result) {
				toaster.pop('error', $rootScope.getTranslation('gym'), result.data.message);
			});
		};

	});

}());