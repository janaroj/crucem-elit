
app.controller('MainController', function($scope, $http, $location){
	$scope.authors = ["Janar", "Eerik", "Indrek", "Anneliis"];
	
	$scope.login = function() {
		$location.path('/login');
	}
});

app.controller('LoginController', function($scope, $http){
	
});