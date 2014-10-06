//Define a function scope, variables used inside it will NOT be globally visible.
(function() {

	var
	//the HTTP headers to be used by all requests
	httpHeaders,

	//the message to be shown to the user
	message,

	//Define the main module.
	app = angular.module('crucem-elit', [ 'ngRoute', 'ngResource', 'ngTable' ]);

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
		.when('/login', {
			controller: 'MainController',
			templateUrl: 'partials/login.html'
		})
		.when('/user/main', {
			controller: 'MainController',
			templateUrl: 'partials/user/main.html'
		})
		.when('/user/contacts', {
			controller: 'ContactController',
			templateUrl: 'partials/user/contacts.html'
		})
		.when('/user/gyms', {
			controller: 'GymsController',
			templateUrl: 'partials/user/gyms.html'
		})
		.otherwise({ redirectTo : "/"});

		//configure $http to catch message responses and show them
		$httpProvider.responseInterceptors.push(function($q) {
			var setMessage = function(response) {
				//if the response has a text and a type property, it is a message to be shown
				if (response.data.text && response.data.type) {
					message = {
						text : response.data.text,
						type : response.data.type,
						show : true
					};
				}
			};
			return function(promise) {
				return promise.then(
				//this is called after each successful server request
				function(response) {
					setMessage(response);
					return response;
				},
				//this is called after each unsuccessful server request
				function(response) {
					setMessage(response);
					return $q.reject(response);
				});
			};
		});

		//configure $http to show a login dialog whenever a 401 unauthorized response arrives
		$httpProvider.responseInterceptors.push(function($rootScope, $q) {
			return function(promise) {
				return promise.then(
				//success -> don't intercept
				function(response) {
					return response;
				},
				//error -> if 401 save the request and broadcast an event
				function(response) {
					if (response.status === 401) {
						var deferred = $q.defer(), req = {
							config : response.config,
							deferred : deferred
						};
						$rootScope.requests401.push(req);
						$rootScope.$broadcast('event:loginRequired');
						return deferred.promise;
					}
					return $q.reject(response);
				});
			};
		});
		httpHeaders = $httpProvider.defaults.headers;
	});

	app.run(function($rootScope, $http, base64, $location) {
		//make current message accessible to root scope and therefore all scopes
		$rootScope.message = function() {
			return message;
		};

		/**
		 * Holds all the requests which failed due to 401 response.
		 */
		$rootScope.requests401 = [];
		
		$rootScope.$on("$routeChangeStart", function() {
			$http.get('api/auth/authenticated/user')
			.success(function(data) {
				$rootScope.user = data;
			});
		});

		$rootScope.$on('event:loginRequired', function() {
			//TODO
		});

		/**
		 * On 'event:loginConfirmed', resend all the 401 requests.
		 */
		$rootScope.$on('event:loginConfirmed', function() {
			var i, requests = $rootScope.requests401, retry = function(req) {
				$http(req.config).then(function(response) {
					req.deferred.resolve(response);
				});
			};

			for (i = 0; i < requests.length; i += 1) {
				retry(requests[i]);
			}
			$rootScope.requests401 = [];
			
			$location.path('/user/main');
		});

		/**
		 * On 'event:loginRequest' send credentials to the server.
		 */
		$rootScope.$on('event:loginRequest',
				function(event, username, password) {
					httpHeaders.common['Authorization'] = 'Basic '
							+ base64.encode(username + ':' + password);
					$http.post('api/auth/authenticate').success(function() {
						$rootScope.$broadcast('event:loginConfirmed');
					});
				});

		/**
		 * On 'logoutRequest' invoke logout on the server
		 */
		$rootScope.$on('event:logoutRequest', function() {
			$http.get('j_spring_security_logout');
			httpHeaders.common['Authorization'] = null;
		});
	});

}());