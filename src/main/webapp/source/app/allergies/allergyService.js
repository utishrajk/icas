/**
 * Created by utish.rajkarnikar on 10/28/14.
 */
'use strict';

angular.module('icas.allergyService', ['ngResource'])
    .factory('AllergyService', ['$resource', '$location',
        function ($resource, $location) {

            var AllergiesResource = $resource('allergys/patient/:patientId', {patientId: '@patientId'}, {'update': { method: 'PUT' }});
            var AllergyByIdResource = $resource('allergys/:id', {id: '@id'}, {'update': { method: 'PUT'}});
            var AdverseEventTypeResource = $resource('adverseeventtypecodes');
            var AllergenResource = $resource('allergencodes');
            var AllergyReactionResource = $resource('allergyreactioncodes');
            var AllergySeverityResource = $resource('allergyseveritycodes');

            return {
                getAllergiesResource: function () {
                    return AllergiesResource;
                },

                delete: function (id, succesCb, errorCb) {
                    AllergyByIdResource.delete({id: id}, succesCb, errorCb);
                },

                create: function (patientId, allergy, successCb, errorCb) {
                    AllergiesResource.save({patientId: patientId}, allergy, successCb, errorCb);
                },

                update: function (allergy, id, successCb, errorCb) {
                    AllergyByIdResource.update({id: id}, allergy, successCb, errorCb);
                },

                getAdverseEventTypeResource: function () {
                    return AdverseEventTypeResource;
                },

                getAllergenResource: function () {
                    return AllergenResource;
                },

                getAllergyReactionResource: function () {
                    return AllergyReactionResource;
                },

                getAllergySeverityResource: function () {
                    return AllergySeverityResource;
                },

                getAllergyByIdResource: function() {
                    return AllergyByIdResource;
                }

            };
        }
    ]);
