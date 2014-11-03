(function() {

	app = angular.module('crucem-elit');

	app.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
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
		.when('/user/workouts', {
			controller: 'WorkoutController',
			templateUrl: 'partials/user/workouts.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.otherwise({ redirectTo : "/"});
		
	    $httpProvider.interceptors.push('interceptor');
	    
	}]);
	
}());