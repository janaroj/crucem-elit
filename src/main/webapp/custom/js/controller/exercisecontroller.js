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
	app.controller('ExerciseController', function($scope, exerciseService, exerciseTypeService, toaster, $modalInstance) {
		$scope.ok = function () {
			$modalInstance.close();
		};

		$scope.cancel = function () {
			$modalInstance.dismiss('cancel');
		};

		$scope.exercises = [{name:"Jooks",countTime:false,countWeight:false,countRepeats:false,comment:"Best exercise EU",clicked:false},
		                    {name:"Kükk",countTime:false,countWeight:true,countRepeats:true,comment:"Best exercise RU", clicked:false}];
		$scope.showExerciseDetails = function (exercise) {
			exercise.clicked = !exercise.clicked;
		}
		
		
	});

}());