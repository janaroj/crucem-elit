(function() {
	var app = angular.module('crucem-elit');
	app.controller('RegisterController', function($scope, $location,
			userService, base64, toaster) {
		$scope.success = null;
		$scope.error = null;
		$scope.doNotMatch = null;
		$scope.errorUserExists = null;
		$scope.register = function() {
			if ($scope.registerAccount.password != $scope.confirmPassword) {
				$scope.doNotMatch = "ERROR";
			} else {
				$scope.doNotMatch = null;
				$scope.registerAccount.passwordHash = $scope.registerAccount.password;
				delete $scope.registerAccount.password;
				userService.register($scope.registerAccount).then(function(data) {
					$location.url("/login");
					toaster.pop('success', 'Registration', 'Account registred successfully!');
				 });
			}
		}
	});

}());