//Define a function scope, variables used inside it will NOT be globally visible.
(function() {

	app = angular.module('crucem-elit', [ 'ui.bootstrap','ngRoute', 'ngResource', 'ngTable', 'ngCookies','angularFileUpload', 'ngAnimate', 'toaster' ]);

	//user operations
	app.run(function ($rootScope, $http, $location, $cookieStore, authService, toaster) {

		$rootScope.hasRole = function (role) {

			if ($rootScope.user === undefined || $rootScope.user.authorities) {
				return false;
			}

			for (var i = 0; i < $rootScope.user.authorities.length; i++) {
				if ($rootScope.user.authorities[i].authority == role)
					return true;
			}
			return false;
		};

		$rootScope.getTranslation = function(key) {
			return $.i18n.prop(key);
		};
		
		$rootScope.login = function(username, password) {
			authService.authenticate($.param({username: username, password: password}))
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

		$rootScope.logout = function () {
			delete $rootScope.user;
			delete $http.defaults.headers.common['X-Auth-Token'];
			$cookieStore.remove('user');
			if ($location.url() !== "/register") {
				$location.url('/');
			}
		};
	});
	
	//monitoring route changes
	app.run(function ($rootScope, $http, $location, $cookieStore, $timeout) {

		$rootScope.$on('$locationChangeStart', function (event, next, current) {
			var user = $cookieStore.get('user');
			if (user !== undefined) {
				$http.defaults.headers.common['X-Auth-Token'] = user.token;
				if ($location.url() === "/") {
					event.preventDefault();
					$timeout(function(){$location.path("/user/main");});
				}
			}
		});
	});
}());