
'use strict';

angular.module('icas.labResultsService', ['ngResource'])
    .constant('LABRESULTS_RESOURCE_URL', '' )
    .constant('FLAGS_RESOURCE_URL', 'resultinterpretationcodes' )
    .constant('STATUS_RESOURCE_URL', 'resultoberservationstatuscodes' )
    .constant('LABTESTNAME_RESOURCE_URL', 'resultoberservationtypes' )
    .constant('LABPANELNAME_RESOURCE_URL', 'resultorganizercodes' )
    .constant('UNITOFMEASURE_RESOURCE_URL', 'unitofmeasurecodes' )
    .constant('LABRESULT_RESOURCE_URL', 'resultorganizers/:id' )
    .constant('PATIENT_LABRESULT_URL', 'resultorganizers/patient/:patientId')

    .factory('LabResultsService', ['$resource','LABRESULTS_RESOURCE_URL','FLAGS_RESOURCE_URL','STATUS_RESOURCE_URL','LABTESTNAME_RESOURCE_URL','LABPANELNAME_RESOURCE_URL','UNITOFMEASURE_RESOURCE_URL','LABRESULT_RESOURCE_URL', 'PATIENT_LABRESULT_URL', '$location',
        function($resource, LABRESULTS_RESOURCE_URL, FLAGS_RESOURCE_URL,STATUS_RESOURCE_URL,LABTESTNAME_RESOURCE_URL,LABPANELNAME_RESOURCE_URL,UNITOFMEASURE_RESOURCE_URL,LABRESULT_RESOURCE_URL,PATIENT_LABRESULT_URL, $location){

            var flagsResources = $resource(FLAGS_RESOURCE_URL);
            var statusResources = $resource(STATUS_RESOURCE_URL);
            var labTestNameResources= $resource(LABTESTNAME_RESOURCE_URL);
            var labPanelNameResources= $resource(LABPANELNAME_RESOURCE_URL);
            var unitOfMeasureResources= $resource(UNITOFMEASURE_RESOURCE_URL);
            var labResultResource = $resource( LABRESULT_RESOURCE_URL,{id: '@id'},{'update': { method:'PUT' }} );
            var patientLabResultResource = $resource(PATIENT_LABRESULT_URL, {patientId: '@patientId'}, {'update': { method: 'PUT' }});

            var labResultsResource = $resource(LABRESULTS_RESOURCE_URL);

            return {

                getFlagsResource: function(){
                  return flagsResources;
                },

                getStatusResource: function(){
                    return statusResources;
                },

                getLabTestNamesResource: function(){
                    return labTestNameResources;
                },
                getLabPanelNamesResource: function(){
                    return labPanelNameResources;
                },

                getUnitOfMeasureResource: function(){
                    return unitOfMeasureResources;
                },

                getLabResultResource: function(){
                    return labResultResource;
                },

                getPatientLabResultResource: function(){
                    return patientLabResultResource;
                },

                create : function(labResult, successCb, errorCb) {
                    labResultResource.save( labResult, successCb, errorCb);
                },

                get : function(id, successCb, errorCb) {
                    return labResultResource.get({id:id}, successCb, errorCb);
                },

                update : function(id, labResult, successCb, errorCb) {
                    labResultResource.update({id:id},labResult,successCb, errorCb );
                },

                delete : function(id, successCb, errorCb) {
                    return labResultResource.delete({id:id}, successCb, errorCb);
                }
            };

    }]);