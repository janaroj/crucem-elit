(function() {
	var app = angular.module('crucem-elit');
	
	app.controller('ContactsController', function($scope, $location, userService, toaster) {
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
		
		$scope.removeContact = function(id) {
			toaster.pop('error', 'Unimplemented', "This feature doesn't work yet")
//			userService.removeContact(id).then(function(data) {
//				toaster.pop('success', 'Contact', 'Contact removed successfully!');
//			});
		};
		
		$scope.getPicture = function(id, location, gender) {
			userService.getProfilePicture(id).then(function(result) {
				ui.util.image.addImage(location, result.data, gender);
			}, 
			function(result) {
				toaster.pop('error', 'Contact' , result.data.message);
			});
		};
		
	});

	app.controller('ContactController', function($scope, $routeParams, userService, toaster) {
		$scope.isChangeable = false;
		$scope.isChangeInProgress = false;
		$scope.init = function() {
			var userId;
			if (isNaN($routeParams.id)) {
				userId = $scope.user.id;
				userService.getProfile().then(function(result) {
					$scope.contact = result.data;
					$scope.isChangeable = true;
					userService.getProfilePicture(userId).then(function(result) {
						ui.util.image.addImage("#image", result.data, $scope.contact.gender);
					}, 
					function(result) {
						toaster.pop('error', 'Contact' , result.data.message);
					});
				}, function(result) {
					toaster.pop('error', 'Contact' , result.data.message);
				});
			}
			else {
				var userId = $routeParams.id;
				userService.getUserById($routeParams.id).then(function(result) {
				$scope.contact = result.data;
				userService.getProfilePicture(userId).then(function(result) {
					ui.util.image.addImage("#image",result.data, $scope.contact.gender);
				}, 
				function(result) {
					toaster.pop('error', 'Contact' , result.data.message);
				});
			}, function(result) {
				toaster.pop('error', 'Contact' , result.data.message);
			});
			}
		};
		
		$scope.isMyProfile = function() {
			if (!$scope.contact || !$scope.user) {
				return false;
			}
			return angular.equals($scope.user, $scope.contact);
		};
		
		$scope.changeProfile = function() {
			$scope.isChangeInProgress = true;
			$scope.isChangeable = false;
		};
		
		$scope.cancelProfile = function(){
			$("#form")[0].reset();
			
			$scope.isChangeInProgress = false;
			$scope.isChangeable = true;
		};
		
		$scope.updateProfile = function() {
			
		};
        
            $scope.open = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };

        $scope.format = 'dd-MM-yyyy';
        
        $scope.toggleMax = function() {
                $scope.maxDate = $scope.maxDate ? null : new Date();
              };
        $scope.toggleMax();

		
		 $scope.onFileSelect = function($files) {
			      userService.uploadProfilePicture($files[0]).then(function() {
			    	  toaster.pop('success', 'Contact', 'File uploaded successfully!');
			      }, function(result) {
			    	  toaster.pop('error', 'Upload', 'Uploading file failed');
			      });
		 };

	});
}());