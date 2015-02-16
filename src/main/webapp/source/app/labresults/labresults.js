'use strict';

angular.module("icas.labresultsModule",
    [   'icas.security',
        'icas.labResultsService',
        'icas.LabResultsDirectives',
        'icas.patientService',
        'icas.loggingModule'
    ])

.config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

		$routeProvider			
		.when('/patient/:patientId/labresults', {
				templateUrl: "labresults/labresults-list.tpl.html",
                controller: "LabResultsListCtrl",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },

                resolve: {
                    loadedData: ['LabResultsService', '$log', '$q','PatientService','$route', function (LabResultsService, $log, $q, PatientService, $route) {

                        // Set up a promise to return
                        var deferred = $q.defer();
                        var patientId = $route.current.params.patientId;

                        if( !isNaN(patientId)){
                            var labResultsResource = LabResultsService.getPatientLabResultResource();
                            var labResultsData = labResultsResource.query(
                                {patientId: patientId},
                                function (response) {
                                },
                                function (error) {
                                    $log.error(error.data.message);
                                    $log.error("Error message: " + error.data.message);
                                }
                            );

                            var labPanelNamesResource = LabResultsService.getLabPanelNamesResource();
                            var labPanelNamesData = labPanelNamesResource.query(
                                function (response) {
                                },
                                function (error) {
                                    $log.error(error.data.message);
                                    $log.error("Error message: " + error.data.message);
                                }
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(data) {

                                },
                                function(error) {
                                    $log.error(error.data.message );
                                    $log.error("Error message: " + error.data.message );
                                }
                            );

                            $q.all([labResultsData.$promise, labPanelNamesData.$promise, patientData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else {
                            console.error("Lab result: No patient id passed");
                        }
                    }]
                }
        })
        .when('/patient/:patientId/labresults/edit/:id', {
            templateUrl: "labresults/labresults-edit.tpl.html",
            controller: "LabResultsEditCtrl",
            access: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
            },
                resolve: {
                    loadedLabData: ['LabResultsService', '$log', '$q','$route','$location', 'PatientService','ErrorService', function (LabResultsService, $log, $q, $route, $location, PatientService, ErrorService) {
                        var labresultId = $route.current.params.id;
                        var patientId = $route.current.params.patientId;

                        if( !isNaN(labresultId) &&  !isNaN(patientId)){
                            // Set up a promise to return
                            var deferred = $q.defer();

                            var flagsResource = LabResultsService.getFlagsResource();
                            var flagsData = flagsResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var statusResource = LabResultsService.getStatusResource();
                            var statusData = statusResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var labTestNamesResource = LabResultsService.getLabTestNamesResource();
                            var labTestNamesData = labTestNamesResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var labPanelNamesResource = LabResultsService.getLabPanelNamesResource();
                            var labPanelNamesData = labPanelNamesResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var unitOfMeasureResource = LabResultsService.getUnitOfMeasureResource();
                            var unitOfMeasureData = unitOfMeasureResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var labResultsResource = LabResultsService.getLabResultResource();
                            var labResultsData = LabResultsService.get(
                                labresultId,
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );


                            $q.all([flagsData.$promise, statusData.$promise, labTestNamesData.$promise, labPanelNamesData.$promise, unitOfMeasureData.$promise, labResultsData.$promise, patientData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else {
                            $log.error("Lab Results id not defined.");
                            $location.path('/error');
                        }


                    }]
                }
        })
        .when('/patient/:patientId/labresults/add', {
            templateUrl: "labresults/labresults-edit.tpl.html",
            controller: "LabResultsAddCtrl",
            access: {
                authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
            },
            resolve: {
                loadedLabData: ['LabResultsService', '$log', '$q','$route','PatientService','ErrorService','$location', function (LabResultsService, $log, $q, $route, PatientService, ErrorService, $location) {
                    var patientId = $route.current.params.patientId;

                    if( !isNaN(patientId)){
                        // Set up a promise to return
                        var deferred = $q.defer();

                        var flagsResource = LabResultsService.getFlagsResource();
                        var flagsData = flagsResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );

                        var statusResource = LabResultsService.getStatusResource();
                        var statusData = statusResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );

                        var labTestNamesResource = LabResultsService.getLabTestNamesResource();
                        var labTestNamesData = labTestNamesResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );

                        var labPanelNamesResource = LabResultsService.getLabPanelNamesResource();
                        var labPanelNamesData = labPanelNamesResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );

                        var unitOfMeasureResource = LabResultsService.getUnitOfMeasureResource();
                        var unitOfMeasureData = unitOfMeasureResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );

                        var patientResource = PatientService.getPatientResource();
                        var patientData = patientResource.get(
                            {id: patientId},
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );

                        $q.all([flagsData.$promise, statusData.$promise, labTestNamesData.$promise, labPanelNamesData.$promise, unitOfMeasureData.$promise, patientData.$promise]).then(function (response) {
                            deferred.resolve(response);
                        });
                        return deferred.promise;
                    }else {
                        console.error("Lab Result: no patient id passed.");
                        $location.path('/error');
                    }


                }]
            }
        });
}])

