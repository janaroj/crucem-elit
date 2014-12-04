(function() {
	var app = angular.module('crucem-elit');
	app.controller('AdminUsersController', function($scope, $q, $filter, $location, ngTableParams, userService, toaster) {

		var userData = null;
		$scope.tableParams = new ngTableParams({
			page: 1,            // show first page
			count: 10,          // count per page
			sorting: {
				email : 'asc'
			}
		}, {
			total: 0,           // length of data
			getData: function($defer, params) {
				if (userData===null) {
					userService.getUsers().then(function(result) {
						userData = result.data;
						ui.util.table.prepareData($defer, $filter, params, userData);
					}, function(result) {
						toaster.pop('error', 'Users' , result.data.message);
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
			if (confirm("Are you sure you wish to delete " + user.name )) {
				userService.deleteUser(user.id).then(function() {
					userData.splice( userData.indexOf(user), 1 );
					$scope.tableParams.reload();
					toaster.pop('success', 'User' , 'User deleted successfully');
				}, 
				function(result) {
					toaster.pop('error', 'User', result.data.message);
				});
			}
		};
		
		$scope.changeRole = function(user) {
			if (confirm("Are you sure you wish to switch the role?")) {
				userService.changeRole(user.id).then(function() {
					user.role = user.role ==='USER' ? 'ADMIN' : 'USER';
					$scope.tableParams.reload();
					toaster.pop('success', 'User' , 'Role changed successfully');
				}, 
				function(result) {
					toaster.pop('error', 'User', result.data.message);
				});
			}
		};
		
	});
}());