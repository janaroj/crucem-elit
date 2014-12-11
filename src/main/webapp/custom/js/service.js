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
	
	/* Intercept http errors */
	app.factory('interceptor', function ($rootScope, $q, $location) {
		return {
			'response': function(response) {
				return response;
			},
			'responseError': function(response) {
				var status = response.status;
				if (status == 401) {
					$rootScope.redirectUrl = $location.url();
					$rootScope.redirectStatus = 401;
					$rootScope.logout();
				}
				return $q.reject(response);
			}
		};
	});
	
	app.factory('CheckAuthentication', function($rootScope, authService, toaster) {
		return function() {
             return authService.checkAuth().then(function(result) {
         		$rootScope.user = result;
         	}, function(result) {
         		$rootScope.logout();
         		toaster.pop('error', 'Session', result.message);
         	});
		}
	});

	app.factory('authService', function($http, $q) {
		return {
			authenticate : function(user) {
				var config = {
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded',
						}
					};
					return $http.post('api/auth/authenticate', user, config);
			},
			checkAuth : function() {
				var deferred = $q.defer();
				$http.get('api/auth/check').then(function(result) {
					deferred.resolve(result.data);
				});
				return deferred.promise;
			}
		}
	});
	
	
	app.factory("autoCompleteService", ["$http", function ($http) {
	    return {
	    	getSuggestions: function (term) {
	            return $http.get("/api/user/search/" + term).then(function (response) {
	                return response.data;
	            });
	        }
	    };
	}]);

	app.service('userService', function($http, $upload) {
		this.register = function(user) {
			return $http.post("/api/register", user);
		};
		this.getContacts = function() {
			return $http.get('/api/user/contacts');
		};
		this.getGenders = function() {
			return $http.get('/api/user/genders');
		};
		this.getRoles = function() {
			return $http.get('/api/admin/roles');
		};
		this.addFriend = function(id) {
			return $http.post('/api/user/friends/' + id);
		};
		this.removeFriend = function(id) {
			return $http.delete('/api/user/friends/' + id);
		};
		this.getUsers = function() {
			return $http.get('/api/admin/users');
		};
		this.getUserById = function(id) {
			return $http.get('/api/user/users/' + id);
		};
		this.changeRole = function(id) {
			return $http.put('/api/admin/users/role/' + id);
		};
		this.updateUser = function(user){
			return $http.put("/api/user/update/user", user);
		};	
		this.deleteUser = function(id) {
			return $http.delete('/api/admin/users/' + id);
		};
		this.fetchFriends = function() {
			return $http.get("/api/user/friends");
		};
		this.joinGym = function(id) {
			return $http.post('api/user/gym/join/' + id);
		}
		this.leaveGym = function() {
			return $http.get('/api/user/gym/leave');
		};
		this.getProfile = function() {
			return $http.get('/api/user/profile');
		};
		this.getProfilePicture = function(id) {
			return $http.get('/api/user/profile/picture/' + id);
		};
		this.uploadProfilePicture = function(image) {
			return  $upload.upload({
		        url: 'api/user/profile/picture', 
		        method:'PUT',
		        file: image, 
		      });
		};
		this.getWorkouts = function() {
			return $http.get('/api/user/workouts');
		};
		this.getWorkout = function(id){
			return $http.get('/api/user/workouts/' + id);
		};
		this.getUpcomingWorkouts = function(){
			return $http.get('/api/user/workouts/upcoming');
		};
		this.deleteWorkout = function(id) {
			return $http.delete('/api/user/workouts/' + id);
		};
		this.addWorkout = function(workout) {
			return $http.post("/api/user/workouts", workout);
		};
		this.deleteResult = function(id) {
			return $http.delete('/api/user/record/' + id);
		};
		this.createComment = function(comment) {
			return $http.post("/api/user/comments", comment);
		};
		this.deleteComment = function(id) {
			return $http.delete("/api/user/comments/" + id);
		};		
	});

	app.service('gymService', function($http, $upload) {
		this.getGyms = function() {
			return $http.get('/api/user/gyms');
		};
		this.getGymById = function(id) {
			return $http.get('/api/user/gyms/' + id);
		};
		this.createGym = function(gym) {
			return $http.post('/api/admin/gyms/', gym);
		};
		this.getGymPicture = function(id) {
			return $http.get('/api/user/gym/picture/' + id);
		};
		this.getComments = function(id) {
			return $http.get('/api/user/gym/comments/' + id);
		};
		this.deleteGym = function(id) {
			return $http.delete('/api/admin/gyms/' + id);
		};
		this.updateGym = function(gym){
			return $http.put("/api/admin/gyms/" + gym.id, gym);
		};
		this.uploadGymPicture = function(image, id) {
			return  $upload.upload({
		        url: 'api/user/gym/'+id+'/picture', 
		        method:'PUT',
		        file: image, 
		      });
		};
	});
	
	app.service('emailService', function($http) {
		this.sendInviteEmail = function(email) {
			return $http.post('/api/user/invite', email);
		};
		this.sendNewPassword = function(email) {
			return $http.post('/api/forgot/password', email);
		};
	});
	
	app.service('workoutService', function($http) {
		this.getWorkouts = function() {
			return $http.get('/api/user/workouts/results');
		};
		this.updateWorkout = function(workout) {
			return $http.put('/api/user/workouts/'+ workout.id, workout);
		};
		this.getWorkoutsWithResults = function() {
			return $http.get('api/user/workouts/records');
		};
	});
	
	app.service('exerciseService', function($http) {
		this.createExercise = function(exercise) {
			return $http.post('/api/admin/exercises', exercise);
		};
		this.getExercises = function() {
			return $http.get('/api/user/exercises');
		};
		this.deleteExercise = function(id) {
			return $http.delete('/api/admin/exercises/' + id);
		};
		this.updateExercise = function(exercise){
			return $http.put("/api/admin/exercises/" + exercise.id, exercise);
		};	
	});
	
	app.service('exerciseTypeService', function($http) {
		this.getExerciseTypes = function() {
			return $http.get('/api/admin/exercisetypes');
		};
		this.createExerciseType = function(exerciseType) {
			return $http.post('/api/admin/exercisetypes', exerciseType);
		};
		this.deleteExerciseType = function(id) {
			return $http.delete('/api/admin/exercisetypes/' + id);
		};
		this.updateExerciseType = function(exerciseType){
			return $http.put("/api/admin/exercisetypes/" + exerciseType.id, exerciseType);
		};	
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