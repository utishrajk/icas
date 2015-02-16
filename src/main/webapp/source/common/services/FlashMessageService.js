/**
 * Created by tomson.ngassa on 7/12/14.
 */

'use strict';

angular.module('icas.flashMessageModule', [])

.factory("FlashMessageService",['$rootScope', function($rootScope) {
    var queue = [];
    var currentMessage = "";

    $rootScope.$on("$routeChangeSuccess", function() {
        currentMessage = queue.shift() || "";
    });

    return {
        setMessage: function(message) {
            queue.push(message);
        },
        getMessage: function() {
            return currentMessage;
        }
    };
}]);