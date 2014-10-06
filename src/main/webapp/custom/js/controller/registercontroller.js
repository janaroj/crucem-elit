(function() {
	var app = angular.module('crucem-elit');
	app.controller('RegisterController', function($scope, $location,
			userService, base64) {
		$scope.success = null;
		$scope.error = null;
		$scope.doNotMatch = null;
		$scope.errorUserExists = null;
		$scope.register = function() {
			if ($scope.registerAccount.password != $scope.confirmPassword) {
				$scope.doNotMatch = "ERROR";
			} else {
				$scope.doNotMatch = null;
				$scope.registerAccount.passwordHash = base64
						.encode($scope.registerAccount.password);
				delete $scope.registerAccount.password;
				userService.register($scope.registerAccount);
				$location.path('/login');
			}
		}
	});

}());