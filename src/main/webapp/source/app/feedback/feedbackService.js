'use strict';

angular.module('icas.feedbackService', ['ngResource'])

    .constant('FEEDBACK_RESOURCE_URL', 'individualprovidersfeedback/:id' )

    .factory('FeedbackService', ['$resource','FEEDBACK_RESOURCE_URL','$location',
                                function($resource, FEEDBACK_RESOURCE_URL, $location){

    var FeedbackResource = $resource( FEEDBACK_RESOURCE_URL,{id: '@id'},{'update': { method:'PUT' }} );

    return {

        update : function(id, feedback, successCb, errorCb) {
            FeedbackResource.update({id:id},feedback,successCb, errorCb );
        },

        getFeedbackResource : function(){
            return FeedbackResource;
        }

    };

}]);