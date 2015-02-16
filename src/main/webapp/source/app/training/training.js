'use strict';

angular.module("icas.dashboarModule", ['icas.security'])

.config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

		$routeProvider
		.when('/training', {
				templateUrl: "training/training.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller:'TrainingCtrl'
			});
}])
.controller('TrainingCtrl', ['$scope',function($scope){
        $scope.onSelectMenu('training');
}]);