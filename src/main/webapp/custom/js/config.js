(function() {

	app = angular.module('crucem-elit');

	app.config(function($routeProvider, $httpProvider) {
		//configure the routing of ng-view
		$routeProvider
		.when('/', {
			controller: 'MainController',
			templateUrl: 'partials/home.html'
		})
		.when('/register', {
			controller: 'RegisterController',
			templateUrl: 'partials/register.html'
		})
		.when('/user/main', {
			controller: 'MainController',
			templateUrl: 'partials/user/main.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/contacts', {
			controller: 'ContactsController',
			templateUrl: 'partials/user/contacts.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/gyms', {
			controller: 'GymsController',
			templateUrl: 'partials/user/gyms.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/gyms/:id', {
			controller: 'GymController',
			templateUrl: 'partials/user/gym.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/users/:id', {
			controller: 'ContactController',
			templateUrl: 'partials/user/contact.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/invite', {
			controller: 'InviteController',
			templateUrl: 'partials/user/invite.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.otherwise({ redirectTo : "/"});
		
		/* Intercept http errors */
		var interceptor = function ($rootScope, $q, $location) {

	        function success(response) {
	            return response;
	        };

	        function error(response) {
	        	
	            var status = response.status;
	            var config = response.config;
	            var method = config.method;
	            var url = config.url;

	            if (status == 401) {
	            	$rootScope.redirectUrl = $location.url();
	            	$rootScope.redirectStatus = 401;
	            	$rootScope.logout();
	            	if ($location.url() !== "/register") {
	            		$location.url('/');
	            	}
	            } else if (status == 403) {
	            	$location.url('/403');
	            } else if (status == 500) {
	            	$location.url('/500');
	            } else{
	            	//skip others
	            }
	            
	            return $q.reject(response);
	        };

	        return function (promise) {
	            return promise.then(success, error);
	        };
	    };
	    
	    $httpProvider.interceptors.push(interceptor);
	    
	});
	
}());