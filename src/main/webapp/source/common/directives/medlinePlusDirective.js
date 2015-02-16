/**
 * Created by tomson.ngassa on 8/8/2014.
 */

'use strict';

angular.module("icas.medlinePlusDirectives", ['icas.medlinePlusService'])

    .directive('medlinePopup', function () {
        return {
            restrict: 'E',
//            require: 'ngModel',
            scope: {
                code: '@',
                type: '@'
            },
            template:   '<div class="dropup dropdown-preview">' +
                            ' <span class="dropdown-light">' +
                                '<span class="dropdown-hover dropup">' +
                                    ' <i class="icon-info-sign bigger-130 grey pointercursor" ng-click="getLinks(code)"></i>' +
                                            '<ul class="dropdown-menu pull-left " style="bottom: 80%;" ng-if="links">' +
                                                ' <li ng-repeat="link in links" style="text-align:left;">' +
                                                     ' <a href="{{link.href}}" ng-hide="(link.href.length == 0)" tabindex="-1" target="_blank">{{link.title}}</a>' +
                                                     ' <span ng-show="(link.href.length == 0)" class="no-entry">{{link.title}}</span>' +
                                                ' </li>' +
                                            '</ul>' +
                                '</span>' +
                            ' </span>' +
                        ' </div>',

            controller: ['$scope', 'MedlinePlusService', function ($scope, MedlinePlusService) {
                $scope.getLinks = function (code) {
                    MedlinePlusService.queryMedlinePlusLinks(
                        {code: code, type: $scope.type},
                        function (response) {
                            $scope.links = response;
                            console.log('Success');
                        },
                        function (error) {
                            console.log('Failure');
                        }
                    );
                };
            }],

            link: function (scope, element, attrs, ngModelCtrl) {
            }
        };
    });
