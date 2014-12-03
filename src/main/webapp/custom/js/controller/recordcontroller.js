(function() {
	var app = angular.module('crucem-elit');

	app.controller('RecordsController', function($scope, $q, $location, $filter, userService, workoutService, ngTableParams, toaster) {
		var recordData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10,          // count per page
			filter: {
				'user.name' : $scope.user.name
			},
			sorting: {
				'wod' : 'asc',
				'result.repeats' : 'desc'
			}
		}, {
			total: 0,           // length of data
			getData: function($defer, params) {
				if (recordData===null) {

					workoutService.getWorkouts().then(function(result) {
						recordData = result.data;
						ui.util.table.prepareData($defer, $filter, params, recordData);
					}, function(result) {
						toaster.pop('error', 'Records' , result.data.message);
					});
				}
				else {
					ui.util.table.prepareData($defer, $filter, params, recordData);
				}
			}
		});

		$scope.tableParams.settings().$loading = true;

		$scope.viewGym = function(id) {
			$location.path('/user/gyms/' + id);
		};

		$scope.viewUser = function(id) {
			$location.path('/user/contacts/' + id);
		};

		$scope.$watch('language()', function(newLang, oldLang) {
			if (oldLang !== newLang) {
				angular.forEach(genders,function(val,key) {val.title = $scope.getTranslation(val.id);})
			}
		});

		var genders = [];

		$scope.genders = function(column) {
			var def = $q.defer();
			userService.getGenders().then(function(result) {
				angular.forEach(result.data, function(item) {
					genders.push({
						'id' : item,
						'title' : $scope.getTranslation(item)
					});
				});
				def.resolve(genders);
			});
			return def;
		};

	});


	app.controller('RecordController', function($scope, $routeParams, $location, userService, workoutService, toaster) {
		$scope.init = function() {
			userService.getWorkout($routeParams.id).then(function(result) {
				$scope.workout = result.data;
			}, function(result) {
				toaster.pop('error', 'Workout' , result.data.message);
			});
		};

	});

}());