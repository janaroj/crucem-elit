(function() {
	var app = angular.module('crucem-elit');

	app.controller('AdminExerciseController', function($scope, exerciseService, exerciseTypeService, toaster) {

		$scope.init = function() {
			exerciseTypeService.getExerciseTypes().then(function(result) {
				$scope.types = result.data;
			},
			function(result) {
				toaster.pop('error', 'Exercise Types',  result.data.message);
			});
		};

		$scope.createExercise = function() {
			exerciseService.createExercise($scope.exercise).then(function(result) {
				toaster.pop('success', 'Exercise' , 'Exercise created successfully');
			}, 
			function(result) {
				toaster.pop('error', 'Exercise', result.data.message);
			});
		};

	});

}());