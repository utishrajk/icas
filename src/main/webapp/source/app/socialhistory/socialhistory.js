'use strict';

angular.module('icas.socialhistoryModule',
        ['ngResource',
         'icas.socialhistoryService',
         'icas.socialhistoryDirectives',
         'icas.filters',
         'icas.security',
         'icas.naturalSort',
         'icas.loggingModule'
        ]
    )
    .config(['$routeProvider','USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/patient/:patientId/socialhistories', {
                templateUrl: "socialhistory/socialhistory-list.tpl.html",
                controller: 'socialhistoryListCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['SocialhistoryService', '$log', '$q','$route', '$location','PatientService','ErrorService', function(SocialhistoryService, $log, $q, $route, $location, PatientService,ErrorService){
                        var patientId = $route.current.params.patientId;
                        if( ! isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var socialHistoryResource = SocialhistoryService.getSocialHistoryResource();
                            var socialHistoryData = socialHistoryResource.query(
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

                            $q.all([socialHistoryData.$promise, patientData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else{
                            $log.error('SocialHistory Resolve: invalid patient id');
                            $location.path('/error');
                        }
                    }]
                }
            })
            .when('/patient/:patientId/socialhistories/add', {
                templateUrl: "socialhistory/socialhistory-create-edit.tpl.html",
                controller: 'socialhistoryCreateCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['SocialhistoryService', 'PatientService','$log', '$q','$route','ErrorService', function(SocialhistoryService,PatientService, $log, $q, $route, ErrorService){

                        var patientId = $route.current.params.patientId;
                        if( ! isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var socialHistoryTypeResource = SocialhistoryService.getSocialHistoryTypeResource();
                            var socialHistoryTypeData = socialHistoryTypeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );
                            // Set up our resource calls
                            var problemStatusCodeResource = SocialhistoryService.getProblemStatusCodeResource();
                            var problemStatusCodeData = problemStatusCodeResource.query(
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
                            $q.all([socialHistoryTypeData.$promise, problemStatusCodeData.$promise, patientData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else{
                            console.error('Social history: no patient id passed.');
                        }

                    }]
                }
            })
            .when('/patient/:patientId/socialhistories/edit/:id', {
                templateUrl: "socialhistory/socialhistory-create-edit.tpl.html",
                controller: 'socialhistoryEditCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['SocialhistoryService', '$log', '$q', '$route','$location','PatientService','ErrorService',   function(SocialhistoryService, $log, $q, $route, $location, PatientService, ErrorService){
                        var socialHistoryId = $route.current.params.id;
                        var patientId = $route.current.params.patientId;

                        if( !isNaN(socialHistoryId) &&  !isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var socialHistoryTypeResource = SocialhistoryService.getSocialHistoryTypeResource();
                            var socialHistoryTypeData = socialHistoryTypeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );
                            // Set up our resource calls
                            var problemStatusCodeResource = SocialhistoryService.getProblemStatusCodeResource();
                            var problemStatusCodeData = problemStatusCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );
                            // Set up our resource calls
                            var socialHistoryOnlyResource = SocialhistoryService.getSocialHistoryOnlyResource();
                            var socialHistoryOnlyData = socialHistoryOnlyResource.get(
                                    {id:socialHistoryId},
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
                            $q.all([socialHistoryTypeData.$promise, problemStatusCodeData.$promise, socialHistoryOnlyData.$promise, patientData.$promise]).then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else{
                            $log.error('SocialHistory Resolve: invalid socialhistory id');
                            $location.path('/error');
                        }

                    }]
                }
            });
    }])

    .run(["$rootScope", "NaturalService", function($rootScope, NaturalService) {
        $rootScope.natural = function (field) {
            if(field){
                field = field === 'socialHistoryTypeCode' ? 'socialHistoryTypeName': field;
                return function (patients) {
                    return NaturalService.naturalValue(patients[field]);
                };
            }
        };
    }])

    .controller('socialhistoryListCtrl', ['$scope', 'SocialhistoryService', '$location', '$log','loadedData','ErrorService', function ($scope, SocialhistoryService, $location, $log, loadedData, ErrorService) {

        var successCallback =  function(data){
            $log.info("Success in processing the request...");
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/socialhistories' );
        };

        var errorCallback = function(error){
            ErrorService.error(error);
        };


        $scope.onSelectSubMenu('socialHistory');
        $scope.populateCustomPatientMenu(loadedData[1]);

        $scope.socialhistories = loadedData[0];
        $scope.filteredsocialhistories = loadedData[0];

        $scope.totalRecords = loadedData[0].length;
        $scope.noRecords =  loadedData[0].length === 0 ? true : false;

        //Initial table page size
        $scope.pageSize = 10;

        //Calculating the the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calcating the first record
        $scope.startRecord = 1;
        //
        //if($scope.selectedPatientId){
        //    //Getting list of socialhistories
        //
        //}else{
        //    $log.error("SocialHistories: No patient has been selected.");
        //}

        $scope.deleteSocialhistory = function(socialhistoryId){
            SocialhistoryService.delete(socialhistoryId,
                function (data) {
                    $scope.deleteEntityById($scope.socialhistories, socialhistoryId);
                    refreshTable();
                },
                errorCallback);
            $scope.socialhistoryid = socialhistoryId;
        };

        var refreshTable = function () {
            $scope.setActivePage(0);
        };

        $scope.onSearch = function(){
            //In case of search resetting the page number to the first page
            $scope.pageNo = 0;

            if(angular.isDefined($scope.searchBy) && ( $scope.searchBy.length > 0) ){
                if($scope.searchBy === 'name'){
                    $scope.composedCriteria = {socialHistoryTypeName : "" + $scope.criteria};
                }else if($scope.searchBy === 'status'){
                    $scope.composedCriteria = {socialHistoryStatusCode : $scope.criteria};
                }
            }else{
                $scope.composedCriteria = $scope.criteria;
            }
            updateShowPageSize($scope.pageNo);
        };

        //Initial table page size
        $scope.pageSize = 10;

        $scope.onPageSizeChange = function(){
            if( $scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredsocialhistories.length;
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

        var calculatePaginationPages = function(numbersocialhistories, pageSize){
            $scope.pages.length = 0;
            var noOfPages = Math.ceil(numbersocialhistories / pageSize);
            $scope.lastPage = noOfPages;
            for (var i=0; i<noOfPages; i++) {
                $scope.pages.push(i);
            }
        };

        $scope.$watch('filteredsocialhistories.length', function(filteredSize){
            calculatePaginationPages(filteredSize,$scope.pageSize );

            //Update showPageSize when filteredSocial history length changes
            $scope.startRecord = parseInt($scope.pageSize) * parseInt( $scope.pageNo) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize ? filteredSize  : upperLimit;
        });

        $scope.$watch('pageSize', function(newPageSize){
            var totalsocialhistory = 0;
            if(angular.isDefined($scope.filteredsocialhistories)){
                totalsocialhistory = $scope.filteredsocialhistories.length;
            }else if(angular.isDefined($scope.totalRecords)){
                totalsocialhistory = $scope.totalRecords.length;
            }
            calculatePaginationPages( totalsocialhistory , newPageSize);
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
            $scope.showPageSize = upperLimit > $scope.filteredsocialhistories.length ? $scope.filteredsocialhistories.length  : upperLimit;
        };
    }])
    .controller('socialhistoryCreateCtrl', ['$scope', 'SocialhistoryService', '$location', '$log', 'loadedData','ErrorService', function ($scope, SocialhistoryService, $location, $log, loadedData,ErrorService) {

        $scope.onSelectSubMenu('socialHistory');

        $scope.socialhistoryTypeCodes = loadedData[0];
        $scope.problemStatusCodes = loadedData[1];
        $scope.populateCustomPatientMenu(loadedData[2]);

        var successCallback = function(data){
            $log.info("Success in processing request");
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/socialhistories' );
        };

        var errorCallback = function(error)
        {
            ErrorService.error(error);
        };

        $scope.save = function(newSocialhistory){
            SocialhistoryService.create( $scope.selectedPatientId, newSocialhistory, successCallback, errorCallback);
        };

        $scope.canSave = function () {
            var disable = true;
            if ($scope.socialhistoryForm.$dirty && $scope.socialhistoryForm.$valid){
                if( $scope.isEndDateBeforeStartDate($scope.socialhistory.startDate, $scope.socialhistory.endDate) || $scope.isFutureDate($scope.socialhistory.startDate) || $scope.isFutureDate($scope.socialhistory.endDate)){
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
    .controller('socialhistoryEditCtrl', ['$scope', 'SocialhistoryService', '$routeParams', '$location', '$log', 'loadedData','ErrorService', function ($scope, SocialhistoryService, $routeParams, $location, $log, loadedData, ErrorService) {
        $scope.onSelectSubMenu('socialHistory');
        $scope.socialhistoryTypeCodes = loadedData[0];
        $scope.problemStatusCodes = loadedData[1];
        $scope.socialhistory = loadedData[2];
        $scope.populateCustomPatientMenu(loadedData[3]);

        var successCallback = function(data){
            $log.info("Success in processing request");
            $scope.redirect('/patient/' + $scope.selectedPatientId + '/socialhistories' );
        };

        var errorCallback = function(error){
            ErrorService.error(error);
        };

        $scope.save = function(socialhistory){
            SocialhistoryService.update(socialhistory.id, socialhistory, successCallback, errorCallback);
        };

        $scope.canSave = function () {
            var disable = true;
            if ($scope.socialhistoryForm.$dirty && $scope.socialhistoryForm.$valid){
                if( $scope.isEndDateBeforeStartDate($scope.socialhistory.startDate, $scope.socialhistory.endDate) || $scope.isFutureDate($scope.socialhistory.startDate) || $scope.isFutureDate($scope.socialhistory.endDate)){
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

