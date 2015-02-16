/**
 * Created by tomson.ngassa on 8/6/2014.
 */


'use strict';

angular.module('icas.medlinePlusService', ['ngResource'])
    .constant('MEDLINEPLUS_RESOURCE_URL', 'medlineplus/:code/:type' )


    .factory('MedlinePlusService', ['$resource','MEDLINEPLUS_RESOURCE_URL',
        function($resource, CONDITION_RESOURCE_URL, PROBLEM_CODE_URL, PROBLEM_STATUS_CODE_URL, CONDITION_ONLY_URL, SMS_SERVICE_URL, $location){

            var medlinePlusResource = $resource(CONDITION_RESOURCE_URL, {code:'@code', type:'@type'});


            return {
                queryMedlinePlusLinks : function(params, successCB, failureCB){
                    medlinePlusResource.query(params, successCB, failureCB);
                }
            };
        }]);