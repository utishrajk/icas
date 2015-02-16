'use strict';

angular.module("icas.caremanagerModule", [
        'icas.security',
        'icas.caremanagerService',
        'icas.loggingModule'
    ])

    .config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

        $routeProvider
            .when('/caremanager/:id', {
                templateUrl: "caremanager/caremanager-profile.tpl.html",
                controller: "CaremanagerCtrl",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['CareManagerService', '$log', '$q','$route', '$location','ErrorService', function(CareManagerService, $log, $q, $route, $location, ErrorService){
                        var careManagerId = $route.current.params.id;
                        if( !isNaN(careManagerId)){
                            // Set up a promise to return
                            var deferred = $q.defer();

                            var careManagerResource = CareManagerService.getCareManagerResource();
                            var careManagerData = careManagerResource.get({id:careManagerId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var stateResource = CareManagerService.getStateResource();
                            var stateResourceData = stateResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var providerTaxonomyResource = CareManagerService.getProviderTaxonomyResource();
                            var providerTaxonomyData = providerTaxonomyResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var countryResource = CareManagerService.getCareManagerCountryResource();
                            var countryResourceData = countryResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var prefixResource = CareManagerService.getCareManagerPrefixResource();
                            var prefixResourceData = prefixResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([stateResourceData.$promise, providerTaxonomyData.$promise, careManagerData.$promise, countryResourceData.$promise, prefixResourceData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;

                        } else {
                            $log.error("CareManager Resolve: invalid id.");
                            $location.path('error');
                        }
                    }]
                }
            });
    }])
    .controller("CaremanagerCtrl",['$scope','$log', '$location', 'loadedData', 'CareManagerService', 'ErrorService', function($scope, $log, $location, loadedData, CareManagerService, ErrorService) {

        $scope.onSelectMenu('careManager');

        // Switch Tabs
        $scope.activeTab  = 'basic';
        $scope.switchTabTo = function (tabId) {
            $scope.activeTab = tabId;
        };

        // Bound care manager data to scope
        $scope.states = loadedData[0];
        $scope.specialities = loadedData[1];
        $scope.caremanager = loadedData[2];
        $scope.countries = loadedData[3];
        $scope.prefixes = loadedData[4];

        $scope.$watch('caremanager.mailingAddressPostalCode', function(zipcode){
            $scope.validateZipCode(zipcode);
        });

        $scope.validateZipCode = function(zipcode){
            if(angular.isDefined(zipcode)){
                if( $scope.isUnitedState( $scope.caremanager.mailingAddressCountryCode)){
                    $scope.zipcodeIsANumber = $scope.isValidNumber(zipcode);
                    if($scope.zipcodeIsANumber){
                        $scope.validZipCodeLength = $scope.isValidContactNumber(zipcode, $scope.caremanager.mailingAddressCountryCode,$scope.US_ZIPCODE_DIGIT_LENGTH  );
                    }else{
                        $scope.validZipCodeLength = false;
                    }
                }else{
                    $scope.zipcodeIsANumber = true;
                    $scope.validZipCodeLength = true;
                }
            }else{
                $scope.validZipCodeLength = true;
                $scope.zipcodeIsANumber = true;
            }
        };

        $scope.validatePhoneNumber = function(phoneNumber){
            if(angular.isDefined(phoneNumber)){
                if( $scope.isUnitedState( $scope.caremanager.mailingAddressCountryCode)){
                    $scope.isPhoneNumber = $scope.isValidNumber(phoneNumber);
                    if($scope.isPhoneNumber){
                        $scope.isValidPhoneNumberLength =  $scope.isValidContactNumber(phoneNumber, $scope.caremanager.mailingAddressCountryCode,$scope.US_TELEPHONE_DIGIT_LENGTH );
                    }
                }else{
                    $scope.isPhoneNumber = $scope.isValidNumber(phoneNumber);
                    $scope.isValidPhoneNumberLength = true;
                }
            }else{
                $scope.isPhoneNumber = true;
                $scope.isValidPhoneNumberLength = true;
            }
        };

        $scope.$watch('caremanager.mailingAddressTelephoneNumber', function(phoneNumber){
            $scope.validatePhoneNumber(phoneNumber);
        });

        $scope.onCountrySelected = function(){
            $scope.validateZipCode($scope.caremanager.mailingAddressPostalCode);
            $scope.validatePhoneNumber($scope.caremanager.mailingAddressTelephoneNumber);
        };

        $scope.save = function(caremanager){

            if($scope.caremanager.mailingAddressCountryCode === $scope.USCountryCode){
                caremanager.nonUSState = "";
            }else{
                caremanager.mailingAddressStateName = "NON US STATE";
            }

            CareManagerService.update(caremanager.id , caremanager,
                function(data){$log.info("Success in processing the request..."); $scope.redirect('/caremanager/' + $scope.account.id);}, //account is being stored in rootscope
                function(error){ ErrorService.error(error);
                });
        };

        $scope.canSave = function(){
            var validZipCode = ($scope.validZipCodeLength && $scope.zipcodeIsANumber);
            var validPhoneNumber = ($scope.isPhoneNumber && $scope.isValidPhoneNumberLength);
            var futureDoB = !$scope.isFutureDate($scope.caremanager.dateOfBirth);

            return $scope.caremanagerForm.$dirty && $scope.caremanagerForm.$valid && validZipCode && validPhoneNumber && futureDoB;
        };

    }])
;
