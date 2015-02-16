
'use strict';

angular.module('icas.patientService', ['ngResource'])
    .constant('PATIENT_RESOURCE_URL', 'patients/:id' )
    .constant('PATIENT_STATE_RESOURCE_URL', 'statecodes' )
    .constant('PATIENT_RACE_RESOURCE_URL', 'racecodes' )
    .constant('COUNTRY_RESOURCE_URL', 'countrycodes' )
    .constant('PATIENT_WEKA_RESOURCE_URL', 'recommendation/:problemCode/:problemCodeSystem/:age/:race/:genderCode/:socialHistoryCode/:zipCode' )
    .constant('TREATMENTPLAN_RESOURCE_URL', 'treatmentplan/:id')

    .factory('PatientService', ['$resource','PATIENT_RESOURCE_URL', 'PATIENT_STATE_RESOURCE_URL','PATIENT_RACE_RESOURCE_URL','PATIENT_WEKA_RESOURCE_URL','COUNTRY_RESOURCE_URL', 'TREATMENTPLAN_RESOURCE_URL',  '$location',
                                function($resource, PATIENT_RESOURCE_URL, PATIENT_STATE_RESOURCE_URL, PATIENT_RACE_RESOURCE_URL, PATIENT_WEKA_RESOURCE_URL, COUNTRY_RESOURCE_URL, TREATMENTPLAN_RESOURCE_URL, $location){


    var PatientResource = $resource( PATIENT_RESOURCE_URL,{id: '@id'},{'update': { method:'PUT' }} );

    var TreatmentPlanResource = $resource(TREATMENTPLAN_RESOURCE_URL, {id: '@id'}, {'update': { method:'PUT' }});

    var PatientStateResource = $resource( PATIENT_STATE_RESOURCE_URL);

    var PatientRaceResource = $resource( PATIENT_RACE_RESOURCE_URL);

    var CountryResource = $resource(COUNTRY_RESOURCE_URL);

    var PatientWekaResource = $resource( PATIENT_WEKA_RESOURCE_URL,
                                        {problemCode: '@problemCode',
                                         problemCodeSystem: '@problemCodeSystem',
                                         age: '@age',
                                         race: '@race',
                                         genderCode: '@genderCode',
                                         socialHistoryCode: '@socialHistoryCode',
                                         zipCode: '@zipCode'
                                        }
                               );

    return {

        create : function(patient, successCb, errorCb) {
            PatientResource.save( patient, successCb, errorCb);
        },

        createPlan : function(treatmentPlan, successCb, errorCb) {
            TreatmentPlanResource.save( treatmentPlan, successCb, errorCb);
        },

        get : function(id, successCb, errorCb) {
            return PatientResource.get({id:id}, successCb, errorCb);
        },

        update : function(id, patient, successCb, errorCb) {
            PatientResource.update({id:id},patient,successCb, errorCb );
        },

        delete : function(id, successCb, errorCb) {
            return PatientResource.delete({id:id}, successCb, errorCb);
        },

        deleteTreatmentPlan : function(id, successCb, errorCb) {
            return TreatmentPlanResource.delete({id:id}, successCb, errorCb);
        },

        getPatientResource : function(){
            return PatientResource;
        },

        getTreatmentPlanResource : function() {
            return TreatmentPlanResource;
        },

        getStateResource : function(){
            return PatientStateResource;
        },

        getCountryResource : function(){
            return CountryResource;
        },

        getRaceResource : function(){
            return PatientRaceResource;
        },

        getWekaResource : function(){
            return PatientWekaResource;
        }

//
//        queryWeka: function(wekaObject, successCb, errorCb){
//            PatientWekaResource.get(wekaObject, successCb, errorCb );
//        }
    };

}]);