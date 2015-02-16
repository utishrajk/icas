'use strict';

angular.module("icas.feedbackDirectives", [])

    .directive("rating", function () {
        return {
            restrict: 'AE',
            scope: {
                score: '=',
                max: '=',
                id: '@',
                updaterating:'&'
            },
            templateUrl: "feedback/rating.tpl.html",
            link: function (scope, element, attrs) {

                scope.updateStars = function() {
                    var idx = 0;
                    scope.stars = [ ];
                    for (idx = 0; idx < scope.max; idx += 1) {
                        scope.stars.push({
                            full: scope.score > idx && scope.score !== 6
                        });
                    }
                };

                scope.hover = function(/** Integer */ idx) {
                    scope.hoverIdx = idx;
                };

                scope.stopHover = function() {
                    scope.hoverIdx = -1;
                };

                scope.starColor = function(/** Integer */ idx) {
                    var starClass = 'rating-normal';
                    if (idx <= scope.hoverIdx) {
                        starClass = 'rating-highlight';
                    }
                    return starClass;
                };

                scope.starClass = function(/** Star */ star, /** Integer */ idx) {
                    var starClass = 'fa-star-o';
                    if (star.full || idx <= scope.hoverIdx) {
                        starClass = 'fa-star';
                    }
                    return starClass;
                };

                scope.setRating = function(idx) {
                    scope.score = idx + 1;
                    scope.stopHover();
                    scope.updaterating({ratingId: scope.score , id: scope.id});
                };

                scope.$watch('score', function(newValue, oldValue) {
                    if (newValue !== null && newValue !== undefined) {
                        scope.updateStars();
                    }
                });

            }
        };
    });




