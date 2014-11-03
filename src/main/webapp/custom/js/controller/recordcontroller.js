(function() {
	var app = angular.module('crucem-elit');
	
	app.controller('RecordController', function($scope, $location, $filter, recordService, ngTableParams, toaster) {
		var recordData = null;
		 $scope.tableParams = new ngTableParams({
		        page: 1,            // show first page
		        count: 10,          // count per page
		        sorting: {
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
		    
	});

}());