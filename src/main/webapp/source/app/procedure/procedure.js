'use strict';

angular.module('icas.procedureModule', [
        'ngResource',
        'icas.procedureService',
        'icas.procedureDirectives',
        'icas.filters',
        'icas.security',
        'icas.naturalSort',
        'icas.loggingModule'
    ])
    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/patient/:patientId/procedures', {
                templateUrl: "procedure/procedure-list.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'ProcedureListCtrl',
                resolve: {
                    loadedData: ['ProcedureService', '$log', '$q','$route', '$location','PatientService','ErrorService', function(ProcedureService, $log, $q, $route, $location, PatientService, ErrorService){
                        var patientId = $route.current.params.patientId;
                        if(!isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var procedureResource = ProcedureService.getProcedureResource();
                            var procedureResourceData = procedureResource.query(
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

                            $q.all([procedureResourceData.$promise, patientData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else{
                            $log.error('SocialHistory Resolve: invalid patient id');
                            $location.path('error');
                        }
                    }]
                }
            })
            .when('/patient/:patientId/procedures/add', {
                templateUrl: "procedure/procedure-create-edit.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'ProcedureCreateCtrl',
                resolve: {
                    loadedData: ['ProcedureService', '$log', '$q','PatientService','$route','ErrorService', function(ProcedureService, $log, $q, PatientService, $route,ErrorService){

                        var patientId = $route.current.params.patientId;
                        if( ! isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var procedureCodeResource = ProcedureService.getProcedureCodeResource();
                            var procedureCodeResourceData = procedureCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );
                            // Set up our resource calls
                            var procedureStatusCodeResource = ProcedureService.getProcedureStatusCodeResource();
                            var procedureStatusCodeResourceData = procedureStatusCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([procedureCodeResourceData.$promise, procedureStatusCodeResourceData.$promise, patientData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else{
                            console.error("Procedure: no patient id passed.");
                        }

                    }]
                }
            })
            .when('/patient/:patientId/procedures/edit/:id', {
                templateUrl: "procedure/procedure-create-edit.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'ProcedureEditCtrl',
                resolve: {
                    loadedData: ['ProcedureService', '$log', '$q','$route', '$location','PatientService','ErrorService',  function(ProcedureService, $log, $q, $route, $location, PatientService,ErrorService){
                        var procedureId = $route.current.params.id;
                        var patientId = $route.current.params.patientId;
                        if( !isNaN(procedureId) &&  !isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var procedureCodeResource = ProcedureService.getProcedureCodeResource();
                            var procedureCodeResourceData = procedureCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );
                            // Set up our resource calls
                            var procedureStatusCodeResource = ProcedureService.getProcedureStatusCodeResource();
                            var procedureStatusCodeResourceData = procedureStatusCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            // Set up our resource calls
                            var procedureOnlyResource = ProcedureService.getProcedureOnlyResource();
                            var procedureOnlyResourceData = procedureOnlyResource.get(
                                {id: procedureId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            // Wait until both resources have resolved their promises, then resolve this promise
                            $q.all([procedureCodeResourceData.$promise, procedureStatusCodeResourceData.$promise, procedureOnlyResourceData.$promise, patientData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else{
                            $log.error("Procedure Resolve: procedure id not defined." );
                            $location.path('/error');
                        }
                    }]
                }
            });
    }])

    .run(["$rootScope", "NaturalService", function($rootScope, NaturalService) {
        $rootScope.natural = function (field) {
            if(field){
                field = field === 'procedureTypeCode' ? 'procedureTypeName': field;
                return function (patients) {
                    return NaturalService.naturalValue(patients[field]);
                };
            }
        };
    }])

    .controller('ProcedureListCtrl', ['$scope', 'ProcedureService', '$location', '$log', 'loadedData','ErrorService', function ($scope, ProcedureService, $location, $log, loadedData, ErrorService) {


        var successCallback = function (data) {
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/procedures');
        };

        var errorCallback = function (error) {
            ErrorService.error(error);
        };

        $scope.onSelectSubMenu('procedure');
        $scope.populateCustomPatientMenu(loadedData[1]);

        $scope.procedures = loadedData[0];
        $scope.filteredProcedures = loadedData[0];
        $scope.totalRecords = loadedData[0].length;

        $scope.noRecords =  loadedData[0].length === 0 ? true : false;

        //Initial table page size
        $scope.pageSize = 10;
        //Calculating the the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calcating the first record
        $scope.startRecord = 1;

        $scope.deleteProcedure = function (procedureId) {
            ProcedureService.delete(procedureId,
                function (data) {
                    $scope.deleteEntityById($scope.procedures, procedureId);
                    refreshTable();
                },
                errorCallback);
        };

        var refreshTable = function () {
            $scope.setActivePage(0);
        };

        $scope.onSearch = function () {
            //In case of search resetting the page number to the first page
            $scope.pageNo = 0;
            if (angular.isDefined($scope.searchBy) && ( $scope.searchBy.length > 0)) {
                if ($scope.searchBy === 'name') {
                    $scope.composedCriteria = {procedureTypeName: "" + $scope.criteria};
                } else if ($scope.searchBy === 'status') {
                    $scope.composedCriteria = {procedureStatusCode: $scope.criteria};
                }
            } else {
                $scope.composedCriteria = $scope.criteria;
            }
            updateShowPageSize($scope.pageNo);
        };

        //Initial table page size
        $scope.pageSize = 10;

        $scope.onPageSizeChange = function () {
            if ($scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredProcedures.length;
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

        var calculatePaginationPages = function (numberProcedures, pageSize) {
            $scope.pages.length = 0;
            var noOfPages = Math.ceil(numberProcedures / pageSize);
            $scope.lastPage = noOfPages;
            for (var i = 0; i < noOfPages; i++) {
                $scope.pages.push(i);
            }
        };

        $scope.$watch('filteredProcedures.length', function (filteredSize) {
            calculatePaginationPages(filteredSize, $scope.pageSize);

            //Update showPageSize when filteredProcedure length changes
            $scope.startRecord = parseInt($scope.pageSize) * parseInt($scope.pageNo) + 1;
            var upperLimit = $scope.startRecord + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize ? filteredSize : upperLimit;

        });

        $scope.$watch('pageSize', function (newPageSize) {
            var totalProcedure = 0;
            if (angular.isDefined($scope.filteredProcedures)) {
                totalProcedure = $scope.filteredProcedures.length;
            } else if (angular.isDefined($scope.totalRecords)) {
                totalProcedure = $scope.totalRecords.length;
            }
            calculatePaginationPages(totalProcedure, newPageSize);
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
            $scope.showPageSize = upperLimit > $scope.filteredProcedures.length ? $scope.filteredProcedures.length : upperLimit;
        };

    }])
    .controller('ProcedureCreateCtrl', ['$scope', 'ProcedureService' , 'loadedData', '$location', '$log','ErrorService', function ($scope, ProcedureService , loadedData, $location, $log, ErrorService) {

        $scope.onSelectSubMenu('procedure');

        $scope.procedureCodes = loadedData[0];
        $scope.procedureStatusCodes = loadedData[1];
        $scope.populateCustomPatientMenu(loadedData[2]);

        var successCallback = function (data) {
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/procedures');
        };

        var errorCallback = function (error) {
            ErrorService.error(error);
        };

        $scope.save = function ( procedure) {
            ProcedureService.create($scope.selectedPatientId, procedure, successCallback, errorCallback);
        };

        $scope.canSave = function () {
            var disable = true;
            if ($scope.procedureForm.$dirty && $scope.procedureForm.$valid){
                if( $scope.isEndDateBeforeStartDate($scope.procedure.procedureStartDate, $scope.procedure.procedureEndDate) || $scope.isFutureDate($scope.procedure.procedureStartDate) || $scope.isFutureDate($scope.procedure.procedureEndDate)){
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
    .controller('ProcedureEditCtrl', ['$scope', 'ProcedureService' , 'loadedData', '$location', '$log','ErrorService', function ($scope, ProcedureService , loadedData, $location, $log, ErrorService) {

        $scope.onSelectSubMenu('procedure');
        $scope.procedureCodes = loadedData[0];
        $scope.procedureStatusCodes = loadedData[1];
        $scope.procedure = loadedData[2];
        $scope.populateCustomPatientMenu(loadedData[3]);

        var successCallback = function (data) {
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/procedures');
        };

        var errorCallback = function (error) {
            ErrorService.error(error);
        };

        $scope.save = function ( procedure) {
            ProcedureService.update($scope.selectedPatientId, procedure, successCallback, errorCallback);
        };

        $scope.canSave = function () {
            var disable = true;
            if ($scope.procedureForm.$dirty && $scope.procedureForm.$valid){
                if( $scope.isEndDateBeforeStartDate($scope.procedure.procedureStartDate, $scope.procedure.procedureEndDate) || $scope.isFutureDate($scope.procedure.procedureStartDate) || $scope.isFutureDate($scope.procedure.procedureEndDate)){
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
