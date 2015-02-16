'use strict';

angular.module('icas.feedbackModule', [
        'ngResource',
        'icas.feedbackService',
        'icas.feedbackDirectives',
        'icas.security',
        'icas.loggingModule'
    ])

    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/feedback/:id', {
                templateUrl: "feedback/feedback.tpl.html",
                access: {
                    authorizedRoles: [USER_ROLES.admin, USER_ROLES.user]
                },
                controller: 'FeedbackCtrl',
                resolve: {
                    loadedFeedback: ['FeedbackService', '$log', '$q','$route', '$location','ErrorService', function(FeedbackService, $log, $q, $route, $location, ErrorService){
                        var careManagerId = $route.current.params.id;
                        if(!isNaN(careManagerId)){
                            // Set up a promise to return
                            var deferred = $q.defer();

                            // Set up our resource calls
                            var feedbackResource = FeedbackService.getFeedbackResource();
                            var feedbackData = feedbackResource.query({id:careManagerId},
                                function(response){ErrorService.success(response);},
                                function(response){ErrorService.error(response);}
                            );

                            feedbackData.$promise.then(function(response) {
                                deferred.resolve(response);
                            });
                            return deferred.promise;
                        }else{
                            $log('Feedback Service: invalid id.');
                            $location.path('/error');
                        }
                    }]
                }
            });
     }])

    .controller('FeedbackCtrl', ['$scope', 'FeedbackService', '$location', '$log', 'loadedFeedback', 'ErrorService', function ($scope, FeedbackService, $location, $log, loadedFeedback,ErrorService) {

        $scope.onSelectMenu('feedback');

        $scope.feedbackServices = loadedFeedback;

        //CRUD OPERATION
        var successCallback =  function(data){
                $scope.redirect('/feedback/' + $scope.account.id);
        };

        var errorCallback = function(error){
            $log.error(error.data.message );
            ErrorService.error(error);
        };

        $scope.updateScore = function(ratingId, id){
            var feedbackService =  $scope.getEntityById( $scope.feedbackServices , id);
            feedbackService.rating_id = ratingId;
            FeedbackService.update(feedbackService.individualprovider_id , feedbackService,successCallback,errorCallback);
        };

    }]);
