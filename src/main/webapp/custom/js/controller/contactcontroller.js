(function() {
	var app = angular.module('crucem-elit');

	app.controller('ContactsController', function($scope, $location, userService, toaster) {
		$scope.init = function() {
			userService.getContacts().then(function(result) {
				$scope.contacts = result.data;
			}, function(result) {
				toaster.pop('error', 'Contacts' , result.data.message);
			});
		}
		
		$scope.viewContact = function(id) {
			$location.path('/user/users/' + id);
		};
		
		$scope.removeContact = function(id) {
			toaster.pop('error', 'Unimplemented', "This feature doesn't work yet")
//			userService.removeContact(id).then(function(data) {
//				toaster.pop('success', 'Contact', 'Contact removed successfully!');
//			});
		};
		
		$scope.getPicture = function(id, sex) {
			userService.getProfilePicture(id).then(function(result) {
				ui.util.image.addImage(result.data, sex);
			}, 
			function(result) {
				toaster.pop('error', 'Contact' , result.data.message);
			});
		}
		
	});

	app.controller('ContactController', function($scope, $routeParams, userService, toaster) {
		$scope.init = function() {
			var userId;
			if (isNaN($routeParams.id)) {
				userId = $scope.user.id;
				userService.getProfile().then(function(result) {
					$scope.contact = result.data;
				}, function(result) {
					toaster.pop('error', 'Contact' , result.data.message);
				});
			}
			else {
				var userId = $routeParams.id;
				userService.getUserById($routeParams.id).then(function(result) {
				$scope.contact = result.data;
			}, function(result) {
				toaster.pop('error', 'Contact' , result.data.message);
			});
			}
			
			userService.getProfilePicture(userId).then(function(result) {
				ui.util.image.addImage(result.data, $scope.contact.sex);
			}, 
			function(result) {
				toaster.pop('error', 'Contact' , result.data.message);
			});
		}
		
		$scope.isMyProfile = function() {
			if (!$scope.contact || !$scope.user) {
				return false;
			}
			return angular.equals($scope.user, $scope.contact);
		}
		
		 $scope.onFileSelect = function($files) {
			      userService.uploadProfilePicture($files[0]).then(function() {
			    	  toaster.pop('success', 'Contact', 'File uploaded successfully!');
			      }, function(result) {
			    	  toaster.pop('error', 'Upload', 'Uploading file failed');
			      });
		 };

	});
}());