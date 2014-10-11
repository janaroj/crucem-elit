//Define a function scope, variables used inside it will NOT be globally visible.
(function() {

    app = angular.module('crucem-elit', [ 'ngRoute', 'ngResource', 'ngTable', 'ngCookies','angularFileUpload', 'ngAnimate', 'toaster' ]);
	
	 //user operations
    app.run(function ($rootScope, $http, $location, $cookieStore) {

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

        $rootScope.logout = function () {
            delete $rootScope.user;
            delete $http.defaults.headers.common['X-Auth-Token'];
            $cookieStore.remove('user');
        };

    })
    //monitoring route changes
    app.run(function ($route, $rootScope, $http, $location, $cookieStore) {

        $rootScope.$on('$routeChangeStart', function (event, next, current) {
            var user = $cookieStore.get('user');
            if (user !== undefined) {
            	$http.defaults.headers.common['X-Auth-Token'] = user.token;
                if ($location.url() === "/") {
                	$location.url("/user/main");
                }
            }
        });
    });
}());