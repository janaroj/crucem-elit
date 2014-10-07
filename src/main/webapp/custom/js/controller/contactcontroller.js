(function() {
	var app = angular.module('crucem-elit');

	app.controller('ContactsController', function($scope, userService) {
		$scope.init = function() {
			userService.getContacts().then(function(data) {
				$scope.contacts = data;
			});
		}
		
		$scope.viewContact = function(id) {
			$location.path('/user/users/' + id);
		};
		
	});

	app.controller('ContactController', function($scope, $routeParams, userService) {
		$scope.init = function() {
			if (true) {
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