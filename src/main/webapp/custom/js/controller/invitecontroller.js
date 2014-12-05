(function() {
	var app = angular.module('crucem-elit');
	app.controller('InviteController', function($scope, $rootScope, emailService, toaster) {
		$scope.invite = function() {
			emailService.sendInviteEmail({email : $scope.email}).then(function() {
				$scope.email = null;
				toaster.pop('success', $rootScope.getTranslation('invite'), $rootScope.getTranslation('email.sent.successfully'));
			}, function(result) {
				toaster.pop('error', $rootScope.getTranslation('invite'), result.data.message);
			});
		};
	});

}());