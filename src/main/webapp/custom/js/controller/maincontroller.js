(function() {
	var app = angular.module('crucem-elit');
	app.controller('MainController', function($scope, $rootScope, i18n, $location) {
		
		$scope.init = function() {
		}

		$scope.language = function() {
			return i18n.language;
		};
		$scope.setLanguage = function(lang) {
			i18n.setLanguage(lang);
		};
		$scope.activeWhen = function(value) {
			return value ? 'active' : '';
		};

		$scope.path = function() {
			return $location.url();
		};
		
		$scope.login = function() {
			$scope.$emit('event:loginRequest', $scope.username,$scope.password);
		};
		$scope.logout = function() {
			$rootScope.user = null;
			$scope.username = $scope.password = null;
			$scope.$emit('event:logoutRequest');
			$location.path('/index');
		};
	});
	
}());