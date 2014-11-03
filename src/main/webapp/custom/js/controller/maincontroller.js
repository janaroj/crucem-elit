(function() {
	var app = angular.module('crucem-elit');
	app.controller('MainController', function($scope, $rootScope, i18n, $location, $http, $cookieStore, emailService, authService, toaster) {
		
		$scope.availableLanguages = ["ee", "en"];
		
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
			authService.authenticate($.param({username: $scope.username, password: $scope.password}))
			.then(function(result){
				$rootScope.user = result.data;
				$http.defaults.headers.common['X-Auth-Token'] = result.data.token;
				$cookieStore.put('user', result.data);
				
				if($rootScope.redirectUrl != null) {
					$location.url($rootScope.redirectUrl);
				}
				else {
					$location.path("/user/main");
				}
				
				$rootScope.redirectUrl = null;
            	$rootScope.redirectStatus = null;
			},
			function(result) {
				if($rootScope.redirectStatus == 401) {
					$location.url($rootScope.redirectUrl);
					toaster.pop('error', 'Authentication', result.data.message);
				}
			});
		};
		
		$scope.forgot = function() {
			emailService.sendNewPassword({email : $scope.email}).then(function() {
				$scope.email = null;
				toaster.pop('success', 'Forgot password', 'Email sent successfully!');
			}, function(result) {
				toaster.pop('error', 'Forgot password', result.data.message);
			});
		};	
	});
	
}());