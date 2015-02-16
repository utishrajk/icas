/**
 * Created by tomson.ngassa on 8/8/2014.
 */


'use strict';

angular.module('icas.loggingModule', [])


.factory( "ErrorService",["$location", "$log",function( $location, $log){
    return {
        success: function(response){
            return response;
        },
        error: function(response){
            if(response.status === null || (parseInt(response.status) > 0)) {
                if(response.status === 500){
                    $location.path('/error/500');
                }else if(response.status === 404){
                    $location.path('/error/404');
                }else if(response.status === 403){
                    $location.path('/error/403');
                }else{
                    $location.path('/error');
                }
            }else{
                $log.error("Invalid status Code");
            }
        }
    };
}])
/**
 * Service that gives us a nice Angular-esque wrapper around the
 * stackTrace.js pintStackTrace() method.
 */
.factory("traceService",
    function(){
        return({
            //print: printStackTrace
        });
    }
)
/**
 * Override Angular's built in exception handler, and tell it to
 * use our new exceptionLoggingService which is defined below
 */
    .provider(
    "$exceptionHandler",{
        $get: function(exceptionLoggingService){
            return(exceptionLoggingService);
        }
    }
)
/**
 * Exception Logging Service, currently only used by the $exceptionHandler
 * it preserves the default behaviour ( logging to the console) but
 * also posts the error server side after generating a stacktrace.
 */
    .factory(
    "exceptionLoggingService",
    ["$log","$window", "traceService",
        function($log, $window, traceService){
            function error(exception, cause){

                // preserve the default behaviour which will log the error
                // to the console, and allow the application to continue running.
                $log.error.apply($log, arguments);

                // now try to log the error to the server side.
                try{
                    var errorMessage = exception.toString();

                    // use our traceService to generate a stack trace
//                        var stackTrace = traceService.print({e: exception});
                    var stackTrace = "";

                    // use AJAX (in this example jQuery) and NOT
                    // an angular service such as $http
                    $.ajax({
                        type: "POST",
                        url: "/logger",
                        contentType: "application/json",
                        data: angular.toJson({
                            url: $window.location.href,
                            message: errorMessage,
                            type: "exception",
                            stackTrace: stackTrace,
                            cause: ( cause || "")
                        })
                    });
                } catch (loggingError){
                    $log.warn("Error server-side logging failed");
                    $log.log(loggingError);
                }
            }
            return(error);
        }]
)

/**
 * Application Logging Service to give us a way of logging
 * error / debug statements from the client to the server.
 */
    .factory(
    "LoggingService",
    ["$log","$window",function($log, $window){
        return({
            error: function(message){
                // preserve default behaviour
                $log.error.apply($log, arguments);
                // send server side
                $.ajax({
                    type: "POST",
                    url: "/logger",
                    contentType: "application/json",
                    data: angular.toJson({
                        url: $window.location.href,
                        message: message,
                        type: "error"
                    })
                });
            },
            debug: function(message){
                $log.log.apply($log, arguments);
                $.ajax({
                    type: "POST",
                    url: "/clientlogger",
                    contentType: "application/json",
                    data: angular.toJson({
                        url: $window.location.href,
                        message: message,
                        type: "debug"
                    })
                });
            }
        });
    }]
);