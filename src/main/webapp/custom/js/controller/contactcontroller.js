(function() {
	var app = angular.module('crucem-elit');

	app.controller('ContactsController', function($scope, userService) {
		$scope.init = function() {
			userService.getContacts().then(function(data) {
				$scope.contacts = data;
			});
		}
	});

	app.controller('ContactController', function($scope, $location) {
		
	});
}());