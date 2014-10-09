(function() {
	var app = angular.module('crucem-elit');
	app.service('i18n', function() {
		var self = this;
		this.setLanguage = function(language) {
			$.i18n.properties({
				name : 'messages',
				path : 'i18n/',
				mode : 'map',
				language : language,
				encoding : 'UTF-8',
				callback : function() {
					self.language = language;
				}
			});
		};
		this.setLanguage('ee');
	});

	app.service('loginService', function($http) {
		this.authenticate = function(user) {
			var config = {
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded',
					}
				};
				return $http.post('api/auth/authenticate', user, config);
		}
	});

	app.service('userService', function($http, $q) {
		this.register = function(user) {
			var deferred = $q.defer();
			$http.post("/api/register", user).success(
					function(data, status, headers, config) {
						deferred.resolve(data);
					}).error(function(data, status, headers, config) {
				alert("AJAX failed!");
				deferred.reject("Error")
			});
			return deferred.promise;
		};
		this.getContacts = function() {
			return $http.get('/api/user/contacts').then(function(result) {
				return result.data;
			});
		};
		this.getUserById = function(id) {
			return $http.get('/api/user/users/' + id).then(function(result) {
				return result.data;
			});
		};
		this.joinGym = function(id) {
			return $http.post('api/user/gym/join/' + id).then(function(result) {
				return result.data;
			});
		}
		this.leaveGym = function() {
			return $http.get('/api/user/gym/leave').then(function(result) {
				return result.data;
			});
		};
		this.getProfile = function() {
			return $http.get('/api/user/profile').then(function(result) {
				return result.data;
			});
		};
	});

	app.service('gymService', function($http) {
		this.getGyms = function() {
			return $http.get('/api/user/gyms').then(function(result) {
				return result.data;
			});
		};
		this.getGymById = function(id) {
			return $http.get('/api/user/gyms/' + id).then(function(result) {
				return result.data;
			});
		}
	});

	app.service('base64', function() {

		var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef"
				+ "ghijklmnopqrstuv" + "wxyz0123456789+/" + "=";

		this.encode = function(input) {
			var output = "";
			var chr1, chr2, chr3 = "";
			var enc1, enc2, enc3, enc4 = "";
			var i = 0;

			while (i < input.length) {
				chr1 = input.charCodeAt(i++);
				chr2 = input.charCodeAt(i++);
				chr3 = input.charCodeAt(i++);

				enc1 = chr1 >> 2;
				enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
				enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
				enc4 = chr3 & 63;

				if (isNaN(chr2)) {
					enc3 = enc4 = 64;
				} else if (isNaN(chr3)) {
					enc4 = 64;
				}

				output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
						+ keyStr.charAt(enc3) + keyStr.charAt(enc4);
				chr1 = chr2 = chr3 = "";
				enc1 = enc2 = enc3 = enc4 = "";
			}

			return output;
		};

		this.decode = function(input) {
			var output = "";
			var chr1, chr2, chr3 = "";
			var enc1, enc2, enc3, enc4 = "";
			var i = 0;
			input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
			while (i < input.length) {
				enc1 = keyStr.indexOf(input.charAt(i++));
				enc2 = keyStr.indexOf(input.charAt(i++));
				enc3 = keyStr.indexOf(input.charAt(i++));
				enc4 = keyStr.indexOf(input.charAt(i++));

				chr1 = (enc1 << 2) | (enc2 >> 4);
				chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
				chr3 = ((enc3 & 3) << 6) | enc4;

				output = output + String.fromCharCode(chr1);

				if (enc3 != 64) {
					output = output + String.fromCharCode(chr2);
				}
				if (enc4 != 64) {
					output = output + String.fromCharCode(chr3);
				}

				chr1 = chr2 = chr3 = "";
				enc1 = enc2 = enc3 = enc4 = "";
			}
		};
	});

}());