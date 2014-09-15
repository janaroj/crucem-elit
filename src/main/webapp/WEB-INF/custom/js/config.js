var httpHeaders;

app.config([ '$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {

	// ======= router configuration
	$routeProvider
		.when('/login', {
			controller: 'LoginController',
			templateUrl: 'html/partials/login.html'
		})
		.when('/index', {
			controller: 'MainController',
			templateUrl: 'html/template/index.html'
		})
		.otherwise({ redirectTo :  "/index"});
	
	
    httpHeaders = $httpProvider.defaults.headers;
}]);