(function() {
	var app = angular.module('crucem-elit');
	app.controller('RegisterController', function($scope, $location,
			userService, base64, toaster) {
		$scope.doNotMatch = null;
		$scope.register = function() {
			if ($scope.registerAccount.password != $scope.confirmPassword) {
				$scope.doNotMatch = "ERROR";
			} else {
				$scope.doNotMatch = null;
				$scope.registerAccount.passwordHash = $scope.registerAccount.password;

				userService.register($scope.registerAccount).then(function(result) {
					$rootScope.user = result.data;
					$location.url("/");
					toaster.pop('success', 'Registration', 'Account registred successfully!');
				}, function(result) {
					delete $scope.registerAccount.password;
					delete $scope.confirmPassword;
					toaster.pop('error', 'Registration', result.data.message);
				});

			}
		};
	});

}());