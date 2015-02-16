'use strict';

angular.module("icas.allergiesModule",
        [
            'ngResource',
            'icas.allergyService',
            'icas.allergyDirectives',
            'icas.security',
            'icas.medlinePlusService',
            'icas.patientService',
            'icas.loggingModule'
        ]
    )
    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {

        $routeProvider
            .when('/patient/:patientId/allergies', {
                templateUrl: "allergies/allergies.tpl.html",
                controller: "AllergiesCtrl",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedAllergies: ['AllergyService', 'PatientService','ErrorService', '$log', '$q', '$route', '$location', function (AllergyService,PatientService, ErrorService, $log, $q, $route, $location) {
                        var patientId = $route.current.params.patientId;

                        if (!isNaN(patientId)) {
                            // Set up a promise to return
                            var deferred = $q.defer();

                            // Set up our resource calls
                            var allergiesResource = AllergyService.getAllergiesResource();

                            var allergyData = allergiesResource.query({patientId: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            $q.all([allergyData.$promise, patientData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;

                        } else {
                            $log.error('Invalid Patient id');
                            $location.path('/error');
                        }
                    }]
                }
            })
            .when('/patient/:patientId/allergies/edit/:id', {
                templateUrl: "allergies/allergies-create-edit.tpl.html",
                controller: 'AllergiesEditCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: [ 'AllergyService','ErrorService','PatientService', '$log', '$q', '$route', '$location', function(AllergyService,ErrorService,PatientService, $log, $q, $route, $location) {
                        var allergyId = $route.current.params.id;
                        var patientId = $route.current.params.patientId;
                        if(!isNaN(allergyId) && !isNaN(patientId)) {
                            // Set up a promise to return
                            var deferred = $q.defer();

                            // Set up resource calls
                            var adverseEventTypeResource = AllergyService.getAdverseEventTypeResource();

                            var adverseEventTypeCodeData = adverseEventTypeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var allergenResource = AllergyService.getAllergenResource();

                            var allergenData = allergenResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var allergyReactionResource = AllergyService.getAllergyReactionResource();

                            var allergyReactionData = allergyReactionResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var allergySeverityResource = AllergyService.getAllergySeverityResource();

                            var allergySeverityData = allergySeverityResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var allergyByIdResource = AllergyService.getAllergyByIdResource();

                            var allergyByIdData = allergyByIdResource.get(
                                {id: allergyId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            // Resolve
                            $q.all([adverseEventTypeCodeData.$promise, allergenData.$promise, allergyReactionData.$promise, allergySeverityData.$promise, allergyByIdData.$promise, patientData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else{
                            $log.error('Invalid Patient id or Allergy id');
                            $location.path('/error');
                        }
                    }]
                }
            })
            .when('/patient/:patientId/allergies/add/', {
                templateUrl: "allergies/allergies-create-edit.tpl.html",
                controller: 'AllergiesCreateCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['AllergyService', 'PatientService','ErrorService', '$log', '$q', '$route', '$location', function (AllergyService, PatientService,ErrorService, $log, $q, $route, $location) {
                        // Set up a promise to return
                        var deferred = $q.defer();

                        var patientId = $route.current.params.patientId;
                        if( !isNaN(patientId)) {
                            // Set up resource calls
                            var adverseEventTypeResource = AllergyService.getAdverseEventTypeResource();

                            var adverseEventTypeCodeData = adverseEventTypeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var allergenResource = AllergyService.getAllergenResource();

                            var allergenData = allergenResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var allergyReactionResource = AllergyService.getAllergyReactionResource();

                            var allergyReactionData = allergyReactionResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var allergySeverityResource = AllergyService.getAllergySeverityResource();

                            var allergySeverityData = allergySeverityResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );
                            // Resolve
                            $q.all([adverseEventTypeCodeData.$promise, allergenData.$promise, allergyReactionData.$promise, allergySeverityData.$promise, patientData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else{
                            $log.error('Invalid Patient id ');
                            $location.path('/error');
                        }

                    }]
                }

            });
    }])
    .controller("AllergiesCtrl", ["$scope", 'AllergyService', '$location', '$log', 'loadedAllergies','ErrorService', function ($scope, AllergyService, $location, $log, loadedAllergies, ErrorService) {

        var errorCallback = function(error){
            ErrorService.error(error);
        };

        $scope.onSelectSubMenu('allergies');
        $scope.populateCustomPatientMenu(loadedAllergies[1]);

        $scope.allergies = loadedAllergies[0];
        $scope.filteredAllergies = loadedAllergies[0];
        $scope.totalRecords = loadedAllergies[0].length;
        $scope.noRecords = loadedAllergies[0].length === 0 ? true : false;

        //Initial tale page size
        $scope.pageSize = 10;

        //Calculating the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calcuating the first record
        $scope.startRecord = 1;


        $scope.deleteAllergy = function(allergyId) {
            AllergyService.delete(allergyId,
                function (data) {
                    $scope.deleteEntityById($scope.allergies, allergyId);
                    refreshTable();
                },
                errorCallback);
            $scope.allergyid = allergyId;
        };

        var refreshTable = function () {
            $scope.setActivePage(0);
        };

        $scope.onSearch = function () {
            $scope.pageNo = 0;

            if(angular.isDefined($scope.searchBy) && ($scope.searchBy.length > 0)) {
                if($scope.searchBy === 'adverseEvent'){
                    $scope.composedCriteria = {adverseEventTypeName : $scope.criteria};
                } else if($scope.searchBy === 'allergen'){
                    $scope.composedCriteria = {allergenName : $scope.criteria};
                }
            } else {
                $scope.composedCriteria = $scope.criteria;
            }
            updateShowPageSize($scope.pageNo);

        };

        $scope.onPageSizeChange = function () {
            if ($scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredAllergies.length;
            } else {
                $scope.showPageSize = $scope.pageSize;
            }
            //On changing page size reset to first page and set the page number to 0
            $scope.startRecord = 1;
            $scope.pageNo = 0;
        };

        // Sorting
        $scope.sortField = undefined;
        $scope.reverse = false;

        $scope.sort = function (fieldName) {
            if ($scope.sortField === fieldName) {
                $scope.reverse = !$scope.reverse;
            } else {
                $scope.sortField = fieldName;
                $scope.reverse = false;
            }
        };

        $scope.isSortUp = function (fieldName) {
            return $scope.sortField === fieldName && !$scope.reverse;
        };

        $scope.isSortDown = function (fieldName) {
            return $scope.sortField === fieldName && $scope.reverse;
        };

        // Pagination
        $scope.pages = [];
        $scope.pageNo = 0;
        $scope.firstPage = 0;
        $scope.lastPage = 0;

        var calculatePaginationPages = function (numberConditions, pageSize) {
            $scope.pages.length = 0;
            var noOfPages = Math.ceil(numberConditions / pageSize);
            $scope.lastPage = noOfPages;
            for (var i = 0; i < noOfPages; i++) {
                $scope.pages.push(i);
            }
        };

        $scope.$watch('filteredAllergies.length', function (filteredSize) {
            calculatePaginationPages(filteredSize, $scope.pageSize);

            //Update showPageSize when filteredContition length changes
            $scope.startRecord = parseInt($scope.pageSize) * parseInt($scope.pageNo) + 1;
            var upperLimit = $scope.startRecord + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize ? filteredSize : upperLimit;
        });

        $scope.$watch('pageSize', function (newPageSize) {
            var totalAllergy = 0;
            if (angular.isDefined($scope.filteredAllergies)) {
                totalAllergy = $scope.filteredAllergies.length;
            } else if (angular.isDefined($scope.totalRecords)) {
                totalAllergy = $scope.totalRecords.length;
            }
            calculatePaginationPages(totalAllergy, newPageSize);
            //Reset current to first page after user changes the page size.
            $scope.pageNo = 0;
        });

        $scope.setActivePage = function (pageNo) {
            if (pageNo >= 0 && pageNo < $scope.pages.length) {
                $scope.pageNo = pageNo;
                // Updating showing pages
                updateShowPageSize(pageNo);
            }
        };

        var updateShowPageSize = function (pageNo) {
            // Updating showing pages
            $scope.startRecord = parseInt($scope.pageSize) * parseInt(pageNo) + 1;
            var upperLimit = $scope.startRecord + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > $scope.filteredAllergies.length ? $scope.filteredAllergies.length : upperLimit;
        };

        $scope.onSelectSubMenu('allergies');
    }])
    .controller("AllergiesCreateCtrl", ['$scope', 'AllergyService', '$location', '$log', 'loadedData','ErrorService',
        function ($scope, AllergyService, $location, $log, loadedData, ErrorService) {

        $scope.adverseEventTypeCodes = loadedData[0];
        $scope.allergenCodes = loadedData[1];
        $scope.allergyReactionCodes = loadedData[2];
        $scope.allergySeverityCodes = loadedData[3];

        $scope.onSelectSubMenu('allergies');
        $scope.populateCustomPatientMenu(loadedData[4]);

        var successCallback = function (data) {
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/allergies');
        };

        var errorCallback = function (error) {
            ErrorService.error(error);
        };

        $scope.save = function (newAllergy) {
            if ($scope.selectedPatientId) {
                AllergyService.create($scope.selectedPatientId, newAllergy, successCallback, errorCallback);
            } else {
                $log.error('Create Allergy : PatientId not defined');
            }
        };
    }])
    .controller("AllergiesEditCtrl", ['$scope', 'AllergyService', '$location', '$log', 'loadedData','ErrorService',function($scope, AllergyService, $location, $log, loadedData, ErrorService) {

        $scope.adverseEventTypeCodes = loadedData[0];
        $scope.allergenCodes = loadedData[1];
        $scope.allergyReactionCodes = loadedData[2];
        $scope.allergySeverityCodes = loadedData[3];
        $scope.allergy = loadedData[4];

        $scope.onSelectSubMenu('allergies');
        $scope.populateCustomPatientMenu(loadedData[5]);

        var successCallback = function(data) {
            $scope.redirect('/patient/'+$scope.selectedPatientId + '/allergies');
        };

        var errorCallback = function(error) {
            ErrorService.error(error);
        };

        $scope.save = function(allergy) {
            AllergyService.update(allergy, allergy.id, successCallback, errorCallback);
        };

    }]);