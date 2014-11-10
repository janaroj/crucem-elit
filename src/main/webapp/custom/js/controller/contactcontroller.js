(function() {
	var app = angular.module('crucem-elit');

	app.controller('ContactsController', function($scope, $rootScope, $location, userService, toaster) {
		$scope.init = function() {
			userService.getContacts().then(function(result) {
				$scope.contacts = result.data;
			}, function(result) {
				toaster.pop('error', 'Contacts' , result.data.message);
			});
		};

		$scope.viewContact = function(id) {
			$location.path('/user/contacts/' + id);
		};
		
		$scope.removeFriend = function(friend) {
			userService.removeFriend(friend.id).then(
				function(data) {
					$rootScope.removeFriend(friend.id);
					if (!angular.equals(friend.gym, $scope.user.gym)) {
						$scope.contacts.splice( $scope.contacts.indexOf(friend), 1 );
					}
					toaster.pop('success', 'Friend', 'Friend removed successfully!');
				},
				function(data) {
					toaster.pop('error', 'Friend', data.result.message);
				}
			);
		};

		$scope.pictures = {};

		$scope.getPicture = function(id, gender) {
			userService.getProfilePicture(id).then(function(result) {
				$scope.pictures[id] = (result.data.type === "error") ? getDefaultImageSrc(gender) : "data:image/png;base64," + result.data;
			}, 
			function(result) {
				toaster.pop('error', 'Contact' , result.data.message);
			});
		};

		var getDefaultImageSrc = function(gender) {
			return (gender === 'FEMALE') ? '../../images/jane_doe_test.png' : '../../images/john_doe_test.png';
		};

	});

	app.controller('ContactController', function($scope, $routeParams, userService, toaster) {
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
					toaster.pop('error', 'Contact' , result.data.message);
				});
			}
			else {
				userId = $routeParams.id;
				userService.getUserById($routeParams.id).then(function(result) {
					$scope.contact = result.data;
					getProfileImage();
					$scope.isProfileLoaded = true;
				}, function(result) {
					toaster.pop('error', 'Contact' , result.data.message);
				});
			}

		};

		var getProfileImage = function() {
			userService.getProfilePicture($scope.contact.id).then(
					function(result) {
						$scope.imageSrc = (result.data.type === "error") ? getDefaultImageSrc() : "data:image/png;base64," + result.data;
					},  
					function(result) { toaster.pop('error', 'Contact' , result.data.message); }
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
			angular.copy($scope.user, $scope.contact);
		};

		$scope.toggleProfileChange = function() {
			$scope.isChangeInProgress = !$scope.isChangeInProgress;
			$scope.isChangeable = !$scope.isChangeable;
		};

		$scope.updateProfile = function() {
			userService.updateUser(createUserDTO()).then(function(result) {
				if (!/^\d{4}-\d{2}-\d{2}$/.test($scope.contact.dateOfBirth)) {
					$scope.contact.dateOfBirth = $.datepicker.formatDate("yy-mm-dd", $scope.contact.dateOfBirth); //HACK to correct DOB
				}
				angular.copy($scope.contact, $scope.user);
				$scope.toggleProfileChange();
				toaster.pop('success', 'Contact' , 'Updated successfully');
			}, 
			function(result) {
				toaster.pop('error', 'Contact' , result.data.message);
			});

		};

		var createUserDTO = function() {
			return userDTO = {
					firstName : $scope.contact.firstName,
					lastName : $scope.contact.lastName,
					weight : $scope.contact.weight,
					length : $scope.contact.length,
					dateOfBirth : $scope.contact.dateOfBirth,
					gender : $scope.contact.gender,
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
							toaster.pop('success', 'Contact', 'File uploaded successfully!');
						}, 
						function(result) {toaster.pop('error', 'Upload', 'Uploading file failed');}
				);
			}
		};

	});
}());