(function() {
	var app = angular.module('crucem-elit');

	app.controller('ContactsController', function($scope, $rootScope, $location, userService, toaster) {
		$scope.init = function() {
			userService.getContacts().then(function(result) {
				$scope.contacts = result.data;
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('contacts'), result.data.message);
			});
		};

		$scope.viewContact = function(id) {
			$location.path('/user/contacts/' + id);
		};
		
		$scope.viewGym = function(id) {
			$location.path('/user/gyms/' + id);
		};
		
		$scope.removeFriend = function(friend) {
			if (confirm($rootScope.getTranslation('remove.from.friends.list')  + " " + friend.name)) {
				userService.removeFriend(friend.id).then(
					function(data) {
						$rootScope.removeFriend(friend.id);
						if (friend.gym.id !== $scope.user.gym.id) {
							$scope.contacts.splice( $scope.contacts.indexOf(friend), 1 );
						}
						toaster.pop('success', $rootScope.getTranslation('friend'), $rootScope.getTranslation('friend.removed.successfully'));
					},
					function(data) {
						toaster.pop('error', $rootScope.getTranslation('friend'), data.result.message);
					}
				);
			}
		};

		$scope.pictures = {};

		$scope.getPicture = function(id, gender) {
			userService.getProfilePicture(id).then(function(result) {
				$scope.pictures[id] = (!result.data) ? getDefaultImageSrc(gender) : "data:image/png;base64," + result.data;
			}, 
			function(result) {
				toaster.pop('error', $rootScope.getTranslation('contact'), result.data.message);
			});
		};

		var getDefaultImageSrc = function(gender) {
			return (gender === 'FEMALE') ? '../../images/jane_doe_test.png' : '../../images/john_doe_test.png';
		};

	});

	app.controller('ContactController', function($scope, $rootScope, $routeParams, $location, userService, toaster) {
		$scope.isChangeable = false;
		$scope.isChangeInProgress = false;
		$scope.isProfileLoaded = false;

		$scope.init = function() {
			if (isNaN($routeParams.id)) {
				userId = $scope.user.id;
				userService.getProfile().then(function(result) {
					$scope.contact = result.data;
					$scope.isChangeable = true;
					getProfileImage();
					$scope.isProfileLoaded = true;
				}, function(result) {
					toaster.pop('error', $rootScope.getTranslation('contact'), result.data.message);
				});
			}
			else {
				userId = $routeParams.id;
				userService.getUserById($routeParams.id).then(function(result) {
					$scope.contact = result.data;
					getProfileImage();
					$scope.isProfileLoaded = true;
				}, function(result) {
					toaster.pop('error', $rootScope.getTranslation('contact'), result.data.message);
				});
			}

		};

		var getProfileImage = function() {
			userService.getProfilePicture($scope.contact.id).then(
					function(result) {
						$scope.imageSrc = (!result.data) ? getDefaultImageSrc() : "data:image/png;base64," + result.data;
					},  
					function(result) { toaster.pop('error', $rootScope.getTranslation('contact'), result.data.message); }
			);
		};

		var getDefaultImageSrc = function() {
			return ($scope.contact.gender === 'FEMALE') ? '../../images/jane_doe_test.png' : '../../images/john_doe_test.png';
		};
		
		$scope.isMyProfile = function() {
			if (!$scope.contact || !$scope.user) {
				return false;
			}
			return $scope.user.id === $scope.contact.id;
		};

		$scope.cancelProfile = function(){
			$scope.toggleProfileChange();
			angular.extend($scope.user, $scope.contact);
		};

		$scope.toggleProfileChange = function() {
			$scope.isChangeInProgress = !$scope.isChangeInProgress;
			$scope.isChangeable = !$scope.isChangeable;
		};

		$scope.updateProfile = function() {
			userService.updateUser(createUserDTO($scope.contact)).then(function(result) {
				if (!/^\d{4}-\d{2}-\d{2}$/.test($scope.contact.dateOfBirth)) {
					$scope.contact.dateOfBirth = $.datepicker.formatDate("yy-mm-dd", $scope.contact.dateOfBirth); //HACK to correct DOB
				}
				angular.extend(createUserDTO($scope.contact), $scope.user);
				$scope.toggleProfileChange();
				toaster.pop('success', $rootScope.getTranslation('contact'), $rootScope.getTranslation('updated.successfully'));
			}, 
			function(result) {
				toaster.pop('error', $rootScope.getTranslation('contact'), result.data.message);
			});

		};

		var createUserDTO = function(user) {
			return userDTO = {
					firstName : user.firstName,
					lastName : user.lastName,
					weight : user.weight,
					length : user.length,
					dateOfBirth : user.dateOfBirth,
					gender : user.gender,
			};
		};

		$scope.open = function($event) {
			$event.preventDefault();
			$event.stopPropagation();

			$scope.opened = true;
		};

		$scope.format = 'yyyy-MM-dd';

		$scope.toggleMax = function() {
			$scope.maxDate = $scope.maxDate ? null : new Date();
		};
		$scope.toggleMax();

		$scope.onFileSelect = function($files) {
			if ($files[0]) {
				userService.uploadProfilePicture($files[0]).then(
						function(result) {
							$scope.imageSrc = "data:image/png;base64," + result.data;
							toaster.pop('success', $rootScope.getTranslation('contact'), $rootScope.getTranslation('file.uploaded.successfully'));
						}, 
						function(result) {toaster.pop('error', $rootScope.getTranslation('upload'), $rootScope.getTranslation('file.upload.failed'));}
				);
			}
		};
		
		$scope.viewGym = function(id) {
			$location.path('/user/gyms/' + id);
		};
		
		$scope.removeFriend = function(friend) {
			if (confirm($rootScope.getTranslation('remove.from.friends.list')  + " " + friend.name)) {
				userService.removeFriend(friend.id).then(
					function(data) {
						$rootScope.removeFriend(friend.id);
						toaster.pop('success', $rootScope.getTranslation('friend'), $rootScope.getTranslation('friend.removed.successfully'));
					},
					function(data) {
						toaster.pop('error', $rootScope.getTranslation('friend'), data.result.message);
					}
				);
			}
		};

	});
}());