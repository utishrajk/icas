'use strict';

angular.module('icas.conditionModule',
        ['ngResource',
         'icas.conditionService',
         'icas.conditionDirectives',
         'icas.filters',
         'icas.security',
         'icas.naturalSort',
         'icas.medlinePlusService',
         'icas.patientService',
         'icas.loggingModule'
        ]
    )
    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/patient/:patientId/conditions', {
                templateUrl: "condition/condition-list.tpl.html",
                controller: 'ConditionListCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['ConditionService', 'PatientService','$log', '$q','$route', '$location','ErrorService',  function(ConditionService,PatientService, $log, $q, $route, $location, ErrorService){
                        var patientId = $route.current.params.patientId;
                        if( ! isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var conditionResource = ConditionService.getConditionResource();
                            var conditionData = conditionResource.query(
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

                            $q.all([conditionData.$promise, patientData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else{
                            $log.error('Conditions Resolve: invalid patient id');
                            $location.path('/error');
                        }
                    }]
                }
            })
            .when('/patient/:patientId/conditions/add/', {
                templateUrl: "condition/condition-create-edit.tpl.html",
                controller: 'ConditionCreateCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['ConditionService','PatientService', '$log', '$q', '$route','ErrorService', function(ConditionService, PatientService, $log, $q, $route, ErrorService){
                        // Set up a promise to return
                        var deferred = $q.defer();
                        // Set up our resource calls
                        var problemCodeResource = ConditionService.getProblemCodeResource();
                        var problemCodeResourceData = problemCodeResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );
                        // Set up our resource calls
                        var problemStatusCodeResource = ConditionService.getProblemStatusCodeResource();
                        var problemStatusCodeResourceData = problemStatusCodeResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );

                        var patientId = $route.current.params.patientId;
                        var patientResource = PatientService.getPatientResource();
                        var patientData = patientResource.get(
                            {id: patientId},
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );


                        // Wait until both resources have resolved their promises, then resolve this promise
                        $q.all([problemCodeResourceData.$promise, problemStatusCodeResourceData.$promise, patientData.$promise]).then(function(response) {
                            deferred.resolve(response);
                        });
                        return deferred.promise;
                    }]
                }
            })
            .when('/patient/:patientId/conditions/edit/:id', {
                templateUrl: "condition/condition-create-edit.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'ConditionEditCtrl',
                resolve: {
                    loadedData: ['ConditionService','PatientService', '$log', '$q','$route', '$location','ErrorService', function(ConditionService,PatientService, $log, $q, $route, $location, ErrorService){
                        var conditionId = $route.current.params.id;
                        var patientId = $route.current.params.patientId;
                        if( !isNaN(conditionId) && !isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var problemCodeResource = ConditionService.getProblemCodeResource();
                            var problemCodeResourceData = problemCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );
                            // Set up our resource calls
                            var problemStatusCodeResource = ConditionService.getProblemStatusCodeResource();
                            var problemStatusCodeResourceData = problemStatusCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var conditionOnlyResource = ConditionService.getConditionOnlyResource();
                            var conditionOnlyResourceData = conditionOnlyResource.get(
                                {id:conditionId},
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
                            $q.all([problemCodeResourceData.$promise, problemStatusCodeResourceData.$promise , conditionOnlyResourceData.$promise, patientData.$promise ]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else{
                           $log.error('Condtion resolve: invalid id.');
                            $location.path('error');
                        }
                    }]
                }
            });
    }])

    .run(["$rootScope", "NaturalService", function($rootScope, NaturalService) {
        $rootScope.natural = function (field) {
            if(field){
                field = field === 'problemCode' ? 'problemDisplayName': field;
                return function (patients) {
                    return NaturalService.naturalValue(patients[field]);
                };
            }
        };
    }])
    .controller('ConditionListCtrl', ['$scope', 'ConditionService', 'MedlinePlusService', '$location', '$log', 'loadedData','ErrorService',  function ($scope, ConditionService, MedlinePlusService,  $location, $log, loadedData, ErrorService) {

        var successCallback =  function(data){
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/conditions' );
        };

        var errorCallback = function(error){
            ErrorService.error(error);
        };

        $scope.onSelectSubMenu('conditions');
        $scope.populateCustomPatientMenu(loadedData[1]);

        $scope.conditions = loadedData[0];
        $scope.filteredConditions = loadedData[0];
        $scope.totalRecords = loadedData[0].length;
        $scope.noRecords =  loadedData[0].length === 0 ? true : false;

        //Initial table page size
        $scope.pageSize = 10;

        //Calculating the the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calculating the first record
        $scope.startRecord = 1;

        //Declare delete on scope
        $scope.deleteCondition = function(conditionId) {
            ConditionService.delete(conditionId,
                function (data) {
                    $scope.deleteEntityById($scope.conditions, conditionId);
                    refreshTable();
                },
                errorCallback);
            $scope.conditionid = conditionId;
        };

        var refreshTable = function () {
            $scope.setActivePage(0);
        };

        $scope.onSearch = function() {
            $scope.pageNo = 0;

            if(angular.isDefined($scope.searchBy) && ($scope.searchBy.length > 0)) {
                if($scope.searchBy === 'name'){
                    $scope.composedCriteria = {problemDisplayName : "" + $scope.criteria};
                } else if($scope.searchBy === 'status'){
                    $scope.composedCriteria = {problemStatusCode : $scope.criteria};
                }
            } else {
                $scope.composedCriteria = $scope.criteria;
            }
            updateShowPageSize($scope.pageNo);

        };

        $scope.onPageSizeChange = function(){
            if( $scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredConditions.length;
            }else{
                $scope.showPageSize = $scope.pageSize ;
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

        var calculatePaginationPages = function(numberConditions, pageSize){
            $scope.pages.length = 0;
            var noOfPages = Math.ceil(numberConditions / pageSize);
            $scope.lastPage = noOfPages;
            for (var i=0; i<noOfPages; i++) {
                $scope.pages.push(i);
            }
        };

        $scope.$watch('filteredConditions.length', function(filteredSize){
            calculatePaginationPages(filteredSize,$scope.pageSize );

            //Update showPageSize when filteredContition length changes
            $scope.startRecord = parseInt($scope.pageSize) * parseInt( $scope.pageNo) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize ?filteredSize  : upperLimit;
        });

        $scope.$watch('pageSize', function(newPageSize){
            var totalCondition = 0;
            if(angular.isDefined($scope.filteredConditions)){
                totalCondition = $scope.filteredConditions.length;
            }else if(angular.isDefined($scope.totalRecords)){
                totalCondition = $scope.totalRecords.length;
            }
            calculatePaginationPages( totalCondition , newPageSize);
            //Reset current to first page after user changes the page size.
            $scope.pageNo = 0;
        });

        $scope.setActivePage = function (pageNo) {
            if (pageNo >=0 && pageNo < $scope.pages.length) {
                $scope.pageNo = pageNo;
                // Updating showing pages
                updateShowPageSize(pageNo);
            }
        };

        var updateShowPageSize = function(pageNo){
            // Updating showing pages
            $scope.startRecord = parseInt($scope.pageSize) * parseInt( pageNo) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > $scope.filteredConditions.length ? $scope.filteredConditions.length  : upperLimit;
        };

        $scope.getLinks = function(code){
            MedlinePlusService.queryMedlinePlusLinks(
                {code: code, type: 'Condition'},
                function(response){
                    $scope.links = response;
                    console.log('Success');
                },
                function(error){
                    ErrorService.error(error);
                }
            );
        };
    }])

    .controller('ConditionCreateCtrl', ['$scope', 'ConditionService', '$location', '$log', 'loadedData', 'ErrorService', function ($scope, ConditionService, $location, $log, loadedData, ErrorService) {

        $scope.problems = loadedData[0];
        $scope.problemStatusCodes = loadedData[1];

        $scope.onSelectSubMenu('conditions');
        $scope.populateCustomPatientMenu(loadedData[2]);

        var successCallback = function(data){
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/conditions' );
        };

        var errorCallback = function(error){
            ErrorService.error(error);
        };

        $scope.save = function(newCondition){

            // Twilio SMS
            var sendSms = {
//                "to": "+14437912189",
                "to": "+14436026214",
                "messageBody": newCondition.problemCode
            };

            if($scope.selectedPatientId){
                ConditionService.create($scope.selectedPatientId, newCondition, successCallback, errorCallback);
                ConditionService.sendSms($scope.selectedPatientId, sendSms, successCallback, errorCallback);

            }else{
                $log.error("ConditionCreateCtrl: PatientId nod defined.");
            }
        };

    }])
    .controller('ConditionEditCtrl', ['$scope', 'ConditionService', '$location', '$log','loadedData','ErrorService', function ($scope, ConditionService, $location, $log, loadedData, ErrorService) {

        $scope.onSelectSubMenu('conditions');

        $scope.problems = loadedData[0];
        $scope.problemStatusCodes = loadedData[1];
        $scope.condition = loadedData[2];
        $scope.populateCustomPatientMenu(loadedData[3]);

        var successCallback = function(data){
            $log.info("Success in processing request");
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/conditions' );
        };

        var errorCallback = function(error){
            $log.error(error.data.message );
            ErrorService.error(error);
        };

        $scope.save = function(condition){
            ConditionService.update(condition.id, condition, successCallback, errorCallback);
        };

        $scope.canSave = function () {
            var disable = true;
            if ($scope.conditionForm.$dirty && $scope.conditionForm.$valid){
                if( $scope.isEndDateBeforeStartDate($scope.condition.startDate, $scope.condition.endDate) || $scope.isFutureDate($scope.condition.startDate) || $scope.isFutureDate($scope.condition.endDate)){
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

