(function() {
	var app = angular.module('crucem-elit');
	app.controller('AdminUsersController', function($scope, $q, $filter, $location, ngTableParams, userService, toaster) {

		var userData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 100,          // count per page
			sorting: {
				email : 'asc'
			}
		}, {
			groupBy : function(value) {
				return value.role;
			}, 
			total: 0,           // length of data
			getData: function($defer, params) {
				if (userData===null) {
					userService.getUsers().then(function(result) {
						userData = result.data;
						ui.util.table.prepareData($defer, $filter, params, userData);
					}, function(result) {
						toaster.pop('error', $rootScope.getTranslation('user') , result.data.message);
					});
				}
				else {
					ui.util.table.prepareData($defer, $filter, params, userData);
				}
			}
		});
		
		$scope.tableParams.settings().$loading = true;
		
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

		var roles = [];
		$scope.roles = function(column) {
			var def = $q.defer();
			userService.getRoles().then(function(result) {
				angular.forEach(result.data, function(item) {
					roles.push({
						'id' : item,
						'title' : $scope.getTranslation(item)
					});
				});
				def.resolve(roles);
			});
			return def;
		};
		
		$scope.$watch('language()', function(newLang, oldLang) {
			if (oldLang !== newLang) {
				angular.forEach(genders,function(val,key) {val.title = $scope.getTranslation(val.id);})
				angular.forEach(roles,function(val,key) {val.title = $scope.getTranslation(val.id);})
			}
		});
		
		$scope.viewGym = function(id) {
			$location.path('/user/gyms/' + id);
		};
		
		$scope.viewUser = function(id) {
			$location.path('/user/contacts/' + id);
		};
		
		$scope.deleteUser = function(user) {
			if (confirm($rootScope.getTranslation('delete.user.confirm') +" " + user.name )) {
				userService.deleteUser(user.id).then(function() {
					userData.splice( userData.indexOf(user), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', $rootScope.getTranslation('user') , $rootScope.getTranslation('user.delete.success'));
				}, 
				function(result) {
					toaster.pop('error', $rootScope.getTranslation('user'), result.data.message);
				});
			}
		};
		
		$scope.changeRole = function(user) {
			if (confirm($rootScope.getTranslation('switch.role.confirm'))) {
				userService.changeRole(user.id).then(function() {
					user.role = user.role ==='USER' ? 'ADMIN' : 'USER';
					$scope.tableParams.reload();
					toaster.pop('success', $rootScope.getTranslation('user') , $rootScope.getTranslation('role.change.success'));
				}, 
				function(result) {
					toaster.pop('error', $rootScope.getTranslation('user'), result.data.message);
				});
			}
		};
		
	});
}());