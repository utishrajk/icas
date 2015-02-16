/*jslint node: true */
/*global angular, describe, it, jasmine, expect, beforeEach, compile, browserTrigger */
"use strict";

describe("Rating directive", function() {

    var scope, element, template = "<div rating score='score' max='max'></div>";

    beforeEach(module("feedback/rating.tpl.html"));

    beforeEach(function() {
        angular.mock.module('icas.feedbackDirectives');
        angular.mock.inject(function($rootScope, $compile) {
            scope = $rootScope.$new();
            element = angular.element(template);
            $compile(element)(scope);
        });
    });

    it('renders full and empty stars', function() {
        scope.score = 2;
        scope.max = 5;
        scope.$digest();

        expect(element.find(".fa.fa-star-o").length).toEqual(3);
        expect(element.find(".fa.fa-star").length).toEqual(2);
        expect(element.find(".rating-highlight").length).toEqual(0);
        expect(element.find(".rating-normal").length).toEqual(5);

        element.find("a").click();
        expect(element.find(".rating-normal").length).toEqual(5);

        element.find("a").mouseover();
        expect(element.find(".rating-normal").length).toEqual(0);
    });
});