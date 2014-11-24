(function() {
	var app = angular.module('crucem-elit');

	app.controller('AdminExerciseTypesController', function($scope, $filter, $location, exerciseTypeService, ngTableParams, toaster) {

		var exerciseTypeData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10,          // count per page
			sorting: {
				name : 'asc'
			}
		}, {
			total: 0,           // length of data
			getData: function($defer, params) {
				if (exerciseTypeData===null) {
					exerciseTypeService.getExerciseTypes().then(function(result) {
						exerciseTypeData = result.data;
						ui.util.table.prepareData($defer, $filter, params, exerciseTypeData);
					}, function(result) {
						toaster.pop('error', 'Exercise Types' , result.data.message);
					});
				}
				else {
					ui.util.table.prepareData($defer, $filter, params, exerciseTypeData);
				}
			}
		});

		$scope.tableParams.settings().$loading = true;
		
		$scope.newExerciseType = function() {
			$location.path('/admin/exercisetype');
		};

		$scope.deleteExerciseType = function(exerciseType) {
			if (confirm("Are you sure you wish to delete " + exerciseType.name )) {
				exerciseTypeService.deleteExerciseType(exerciseType.id).then(function() {
					exerciseTypeData.splice( exerciseTypeData.indexOf(exerciseType), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', 'Exercise Type' , 'Exercisetype deleted successfully');
				}, 
				function(result) {
					toaster.pop('error', 'Exercise Type', result.data.message);
				});
			}
		};
		
		$scope.updateExerciseType = function(exerciseType) {
			var oldName = exerciseType.name;
			exerciseType.name = exerciseType.newName;
			exerciseType.$edit = false; 
			exerciseTypeService.updateExerciseType(exerciseType.id, exerciseType).then(function(){
				toaster.pop('success', 'Exercise Type' , 'Exercisetype updated successfully');
			}, function(result) {
				exerciseType.name = oldName;
				toaster.pop('error', 'Exercise Type', result.data.message);
			});
		};
		
		$scope.editExerciseType = function(exerciseType) {
			exerciseType.newName = exerciseType.name;
			exerciseType.$edit = true;
		};

	});

	app.controller('AdminExerciseTypeController', function($scope, $location, exerciseTypeService, toaster) {

		$scope.createExerciseType = function() {
			exerciseTypeService.createExerciseType($scope.exerciseType).then(function(result) {
				$location.path('/admin/exercisetypes');
				toaster.pop('success', 'Exercise Type' , 'Exercisetype created successfully');
			}, 
			function(result) {
				toaster.pop('error', 'Exercise Type', result.data.message);
			});
		};

	});

}());