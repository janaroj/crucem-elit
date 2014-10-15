(function() {
	var app = angular.module('crucem-elit');
	app.controller('InviteController', function($scope, emailService, toaster) {
		$scope.invite = function() {
			emailService.sendInviteEmail({email : $scope.email}).then(function() {
				$scope.email = null;
				toaster.pop('success', 'Invite', 'Email sent successfully!');
			}, function(result) {
				toaster.pop('error', 'Invite', result.data.message);
			});
		}
	});

}());