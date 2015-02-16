/**
 * Created by tomson.ngassa on 10/28/2014.
 */
'use strict';

angular.module('icas.medicationsService', ['ngResource'])
    .constant('ROUTE_CODE_RESOURCE_URL', 'routecodes' )
    .constant('DOSAGE_FORM_RESOURCE_URL', 'productformcodes' )
    .constant('UNITOFMEASURE_RESOURCE_URL', 'unitofmeasurecodes' )
    .constant('MEDICATION_CODE_RESOURCE_URL', 'medicationcodes' )
    .constant('MEDICATION_RESOURCE_URL', 'medications/:id' )
    .constant('PATIENT_MEDICATION_URL', 'medications/patient/:patientId')


    .factory('MedicationsService', ['$resource','ROUTE_CODE_RESOURCE_URL','DOSAGE_FORM_RESOURCE_URL','UNITOFMEASURE_RESOURCE_URL','MEDICATION_RESOURCE_URL', 'MEDICATION_CODE_RESOURCE_URL','PATIENT_MEDICATION_URL', '$location',
        function($resource,ROUTE_CODE_RESOURCE_URL,DOSAGE_FORM_RESOURCE_URL, UNITOFMEASURE_RESOURCE_URL,MEDICATION_RESOURCE_URL,MEDICATION_CODE_RESOURCE_URL,PATIENT_MEDICATION_URL, $location){

            var routeCodeResources= $resource(ROUTE_CODE_RESOURCE_URL);
            var dosageFormResources= $resource(DOSAGE_FORM_RESOURCE_URL);
            var unitOfMeasureResources= $resource(UNITOFMEASURE_RESOURCE_URL);
            var medicationCodeResources= $resource(MEDICATION_CODE_RESOURCE_URL);
            var MedicationResource = $resource( MEDICATION_RESOURCE_URL,{id: '@id'},{'update': { method:'PUT' }} );
            var patientMedicationResource = $resource(PATIENT_MEDICATION_URL, {patientId: '@patientId'}, {'update': { method: 'PUT' }});

            return {

                getRouteCodeResource: function(){
                    return routeCodeResources;
                },

                getDosageFormResource: function(){
                    return dosageFormResources;
                },

                getUnitOfMeasureResource: function(){
                    return unitOfMeasureResources;
                },

                getMedicationResource: function(){
                    return MedicationResource;
                },

                getMedicationCodeResource: function(){
                    return medicationCodeResources;
                },

                getPatientMedicationResource: function(){
                    return patientMedicationResource;
                },

                create : function(medication, successCb, errorCb) {
                    MedicationResource.save( medication, successCb, errorCb);
                },

                get : function(id, successCb, errorCb) {
                    return MedicationResource.get({id:id}, successCb, errorCb);
                },

                update : function( medication, successCb, errorCb) {
                    MedicationResource.update({id:medication.id},medication,successCb, errorCb );
                },

                delete : function(id, successCb, errorCb) {
                    return MedicationResource.delete({id:id}, successCb, errorCb);
                }
            };

        }]);