'use strict';

angular.module('icas.caremanagerService', ['ngResource'])
    .constant('CARE_MANAGER_RESOURCE_URL', 'individualproviders/:id' )
    .constant('CARE_MANAGER_STATE_RESOURCE_URL', 'statecodes' )
    .constant('CARE_MANAGER_PROVIDER_TAXONOMY_RESOURCE_URL', 'providertaxonomycodes' )
    .constant('CARE_MANAGER_COUNTRY_RESOURCE_URL', 'countrycodes' )
    .constant('CARE_MANAGER_PREFIXES_RESOURCE_URL', 'prefixes' )

    .factory('CareManagerService', ['$resource','CARE_MANAGER_RESOURCE_URL', 'CARE_MANAGER_STATE_RESOURCE_URL', 'CARE_MANAGER_PROVIDER_TAXONOMY_RESOURCE_URL','CARE_MANAGER_COUNTRY_RESOURCE_URL', 'CARE_MANAGER_PREFIXES_RESOURCE_URL', '$location',
        function($resource, CARE_MANAGER_RESOURCE_URL, CARE_MANAGER_STATE_RESOURCE_URL, CARE_MANAGER_PROVIDER_TAXONOMY_RESOURCE_URL, CARE_MANAGER_COUNTRY_RESOURCE_URL,CARE_MANAGER_PREFIXES_RESOURCE_URL, $location){

            var CareManagerResource = $resource( CARE_MANAGER_RESOURCE_URL,{id: '@id'},{'update': { method:'PUT' }} );
            var CareManagerStateResource = $resource( CARE_MANAGER_STATE_RESOURCE_URL);
            var CareManagerCountryResource = $resource( CARE_MANAGER_COUNTRY_RESOURCE_URL);
            var CareManagerProviderTaxonomyResource = $resource( CARE_MANAGER_PROVIDER_TAXONOMY_RESOURCE_URL);
            var CareManagerPrefixResource = $resource( CARE_MANAGER_PREFIXES_RESOURCE_URL);

            return {
                update : function(id, caremanager, successCb, errorCb) {
                    CareManagerResource.update({id:id},caremanager,successCb, errorCb );
                },
                
                getStateResource: function(successCb, errorCb) {
                    return CareManagerStateResource;
                },

                getProviderTaxonomyResource: function() {
                    return CareManagerProviderTaxonomyResource;
                },

                getCareManagerResource : function(){
                    return CareManagerResource;
                },
                getCareManagerCountryResource : function(){
                    return CareManagerCountryResource;
                },

                getCareManagerPrefixResource : function(){
                    return CareManagerPrefixResource;
                }
            };
        }]);