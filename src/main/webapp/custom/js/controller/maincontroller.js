(function() {
	var app = angular.module('crucem-elit');
	app.controller('MainController', function($scope, $rootScope, i18n, $location, emailService, toaster) {
		
		$scope.mainImgSrc = '../images/cf.jpg';
		
		$scope.availableLanguages = ["ee", "en"];
		
		$scope.language = function() {
			return i18n.language;
		};
		$scope.setLanguage = function(lang) {
			i18n.setLanguage(lang);
		};
		$scope.activeWhen = function(value) {
			return value ? 'active' : '';
		};
		
		$scope.pathContains = function(path) {
			return $location.url().indexOf(path) > -1;
		};

		$scope.forgot = function() {
			emailService.sendNewPassword({email : $scope.email}).then(function() {
				$scope.email = null;
				toaster.pop('success', $rootScope.getTranslation('forgot.password.title'), $rootScope.getTranslation('email.sent.successfully'));
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('forgot.password.title'), result.data.message);
			});
		};	
		
		$scope.newWorkout = function() {
			$location.path('/user/workout/add');
		}
		
	});
	
}());