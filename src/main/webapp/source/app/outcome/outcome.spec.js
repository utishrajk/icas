/**
 * Created by tomson.ngassa on 3/3/14.
 */

'use strict';

xdescribe('icas.outcomeModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("icas.outcomeModule");
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
            dependencies = module.value('icas.outcomeModule').requires;
        });

        it("should have ngResource as a dependency", function() {
            expect(hasModule('ngResource')).toEqual(true);
        });

        it("should have icas.outcomeService as a dependency", function() {
            expect(hasModule('icas.outcomeService')).toEqual(true);
        });

        it("should have icas.outcomeDirectives as a dependency", function() {
            expect(hasModule('icas.outcomeDirectives')).toEqual(true);
        });

        it("should have icas.naturalSort as a dependency", function() {
            expect(hasModule('icas.naturalSort')).toEqual(true);
        });

        it("should have nvd3ChartDirectives as a dependency", function() {
            expect(hasModule('nvd3ChartDirectives')).toEqual(true);
        });
    });
});

xdescribe("icas.outcomeModule OutcomeListCtrl", function () {
    beforeEach(module('ngRoute'));
    beforeEach(module('icas.outcomeModule'));
    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, $controller, mockOutcomeService, outcomeListCtrl, $compile, mockLoadedOutcomes, outcomes, conditionCodes, statusCodes, route, rootScope, location ,httpBackend;

    beforeEach(inject(function (_$compile_, $rootScope, _$controller_, _$route_, $location, $httpBackend) {
        scope = $rootScope.$new();
        $controller = _$controller_;
        route = _$route_;
        rootScope = $rootScope;
        location = $location;
        httpBackend = $httpBackend;

        $compile = _$compile_;

        var outcomes = [{id: 0,patientId: 213, cgiICode: "7", cgiIName: "Very much worse since the initiation of treatment", cgiSCode: "5", cgiSName: "Markedly ill", startDate:"05/21/2014", endDate: "05/28/2014"},
            {id: 1,patientId: 213, cgiICode: "7", cgiIName: "Very much worse since the initiation of treatment", cgiSCode: "5", cgiSName: "Markedly ill",startDate:"05/21/2014", endDate: "05/28/2014"},
            {id: 2,patientId: 213, cgiICode: "7", cgiIName: "Very much worse since the initiation of treatment", cgiSCode: "5", cgiSName: "Markedly ill",startDate:"05/21/2014", endDate: "05/28/2014"},
            {id: 3,patientId: 213, cgiICode: "7", cgiIName: "ok", cgiSCode: "5", cgiSName: "Markedly ill",startDate:"05/21/2014", endDate: "05/28/2014"}];

        var successCb = function () {
            console.log('Success');
        };

        var errorCb = function () {
            console.log('Error');
        };

        mockOutcomeService = {
            get: function (id, successCb, errorCb) {
                for (var i = 0; i < outcomes.length; i++) {
                    if (outcomes[i].id === id) {
                        return outcomes[i];
                    }
                }
            },
            delete: function (id, successCb, errorCb) {
                outcomes.splice(id, 1);
                return {status: 200};
            }
        };
        scope.selectedPatientId = 213;
        outcomeListCtrl = $controller('OutcomeListCtrl', {
            $scope: scope,
            OutcomeService: mockOutcomeService,
            loadedOutcomes: outcomes
        });
    }));

    it('should retrieve a list of conditions', function () {
        expect(scope.outcomes.length).toBeGreaterThan(0);
        expect(scope.pageSize).toEqual(10);
        expect(scope.reverse).toBeFalsy();
        expect(scope.sortField).toBeUndefined();
        expect(scope.pageNo).toEqual(0);
        expect(scope.lastPage).toEqual(0);
        expect(scope.firstPage).toEqual(0);
        expect(scope.pages).toEqual([]);
        expect(scope.pages.length).toEqual(0);
    });

    it('should sort by column', function () {
        scope.sort('cgiSName');
        expect(scope.reverse).toBeFalsy();
        scope.sort('cgiSName');
        expect(scope.reverse).toBeTruthy();
    });

    it('should delete an outcome', function () {
        scope.deleteOutcome(3);
        var outcome = mockOutcomeService.get(3);
        expect(outcome).toEqual(undefined);
    });

    it('should search outcome table', function () {
        scope.criteria = 'ok';
        scope.onSearch();
        expect(scope.composedCriteria).toEqual(scope.criteria);
    });

    it('should sort up', function(){
        scope.sortField = 'cgiIName';
        expect(scope.isSortUp(scope.sortField)).toBeTruthy();
        scope.sort(scope.sortField);
        expect(scope.isSortUp(scope.sortField)).toBeFalsy();
    });

    it('should sort down', function(){
        scope.sortField = 'cgiIName';
        expect(scope.isSortDown(scope.sortField)).toBeFalsy();
        scope.sort(scope.sortField);
        expect(scope.isSortDown(scope.sortField)).toBeTruthy();
    });

    it('should calculate pagination pages', function(){
        scope.pages = [];
        //Create 5 pages
        for (var i=0; i<5; i++) {
            scope.pages.push(i);
        }
        scope.setActivePage(2);
        expect(scope.pageNo).toEqual(2);
    });

    it('should change showPageSize on page size change', function(){
        scope.pageSize = 20;
        scope.onPageSizeChange();
        expect(scope.showPageSize).toEqual(4);  // Since the list of condition is 4
        expect(scope.startRecord).toEqual(1);
        expect(scope.pageNo).toEqual(0);

        scope.pageSize = 2;
        scope.onPageSizeChange();
        expect(scope.showPageSize).toEqual(2);  // Since the list of patients is 4
        expect(scope.startRecord).toEqual(1);
        expect(scope.pageNo).toEqual(0);
    });

    it("should update the showpage variable when the filter condition length changes", function(){
        expect(scope.pages).toEqual([]);
        //In case the filter size is less than the page size
        scope.filteredOutcomes =  [{id: 0,patientId: 213},{id: 1,patientId: 213}];

        scope.$apply();
        expect(scope.startRecord ).toEqual(1);
        expect(scope.showPageSize ).toEqual(2);

        //In case the filter size is more than the page size
        scope.filteredOutcomes =  [{id: 0,patientId: 213},{id: 1,patientId: 213},{id: 2,patientId: 213},{id: 3,patientId: 213},{id: 4,patientId: 213},
            {id: 5,patientId: 213},{id: 6,patientId: 213},{id: 7,patientId: 213},{id: 8,patientId: 213},{id: 9,patientId: 213},
            {id: 10,patientId: 213},{id: 11,patientId: 213},{id: 12,patientId: 213},{id: 13,patientId: 213},{id: 14,patientId: 213}];

        scope.$apply();
        expect(scope.startRecord ).toEqual(1);
        expect(scope.showPageSize ).toEqual(10);
        expect(scope.pages).toEqual([0,1]);
    });

    it("should update pagination pages when page size changes", function(){
        //If page size is greater than total outcomes
        expect(scope.pageSize).toEqual(10);
        scope.pageSize = 25;
        scope.$apply();
        expect(scope.pages).toEqual([0]);

        //If page size is less than total patients
        scope.pageSize = 2;
        scope.$apply();
        expect(scope.pages).toEqual([0, 1]);

        scope.totalRecords = 12;
        scope.pageSize = 10;
        scope.$apply();
        expect(scope.pages).toEqual([0]);
    });

    it("should prepare example controller", function(){
        var cgii = [], cgis = [];
        var series1 = {"key" : "CGI-I","values": cgii};
        var series2 = {"key" : "CGI-S","area": true,"values": cgis};

        scope.ExampleCtrl(scope);
        expect(scope.exampleData[0]).toBeDefined();
        expect(scope.exampleData[1]).toBeDefined();
    });

    it("should load resolve data", function(){
        expect(route.current).toBeUndefined();
        httpBackend.expectGET('outcomes/patient/213').respond(200);
        httpBackend.expectGET('outcome/outcome-list.tpl.html').respond(200);

        location.path('/patient/213/outcomes');
        rootScope.$digest();

        expect(route.current.templateUrl).toBe('outcome/outcome-list.tpl.html');
        expect(route.current.controller).toEqual('OutcomeListCtrl');
        expect(route.current.resolve.loadedOutcomes).toBeDefined();
    });

});