.run(["$rootScope", "NaturalService", function($rootScope, NaturalService) {
    $rootScope.natural = function (field) {
        if(field){
            //field = field === 'raceCode' ? 'raceCodeDisplayName': field;
            return function (labResults) {
                return NaturalService.naturalValue(labResults[field]);
            };
        }
    };
}])
.controller("LabResultsListCtrl", ["$scope","LabResultsService", "loadedData", "$route", 'ErrorService', function($scope, LabResultsService, loadedData, $route, ErrorService){
        $scope.onSelectSubMenu('labresults');

//      Initial table page size
        $scope.pageSize = 10;

        $scope.labResults = loadedData[0];
        $scope.labPanelNames = loadedData[1];
        $scope.populateCustomPatientMenu(loadedData[2]);

        $scope.filteredLabResults = $scope.labResults;
        $scope.totalRecords = $scope.labResults.length;

        //Calculating the the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calcating the first record
        $scope.startRecord = 1;

        $scope.deleteLabResult = function(labResultId){
            LabResultsService.delete(labResultId,
                function(data){
                    $scope.deleteEntityById($scope.labResults, labResultId);
                    $route.reload();
                },
                function(error){
                    ErrorService.error(error);
                });
        };

        $scope.onSearch = function(){
            $scope.pageNo = 1;

            if(angular.isDefined($scope.searchBy) && ( $scope.searchBy.length > 0) ){
                if($scope.searchBy === 'resultOrganizerDateTime'){
                    $scope.composedCriteria = {resultOrganizerDateTime : "" + $scope.criteria};
                }else if($scope.searchBy === 'resultOrganizerCodeDisplayName'){
                    $scope.composedCriteria = {resultOrganizerCodeDisplayName : $scope.criteria};
                }
            }else{
                $scope.composedCriteria = $scope.criteria;
            }
        };

        $scope.onPageSizeChange = function(){
            if( $scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredLabResults.length;
            }else{
                $scope.showPageSize = $scope.pageSize ;
            }
            //On changing page size reset to first page and set the page number to 0
            $scope.startRecord = 1;
            $scope.pageNo = 1;
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
        $scope.pageNo = 1;
        $scope.firstPage = 1;
        $scope.lastPage = 1;

        var calculatePaginationPages = function(numberPatients, pageSize){
            $scope.pages.length = 0;
            var noOfPages = Math.ceil(numberPatients / pageSize);
            $scope.lastPage = noOfPages;
            for (var i=0; i< noOfPages; i++) {
                $scope.pages.push(i);
            }
        };

        $scope.$watch('filteredLabResults.length', function(filteredSize){
            calculatePaginationPages(filteredSize,$scope.pageSize );
            //Update showPageSize when filteredPatient length changes
            $scope.startRecord = parseInt($scope.pageSize) *( parseInt( $scope.pageNo) -1) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize? filteredSize  : upperLimit;
        });

        $scope.$watch('pageSize', function(newPageSize){
            var totalPatient = 0;
            if(angular.isDefined($scope.filteredLabResults)){
                totalPatient = $scope.filteredLabResults.length;
            }else if(angular.isDefined($scope.totalRecords)){
                totalPatient = $scope.totalRecords.length;
            }
            calculatePaginationPages( totalPatient , newPageSize);
            //Reset current to first page after user changes the page size.
            $scope.pageNo = 1;
        });

        $scope.setActivePage = function (page_Num, increment) {
            var pageNo = parseInt(page_Num) + parseInt(increment);
            if (pageNo >=1 && pageNo <= $scope.pages.length) {
                $scope.pageNo = pageNo;
                // Updating showing pages
                updateShowPageSize(pageNo);
            }
        };

        $scope.$watch('pageNo', function(currentPageNo){
            var newPageNo = parseInt(currentPageNo);
            if(!isNaN(currentPageNo) && ( newPageNo > 0 && (newPageNo <= $scope.pages.length) ) ){
                $scope.paginationIndexError = "";
                updateShowPageSize(currentPageNo);
            }else{
//                $scope.pageNo = 1;
                $scope.paginationIndexError = "Page number does not exist";
            }
        });

        var updateShowPageSize = function(pageNo){
            // Updating showing pages
            $scope.startRecord = parseInt($scope.pageSize) * ( parseInt( pageNo) - 1) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            var filteredLabResultslenght = $scope.filteredLabResults.length;
            $scope.showPageSize = upperLimit > filteredLabResultslenght ? filteredLabResultslenght  : upperLimit;
        };

}])

.controller("LabResultsAddCtrl", ["$scope", "$routeParams","$route", "LabResultsService","loadedLabData","ErrorService", function($scope,$routeParams, $route, LabResultsService, loadedLabData,ErrorService){
    $scope.onSelectSubMenu('labresults');

    $scope.flags = loadedLabData[0];
    $scope.status = loadedLabData[1];
    $scope.labTestNames = loadedLabData[2];
    $scope.labPanelNames = loadedLabData[3];
    $scope.unitsOfMeasure = loadedLabData[4];
    $scope.populateCustomPatientMenu(loadedLabData[5]);

    $scope.resultObservations = [];

    $scope.addEmptyLabTest = function(){
        $scope.resultObservations.push({});
        if(!angular.isDefined( $routeParams.id)){
            $scope.editMode = true;
        }
    };

    $scope.saveLabResult = function(labResults){
        $scope.labResults.patientId = $scope.selectedPatientId;
        $scope.labResults.resultObservations = $scope.resultObservations;

        LabResultsService.create( $scope.labResults,
            function(response){
                $scope.redirect("/patient/" + $scope.selectedPatientId + "/labresults");
            },
            function(error){
                ErrorService.error(error);
            });
    };

    $scope.removeLabTest = function(index){
        $scope.resultObservations.splice(index, 1);
    };

    $scope.saveLabTest = function(labTest, index){
        $scope.resultObservations[index] = labTest;
    };

}])
.controller("LabResultsEditCtrl", ["$scope", "$routeParams","$route", "LabResultsService","loadedLabData","ErrorService", function($scope,$routeParams, $route, LabResultsService, loadedLabData, ErrorService){
        $scope.onSelectSubMenu('labresults');

        $scope.flags = loadedLabData[0];
        $scope.status = loadedLabData[1];
        $scope.labTestNames = loadedLabData[2];
        $scope.labPanelNames = loadedLabData[3];
        $scope.unitsOfMeasure = loadedLabData[4];
        $scope.labResults  = loadedLabData[5];
        $scope.populateCustomPatientMenu(loadedLabData[6]);

        $scope.resultObservations = $scope.labResults.resultObservations;

        $scope.addEmptyLabTest = function(){
            $scope.resultObservations.push({});
            if(!angular.isDefined( $routeParams.id)){
                $scope.editMode = true;
            }
        };

        $scope.saveLabResult = function(labResults){
            $scope.labResults.patientId = $scope.selectedPatientId;
            $scope.labResults.resultObservations = $scope.resultObservations;
            LabResultsService.update($routeParams.id, $scope.labResults,
                function(response){
                    console.log(response);
                    $scope.redirect("/patient/" + $scope.selectedPatientId + "/labresults");
                },
                function(error){
                    ErrorService.error(error);
                });
        };

        $scope.removeLabTest = function(index){
            $scope.resultObservations.splice(index, 1);
        };

        $scope.saveLabTest = function(labTest, index){
            $scope.resultObservations[index] = labTest;
        };

}]);
