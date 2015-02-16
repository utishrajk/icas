'use strict';
angular.module('icas', [
        'templates-app',
        'templates-common',
        'ngRoute',
        'icas.dashboarModule',
        'icas.caremanagerModule',
        'icas.organizationModule',
        'icas.patientModule',
        'icas.conditionModule',
        'icas.socialhistoryModule',
        'icas.reportsModule',
        'icas.toolsandresourcesModule',
        'icas.directives',
        'icas.breadcrumbsModule',
        'icas.procedureModule',
        'icas.outcomeModule',
        'icas.feedbackModule',
        'icas.reminderModule',
        'icas.activityModule',
        'icas.loginModule',
        'http-auth-interceptor',
        'spring-security-csrf-token-interceptor',
        'ngCookies',
        'icas.security',
        'icas.userService',
        'icas.medlinePlusDirectives',
        'icas.loggingModule',
        'icas.labresultsModule',
        'icas.medicationsModule',
        'icas.allergiesModule',
        'icas.errorModule'

    ])

    .config(['$routeProvider', '$locationProvider', '$compileProvider', 'USER_ROLES', '$httpProvider',
        function ($routeProvider, $locationProvider, $compileProvider, USER_ROLES, $httpProvider) {

            $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|tel):/);

        }])

    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.useXDomain = true;        
    }
    ])

    .run(['$rootScope', '$location', '$http', 'AuthenticationSharedService', 'Session', 'USER_ROLES', '$cookieStore',
        function ($rootScope, $location, $http, AuthenticationSharedService, Session, USER_ROLES, $cookieStore) {
            $rootScope.$on('$routeChangeStart', function (event, next) {
                $rootScope.authenticated = AuthenticationSharedService.isAuthenticated();
                $rootScope.isAuthorized = AuthenticationSharedService.isAuthorized;
                $rootScope.userRoles = USER_ROLES;
                $rootScope.account = Session;

                var authorizedRoles = ( angular.isDefined(next.access) && angular.isDefined(next.access.authorizedRoles) ) ? next.access.authorizedRoles : "";
                if (!AuthenticationSharedService.isAuthorized(authorizedRoles)) {
                    event.preventDefault();
                    if (AuthenticationSharedService.isAuthenticated()) {
                        // user is not allowed
                        $rootScope.$broadcast("event:auth-notAuthorized");
                    } else {
                        // user is not logged in
                        $rootScope.$broadcast("event:auth-loginRequired");
                    }
                } else {
                    // Check if the customer is still authenticated on the server
                    // Try to load a protected 1 pixel image.
                    if (!$rootScope.authenticated && $location.path() === "/register") {
                        $location.path('/register').replace();
                    }
                }
            });

            // Call when the the client is confirmed
            $rootScope.$on('event:auth-loginConfirmed', function (data) {
                if ($location.path() === "/login") {
                    $location.path('/training').replace();
                }
            });

            // Call when the 401 response is returned by the server
            $rootScope.$on('event:auth-loginRequired', function (rejection) {
                Session.destroy();
                $rootScope.authenticated = false;
                if ($location.path() !== "/" && $location.path() !== "") {
                    $location.path('/login').replace();
                }
            });

            // Call when the 403 response is returned by the server
            $rootScope.$on('event:auth-notAuthorized', function (rejection) {
                $rootScope.errorMessage = 'Access deny';
                $rootScope.errorCode = '403';
                $location.path('/error').replace();
            });

            // Call when the user logs out
            $rootScope.$on('event:auth-loginCancelled', function () {
                $location.path('/login');
            });
        }])

    .controller('ErrorCtrl', [ '$scope', '$location', function ($scope, $location) {

    }])
    .controller('AppCtrl', [ '$scope', '$location', 'BreadcrumbsService', 'SocialhistoryService', 'ConditionService', '$route', '$cookieStore','$anchorScroll', '$timeout',
        function ($scope, $location, BreadcrumbsService, SocialhistoryService, ConditionService, $route, $cookieStore, $anchorScroll, $timeout) {

            $scope.headnavbar = '../head-navbar.tpl.html';
            $scope.breadcrums = '../breadcrums.tpl.html';
            $scope.sidenavbar = '../side-navbar.tpl.html';
            $scope.patientbanner = '../patient-banner.tpl.html';
            $scope.companyName = "FEi Systems";
            $scope.projectName = "ICAS";
			$scope.USCountryCode= "US";
			
			$scope.US_TELEPHONE_DIGIT_LENGTH = 10;
            $scope.US_ZIPCODE_DIGIT_LENGTH = 5;
            $scope.appVersion = "(version 0.0.1)";

            $scope.notification = "";

            //Date pattern used by all the forms to check the format of the date
            $scope.datePattern = /(0[1-9]|1[012])[ \/.](0[1-9]|[12][0-9]|3[01])[ \/.](19|20)\d\d/;

            $scope.emailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

            $scope.passwordPattern = /^(?:(?=.*\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))(?!.*(.)\1{2,})[A-Za-z0-9!~<>,;:_=?*+#."&§%°()\|\[\]\-\$\^\@\/]{8,32}$/;

            $scope.breadcrumbs = function () {
                var breadcrumbs = BreadcrumbsService.getAll();

                //updateing the current page
                if (breadcrumbs.length >= 1) {
                    // Update the current page
                    $scope.currentPage = breadcrumbs[0].name;
                }
                return breadcrumbs;
            };

            $scope.redirect = function (path) {
                $location.path(path);
                $route.reload();
            };

            $scope.getEntityById = function (entityList, entityId) {
                for (var i = 0; i < entityList.length; i++) {
                    if (entityList[i].id === parseInt(entityId)) {
                        return entityList[i];
                    }
                }
            };

            $scope.getEntityByCode = function (entityList, code) {
                for (var i = 0; i <   entityList.length; i++) {
                    if (  entityList[i].code === code) {
                        return  entityList[i];
                    }
                }
            };

            $scope.isNullOrUndefined = function(value){
                return angular.isUndefined(value) || value === null;
            };

            $scope.deleteEntityById = function (entityList, entityId) {
                for (var i = 0; i < entityList.length; i++) {
                    if (entityList[i].id === parseInt(entityId)) {
                        entityList.splice(i, 1);
                        break;
                    }
                }
            };

            $scope.openCustomMenu = false;

            $scope.setSelectedPatient = function(patient){
                $scope.selectedPatient = patient;
            };

            $scope.populateCustomPatientMenu = function (selectedPatient) {
                $scope.selectedPatient = selectedPatient;
                $scope.selectedPatientId = selectedPatient.id;
                $scope.selectedPatientFullName = angular.isDefined(selectedPatient) ? selectedPatient.fullName:'';

                $scope.collapseDemographics = "";
                $scope.toggleDemographicsClass = false;

                $scope.collapseAllergy = "";
                $scope.toggleAllergyClass = false;

                $scope.collapseConditions = "";
                $scope.toggleConditionsClass = false;

                $scope.collapseMedications = "";
                $scope.toggleMedicationsClass = false;

                $scope.collapseSocialhistory = "";
                $scope.toggleSocialhistoryClass = false;

                $scope.collapseProcedure = "";
                $scope.toggleProcedureClass = false;

                $scope.collapseLabResult = "";
                $scope.toggleLabResultClass = false;

                $scope.collapseOutcome = "";
                $scope.toggleOutcomeClass = false;

            };


            $scope.onDemographicsClick = function(){
                $scope.collapseDemographics = $scope.collapseDemographics === "collapse" ? '' : 'collapse';
                $scope.toggleDemographicsClass = !$scope.toggleDemographicsClass;
            };

            $scope.onAllergyClick = function() {
                $scope.collapseAllergy = $scope.collapseAllergy === "collapse" ? '' : 'collapse';
                $scope.toggleAllergyClass = !$scope.toggleAllergyClass;
            };

            $scope.onConditionsClick = function(){
                $scope.collapseConditions = $scope.collapseConditions === "collapse" ? '' : 'collapse';
                $scope.toggleConditionsClass = !$scope.toggleConditionsClass;
            };

            $scope.onMedicationsClick = function() {
                $scope.collapseMedications = $scope.collapseMedications === "collapse" ? '' : 'collapse';
                $scope.toggleMedicationsClass = !$scope.toggleMedicationsClass;
            };

            $scope.onLabResultsClick = function() {
                $scope.collapseLabResults = $scope.collapseLabResults === "collapse" ? '' : 'collapse';
                $scope.toggleLabResultsClass = !$scope.toggleLabResultsClass;
            };

            $scope.onSocialHistoryClick = function(){
                $scope.collapseSocialhistory = $scope.collapseSocialhistory === "collapse" ? '' : 'collapse';
                $scope.toggleSocialhistoryClass = !$scope.toggleSocialhistoryClass;
            };

            $scope.onProcedureClick = function(){
                $scope.collapseProcedure = $scope.collapseProcedure === "collapse" ? '' : 'collapse';
                $scope.toggleProcedureClass = !$scope.toggleProcedureClass;
            };

            $scope.onOutcomeClick = function() {
                $scope.collapseOutcome = $scope.collapseOutcome === "collapse" ? '' : 'collapse';
                $scope.toggleOutcomeClass = !$scope.toggleOutcomeClass;
            };

            //$scope.removeActiveClassInSideNavBar = function () {
            //    $scope.trainingMenuitem = false;
            //    $scope.careManagerMenuitem = false;
            //    $scope.organizationMenuitem = false;
            //    $scope.patientListMenuitem = false;
            //    $scope.reportsMenuitem = false;
            //    $scope.toolsResourceMenuitem = false;
            //    $scope.conditionsMenuitem = false;
            //    $scope.socialHistoryMenuitem = false;
            //    $scope.procedureMenuitem = false;
            //    $scope.treatmentPlanMenuitem = false;
            //    $scope.demographicsMenuitem = false;
            //    $scope.summaryCareRecordMenuitem = false;
            //    $scope.remindersMenuitem = false;
            //    $scope.feedbackMenuitem = false;
            //    $scope.activityMenuitem = false;
            //};
            //
            //$scope.addActiveClassInSideNavBar = function (menuItem) {
            //    if (menuItem === 'training') {
            //        $scope.trainingMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    } else if (menuItem === 'careManager') {
            //        $scope.careManagerMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    } else if (menuItem === 'organization') {
            //        $scope.organizationMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    } else if (menuItem === 'patientList') {
            //        $scope.patientListMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    }else if (menuItem === 'reports') {
            //        $scope.reportsMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    } else if (menuItem === 'feedback') {
            //        $scope.feedbackMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    } else if (menuItem === 'toolsAndResource') {
            //        $scope.toolsResourceMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    } else if (menuItem === 'reminders') {
            //        $scope.remindersMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    } else if (menuItem === 'conditions') {
            //        $scope.enableCustomPatientMenu();
            //        $scope.conditionsMenuitem = true;
            //    } else if (menuItem === 'socialHistory') {
            //        $scope.enableCustomPatientMenu();
            //        $scope.socialHistoryMenuitem = true;
            //    } else if (menuItem === 'procedure') {
            //        $scope.enableCustomPatientMenu();
            //        $scope.procedureMenuitem = true;
            //    } else if (menuItem === 'treatmentPlan') {
            //        $scope.enableCustomPatientMenu();
            //        $scope.treatmentPlanMenuitem = true;
            //    } else if (menuItem === 'activity') {
            //        $scope.activityMenuitem = true;
            //        $scope.disableCustomPatientMenu();
            //    } else if (menuItem === 'demographics') {
            //        $scope.enableCustomPatientMenu();
            //        $scope.demographicsMenuitem = true;
            //    }
            //    else if (menuItem === 'summaryCareRecord') {
            //        $scope.enableCustomPatientMenu();
            //        $scope.summaryCareRecordMenuitem = true;
            //    }
            //};

            //$scope.onSelectmenu = function (menuItem) {
            //    $scope.enableDropDownMenu = false;
            //    $scope.removeActiveClassInSideNavBar();
            //    $scope.addActiveClassInSideNavBar(menuItem);
            //};

            //$scope.enableCustomPatientMenu = function () {
            //    $scope.openCustomMenu = true;
            //};
            //
            //$scope.disableCustomPatientMenu = function () {
            //    $scope.openCustomMenu = false;
            //};

            $scope.toJSON = function (obj) {
                return JSON.stringify(obj, null, 2);
            };

            //hide or show elements use in demographics table
            //Determine whether to route to demographics or patientlist page
            $scope.isDemographics = false;

            $scope.toggleDemographicMode = function (isDemographics) {
                $scope.isDemographics = isDemographics;
            };

            $scope.showPatientProfile = function () {
                $location.path("/patient/" + $scope.selectedPatientId + "/patientprofile");
            };

            $scope.isFutureDate = function (currentDate) {
                var result = false;
                if($scope.isNullOrUndefined(currentDate)){
                    return result;
                }else if (currentDate) {
                    var today = new Date().getTime();
                    var newDate = new Date(currentDate).getTime();

                    if (newDate > today) {
                        result = true;
                    }
                }
                return result;

            };

            $scope.isEndDateBeforeStartDate = function (startDate, endDate) {
                var result = false;
                if($scope.isNullOrUndefined(startDate) || $scope.isNullOrUndefined(endDate)){
                    return result;
                }else if (startDate && endDate) {
                    var start = new Date(startDate);
                    var end = new Date(endDate);
                    if (end < start) {
                        result = true;
                    }
                }
                return result;
            };
            
            $scope.getAge = function(dateString) {
                var today = new Date();
                var birthDate = new Date(dateString);
                var age = today.getFullYear() - birthDate.getFullYear();
                var m = today.getMonth() - birthDate.getMonth();
                if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }
                return age;	
            };

            $scope.enableDropDownMenu = false;

            $scope.toggleDropDownMenu = function () {
                $scope.enableDropDownMenu = !$scope.enableDropDownMenu;
            };

            $scope.gotoTop = function () {
                //Id of the element you want to scroll to
                $location.hash('top');
                // call $anchorScroll()
                $anchorScroll();
            };

            // Create patient
            $scope.showError = function (ngModelController, error) {
                return ngModelController.$error[error];
            };

            $scope.isValidNumber = function (number) {
                var result = false;
                if($scope.isNullOrUndefined(number)){
                    return result;
                }else {
                    var trimmedNumber = number.trim();
                    if ( !isNaN(trimmedNumber) && (trimmedNumber.length > 0)) {
                        result = true;
                    }
                }
                return result;
            };

            $scope.isUnitedState = function(countryCode){
                return countryCode === "US";
            };

            $scope.isValidContactNumber = function (number, countryCode, requireLength) {
                var result = false;
                if( $scope.isNullOrUndefined(number) ){
                    result = false;
                }else {
                    result = $scope.isUnitedState(countryCode) && (  number.length === requireLength );
                }
                return result;
            };

            $scope.forgotPasswordUser = function (user) {
                $scope.user = user;
            };

            $scope.urlHistory = [];

            $scope.$on('$routeChangeSuccess', function () {
                if ($location.$$absUrl.split('#')[1] !== $scope.urlHistory[$scope.urlHistory.length - 1]) {
                    $scope.urlHistory.push($location.$$absUrl.split('#')[1]);
                }
            });

            var back = function(){
                $scope.urlHistory.pop();
                $location.path($scope.urlHistory[$scope.urlHistory.length - 1]);
            };

            //Back from back button
            $scope.goBack = function () {
//                window.history.back();
                back();
            };

            //Back from browser back button
            $scope.$back = function () {
//            window.history.back();
                back();
            };

            $scope.onSelectMenu = function (menuItem) {
                $scope.menuItem = menuItem;
                $scope.openCustomMenu = false;
            };

            $scope.onSelectSubMenu = function (menuItem) {
                $scope.menuItem = "";
                $scope.subMenuItem = menuItem;
                $scope.openCustomMenu = true;
                $scope.enableDropDownMenu = false;
            };
        }])

;
