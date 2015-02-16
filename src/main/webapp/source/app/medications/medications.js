'use strict';

angular.module("icas.medicationsModule",
    [
        'icas.security',
        'icas.medicationsService',
        'icas.patientService',
        'icas.medicationDirectives',
        'icas.loggingModule'
    ])

    .config(['$routeProvider', 'USER_ROLES', function($routeProvider, USER_ROLES) {

            $routeProvider
            .when('/patient/:patientId/medications', {
                    templateUrl: "medications/medications-list.tpl.html",
                    controller: "MedicationsListCtrl",
                    access: {
                        authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                    },
                    resolve: {
                        loadedData: ['MedicationsService', '$log', '$q','PatientService','$route','ErrorService', function (MedicationsService, $log, $q, PatientService, $route, ErrorService) {

                            // Set up a promise to return
                            var deferred = $q.defer();
                            var patientId = $route.current.params.patientId;

                            if( !isNaN(patientId)){
                                var medicationsResource = MedicationsService.getPatientMedicationResource();
                                var medicationData = medicationsResource.query(
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

                                $q.all([medicationData.$promise,patientData.$promise]).then(function (response) {
                                    deferred.resolve(response);
                                });

                                return deferred.promise;
                            }else {
                                console.error("Medications: No patient id passed");
                            }
                        }]
                    }
            })
            .when('/patient/:patientId/medications/add', {
                templateUrl: "medications/medications-create-edit.tpl.html",
                controller: "MedicationsAddCtrl",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['MedicationsService', '$log', '$q','PatientService', '$route','ErrorService', function (MedicationsService, $log, $q, PatientService, $route, ErrorService) {

                        // Set up a promise to return
                        var deferred = $q.defer();
                        var patientId = $route.current.params.patientId;

                        if( !isNaN(patientId)){
                            var routeCodeResource = MedicationsService.getRouteCodeResource();
                            var routeCodeData = routeCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var dosageFormResource = MedicationsService.getDosageFormResource();
                            var doseFormData = dosageFormResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var unitOfMeasureResource = MedicationsService.getUnitOfMeasureResource();
                            var unitOfMeasurementData = unitOfMeasureResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );


                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var medicationCodeResource = MedicationsService.getMedicationCodeResource();
                            var medicationCodeData = medicationCodeResource.query(
                                function (response) {
                                },
                                function (error) {
                                    $log.error(error.data.message);
                                    $log.error("Error message: " + error.data.message);
                                }
                            );

                            $q.all([routeCodeData.$promise, doseFormData.$promise,unitOfMeasurementData.$promise,patientData.$promise, medicationCodeData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else {
                            console.error("Medications: No patient id passed");
                        }
                    }]
                }
            })
            .when('/patient/:patientId/medications/edit/:id', {
                templateUrl: "medications/medications-create-edit.tpl.html",
                controller: "MedicationsEditCtrl",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                resolve: {
                    loadedData: ['MedicationsService', '$log', '$q','PatientService','$route','ErrorService', function (MedicationsService, $log, $q, PatientService, $route, ErrorService) {

                        // Set up a promise to return
                        var deferred = $q.defer();
                        var patientId = $route.current.params.patientId;
                        var medicationId = $route.current.params.id;

                        if( !isNaN(patientId) && !isNaN(medicationId)){

                            var mediactionResource = MedicationsService.getMedicationResource();
                            var mediactionData = mediactionResource.get(
                                {id: medicationId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var routeCodeResource = MedicationsService.getRouteCodeResource();
                            var routeCodeData = routeCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var dosageFormResource = MedicationsService.getDosageFormResource();
                            var doseFormData = dosageFormResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var unitOfMeasureResource = MedicationsService.getUnitOfMeasureResource();
                            var unitOfMeasurementData = unitOfMeasureResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                {id: patientId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            var medicationCodeResource = MedicationsService.getMedicationCodeResource();
                            var medicationCodeData = medicationCodeResource.query(
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            $q.all([mediactionData.$promise, routeCodeData.$promise, doseFormData.$promise,unitOfMeasurementData.$promise,patientData.$promise, medicationCodeData.$promise]).then(function (response) {
                                deferred.resolve(response);
                            });

                            return deferred.promise;
                        }else {
                            console.error("Medications: No patient id passed");
                        }
                    }]
                }
            });
    }])
    .run(["$rootScope", "NaturalService", function($rootScope, NaturalService) {
        $rootScope.natural = function (field) {
            if(field){
                return function (medication) {
                    return NaturalService.naturalValue(medication[field]);
                };
            }
        };
    }])
    .controller("MedicationsListCtrl", ["$scope", "MedicationsService", "loadedData","$route","ErrorService", function($scope, MedicationsService, loadedData, $route, ErrorService){
            $scope.onSelectSubMenu('medications');

//      Initial table page size
        $scope.pageSize = 10;

        $scope.medications = loadedData[0];

//        $scope.labPanelNames = loadedData[1];
        $scope.populateCustomPatientMenu(loadedData[1]);

//        $scope.status = MedicationsService.getStatus();

        $scope.filteredMedications = loadedData[0];

        $scope.totalRecords = loadedData[0].length;

        //Calculating the the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calcating the first record
        $scope.startRecord = 1;

        $scope.deleteMedication = function(medicationId){
            MedicationsService.delete(medicationId,
                function(data){
                    $scope.deleteEntityById($scope.medications, medicationId);
                    $route.reload();
                },
                function(error){
                    ErrorService.error(error);
                });
        };

        $scope.onSearch = function(){
            $scope.pageNo = 1;
            if(angular.isDefined($scope.searchBy) && ( $scope.searchBy.length > 0) ){
                if($scope.searchBy === 'medicationCodeDisplayName'){
                    $scope.composedCriteria = {medicationCodeDisplayName : "" + $scope.criteria};
                }
                else if($scope.searchBy === 'productFormCodeDisplayName'){
                    $scope.composedCriteria = {productFormCodeDisplayName : $scope.criteria};
                }
            }else{
                $scope.composedCriteria = $scope.criteria;
            }
        };


        $scope.onPageSizeChange = function(){
            if( $scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredMedications.length;
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

        $scope.$watch('filteredMedications.length', function(filteredSize){
            calculatePaginationPages(filteredSize,$scope.pageSize );

            //Update showPageSize when filteredPatient length changes
            $scope.startRecord = parseInt($scope.pageSize) *( parseInt( $scope.pageNo) -1) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize? filteredSize  : upperLimit;

        });

        $scope.$watch('pageSize', function(newPageSize){
            var totalPatient = 0;
            if(angular.isDefined($scope.filteredMedications)){
                totalPatient = $scope.filteredMedications.length;
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
            var filteredMedicationslenght = $scope.filteredMedications.length;
            $scope.showPageSize = upperLimit > filteredMedicationslenght ? filteredMedicationslenght  : upperLimit;
        };
    }])
    .controller("MedicationsAddCtrl", ["$scope","loadedData","MedicationsService","ErrorService", function($scope, loadedData, MedicationsService,ErrorService){
        $scope.onSelectSubMenu('medications');

        $scope.routecodes = loadedData[0];
        $scope.productformcodes = loadedData[1];
        $scope.unitofmeasurecodes = loadedData[2];
        $scope.populateCustomPatientMenu(loadedData[3]);
        $scope.medicationCodes = loadedData[4];

        $scope.save = function(medication){
            medication.patientId = loadedData[3].id;
            MedicationsService.create(medication,
                function(response){
                    $scope.redirect("/patient/" + $scope.selectedPatientId + "/medications");
                },
                function(error){
                    ErrorService.error(error);
                }
            );
        };

    }])
    .controller("MedicationsEditCtrl", ["$scope","loadedData","MedicationsService","ErrorService", function($scope, loadedData, MedicationsService,ErrorService){
        $scope.onSelectSubMenu('medications');
        $scope.medication = loadedData[0];
        $scope.routecodes = loadedData[1];
        $scope.productformcodes = loadedData[2];
        $scope.unitofmeasurecodes = loadedData[3];
        $scope.populateCustomPatientMenu(loadedData[4]);
        $scope.medicationCodes = loadedData[5];

        $scope.save = function(medication){
            MedicationsService.update(medication,
                function(response){
                    $scope.redirect("/patient/" + $scope.selectedPatientId + "/medications");
                },
                function(error){
                    ErrorService.error(error);
                }
            );
        };


    }]);