xdescribe("icas.outcomeModule OutcomeEditCtrl ", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('icas.outcomeModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, $controller, MockOutcomeService, OutcomeEditCtrl, loadedData, location, outcomes, cgiICodes, cgiSCodes,procedureTypeCodes, form, route, httpBackend, rootScope ;

    beforeEach(inject(function( $rootScope, _$controller_, $compile, _$location_, $route, $httpBackend){
        scope = $rootScope.$new();
        $controller = _$controller_;
        location=_$location_;
        route = $route;
        rootScope = $rootScope;
        httpBackend = $httpBackend;

        var element = angular.element(
            '<form name="outcomeForm">' +
                '<input ng-model="outcome.symptoms" name="symptoms" type="text" />' +
                '<input ng-model="outcome.tolerability" name="tolerability"  type="text" />' +
                '</form>'
        );

        scope.outcome = { symptoms:null, tolerability: null};
        $compile(element)(scope);
        scope.$digest();
        form = scope.outcomeForm;

        outcomes = [{id: 2,patientId: 213, cgiICode: "7", cgiIName: "Very much worse since the initiation of treatment", cgiSCode: "5", cgiSName: "Markedly ill", endDate: "05/28/2014"},
            {id: 1,patientId: 213, cgiICode: "7", cgiIName: "Very much worse since the initiation of treatment", cgiSCode: "5", cgiSName: "Markedly ill", endDate: "05/28/2014"}];

        cgiICodes = [{code: "1",codeSystem: "2.16.840.1.113883.6.12",codeSystemName: "CPT", displayName: "Very much improved since the initiation of treatment", originalText: null },
            {code: "2",codeSystem: "2.16.840.1.113883.6.12", codeSystemName: "CPT", displayName: "Much improved", originalText: null }];

        cgiSCodes = [{code: "1",codeSystem: "2.16.840.1.113883.6.12", codeSystemName: "CPT", displayName: "Normal, not at all ill", originalText: null },
            {code: "2",codeSystem: "2.16.840.1.113883.6.12", codeSystemName: "CPT", displayName: "Borderline mentally ill", originalText: null }];

        procedureTypeCodes =  [{code: "H0018", displayName: "Behavioral health; short-term residential" },
            {code: "H2034", displayName: "Alcohol and/or drug abuse halfway house services, per diem" } ];

        var successCb = function(){console.log('Success');};

        var errorCb =  function (){console.log('Error');};

        MockOutcomeService = {
            get : function(id, successCb, errorCb) {
                for(var i = 0; i < outcomes.length ; i++ ){
                    if(outcomes[i].id === id){
                        return outcomes[i];
                    }
                }
            },

            update : function(id, outcome, successCb, errorCb) {
                for(var i = 0; i < outcomes.length ; i++ ){
                    if(outcomes[i].id === outcome.id){
                        outcomes.splice(i, 1, outcome);
                        break;
                    }
                }
            }
        };

        scope.isFutureDate = function (currentDate) {
            var result = false;
            if (currentDate) {
                var today = new Date().getTime();
                var newDate = new Date(currentDate).getTime();

                if (newDate > today) {
                    result = true;
                }
            }
            return result;

        };

        scope.isEndDateBeforeStartDate = function (startDate, endDate) {
            var result = false;
            if (startDate && endDate) {
                var start = new Date(startDate);
                var end = new Date(endDate);
                if (end < start) {
                    result = true;
                }
            }
            return result;
        };

        loadedData = [procedureTypeCodes, cgiICodes, cgiSCodes, outcomes];

        OutcomeEditCtrl = $controller('OutcomeEditCtrl', {
            $scope: scope,
            OutcomeService:MockOutcomeService,
            loadedData: loadedData
        });

    }));

    it('Should contain default values', function(){
        expect(scope.procedureTypeCodes).toEqualData(procedureTypeCodes);
        expect(scope.cgiICodes).toEqualData(cgiICodes);
        expect(scope.cgiSCodes).toEqualData(cgiSCodes);
        expect(scope.outcome).toEqualData(outcomes);
    });

    it('Should update edit outcome', function(){
        var editOutcome =  {id: 2,patientId: 213, cgiICode: "7", cgiIName: "Ok", cgiSCode: "5", cgiSName: "Markedly ill", endDate: "05/28/2014"};
        scope.save(editOutcome,function(){},function(){} );
        var newOutcome = MockOutcomeService.get(editOutcome.id);
        expect(newOutcome).toEqualData(editOutcome);
    });

    it("Should enable save button if validation pass", function(){
        expect(form ).toBeDefined();
        expect(scope.canSave()).toBeTruthy();
        form.symptoms.$setViewValue("Symptoms1");
        form.tolerability.$setViewValue("tolerability1");
        scope.$digest();
        expect(scope.canSave()).toBeFalsy();

        scope.outcome.startDate = "04/01/2014";
        scope.outcome.endDate = "03/01/2014";
        scope.$digest();
        expect(scope.canSave()).toBeTruthy();
    });

//    xit('outcome form should be invalid initially', function(){
//        expect(form.$valid).toBeFalsy(); //because patient.firstname is required
//        expect(form.patientFirstName.$dirty).toBeFalsy();
//    });

//    xit('Should be able to save coutcome form', function(){
//        expect(scope.canSave()).toBeFalsy();
//        setPatientFirstName("Tomson");
//        expect(scope.canSave()).toBeTruthy();
//    });

    it("should load resolve data", function(){
        expect(route.current).toBeUndefined();
        httpBackend.expectGET('proceduretypecodes').respond(200);
        httpBackend.expectGET('cgiicodes').respond(200);
        httpBackend.expectGET('cgiscodes').respond(200);
        httpBackend.expectGET('outcomes/1').respond(200);
        httpBackend.expectGET('outcome/outcome-create-edit.tpl.html').respond(200);

        location.path('/patient/213/outcomes/edit/1');
        rootScope.$digest();

        expect(route.current.templateUrl).toBe('outcome/outcome-create-edit.tpl.html');
        expect(route.current.controller).toEqual('OutcomeEditCtrl');
        expect(route.current.resolve.loadedData).toBeDefined();
    });
});

