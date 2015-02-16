'use strict';

describe('icas.feedbackService', function(){
    var module;

    beforeEach(function() {
        module = angular.module("icas.feedbackService");
    });

    it("should be registered", function() {
        expect(module).not.toEqual(null);
    });

    describe("Dependencies:", function() {
        var dependencies;
        var hasModule = function(m) {
            return dependencies.indexOf(m) >= 0;
        };

        beforeEach(function() {
            dependencies = module.value('icas.feedbackService').requires;
        });

        it("should have ngResource as a dependency", function() {
            expect(hasModule('ngResource')).toEqual(true);
        });
    });
});

describe('feedbackService', function(){
    var mockFeedbackService, $httpBackend;

    beforeEach(module('icas.feedbackService'));

    beforeEach(function () {
        angular.mock.inject(function ($injector) {
            $httpBackend = $injector.get('$httpBackend');
            mockFeedbackService = $injector.get('FeedbackService');
        });
    });

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('should update a feedback', inject(function () {
        var feedback = {"rating_id":1,"service_id":1,"name":"Usefulness of recommendations for treating opiate/opioid clients","id":8,"individualprovider_id":2};
        $httpBackend.expectPUT('individualprovidersfeedback/1').respond({status: 200});
        var status = mockFeedbackService.update(1, feedback,
            function(data ){
                status = data.status;
            },
            function(error){});
        $httpBackend.flush();
        expect(status).toEqual(200);
    }));

    it('should get list of feedbacks', inject(function () {
        var feedbacks = [{"rating_id":1,"service_id":1,"name":"Usefulness of recommendations for treating opiate/opioid clients","id":8,"individualprovider_id":2},
            {"rating_id":1,"service_id":2,"name":"Fit with daily counseling workflow","id":9,"individualprovider_id":2},
            {"rating_id":1,"service_id":3,"name":"Access to information about effective treatments and feedback_services_code","id":10,"individualprovider_id":2},
            {"rating_id":1,"service_id":4,"name":"Quality of BHAM clinical data","id":11,"individualprovider_id":2},
            {"rating_id":1,"service_id":5,"name":"Perceived effect on client outcomes","id":12,"individualprovider_id":2},
            {"rating_id":6,"service_id":6,"name":"Whether recommendations were followed","id":13,"individualprovider_id":2},
            {"rating_id":6,"service_id":7,"name":"Increasing recommendations for MAT and other treatments associated with improved clinical outcomes","id":14,"individualprovider_id":2}];

        $httpBackend.expectGET('individualprovidersfeedback').respond(feedbacks);
        var feedbackResource = mockFeedbackService.getFeedbackResource();
        var result = feedbackResource.query(function(data){result = data;},function(error){});
        $httpBackend.flush();
        expect(result).toEqualData(feedbacks);

    }));

});
