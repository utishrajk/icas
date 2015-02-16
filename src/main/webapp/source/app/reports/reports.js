'use strict';

angular.module("icas.reportsModule", ['icas.security'])

.config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

		$routeProvider			
		.when('/reports', {				
				templateUrl: "reports/reports.tpl.html",
                controller: "ReportCtrl",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                }
			});
}])
    .controller("ReportCtrl", ["$scope", function($scope){
        $scope.onSelectMenu('reports');
}])
;