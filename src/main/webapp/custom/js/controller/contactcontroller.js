(function() {
	var app = angular.module('crucem-elit');

	app.controller('ContactsController', function($scope, $location, userService, toaster) {
		$scope.init = function() {
			userService.getContacts().then(function(data) {
				$scope.contacts = data;
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
		
	});

	app.controller('ContactController', function($scope, $routeParams, userService) {
		$scope.init = function() {
			if (isNaN($routeParams.id)) {
				userService.getProfile().then(function(data) {
					$scope.user = data;
				});
			}
			else {userService.getUserById($routeParams.id).then(function(data) {
				$scope.user = data;
			});
			}
		}
	});
}());