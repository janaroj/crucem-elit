(function() {
	var app = angular.module('crucem-elit');

	app.controller('RecordController', function($scope, $q, $location, $filter, userService, recordService, ngTableParams, toaster) {
		var recordData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10,          // count per page
			filter: {
				'user.name' : $scope.user.name
			},
			sorting: {
				'exercise.name' : 'asc',
				'result' : 'desc'
			}
		}, {
			total: 0,           // length of data
			getData: function($defer, params) {
				if (recordData===null) {
					recordService.getRecords($scope.tableParams, $defer).then(function(result) {
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

		$scope.genders = function(column) {
			var def = $q.defer();
			var genders = [];
			userService.getGenders().then(function(result) {
				angular.forEach(result.data, function(item) {
					genders.push({
						'id' : item,
						'title' : item
					});
				});
				def.resolve(genders);
			});
			return def;
		};

	});

}());