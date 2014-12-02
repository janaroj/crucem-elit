//Define a function scope, variables used inside it will NOT be globally visible.
(function() {

	app = angular.module('crucem-elit', [ 'ui.bootstrap','ngRoute', 'ngResource', 'ngTable', 'ngCookies','angularFileUpload', 'ngAnimate', 'toaster' ]);

	//user operations
	app.run(function ($rootScope, $http, $location, $cookieStore, $filter, authService, userService, toaster) {
		
		var friends = null;

		$rootScope.hasRole = function (role) {

			if ($rootScope.user === undefined || !$rootScope.user.role || $rootScope.user.role !== role) {
				return false;
			}

			return true;
		};
		
		$rootScope.getFriends = function() {
			if (!friends) {
				friends = $cookieStore.get('friends');
			}
			return friends;
		};
		
		$rootScope.isFriend = function(contact) {
			var friends = $rootScope.getFriends();
			for (var i = 0; i < friends.length; i++) {
				if (friends[i].id === contact.id) {
					return true;
				}
			}

			return false;
		};
		
		$rootScope.removeFriend = function(id) {
			friends =  $filter('filter')(friends, {id: id}, function (obj, test) { return obj !== test; });
			$cookieStore.put('friends', friends);
		};
		
		$rootScope.addFriend = function(friend) {
			userService.addFriend(friend.id).then(
				function(result) {
					friends.push(friend);
					$cookieStore.put('friends', friends);
					toaster.pop('success', 'Friend', 'Friend added successfully!');
				},
				function(result) {
					toaster.pop('error', 'Friend' , result.data.message);
				}
			);

		};

		$rootScope.getTranslation = function(key) {
			if (key === "") {
				return "-";
			}
			
			if (!key) {
				return "";
			}
			return $.i18n.prop(key);
		};

		$rootScope.loginClicked = false;

		$rootScope.login = function(username, password) {
			$rootScope.loginClicked = true;
			authService.authenticate($.param({username: username, password: password}))
			.then(function(result){

				$rootScope.user = result.data;
				$http.defaults.headers.common['X-Auth-Token'] = result.data.token;
				$cookieStore.put('user', result.data);

				if($rootScope.redirectUrl != null) {
					$location.url($rootScope.redirectUrl);
				}
				else {
					$location.path("/user/main");
				}

				$rootScope.redirectUrl = null;
				$rootScope.redirectStatus = null;
				$rootScope.loginClicked = false;

				userService.fetchFriends().then(
					function(result){
						friends = result.data;
						$cookieStore.put('friends', result.data);
					}, 
					function(result) {
						toaster.pop('error', 'Fetching friends', result.data.message);
					});
			},
			function(result) {
				$rootScope.loginClicked = false;
				if($rootScope.redirectStatus == 401) {
					$location.url($rootScope.redirectUrl);
					toaster.pop('error', 'Authentication', result.data.message);
				}
			});
		};

		$rootScope.logout = function () {
			delete $rootScope.user;
			delete $http.defaults.headers.common['X-Auth-Token'];
			$cookieStore.remove('user');
			$cookieStore.remove('friends');
			if ($location.url() !== "/register") {
				$location.url('/');
			}
		};
	});

	//monitoring route changes
	app.run(function ($rootScope, $http, $location, $cookieStore, $timeout) {

		$rootScope.$on('$locationChangeStart', function (event, next, current) {
			var user = $cookieStore.get('user');
			if (user !== undefined) {
				$http.defaults.headers.common['X-Auth-Token'] = user.token;
				if ($location.url() === "/") {
					event.preventDefault();
					$timeout(function(){$location.path("/user/main");});
				}
			}
		});
	});
}());