(function() {
	var app = angular.module('crucem-elit');
	app.controller('MainController', function($scope, $rootScope, i18n, $location, $http, $cookieStore, loginService, toaster) {
		
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
			loginService.authenticate($.param({username: $scope.username, password: $scope.password}))
			.success(function(user) {
				$rootScope.user = user;
				$http.defaults.headers.common['X-Auth-Token'] = user.token;
				$cookieStore.put('user', user);
				
				if($rootScope.redirectUrl != null && $rootScope.redirectUrl.indexOf('/login') == -1) {
					$location.url($rootScope.redirectUrl);
				}
				else {
					$location.path("/user/main");
				}
				
				$rootScope.redirectUrl = null;
            	$rootScope.redirectStatus = null;
			
			})
			.error(function(err) {
				if($rootScope.redirectStatus == 401) {
					$location.url($rootScope.redirectUrl);
					toaster.pop('error', 'Authentication', 'Authentication failed!');
				}
			});
		};
		
	});
	
}());