'use strict';

angular.module('icas.outcomeModule',
        ['ngResource',
            'icas.outcomeService',
            'icas.outcomeDirectives',
            'icas.filters',
            'icas.naturalSort',
            'icas.security',
            'nvd3ChartDirectives',
            'icas.loggingModule'
        ]
    )
    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/patient/:patientId/outcomes', {
                templateUrl: "outcome/outcome-list.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'OutcomeListCtrl',
                resolve: {
                    loadedData: ['OutcomeService', '$log', '$q', '$route', 'PatientService','ErrorService','$location', function (OutcomeService, $log, $q, $route, PatientService, ErrorService, $location) {
                        var patientId = $route.current.params.patientId;
                        if (!isNaN(patientId)) {
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var outcomeResource = OutcomeService.getOutcomeResource();
                            var outcomeData = outcomeResource.query(
                                {patientId: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var medicationCodeResource = OutcomeService.getMedicationCodeOutcomesResource();
                            var medicationCodeData = medicationCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            $q.all([outcomeData.$promise, patientData.$promise, medicationCodeData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        } else {
                            $log.error('Outcomes Resolve: invalid patient id');
                            $location.path('/error');
                        }
                    }]
                }
            })
            .when('/patient/:patientId/outcomes/add/', {
                templateUrl: "outcome/outcome-create-edit.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },

                controller: 'OutcomeCreateCtrl',
                resolve: {
                    loadedData: ['OutcomeService', '$log', '$q','PatientService','$route','ErrorService',function (OutcomeService, $log, $q, PatientService, $route,ErrorService) {

                        var patientId = $route.current.params.patientId;
                        if (!isNaN(patientId)) {

                            // Set up a promise to return
                            var deferred = $q.defer();

                            var procedureCodeResource = OutcomeService.getProcedureCodeResource();
                            var procedureCodeResourceData = procedureCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var cgiICodeResource = OutcomeService.getCgiICodeResource();
                            var cgiICodeResourceData = cgiICodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var cgiSCodeResource = OutcomeService.getCgiSCodeResource();
                            var cgiSCodeResourceData = cgiSCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var medicationCodeResource = OutcomeService.getMedicationCodeOutcomesResource();
                            var medicationCodeData = medicationCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ ErrorService.error(response);}
                            );

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([procedureCodeResourceData.$promise, cgiICodeResourceData.$promise, cgiSCodeResourceData.$promise, patientData.$promise, medicationCodeData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else{
                            console.error("Outcome: no patient id passed.");
                        }

                    }]
                }
            })
            .when('/patient/:patientId/outcomes/edit/:id', {
                templateUrl: "outcome/outcome-create-edit.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'OutcomeEditCtrl',
                resolve: {
                    loadedData: ['OutcomeService', '$log', '$q', '$route', 'PatientService','ErrorService','$location', function (OutcomeService, $log, $q, $route, PatientService, ErrorService, $location) {
                        var outcomeId = $route.current.params.id;
                        var patientId = $route.current.params.patientId;
                        if (!isNaN(outcomeId) && !isNaN(patientId)) {
                            // Set up a promise to return
                            var deferred = $q.defer();

                            var procedureCodeResource = OutcomeService.getProcedureCodeResource();
                            var procedureCodeResourceData = procedureCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var cgiICodeResource = OutcomeService.getCgiICodeResource();
                            var cgiICodeResourceData = cgiICodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var cgiSCodeResource = OutcomeService.getCgiSCodeResource();
                            var cgiSCodeResourceData = cgiSCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var outcomeOnlyResource = OutcomeService.getOutcomeOnlyResource();
                            var outcomeOnlyResourceData = outcomeOnlyResource.get(
                                {id: outcomeId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var medicationCodeResource = OutcomeService.getMedicationCodeOutcomesResource();
                            var medicationCodeData = medicationCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([procedureCodeResourceData.$promise, cgiICodeResourceData.$promise, cgiSCodeResourceData.$promise, outcomeOnlyResourceData.$promise, patientData.$promise, medicationCodeData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        } else {
                            $log.error('Condtion resolve: outcome Id not defined');
                            $location.path('/error');
                        }
                    }]
                }
            });
    }])

    .run(["$rootScope", "NaturalService", function ($rootScope, NaturalService) {
        $rootScope.natural = function (field) {
            if (field) {
                field = field === 'problemCode' ? 'problemDisplayName' : field;
                return function (patients) {
                    return NaturalService.naturalValue(patients[field]);
                };
            }
        };
    }])
    .controller('OutcomeListCtrl', ['$scope', 'OutcomeService', '$location', '$log', 'loadedData', '$route','ErrorService', function ($scope, OutcomeService, $location, $log, loadedData, $route, ErrorService) {

        var successCallback = function (data) {
            $log.info("Success in processing the request...");
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/outcomes');
        };

        var errorCallback = function (error) {
            $log.error(error.data.message);
            ErrorService.error(error);
        };

        $scope.outcomes = loadedData[0];
        $scope.filteredOutcomes = loadedData[0];
        $scope.totalRecords = loadedData[0].length;
        $scope.onSelectSubMenu('outcome');
        $scope.populateCustomPatientMenu(loadedData[1]);
        $scope.medicationCodeOutcomes = loadedData[2];

        $scope.noRecords = loadedData[0].length === 0 ? true : false;

        //Initial table page size
        $scope.pageSize = 10;

        //Calculating the the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calculating the first record
        $scope.startRecord = 1;

        //Declare delete on scope
        $scope.deleteOutcome = function (outcomeId) {
            OutcomeService.delete(outcomeId,
                function (data) {
                    $scope.deleteEntityById($scope.outcomes, outcomeId);
                    refreshTable();
                },
                errorCallback);
            $scope.outcomeid = outcomeId;
        };

        var refreshTable = function () {
            $location.path("/patient/" + $scope.selectedPatientId + "/outcomes");
            $route.reload();
        };

        $scope.onSearch = function () {
            $scope.pageNo = 0;
            $scope.composedCriteria = $scope.criteria;
            updateShowPageSize($scope.pageNo);
        };

        $scope.onPageSizeChange = function () {
            if ($scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredOutcomes.length;
            } else {
                $scope.showPageSize = $scope.pageSize;
            }
            //On changing page size reset to first page and set the page number to 0
            $scope.startRecord = 1;
            $scope.pageNo = 0;
        };

        //SORTING
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

        // PAGINATION
        $scope.pages = [];
        $scope.pageNo = 0;
        $scope.firstPage = 0;
        $scope.lastPage = 0;

        var calculatePaginationPages = function (numberOutcomes, pageSize) {
            $scope.pages.length = 0;
            var noOfPages = Math.ceil(numberOutcomes / pageSize);
            $scope.lastPage = noOfPages;
            for (var i = 0; i < noOfPages; i++) {
                $scope.pages.push(i);
            }
        };

        $scope.$watch('filteredOutcomes.length', function (filteredSize) {
            calculatePaginationPages(filteredSize, $scope.pageSize);

            //Update showPageSize when filteredContition length changes
            $scope.startRecord = parseInt($scope.pageSize) * parseInt($scope.pageNo) + 1;
            var upperLimit = $scope.startRecord + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize ? filteredSize : upperLimit;
        });

        $scope.$watch('pageSize', function (newPageSize) {
            var totalOutcome = 0;
            if (angular.isDefined($scope.filteredOutcomes)) {
                totalOutcome = $scope.filteredOutcomes.length;
            } else if (angular.isDefined($scope.totalRecords)) {
                totalOutcome = $scope.totalRecords.length;
            }
            calculatePaginationPages(totalOutcome, newPageSize);
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
            $scope.showPageSize = upperLimit > $scope.filteredOutcomes.length ? $scope.filteredOutcomes.length : upperLimit;
        };

        $scope.ExampleCtrl = function ($scope){

            var originalData = $scope.outcomes;
            var cgii = [], cgis = [];

            for(var i = 0; i < originalData.length; i++) {
                cgii.push([new Date(originalData[i].startDate).getTime(), originalData[i].cgiICode]);
                cgis.push([new Date(originalData[i].startDate).getTime(), originalData[i].cgiSCode]);
            }

            var series1 = {
                "key" : "CGI-I",
                "values": cgii
            };

            var series2 = {
                "key" : "CGI-S",
                "area": true,
                "values": cgis
            };

            $scope.exampleData = [];
            $scope.exampleData.push(series1);
            $scope.exampleData.push(series2);

            $scope.xAxisTickFormatFunction = function(){
                return function(d){
                    return d3.time.format('%x')(new Date(d));
                };
            };
        };
    }])
    .controller('OutcomeCreateCtrl', ['$scope', 'OutcomeService', '$location', '$log', 'loadedData','ErrorService', function ($scope, OutcomeService, $location, $log, loadedData,ErrorService) {

        $scope.procedureTypeCodes = loadedData[0];
        $scope.cgiICodes = loadedData[1];
        $scope.cgiSCodes = loadedData[2];
        $scope.onSelectSubMenu('outcome');
        $scope.populateCustomPatientMenu(loadedData[3]);
        $scope.medicationCodeOutcomes = loadedData[4];

        var successCallback = function (data) {
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/outcomes');
        };

        var errorCallback = function (error) {
            ErrorService.error(error);
        };

        $scope.save = function (newOutcome) {
            if ($scope.selectedPatientId) {
                OutcomeService.create($scope.selectedPatientId, newOutcome, successCallback, errorCallback);
            } else {
                $log.error("OutcomeCreateCtrl: PatientId nod defined.");
            }
        };

        $scope.canSave = function () {
            var disable = true;
            if ($scope.outcomeForm.$dirty && $scope.outcomeForm.$valid){
                if( $scope.isEndDateBeforeStartDate($scope.outcome.startDate, $scope.outcome.endDate) || $scope.isFutureDate($scope.outcome.startDate) || $scope.isFutureDate($scope.outcome.endDate)){
                    disable = true;
                } else {
                    disable = false;
                }
                return disable;
            }else {
                return disable;
            }
        };

    }])
    .controller('OutcomeEditCtrl', ['$scope', 'OutcomeService', '$location', '$log', 'loadedData','ErrorService', function ($scope, OutcomeService, $location, $log, loadedData,ErrorService) {

        $scope.procedureTypeCodes = loadedData[0];
        $scope.cgiICodes = loadedData[1];
        $scope.cgiSCodes = loadedData[2];
        $scope.outcome = loadedData[3];
        $scope.onSelectSubMenu('outcome');
        $scope.populateCustomPatientMenu(loadedData[4]);
        $scope.medicationCodeOutcomes = loadedData[5];

        var successCallback = function (data) {
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/outcomes');
        };

        var errorCallback = function (error) {
            ErrorService.error(error);
        };

        $scope.save = function (outcome) {
            OutcomeService.update(outcome.id, outcome, successCallback, errorCallback);
        };

        $scope.canSave = function () {
            var disable = true;
            if ($scope.outcomeForm.$dirty && $scope.outcomeForm.$valid){
                if( $scope.isEndDateBeforeStartDate($scope.outcome.startDate, $scope.outcome.endDate) || $scope.isFutureDate($scope.outcome.startDate) || $scope.isFutureDate($scope.outcome.endDate)){
                    disable = true;
                } else {
                    disable = false;
                }
                return disable;
            }else {
                return disable;
            }
        };

    }]);

