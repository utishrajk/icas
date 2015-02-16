'use strict';

angular.module('icas.patientModule', [
        'ngResource',
        'icas.patientService',
        'icas.patientDirectives',
        'icas.conditionService',
        'icas.socialhistoryService',
        'icas.procedureService',
        'icas.filters',
        'icas.security',
        'icas.naturalSort',
        'icas.labResultsService',
        'icas.LabResultsDirectives',
        'icas.loggingModule',
        'icas.medicationsService',
        'icas.allergyService',
        'ngDialog'
])

    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/patients', {
                templateUrl: "patient/patient-list.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'PatientListCtrl',
                resolve: {
                    loadedPatients: ['PatientService', '$log', '$q','$route','ErrorService', function(PatientService, $log, $q, $route, ErrorService){
                        // Set up a promise to return
                        var deferred = $q.defer();
                        // Set up our resource calls
                        var patientResource = PatientService.getPatientResource();
                        var patientData = patientResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );

                        patientData.$promise.then(function(response) {
                            deferred.resolve(response);
                        });
                        return deferred.promise;
                    }]
                }
            })
            .when('/patients/add', {
                templateUrl: "patient/patient-create-edit.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'PatientCreateCtrl',
                resolve: {
                    loadedData: ['PatientService', '$log', '$q','ErrorService', function(PatientService, $log, $q, ErrorService){
                        // Set up a promise to return
                        var deferred = $q.defer();
                        // Set up our resource calls
                        var stateResource = PatientService.getStateResource();
                        var stateResourceData = stateResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );
                        var countryResource = PatientService.getCountryResource();
                        var countryResourceData = countryResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );
                        // Set up our resource calls
                        var raceResource = PatientService.getRaceResource();
                        var raceResourceData = raceResource.query(
                            function(response){ErrorService.success(response);},
                            function(response){ErrorService.error(response);}
                        );
                        // Wait until both resources have resolved their promises, then resolve this promise
                        $q.all([stateResourceData.$promise, raceResourceData.$promise, countryResourceData.$promise]).then(function(response) {
                            deferred.resolve(response);
                        });
                        return deferred.promise;
                    }]
                }
            })
            .when('/patients/edit/:id', {
                templateUrl: "patient/patient-create-edit.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'PatientEditCtrl',
                resolve: {
                        loadedData: ['PatientService', '$log', '$q', '$route', '$location','ErrorService', function(PatientService, $log, $q, $route, $location, ErrorService){
                            var patientId = $route.current.params.id;
                            if( !isNaN(patientId)){
                                    // Set up a promise to return
                                    var deferred = $q.defer();

                                    var stateResource = PatientService.getStateResource();
                                   var stateResourceData = stateResource.query(
                                       function(response){ErrorService.success(response);},
                                       function(response){ErrorService.error(response);}
                                   );

                                    var countryResource = PatientService.getCountryResource();
                                    var countryResourceData = countryResource.query(
                                        function(response){ErrorService.success(response);},
                                        function(response){ErrorService.error(response);}
                                    );

                                    var raceResource = PatientService.getRaceResource();
                                    var raceResourceData = raceResource.query(
                                        function(response){ErrorService.success(response);},
                                        function(response){ErrorService.error(response);}
                                    );

                                    var patientResource = PatientService.getPatientResource();
                                    var patientResourceData = patientResource.get({id:patientId},
                                        function(response){ErrorService.success(response);},
                                        function(response){ErrorService.error(response);}
                                    );
                                    // Wait until both resources have resolved their promises, then resolve this promise
                                    $q.all([stateResourceData.$promise, raceResourceData.$promise, patientResourceData.$promise, countryResourceData.$promise]).then(function(response) {
                                        deferred.resolve(response);
                                    });
                                    return deferred.promise;
                            }else{
                                $log.error("Patient Edit Resolve: patient id not defined.");
                                $location.path('/error');
                            }
                        }]
                }
            })
            .when('/patient/:patientId/treatmentplan', {
                templateUrl: "patient/patient-treatment-plan.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'PatientTreatmentPlanCtrl',
                resolve: {
                    loadedData: ['$log', '$q','PatientService','$route','$location','ErrorService','ConditionService','SocialhistoryService',
                        function ($log, $q, PatientService, $route, $location, ErrorService, ConditionService, SocialhistoryService) {

                            var patientId = $route.current.params.patientId;

                            if (!isNaN(patientId)) {

                                var deffered = $q.defer();

                                var treatmentPlanResource = PatientService.getTreatmentPlanResource();
                                var treatmentPlanData = treatmentPlanResource.query(
                                    function (response) {
                                        ErrorService.success(response);
                                    },
                                    function (response) {
                                        ErrorService.error(response);
                                    }
                                );

                                var patientResource = PatientService.getPatientResource();
                                var patientData = patientResource.get(
                                    {id: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );
                                var conditionResource = ConditionService.getConditionResource();
                                var conditionData = conditionResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var socialhistoryResource = SocialhistoryService.getSocialHistoryResource();
                                var socialhistoryData = socialhistoryResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                $q.all([treatmentPlanData.$promise, patientData.$promise, conditionData.$promise,socialhistoryData.$promise ]).then(function (response) {
                                    deffered.resolve(response);
                                });

                                return deffered.promise;
                            } else {
                                $log.error('Conditions Resolve : invalid patient id');
                                $location.path('/error');
                            }
                        }]
                }})
            .when('/patient/:id/treatmentplanwizard', {
                templateUrl: "patient/patient-treatment-planning-wizard.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'PatientTreatmentWizardCtrl',
                resolve: {
                    loadedData: ['AllergyService', 'ConditionService', 'MedicationsService', 'SocialhistoryService', 'ProcedureService', 'PatientService', 'LabResultsService', 'OutcomeService','$log', '$q', '$route','$location','ErrorService',
                        function (AllergyService, ConditionService, MedicationsService, SocialhistoryService, ProcedureService, PatientService, LabResultsService, OutcomeService, $log, $q, $route, $location, ErrorService) {

                            var patientId = $route.current.params.id;

                            if (!isNaN(patientId)) {

                                var deffered = $q.defer();

                                var allergiesResource = AllergyService.getAllergiesResource();
                                var allergyData = allergiesResource.query({patientId: patientId},
                                    function(response) {ErrorService.success(response);},
                                    function(response) {ErrorService.error(response);}
                                );

                                var conditionResource = ConditionService.getConditionResource();
                                var conditionData = conditionResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var socialhistoryResource = SocialhistoryService.getSocialHistoryResource();
                                var socialhistoryData = socialhistoryResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var procedureResource = ProcedureService.getProcedureResource();
                                var procedureData = procedureResource.query(
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

                                var medicationResource = MedicationsService.getPatientMedicationResource();
                                var medicationData = medicationResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var labPanelNamesResource = LabResultsService.getLabPanelNamesResource();
                                var labPanelNamesData = labPanelNamesResource.query(
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var labResultsResource = LabResultsService.getPatientLabResultResource();
                                var labResultsData = labResultsResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var outcomeResource = OutcomeService.getOutcomeResource();
                                var outcomeData = outcomeResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var wekaResources = PatientService.getWekaResource();
                                var wekaData =wekaResources.get(
                                    {problemCode:'I10',problemCodeSystem:'2.16.840.1.113883.6.3',age:'2',race:'1002-5',genderCode:'Male',zipCode: '21075',socialHistoryCode: 'F11.20'},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                $q.all([conditionData.$promise,
                                        socialhistoryData.$promise,
                                        procedureData.$promise,
                                        patientData.$promise,
                                        allergyData.$promise,
                                        medicationData.$promise,
                                        labPanelNamesData.$promise,
                                        labResultsData.$promise,
                                        outcomeData.$promise,
                                        wekaData.$promise
                                    ]).then(function(response) {
                                    deffered.resolve(response);
                                });

                                return deffered.promise;
                            } else {
                                $log.error('Conditions Resolve : invalid patient id');
                                $location.path('/error');
                            }
                        }]
                }
            })
            .when('/patient/:patientId/treatmentplan/:id', {
                templateUrl: "patient/patient-treatment-planning-wizard.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'PatientTreatmentWizardCtrl',
                resolve: {
                    loadedData: ['AllergyService', 'ConditionService', 'MedicationsService', 'SocialhistoryService', 'ProcedureService', 'PatientService', 'LabResultsService', 'OutcomeService','$log', '$q', '$route','$location','ErrorService',
                        function (AllergyService, ConditionService, MedicationsService, SocialhistoryService, ProcedureService, PatientService, LabResultsService, OutcomeService, $log, $q, $route, $location, ErrorService) {

                            var patientId = $route.current.params.patientId;

                            if (!isNaN(patientId)) {

                                var deffered = $q.defer();

                                var conditionResource = ConditionService.getConditionResource();
                                var conditionData = conditionResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var socialhistoryResource = SocialhistoryService.getSocialHistoryResource();
                                var socialhistoryData = socialhistoryResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var procedureResource = ProcedureService.getProcedureResource();
                                var procedureData = procedureResource.query(
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

                                var allergiesResource = AllergyService.getAllergiesResource();
                                var allergyData = allergiesResource.query({patientId: patientId},
                                    function(response) {ErrorService.success(response);},
                                    function(response) {ErrorService.error(response);}
                                );

                                var medicationResource = MedicationsService.getPatientMedicationResource();
                                var medicationData = medicationResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var labPanelNamesResource = LabResultsService.getLabPanelNamesResource();
                                var labPanelNamesData = labPanelNamesResource.query(
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var labResultsResource = LabResultsService.getPatientLabResultResource();
                                var labResultsData = labResultsResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var outcomeResource = OutcomeService.getOutcomeResource();
                                var outcomeData = outcomeResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var treatmentId = $route.current.params.id;
                                var treatmentResource = PatientService.getTreatmentPlanResource();
                                var treatmentData = treatmentResource.get(
                                    {id: treatmentId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var wekaResources = PatientService.getWekaResource();
                                var wekaData =wekaResources.get(
                                    {problemCode:'I10',problemCodeSystem:'2.16.840.1.113883.6.3',age:'2',race:'1002-5',genderCode:'Male',zipCode: '21075',socialHistoryCode: 'F11.20'},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                $q.all([conditionData.$promise,
                                        socialhistoryData.$promise,
                                        procedureData.$promise,
                                        patientData.$promise,
                                        allergyData.$promise,
                                        medicationData.$promise,
                                        labPanelNamesData.$promise,
                                        labResultsData.$promise,
                                        outcomeData.$promise,
                                        treatmentData.$promise,
                                        wekaData.$promise
                                    ]).then(function(response) {
                                        deffered.resolve(response);
                                    });

                                return deffered.promise;
                            } else {
                                $log.error('Conditions Resolve : invalid patient id');
                                $location.path('/error');
                            }
                        }]
                }

            })
            .when('/patient/:id/patientprofile', {
                templateUrl: "patient/patient-profile.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'PatientProfileCtrl',
                resolve: {
                    loadedData: ['AllergyService', 'ConditionService', 'MedicationsService', 'SocialhistoryService', 'ProcedureService', 'PatientService', 'LabResultsService', 'OutcomeService', '$log', '$q', '$route','$location','ErrorService',
                        function (AllergyService, ConditionService, MedicationsService, SocialhistoryService, ProcedureService,PatientService, LabResultsService, OutcomeService, $log, $q, $route, $location,ErrorService) {

                            var patientId = $route.current.params.id;

                            if (!isNaN(patientId)) {

                                var deffered = $q.defer();

                                var allergiesResource = AllergyService.getAllergiesResource();
                                var allergyData = allergiesResource.query({patientId: patientId},
                                    function(response) {ErrorService.success(response);},
                                    function(response) {ErrorService.error(response);}
                                );

                                var conditionResource = ConditionService.getConditionResource();
                                var conditionData = conditionResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var medicationResource = MedicationsService.getPatientMedicationResource();
                                var medicationData = medicationResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var socialhistoryResource = SocialhistoryService.getSocialHistoryResource();
                                var socialhistoryData = socialhistoryResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var procedureResource = ProcedureService.getProcedureResource();
                                var procedureData = procedureResource.query(
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

                                var labPanelNamesResource = LabResultsService.getLabPanelNamesResource();
                                var labPanelNamesData = labPanelNamesResource.query(
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var labResultsResource = LabResultsService.getPatientLabResultResource();
                                var labResultsData = labResultsResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );

                                var outcomeResource = OutcomeService.getOutcomeResource();
                                var outcomeData = outcomeResource.query(
                                    {patientId: patientId},
                                    function(response){ErrorService.success(response);},
                                    function(response){ErrorService.error(response);}
                                );


                                $q.all([conditionData.$promise,
                                        socialhistoryData.$promise,
                                        procedureData.$promise,
                                        patientData.$promise,
                                        allergyData.$promise,
                                        medicationData.$promise,
                                        labPanelNamesData.$promise,
                                        labResultsData.$promise,
                                        outcomeData.$promise
                                    ]).then(function(response) {
                                    deffered.resolve(response);
                                });

                                return deffered.promise;
                            } else {
                                $log.error('Conditions Resolve : invalid patient id');
                                $location.path('/error');
                            }
                        }]
                }
            })
            .when('/patient/:id/demographics', {
                templateUrl: "patient/patient-list.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'PatientListCtrl',
                resolve: {
                    loadedPatients: ['PatientService', '$log', '$q','$route', '$location','ErrorService', function(PatientService, $log, $q, $route, $location, ErrorService){
                        var patientId = $route.current.params.id;
                        var queryParams = {};
                        if( !isNaN(patientId) ){
                            queryParams = {id: patientId};
                            // Set up a promise to return
                            var deferred = $q.defer();
                            // Set up our resource calls
                            var patientResource = PatientService.getPatientResource();
                            var patientData = patientResource.get(
                                queryParams,
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );
                            patientData.$promise.then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else{
                            $log.error('Patient Demographics Resolve: iinvalid patient id');
                            $location.path('/error');
                        }

                    }]
                }
            });
    }])

    .run(["$rootScope", "NaturalService", function($rootScope, NaturalService) {
        $rootScope.natural = function (field) {
            if(field){
                field = field === 'raceCode' ? 'raceCodeDisplayName': field;
                return function (patients) {
                      return NaturalService.naturalValue(patients[field]);
                };
            }
        };
    }])

    .controller('PatientListCtrl', ['$scope', 'PatientService', '$location', '$log', 'loadedPatients','$routeParams','ErrorService',   function ($scope, PatientService, $location, $log, loadedPatients, $routeParams, ErrorService) {
        if(angular.isDefined($routeParams.id)){
            PatientService.get($routeParams.id,
                function(patient){
                    $scope.patient = patient;
                    $scope.populateCustomPatientMenu(patient);
                },
                function(error){
                    ErrorService.error(error);
                }
            );
            $scope.onSelectSubMenu('demographics');

        }else {
            $scope.onSelectMenu('patients');
        }

        var patientList = [];

        if(!angular.isArray(loadedPatients)){
            patientList.push(loadedPatients);
            $scope.toggleDemographicMode(true);
        }else{
            $scope.toggleDemographicMode(false);
            patientList = loadedPatients;
        }

//      Initial table page size
        $scope.pageSize = 10;

        $scope.patients = patientList;
        $scope.filteredPatients = patientList;
        $scope.totalRecords = patientList.length;

        //Calculating the the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calcating the first record
        $scope.startRecord = 1;

        $scope.deletePatient = function(patientId){
            PatientService.delete(patientId,
                function(data){
                    $scope.deleteEntityById($scope.patients, patientId);
                    $scope.flashMessage = "Success in deleting patient.";
                    refreshTable();
                },
                function(error){
                    ErrorService.error(error);
                });
        };

        //Refreshing table by making the first page the current page
        var refreshTable = function(){
            $scope.setActivePage(0);
        };

        $scope.onSearch = function(){
            //In case of search resetting the page number to the first page
            $scope.pageNo = 1;

            if(angular.isDefined($scope.searchBy) && ( $scope.searchBy.length > 0) ){
                if($scope.searchBy === 'firstName'){
                    $scope.composedCriteria = {firstName : "" + $scope.criteria};
                }else if($scope.searchBy === 'lastName'){
                    $scope.composedCriteria = {lastName : $scope.criteria};
                }else if($scope.searchBy === 'telephone'){
                    $scope.composedCriteria = {telephone : $scope.criteria};
                }
            }else{
                $scope.composedCriteria = $scope.criteria;
            }
        };


        $scope.onPageSizeChange = function(){
            if( $scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredPatients.length;
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

        $scope.$watch('filteredPatients.length', function(filteredSize){
            calculatePaginationPages(filteredSize,$scope.pageSize );

            //Update showPageSize when filteredPatient length changes
            $scope.startRecord = parseInt($scope.pageSize) *( parseInt( $scope.pageNo) -1) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize? filteredSize  : upperLimit;

        });

        $scope.$watch('pageSize', function(newPageSize){
            var totalPatient = 0;
            if(angular.isDefined($scope.filteredPatients)){
                totalPatient = $scope.filteredPatients.length;
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
            var filteredpatientslenght = $scope.filteredPatients.length;
            $scope.showPageSize = upperLimit > filteredpatientslenght ? filteredpatientslenght  : upperLimit;
        };
    }])
    .controller('PatientCreateCtrl', ['PatientService', '$scope', '$log', '$location','loadedData','ErrorService',  function(PatientService, $scope, $log, $location, loadedData, ErrorService){

        $scope.onSelectMenu('patients');

        $scope.states = loadedData[0];
        $scope.races = loadedData[1];
        $scope.countries = loadedData[2];

        //CRUD OPERATION
        var successCallback =  function(data){
            $scope.redirect('/patients');
            //$scope.setNotification("Success in saving patient!!!");

        };

        var errorCallback = function(error){
            ErrorService.error(error);
        };

        $scope.save = function(patient){
            if($scope.patient.addressCountryCode === $scope.USCountryCode){
                patient.nonUSState = null;
            }else{
                patient.addressStateCode = "NONUS";
            }

            PatientService.create(patient,successCallback,errorCallback);
        };

        $scope.canSave = function() {
            return $scope.patientForm.$dirty && $scope.patientForm.$valid && !$scope.showCompareDateError &&  $scope.zipcodeIsANumber && $scope.validZipCodeLength;
        };

        $scope.showCompareDateError = false;

        $scope.$watch('patient.birthDate', function(birthdate){
            $scope.showCompareDateError = $scope.isFutureDate(birthdate);
        });

        $scope.validateZipCode = function(zipcode){
            if(angular.isDefined(zipcode)){
                if( $scope.isUnitedState( $scope.patient.addressCountryCode)){
                    $scope.zipcodeIsANumber = $scope.isValidNumber(zipcode);
                    if($scope.zipcodeIsANumber){
                        $scope.validZipCodeLength = $scope.isValidContactNumber(zipcode, $scope.patient.addressCountryCode,$scope.US_ZIPCODE_DIGIT_LENGTH  );
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

        $scope.$watch('patient.addressPostalCode', function(zipcode){
            $scope.validateZipCode(zipcode);
        });

        $scope.onCountrySelected = function(){
            $scope.validateZipCode($scope.patient.addressPostalCode);
        };

    }])
    .controller('PatientEditCtrl', ['PatientService', '$scope', '$log', '$location','loadedData', 'ErrorService', function(PatientService, $scope, $log, $location, loadedData, ErrorService){

        $scope.onSelectMenu('patients');

        $scope.states = loadedData[0];
        $scope.races = loadedData[1];
        $scope.patient = loadedData[2];
        $scope.countries = loadedData[3];

        //CRUD OPERATION
        var successCallback =  function(data){
            //TODO Show success to user => notification
            //Route to patient demographics id enable otherwise patient list
            if($scope.isDemographics){
                $scope.setSelectedPatient($scope.patient);
                $scope.redirect('/patient/' + $scope.selectedPatientId + '/demographics');
            }else{
                $scope.redirect('/patients');
            }
        };

        var errorCallback = function(error){
            ErrorService.error(error);
        };

        $scope.save = function(patient){
            if($scope.patient.addressCountryCode === $scope.USCountryCode){
                patient.nonUSState = null;
            }else{
                patient.addressStateCode = "NONUS";
            }
            PatientService.update(patient.id , patient,successCallback,errorCallback);
        };

        $scope.canSave = function() {
            return $scope.patientForm.$dirty && $scope.patientForm.$valid && !$scope.showCompareDateError &&  $scope.zipcodeIsANumber && $scope.validZipCodeLength;
        };

        $scope.showCompareDateError = false;

        $scope.$watch('patient.birthDate', function(birthdate){
            $scope.showCompareDateError = $scope.isFutureDate(birthdate);
        });

        $scope.validateZipCode = function(zipcode){
            if(angular.isDefined(zipcode)){
                if( $scope.isUnitedState( $scope.patient.addressCountryCode)){
                    $scope.zipcodeIsANumber = $scope.isValidNumber(zipcode);
                    if($scope.zipcodeIsANumber){
                        $scope.validZipCodeLength = $scope.isValidContactNumber(zipcode, $scope.patient.addressCountryCode,$scope.US_ZIPCODE_DIGIT_LENGTH  );
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

        $scope.$watch('patient.addressPostalCode', function(zipcode){
            $scope.validateZipCode(zipcode);
        });

        $scope.onCountrySelected = function(){
            $scope.validateZipCode($scope.patient.addressPostalCode);
        };

    }])
    .controller('PatientTreatmentWizardCtrl', ['$scope', 'PatientService', '$location', '$log', 'loadedData', '$routeParams', '$window', 'ErrorService','ngDialog', function ($scope, PatientService, $location, $log, loadedData, $routeParams, $window, ErrorService, ngDialog) {

        $scope.onSelectSubMenu('treatmentPlan');

        $scope.conditions = loadedData[0];
        $scope.socialhistories = loadedData[1];
        $scope.procedures = loadedData[2];
        $scope.patient = loadedData[3];
        $scope.populateCustomPatientMenu(loadedData[3]);
        $scope.allergies = loadedData[4];
        $scope.medications = loadedData[5];
//        $scope.labPanelNames = loadedData[6];
        $scope.labResults = loadedData[7];
        $scope.outcomes = loadedData[8];
        $scope.treatmentplan = loadedData[9];

        var recommendations;

        if(loadedData.length === 11){
            recommendations = loadedData[10].recommendations[0];
        }else if(loadedData.length === 10){
           recommendations = loadedData[9].recommendations[0];
        }

        if(angular.isDefined(recommendations)){
            $scope.goals = recommendations.patientGoal ;
            $scope.objectives = recommendations.objectives ;
            $scope.interventions = recommendations.probabilityDistributionList;
        }
        
        $scope.longTermGoal = false;
        //$scope.shortTermGoal = false;

        $scope.steps = ['one', 'two', 'three', 'four'];
        $scope.step = 0;

        $scope.one = 'active';
        $scope.two = '';
        $scope.three = '';
        $scope.four = '';

        $scope.isFirstStep = function() {
            return $scope.step === 0; 
        };

        $scope.isLastStep = function() {
            return $scope.step === ($scope.steps.length - 1);
        };

        $scope.isCurrentStep = function(step) {
            return $scope.step === step;
        };

        $scope.setCurrentStep = function(step) {
            $scope.step = step;
        };

        $scope.getCurrentStep = function() {
            $scope.currentStep =  $scope.steps[$scope.step];
            if( $scope.currentStep === 'one') {
                $scope.four = '';
                $scope.three = '';
                $scope.two = '';
                $scope.one = 'active';
            }else if( $scope.currentStep === 'two') {
                $scope.four = '';
                $scope.three = '';
                $scope.two = 'active';
                $scope.one = 'complete';
            }else if( $scope.currentStep === 'three') {
                $scope.four = '';
                $scope.three = 'active';
                $scope.two = 'complete';
                $scope.one = 'complete';
            }else if( $scope.currentStep === 'four') {
                $scope.four = 'active';
                $scope.three = 'complete';
                $scope.two = 'complete';
                $scope.one = 'complete';
            }
            return $scope.steps[$scope.step];
        };

        $scope.getNextLabel = function() {
            return ($scope.isLastStep()) ? 'Submit' : 'Next';
        };

        $scope.handlePrevious = function() {
            $scope.step -= ($scope.isFirstStep()) ? 0 : 1;
        };

        $scope.handleNext = function() {
            if($scope.isLastStep()) {
                $scope.save();
            } else {
                if($scope.step === 0){
                    //getWekaData();
                }
                $scope.step += 1;
            }
        };

//        var getWekaData = function(){
//            var condition = $scope.conditions[0];
//            var socialHistory = $scope.socialhistories[0];
//
////            var wekaObject = {
////                problemCode:condition.problemCode,
////                problemCodeSystem:'2.16.840.1.113883.6.3',
////                age:'5',
////                race:$scope.selectedPatient.raceCode,
////                genderCode:$scope.selectedPatient.administrativeGenderCodeDisplayName,
////                zipCode: $scope.selectedPatient.addressPostalCode,
////                socialHistoryCode: socialHistory.socialHistoryCode
////            };
//
//
//
//            var wekaObject = {
//                problemCode:'I10',
//                problemCodeSystem:'2.16.840.1.113883.6.3',
//                age:'2',
//                race:'1002-5',
//                genderCode:'Male',
//                zipCode: '21075',
//                socialHistoryCode: 'F11.20'
//            };
//
//            PatientService.queryWeka(
//                wekaObject,
//                function(wekaResponse){
//
//
//                    if(recommendations){
//                        $scope.goals = recommendations.patientGoal ;
//                        $scope.objectives = recommendations.objectives ;
//                        $scope.interventions = recommendations.probabilityDistributionList;
//                    }else{
//                        $log.error("Empty recoommendation object from Weka.");
//                    }
//
//                },
//                function(error){
//                    ErrorService.error(error);
//                }
//            );
//        };

        $scope.openInfoButtonPage = function(){
            $window.open("http://www.nlm.nih.gov/medlineplus/drugabuse.html");
        };

        $scope.openEvidencePage = function(){
            //$window.open("assets/documents/practice_guidelines_treatment_patient_substance_use_disorder_2_edition_apa.pdf");

            ngDialog.open({template:'evidence-dialog'});
        };

        // Locally Persist Selected Treatment Plan Data

        // 1. Goals (Short Term)
        $scope.shortTermGoal = "";
        $scope.toggleShortTerm = function(goal) {
            if ($scope.shortTermGoal === "") {
                $scope.shortTermGoal = goal;
            } else {
                $scope.shortTermGoal = "";
            }
        };

        // 1. Goals (Long Term)
        $scope.longTermGoal = "";
        $scope.toggleLongTerm = function(goal) {
            if ($scope.longTermGoal === "") {
                $scope.longTermGoal = goal;
            } else {
                $scope.longTermGoal = "";
            }
        };


        // 2. Objectives
        $scope.selectedObjectives=[];
        $scope.toggleObjective = function(objective) {
            var idx = $scope.selectedObjectives.indexOf(objective);
            // if currently selected
            if (idx > -1) {
                $scope.selectedObjectives.splice(idx, 1);
            }
            // is newly selected
            else {
                $scope.selectedObjectives.push(objective);
            }
        };

        // 3. Interventions
        $scope.selectedInterventions=[];
        $scope.toggleIntervention = function(inputIntervention) {

            $scope.intervention = inputIntervention;
            $scope.intervention.isChecked = false;


            var checked = true;

            var intervention =  {
                    cptCode:inputIntervention.service,
                description:inputIntervention.description,
                      notes:"intervention notes",
                 targetDate:$scope.intervention.targetDate,
             resolutionDate:"11/11/2014"
            };

            for (var i = -1; i < $scope.selectedInterventions.length; i++) {
                if (angular.equals(intervention, $scope.selectedInterventions[i+1])){
                    $scope.selectedInterventions.splice(i+1, 1);
                    checked = false;
                }
            }
            if (checked){
                $scope.selectedInterventions.push(intervention);
            }

        };


        $scope.interventionExist = function(intervention) {
            for (var i = -1; i < $scope.selectedInterventions.length; i++) {
                if (angular.equals(intervention, $scope.selectedInterventions[i+1])){
                    return false;
                }
            }
            return true;
        };


        //CRUD OPERATION
        var successCallback =  function(data){
            $scope.redirect('/patient/' + $scope.patient.id + '/treatmentplan');
        };

        var errorCallback = function(error){
            ErrorService.error(error);
        };

        $scope.save = function(){
            var treatmentPlan = {
                primaryDiagnosis: "mock primarydiagonosis",
                longTermGoal:$scope.longTermGoal,
                shortTermGoal:$scope.shortTermGoal,
                goalsNote: $scope.goals.goalNote,
                objectivesNote: "mock note",
                evidence: "mock url",
                objectives:$scope.selectedObjectives,
                interventions:$scope.selectedInterventions
            };
            console.log(treatmentPlan);

            PatientService.createPlan(treatmentPlan,successCallback,errorCallback);

        };

    }])
    .controller("PatientTreatmentPlanCtrl", ["$scope","PatientService","LabResultsService", "loadedData", "$route","ErrorService",  function($scope, PatientService, LabResultsService, loadedData, $route, ErrorService){

        $scope.onSelectSubMenu('treatmentPlan');

        // Initial table page size
        $scope.pageSize = 10;

        $scope.treatmentplans = loadedData[0];
        $scope.filteredTreatmentplans  = loadedData[0];
        $scope.totalRecords = loadedData[0].length;

        $scope.patient = loadedData[1];
        $scope.populateCustomPatientMenu(loadedData[1]);
        $scope.conditions = loadedData[2];
        $scope.socialhistory = loadedData[3];

        $scope.patient = loadedData[1];

        $scope.patient = loadedData[1];

        //Calculating the the size of the shown page
        $scope.showPageSize = $scope.pageSize > $scope.totalRecords ? $scope.totalRecords : $scope.pageSize;

        //Calcating the first record
        $scope.startRecord = 1;

        $scope.deleteTreatmentplan = function(planId){

            PatientService.delete(patientId,
                function(data){
                    $scope.deleteEntityById($scope.treatmentplans, planId);
                    $scope.flashMessage = "Success in deleting Treatment Plan.";
                    refreshTable();
                },
                function(error){
                    ErrorService.error(error);
                });
        };

        //Refreshing table by making the first page the current page
        var refreshTable = function(){
            $scope.setActivePage(0);
        };

        $scope.onSearch = function(){
            //In case of search resetting the page number to the first page
            $scope.pageNo = 1;

            if(angular.isDefined($scope.searchBy) && ( $scope.searchBy.length > 0) ){
                if($scope.searchBy === 'planCreateTime'){
                    $scope.composedCriteria = {planCreateTime : $scope.criteria};
                }else if($scope.searchBy === 'primaryDiagnosis'){
                    $scope.composedCriteria = {primaryDiagnosis : $scope.criteria};
                }
            }else{
                $scope.composedCriteria = $scope.criteria;
            }
        };


        $scope.onPageSizeChange = function(){
            if( $scope.pageSize > $scope.totalRecords) {
                $scope.showPageSize = $scope.filteredTreatmentplans .length;
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

        $scope.$watch('filteredTreatmentplans .length', function(filteredSize){
            calculatePaginationPages(filteredSize,$scope.pageSize );

            //Update showPageSize when filteredPatient length changes
            $scope.startRecord = parseInt($scope.pageSize) *( parseInt( $scope.pageNo) -1) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            $scope.showPageSize = upperLimit > filteredSize? filteredSize  : upperLimit;

        });

        $scope.$watch('pageSize', function(newPageSize){
            var totalPatient = 0;
            if(angular.isDefined($scope.filteredTreatmentplans )){
                totalPatient = $scope.filteredTreatmentplans .length;
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
                $scope.paginationIndexError = "Page number does not exist";
            }
        });

        var updateShowPageSize = function(pageNo){
            // Updating showing pages
            $scope.startRecord = parseInt($scope.pageSize) * ( parseInt( pageNo) - 1) + 1;
            var upperLimit = $scope.startRecord  + parseInt($scope.pageSize) - 1;
            var filteredTreatmentplanslength = $scope.filteredTreatmentplans .length;
            $scope.showPageSize = upperLimit > filteredTreatmentplanslength ? filteredTreatmentplanslength  : upperLimit;
        };


        $scope.deleteTreatmentPlan = function(planId){
            PatientService.deleteTreatmentPlan(planId,
                function(data){
                    $scope.deleteEntityById($scope.treatmentplans, planId);
                    $scope.flashMessage = "Success in deleting plan.";
                    refreshTable();
                },
                function(error){
                    ErrorService.error(error);
                });
        };

        $scope.addThreeDots = function(str){
            if(angular.isDefined(str)){
                return str + " ...";
            }else{
                return "";
            }

        };

    }])
    .controller('PatientProfileCtrl', ['$scope','$log', 'loadedData', '$routeParams', function($scope, $log, loadedData, $routeParams){

        $scope.onSelectSubMenu('summaryCareRecord');

        $scope.conditions = loadedData[0];
        $scope.socialhistories = loadedData[1];
        $scope.procedures = loadedData[2];
        $scope.patient = loadedData[3];
        $scope.populateCustomPatientMenu(loadedData[3]);
        $scope.allergies = loadedData[4];
        $scope.medications = loadedData[5];
//        $scope.labPanelNames = loadedData[6];
        $scope.labResults = loadedData[7];
        $scope.outcomes = loadedData[8];

     }]);

