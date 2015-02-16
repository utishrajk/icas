'use strict';

angular.module("icas.organizationModule", ['icas.security', 'icas.organizationService',  'icas.loggingModule'])

.config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

		$routeProvider			
		.when('/organization/:id', {
                controller: 'OrganizationProfileCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                },
                templateUrl: "organization/organization.tpl.html",
                resolve: {
                    loadedData: ['OrganizationService', '$log', '$q','$route', '$location','ErrorService', function(OrganizationService, $log, $q, $route, $location, ErrorService){
                        var userId = $route.current.params.id;
                        if( ! isNaN(userId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var organizationResource = OrganizationService.getOrganizationResource();
                            var organizationData = organizationResource.get({id:userId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var stateResource = OrganizationService.getStateResource();
                            var stateData = stateResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var countryResource = OrganizationService.getCountryResource();
                            var countryData = countryResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var serviceResource = OrganizationService.getServiceResource();
                            var serviceData = serviceResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var prefixResource = OrganizationService.getPrefixResource();
                            var prefixData = prefixResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([organizationData.$promise, stateData.$promise, countryData.$promise, serviceData.$promise, prefixData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;

                        } else {
                            $log.error("CareManager Get Resolve: Care manager id not defined.");
                            $location.path('error');
                        }
                    }]
                }
			});
}])
.controller('OrganizationProfileCtrl', ['$scope','loadedData', 'OrganizationService', '$log','ErrorService', function ($scope, loadedData, OrganizationService, $log, ErrorService){

        $scope.onSelectMenu('organization');

        // Switch Tabs
        $scope.activeTab  = 'organizationProfile';
        $scope.isNotANumber = false;

        var temporalOrgProfile = loadedData[0];
        temporalOrgProfile.npi = parseInt(temporalOrgProfile.npi);
        $scope.organizationProfile = temporalOrgProfile;

        $scope.states = loadedData[1];
        $scope.countries = loadedData[2];
        $scope.services = loadedData[3];
        $scope.prefixes = loadedData[4];

        $scope.switchTabTo = function (tabId) {
            $scope.activeTab = tabId;
        };

        $scope.save = function(organizationProfile){

            if($scope.organizationProfile.mailingAddressCountryCode === $scope.USCountryCode){
                organizationProfile.nonUSState = null;
            }else{
                organizationProfile.mailingAddressStateName = "NON US STATE";
            }

            OrganizationService.update(
                organizationProfile,
                function(response){
                    $log.info('Success increating organization');
                },
                function(error){
                    ErrorService.error(error);
                }
            );
        };

        $scope.$watch('organizationProfile.npi', function(npi){
            if(isNaN(npi)){
                $scope.isNotANumber = true;
            }else{
                $scope.isNotANumber = false;
            }
        });

        $scope.validateZipCode = function(zipcode){
            if(angular.isDefined(zipcode)){
                if( $scope.isUnitedState( $scope.organizationProfile.mailingAddressCountryCode)){
                    $scope.zipcodeIsANumber = $scope.isValidNumber(zipcode);
                    if($scope.zipcodeIsANumber){
                        $scope.validZipCodeLength = $scope.isValidContactNumber(zipcode, $scope.organizationProfile.mailingAddressCountryCode,$scope.US_ZIPCODE_DIGIT_LENGTH  );
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

        $scope.$watch('organizationProfile.mailingAddressPostalCode', function(zipcode){
            $scope.validateZipCode(zipcode);
        });

        $scope.validatePhoneNumber = function(phoneNumber){
            if(angular.isDefined(phoneNumber)){
                if( $scope.isUnitedState( $scope.organizationProfile.mailingAddressCountryCode)){
                    $scope.isPhoneNumber = $scope.isValidNumber(phoneNumber);
                    if($scope.isPhoneNumber){
                        $scope.isValidPhoneNumberLength =  $scope.isValidContactNumber(phoneNumber, $scope.organizationProfile.mailingAddressCountryCode,$scope.US_TELEPHONE_DIGIT_LENGTH );
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

        $scope.$watch('organizationProfile.mailingAddressTelephoneNumber', function(phoneNumber){
            $scope.validatePhoneNumber(phoneNumber);
        });

        $scope.validateFaxNumber = function(faxNumber){
            if(angular.isDefined(faxNumber)){
                if( $scope.isUnitedState( $scope.organizationProfile.mailingAddressCountryCode)){
                    $scope.isFaxNumber = $scope.isValidNumber(faxNumber);
                    if($scope.isFaxNumber){
                        $scope.isValidFaxNumberLength =  $scope.isValidContactNumber(faxNumber, $scope.organizationProfile.mailingAddressCountryCode,$scope.US_TELEPHONE_DIGIT_LENGTH );
                    }
                }else{
                    $scope.isFaxNumber = $scope.isValidNumber(faxNumber);
                    $scope.isValidFaxNumberLength = true;
                }
            }else{
                $scope.isFaxNumber = true;
                $scope.isValidFaxNumberLength = true;
            }
        };

        $scope.$watch('organizationProfile.mailingAddressFaxNumber', function(faxNumber){
            $scope.validateFaxNumber(faxNumber);
        });

        $scope.validateAuthorizePhoneNumber = function(phoneNumber){
            if(angular.isDefined(phoneNumber)){
                if( $scope.isUnitedState( $scope.organizationProfile.mailingAddressCountryCode)){
                    $scope.isAuthorizedPhoneNumber = $scope.isValidNumber(phoneNumber);
                    if($scope.isAuthorizedPhoneNumber){
                        $scope.isValidAuthorizedPhoneNumberLength =  $scope.isValidContactNumber(phoneNumber, $scope.organizationProfile.mailingAddressCountryCode,$scope.US_TELEPHONE_DIGIT_LENGTH );
                    }
                }else{
                    $scope.isAuthorizedPhoneNumber = $scope.isValidNumber(phoneNumber);
                    $scope.isValidAuthorizedPhoneNumberLength = true;
                }
            }else{
                $scope.isAuthorizedPhoneNumber = true;
                $scope.isValidAuthorizedPhoneNumberLength = true;
            }
        };

        $scope.$watch('organizationProfile.authorizedOfficialTelephoneNumber', function(phoneNumber){
            $scope.validateAuthorizePhoneNumber(phoneNumber);
        });

        $scope.validateStateZipcodePhoneFax = function(){
            $scope.validateAuthorizePhoneNumber($scope.organizationProfile.authorizedOfficialTelephoneNumber);
            $scope.validateZipCode($scope.organizationProfile.mailingAddressPostalCode);
            $scope.validatePhoneNumber($scope.organizationProfile.mailingAddressTelephoneNumber);
            $scope.validateFaxNumber($scope.organizationProfile.mailingAddressFaxNumber);
        };

        $scope.canSave = function(){
            var validAuthorizePhoneNumber = ($scope.isAuthorizedPhoneNumber && $scope.isValidAuthorizedPhoneNumberLength);
            var validZipCode = ($scope.zipcodeIsANumber && $scope.validZipCodeLength);
            var validPhoneNumber = ($scope.isPhoneNumber && $scope.isValidPhoneNumberLength);
            var validFaxNumber = ($scope.isFaxNumber && $scope.isValidFaxNumberLength);

            return $scope.organizationProfileForm.$dirty && $scope.organizationProfileForm.$valid && validZipCode && validPhoneNumber && validFaxNumber && validAuthorizePhoneNumber ;
        };

        $scope.isSelectedService = function(code){
            var selected = false;
            var services = $scope.organizationProfile.services;
            for(var i = 0 ; i < services.length; i++){
                if(services[i] === code){
                    selected = true;
                    break;
                }
            }
            return selected;
        };

    }]);