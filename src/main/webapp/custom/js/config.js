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
		.when('/user/records', {
			controller: 'RecordsController',
			templateUrl: 'partials/user/records.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/contacts/:id', {
			controller: 'ContactController',
			templateUrl: 'partials/user/contact.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/invite', {
			controller: 'InviteController',
			templateUrl: 'partials/user/invite.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/forgot', {
			controller: 'MainController',
			templateUrl: 'partials/forgot.html'
		})
		.when('/user/workouts', {
			controller: 'WorkoutsController',
			templateUrl: 'partials/user/workouts.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/workout/add', {
			controller: 'WorkoutController',
			templateUrl: 'partials/user/workout.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/admin/gyms', {
			controller: 'GymsController',
			templateUrl: 'partials/user/gyms.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/admin/gym', {
			controller: 'AdminGymController',
			templateUrl: 'partials/admin/gym.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/admin/exercises', {
			controller: 'AdminExercisesController',
			templateUrl: 'partials/admin/exercises.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/admin/exercise', {
			controller: 'AdminExerciseController',
			templateUrl: 'partials/admin/exercise.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/admin/exercisetypes', {
			controller: 'AdminExerciseTypesController',
			templateUrl: 'partials/admin/exercisetypes.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/admin/exercisetype', {
			controller: 'AdminExerciseTypeController',
			templateUrl: 'partials/admin/exercisetype.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/admin/users', {
			controller: 'AdminUsersController',
			templateUrl: 'partials/admin/users.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.when('/user/workout/view/:id', {
			controller: 'RecordController',
			templateUrl: 'partials/user/fillworkout.html',
			resolve: {authentication : function(CheckAuthentication) {return CheckAuthentication();}}
		})
		.otherwise({ redirectTo : "/"});
		
	    $httpProvider.interceptors.push('interceptor');
	    
	}]);
	
}());