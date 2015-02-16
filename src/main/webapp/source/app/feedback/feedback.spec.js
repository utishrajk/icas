'use strict';

xdescribe('icas.feedbackModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("icas.feedbackModule");
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
            dependencies = module.value('icas.feedbackModule').requires;
        });

        it("should have icas.security as a dependency", function() {
            expect(hasModule('icas.security')).toEqual(true);
        });

        it("should have bhma.feedbackService as a dependency", function() {
            expect(hasModule('icas.feedbackService')).toEqual(true);
        });

    });
});

xdescribe("icas.feedbackModule FeedbackCtrl", function() {

    //    Arrange:
    beforeEach(module('ngRoute'));
    beforeEach(module('icas.feedbackModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, MockFeedbackService, feedback, loadedFeedback;
    beforeEach(inject(function($rootScope, $controller){
        
        var feedbacks = [{"rating_id":1,"service_id":1,"name":"Usefulness of recommendations for treating opiate/opioid clients","id":8,"individualprovider_id":2},
            {"rating_id":1,"service_id":2,"name":"Fit with daily counseling workflow","id":9,"individualprovider_id":2},
            {"rating_id":1,"service_id":3,"name":"Access to information about effective treatments and feedback_services_code","id":10,"individualprovider_id":2},
            {"rating_id":1,"service_id":4,"name":"Quality of BHAM clinical data","id":11,"individualprovider_id":2},
            {"rating_id":1,"service_id":5,"name":"Perceived effect on client outcomes","id":12,"individualprovider_id":2},
            {"rating_id":6,"service_id":6,"name":"Whether recommendations were followed","id":13,"individualprovider_id":2},
            {"rating_id":6,"service_id":7,"name":"Increasing recommendations for MAT and other treatments associated with improved clinical outcomes","id":14,"individualprovider_id":2}];

        scope = $rootScope.$new();
        feedback = feedbacks[0];
        loadedFeedback =feedbacks;
        scope.feedbackServices = loadedFeedback;

        // Create mock service
        MockFeedbackService = jasmine.createSpyObj('feedbackService', [ 'get', 'query', 'update' ]);
        MockFeedbackService.query.andReturn(feedbacks);
        MockFeedbackService.get.andReturn(feedback);


        scope.getEntityById = function (entityList, entityId) {
            for (var i = 0; i < entityList.length; i++) {
                if (entityList[i].id === parseInt(entityId)) {
                    return entityList[i];
                }
            }
        };

        $controller('FeedbackCtrl', {
            $scope: scope,
            FeedbackService: MockFeedbackService,
            loadedFeedback: loadedFeedback
        });

    }));

    //    Act and Assert
    it('Should update feedback rating', function(){
        expect(MockFeedbackService.query().length).toEqual(7);
        // Remember this is the first feedback service, so updating the rating for only first feedback service.
        feedback.rating_id = 3;
        scope.updateScore(feedback.rating_id, feedback.id, function () {}, function () {});
        expect(MockFeedbackService.query().length).toEqual(7);
        expect(MockFeedbackService.get(feedback.id).rating_id).toEqual(3);
    });

});