xdescribe("icas.outcomeModule OutcomeCreateCtrl ", function() {
    beforeEach(module('ngRoute'));
    beforeEach(module('icas.outcomeModule'));

    var scope, $controller, MockOutcomeService, OutcomeCreateCtrl, cgiICodes, cgiSCodes, procedureTypeCodes, outcomes, form, route, httpBackend, location, rootScope;

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(inject(function( $rootScope, _$controller_, $compile, $route, $location, $httpBackend){
        scope = $rootScope.$new();
        $controller = _$controller_;
        route = $route;
        location = $location;
        httpBackend = $httpBackend;
        rootScope = $rootScope;


        var element = angular.element(
            '<form name="outcomeForm">' +
                '<input ng-model="outcome.symptoms" name="symptoms" type="text" />' +
                '<input ng-model="outcome.tolerability" name="tolerability"  type="text" />' +
                '</form>'
        );
        scope.outcome = { symptoms:null, tolerability: null};

        $compile(element)(scope);
        scope.$digest();
        form = scope.outcomeForm;

        cgiICodes = [{code: "1",codeSystem: "2.16.840.1.113883.6.12",codeSystemName: "CPT", displayName: "Very much improved since the initiation of treatment", originalText: null },
            {code: "2",codeSystem: "2.16.840.1.113883.6.12", codeSystemName: "CPT", displayName: "Much improved", originalText: null }];

        cgiSCodes = [{code: "1",codeSystem: "2.16.840.1.113883.6.12", codeSystemName: "CPT", displayName: "Normal, not at all ill", originalText: null },
            {code: "2",codeSystem: "2.16.840.1.113883.6.12", codeSystemName: "CPT", displayName: "Borderline mentally ill", originalText: null }];

        procedureTypeCodes =  [{code: "H0018", displayName: "Behavioral health; short-term residential" },
            {code: "H2034", displayName: "Alcohol and/or drug abuse halfway house services, per diem" } ];

        outcomes = [{id: 2,patientId: 213, cgiICode: "7", cgiIName: "Very much worse since the initiation of treatment", cgiSCode: "5", cgiSName: "Markedly ill", endDate: "05/28/2014"},
            {id: 1,patientId: 213, cgiICode: "7", cgiIName: "Very much worse since the initiation of treatment", cgiSCode: "5", cgiSName: "Markedly ill", endDate: "05/28/2014"}];

        var successCb = function(){console.log('Success');};

        var errorCb =  function (){console.log('Error');};

        MockOutcomeService = {
            query: function(){
              return outcomes;
            },
            create : function(id, outcome, successCb, errorCb) {
                outcomes.push(outcome);
            }
        };

        var loadedData = [procedureTypeCodes, cgiICodes, cgiSCodes];

        scope.selectedPatientId = 213;

        scope.isFutureDate = function (currentDate) {
            var result = false;
            if (currentDate) {
                var today = new Date().getTime();
                var newDate = new Date(currentDate).getTime();

                if (newDate > today) {
                    result = true;
                }
            }
            return result;

        };

        scope.isEndDateBeforeStartDate = function (startDate, endDate) {
            var result = false;
            if (startDate && endDate) {
                var start = new Date(startDate);
                var end = new Date(endDate);
                if (end < start) {
                    result = true;
                }
            }
            return result;
        };

        OutcomeCreateCtrl = $controller('OutcomeCreateCtrl', {
            $scope: scope,
            OutcomeService:MockOutcomeService,
            loadedData: loadedData
        });

    }));

    it('Should contain default values', function(){
        expect(scope.procedureTypeCodes).toEqualData(procedureTypeCodes);
        expect(scope.cgiICodes).toEqualData(cgiICodes);
        expect(scope.cgiSCodes).toEqualData(cgiSCodes);
    });

    it('Should save outcome', function(){
        var outcome =  {id: 3,patientId: 213, cgiICode: "7", cgiIName: "Very much worse since the initiation of treatment", cgiSCode: "5", cgiSName: "Markedly ill", endDate: "05/28/2014"};
        expect(MockOutcomeService.query().length).toEqual(2);
        scope.save(outcome);
        expect(MockOutcomeService.query().length).toEqual(3);
    });

    it("Should enable save button if validation pass", function(){
        expect(form ).toBeDefined();
        expect(scope.canSave()).toBeTruthy();
        form.symptoms.$setViewValue("Symptoms1");
        form.tolerability.$setViewValue("tolerability1");
        scope.$digest();
        expect(scope.canSave()).toBeFalsy();

        scope.outcome.startDate = "04/01/2014";
        scope.outcome.endDate = "03/01/2014";
        scope.$digest();
        expect(scope.canSave()).toBeTruthy();
    });

    it("should load resolve data", function(){
        expect(route.current).toBeUndefined();
        httpBackend.expectGET('proceduretypecodes').respond(200);
        httpBackend.expectGET('cgiicodes').respond(200);
        httpBackend.expectGET('cgiscodes').respond(200);
        httpBackend.expectGET('outcome/outcome-create-edit.tpl.html').respond(200);

        location.path('/patient/213/outcomes/add/');
        rootScope.$digest();

        expect(route.current.templateUrl).toBe('outcome/outcome-create-edit.tpl.html');
        expect(route.current.controller).toEqual('OutcomeCreateCtrl');
        expect(route.current.resolve.loadedData).toBeDefined();
    });
});
