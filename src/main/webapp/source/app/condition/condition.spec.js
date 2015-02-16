'use strict';

xdescribe('icas.conditionModule', function () {
    var module;

    beforeEach(function () {
        module = angular.module("icas.conditionModule");
    });

    it("should be registered", function () {
        expect(module).not.toEqual(null);
    });

    describe("Dependencies:", function () {

        var dependencies;

        var hasModule = function (m) {
            return dependencies.indexOf(m) >= 0;
        };

        beforeEach(function () {
            dependencies = module.value('icas.conditionModule').requires;
        });

        it("should have ngResource as a dependency", function () {
            expect(hasModule('ngResource')).toEqual(true);
        });

        it("should have icas.conditionService as a dependency", function () {
            expect(hasModule('icas.conditionService')).toEqual(true);
        });

        it("should have icas.conditionDirectives as a dependency", function () {
            expect(hasModule('icas.conditionDirectives')).toEqual(true);
        });

        it("should have icas.filters as a dependency", function () {
            expect(hasModule('icas.filters')).toEqual(true);
        });

        it("should have icas.naturalSort as a dependency", function () {
            expect(hasModule('icas.naturalSort')).toEqual(true);
        });
    });

});

xdescribe("icas.conditionModule ConditionListCtrl", function () {
    beforeEach(module('ngRoute'));
    beforeEach(module('icas.conditionModule'));

    var scope, $controller, mockConditionService, conditionListCtrl, $route, $compile, mockLoadedConditions, conditions, conditionCodes, statusCodes, rootScope, location, httpBackend;

    beforeEach(inject(function (_$compile_, $rootScope, _$controller_, _$route_, $location, $httpBackend) {
        scope = $rootScope.$new();
        $controller = _$controller_;
        $route = _$route_;
        $compile = _$compile_;

        rootScope = $rootScope;
        location = $location;
        httpBackend = $httpBackend;

        conditions = [
            {id: "1", name: "c1"},
            {id: "2", name: "c2"},
            {id: "3", name: "c3"},
            {id: "4", name: "c4"} //to be deleted
        ];

        conditionCodes = [
            {"code": "c1", "displayName": "d1"},
            {"code": "c2", "displayName": "d2"}
        ];

        statusCodes = [
            {"code": "s1", "displayName": "d1"},
            {"code": "s2", "displayName": "d2"}
        ];

        var successCb = function () {
            console.log('Success');
        };

        var errorCb = function () {
            console.log('Error');
        };

        mockConditionService = {
            query: function (conditionId, successCb, errorCb) {
                return conditions;
            },

            create: function (conditionId, condition, successCb, errorCb) {
                return {status: 201};
            },

            get: function (id, successCb, errorCb) {
                for (var i = 0; i < conditions.length; i++) {
                    if (conditions[i].id === id) {
                        return conditions[i];
                    }
                }
            },

            update: function (id, condition, successCb, errorCb) {
                return {status: 200};
            },

            delete: function (id, successCb, errorCb) {
                conditions.splice(id, 1);
                return {status: 200};
            },

            getProblems: function (successCb, errorCb) {
                return conditionCodes;
            },

            getProblemStatusCodes: function (successCb, errorCb) {
                return statusCodes;
            }
        };

        mockLoadedConditions = mockConditionService.query(213, successCb, errorCb);

        scope.selectedPatientId = 213;
        conditionListCtrl = $controller('ConditionListCtrl', {
            $scope: scope,
            ConditionService: mockConditionService,
            loadedConditions: mockLoadedConditions
        });
    }));

    it('should retrieve a list of conditions', function () {
        expect(scope.conditions.length).toBeGreaterThan(0);
        expect(scope.pageSize).toEqual(10);
        expect(scope.reverse).toBeFalsy();
        expect(scope.sortField).toBeUndefined();
        expect(scope.pageNo).toEqual(0);
        expect(scope.lastPage).toEqual(0);
        expect(scope.firstPage).toEqual(0);
        expect(scope.pages).toEqual([]);
        expect(scope.pages.length).toEqual(0);
    });

    it('should sort by name column', function () {
        scope.sort('problemDisplayName');
        expect(scope.reverse).toBeFalsy();
        scope.sort('problemDisplayName');
        expect(scope.reverse).toBeTruthy();
    });

    it('should sort by status column', function () {
        scope.sort('problemStatusCode');
        expect(scope.reverse).toBeFalsy();
        scope.sort('problemStatusCode');
        expect(scope.reverse).toBeTruthy();
    });

    it('should sort by startDate column', function () {
        scope.sort('startDate');
        expect(scope.reverse).toBeFalsy();
        scope.sort('startDate');
        expect(scope.reverse).toBeTruthy();
    });

    it('should sort by endDate column', function () {
        scope.sort('endDate');
        expect(scope.reverse).toBeFalsy();
        scope.sort('endDate');
        expect(scope.reverse).toBeTruthy();
    });

    it('should delete a condition', function () {
        scope.deleteCondition(4);
        var condition = mockConditionService.get(4);
        expect(condition).toEqual(undefined);
    });

    it('should search by name', function () {
        scope.searchBy = 'name';
        scope.criteria = 'c1';
        scope.onSearch();
        expect(scope.composedCriteria).toNotEqual(undefined);
    });

    it('should search by status', function () {
        scope.searchBy = 'status';
        scope.criteria = 'c1';
        scope.onSearch();
        expect(scope.composedCriteria).toNotEqual(undefined);
    });

    it('should search by everything', function () {
        scope.searchBy = undefined;
        scope.criteria = 'c1';
        scope.onSearch();
        expect(scope.composedCriteria).toNotEqual(undefined);
    });

    it('should sortup', function(){
        scope.sortField = 'name';
        expect(scope.isSortUp(scope.sortField)).toBeTruthy();
        scope.sort(scope.sortField);
        expect(scope.isSortUp(scope.sortField)).toBeFalsy();
    });

    it('should sort down', function(){
        scope.sortField = 'name';
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
        scope.filteredConditions = [{id: "1", name: "c1"},{id: "2", name: "c2"}];

        scope.$apply();
        expect(scope.startRecord ).toEqual(1);
        expect(scope.showPageSize ).toEqual(2);

        //In case the filter size is more than the page size
        scope.filteredConditions = [{id: "1", name: "c1"},{id: "2", name: "c2"},{id: "3", name: "c3"},{id: "4", name: "c4"},{id: "5", name: "c5"},{id: "6", name: "c6"},
            {id: "7", name: "c7"},{id: "8", name: "c8"},{id: "9", name: "c9"},{id: "10", name: "c10"},{id: "11", name: "c11"},{id: "12", name: "c12"}];
        scope.$apply();
        expect(scope.startRecord ).toEqual(1);
        expect(scope.showPageSize ).toEqual(10);
        expect(scope.pages).toEqual([0,1]);

    });

    it("should update pagination pages when page size changes", function(){
        //If page size is greater than total patients
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

    it("should load resolve data", function(){
        expect($route.current).toBeUndefined();
        httpBackend.expectGET('problems/patient/213').respond(200);
        httpBackend.expectGET('condition/condition-list.tpl.html').respond(200);

        location.path('/patient/213/conditions');
        rootScope.$digest();

        expect($route.current.templateUrl).toBe('condition/condition-list.tpl.html');
        expect($route.current.controller).toEqual('ConditionListCtrl');
        expect($route.current.resolve.loadedConditions).toBeDefined();

    });

});

xdescribe("icas.conditionModule ConditionEditCtrl", function () {
    beforeEach(module('ngRoute'));
    beforeEach(module('icas.conditionModule'));

    var scope, $controller, mockConditionService, ConditionEditCtrl, $route, $compile, form, conditions, conditionCodes, statusCodes, condition, loadedData, location, rootScope, httpBackend;

    beforeEach(function () {
        this.addMatchers({
            toEqualData: function (expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(inject(function (_$compile_, $rootScope, _$controller_, _$route_, $location, $httpBackend) {
        scope = $rootScope.$new();
        $controller = _$controller_;
        $route = _$route_;
        $compile = _$compile_;

        rootScope = $rootScope;
        location = $location;
        httpBackend = $httpBackend;


        var element = angular.element(
            '<form name="conditionForm">' +
                '<input ng-model="condition.startDate" name="startDate" type="text" />' +
                '<input ng-model="condition.endDate" name="endDate"  type="text" />' +
                '</form>'
        );

        scope.condition = { startDate:null, endDate: null};
        $compile(element)(scope);
        scope.$digest();
        form = scope.conditionForm;

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

        conditions = [
            {id: "1", name: "c1"},
            {id: "2", name: "c2"}
        ];

        conditionCodes = [
            {"code": "c1", "displayName": "d1"},
            {"code": "c2", "displayName": "d2"}
        ];

        statusCodes = [
            {"code": "s1", "displayName": "d1"},
            {"code": "s2", "displayName": "d2"}
        ];

        var successCb = function () {
            console.log('Success');
        };

        var errorCb = function () {
            console.log('Error');
        };

        mockConditionService = {
            query: function (conditionId, successCb, errorCb) {
                return conditions;
            },

            create: function (conditionId, condition, successCb, errorCb) {
                return {status: 201};
            },

            get: function (id, successCb, errorCb) {
                for (var i = 0; i < conditions.length; i++) {
                    if (conditions[i].id === id) {
                        return conditions[i];
                    }
                }
            },

            update: function (id, condition, successCb, errorCb) {
                return {status: 200};
            },

            delete: function (id, successCb, errorCb) {
                return {status: 200};
            },

            getProblems: function (successCb, errorCb) {
                return conditionCodes;
            },

            getProblemStatusCodes: function (successCb, errorCb) {
                return statusCodes;
            }
        };

        //loadedData is pre-populated when ConditionEditCtrl is called.
        loadedData = [conditionCodes, statusCodes, conditions[0]];

        ConditionEditCtrl = $controller('ConditionEditCtrl', {
            $scope: scope,
            ConditionService: mockConditionService,
            loadedData: loadedData
        });

    }));

    it('Should contain default values for dropdown and the selected condition', function () {
        expect(scope.problems).toEqualData(conditionCodes);
        expect(scope.problemStatusCodes).toEqualData(statusCodes);
        expect(scope.condition).toEqualData(conditions[0]);
    });

    it('Should edit a condition', function () {
        scope.save(conditions[0]);
        var editedCondition = mockConditionService.get(conditions[0].id);
        expect(editedCondition).toEqualData(conditions[0]);
    });

    it("Should enable save button if validation pass", function(){
        expect(form ).toBeDefined();
        expect(scope.canSave()).toBeTruthy();
        form.startDate.$setViewValue("04/01/2014");
        form.endDate.$setViewValue("04/04/2014");
        scope.$digest();
        expect(scope.canSave()).toBeFalsy();

        scope.condition.startDate = "04/01/2014";
        scope.condition.endDate = "03/01/2014";
        scope.$digest();
        expect(scope.canSave()).toBeTruthy();
    });

    it("should load resolve data", function(){
        expect($route.current).toBeUndefined();
        httpBackend.expectGET('problemcodes').respond(200);
        httpBackend.expectGET('actstatuscodes').respond(200);
        httpBackend.expectGET('problems/1').respond(200);
        httpBackend.expectGET('condition/condition-create-edit.tpl.html').respond(200);

        location.path('/patient/213/conditions/edit/1');
        rootScope.$digest();

        expect($route.current.templateUrl).toBe('condition/condition-create-edit.tpl.html');
        expect($route.current.controller).toEqual('ConditionEditCtrl');
        expect($route.current.resolve.loadedData).toBeDefined();

    });
});

xdescribe("icas.conditionModule ConditionCreateCtrl", function () {
    beforeEach(module('ngRoute'));
    beforeEach(module('icas.conditionModule'));

    var scope, $controller, mockConditionService, ConditionCreateCtrl, $route, $compile, form, conditions, conditionCodes, statusCodes, condition, loadedData, location, rootScope, httpBackend;

    beforeEach(function () {
        this.addMatchers({
            toEqualData: function (expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(inject(function (_$compile_, $rootScope, _$controller_, _$route_, $location, $httpBackend) {
        scope = $rootScope.$new();
        $controller = _$controller_;
        $route = _$route_;
        $compile = _$compile_;

        rootScope = $rootScope;
        location = $location;
        httpBackend = $httpBackend;

        conditions = [
            {id: "1", name: "c1"},
            {id: "2", name: "c2"}
        ];

        conditionCodes = [
            {"code": "c1", "displayName": "d1"},
            {"code": "c2", "displayName": "d2"}
        ];

        statusCodes = [
            {"code": "s1", "displayName": "d1"},
            {"code": "s2", "displayName": "d2"}
        ];

        var successCb = function () {
            console.log('Success');
        };

        var errorCb = function () {
            console.log('Error');
        };

        mockConditionService = {
            query: function (conditionId, successCb, errorCb) {
                return conditions;
            },

            create: function (conditionId, condition, successCb, errorCb) {
                conditions.push(condition);
                return {status: 201};
            },

            get: function (id, successCb, errorCb) {
                for (var i = 0; i < conditions.length; i++) {
                    if (conditions[i].id === id) {
                        return conditions[i];
                    }
                }
            },

            update: function (id, condition, successCb, errorCb) {
                return {status: 200};
            },

            delete: function (id, successCb, errorCb) {
                return {status: 200};
            },

            getProblems: function (successCb, errorCb) {
                return conditionCodes;
            },

            getProblemStatusCodes: function (successCb, errorCb) {
                return statusCodes;
            }
        };

        //loadedData is pre-populated when ConditionEditCtrl is called.
        loadedData = [conditionCodes, statusCodes];

        scope.selectedPatientId = 213;
        ConditionCreateCtrl = $controller('ConditionCreateCtrl', {
            $scope: scope,
            ConditionService: mockConditionService,
            loadedData: loadedData
        });

    }));

    it('Should contain default values for dropdown and the selected condition', function () {
        expect(scope.problems).toEqualData(conditionCodes);
        expect(scope.problemStatusCodes).toEqualData(statusCodes);
    });

    xit('Should create a condition', function () {
        var newCondition = {id: "3", name: "c3", problemCode:"123"};
        scope.save(newCondition);
        var retrievedCondition = mockConditionService.get(newCondition.id);
        expect(newCondition).toEqualData(retrievedCondition);
    });

    it("should load resolve data", function(){
        expect($route.current).toBeUndefined();
        httpBackend.expectGET('problemcodes').respond(200);
        httpBackend.expectGET('actstatuscodes').respond(200);
        httpBackend.expectGET('condition/condition-create-edit.tpl.html').respond(200);

        location.path('/patient/:patientId/conditions/add/');
        rootScope.$digest();

        expect($route.current.templateUrl).toBe('condition/condition-create-edit.tpl.html');
        expect($route.current.controller).toEqual('ConditionCreateCtrl');
        expect($route.current.resolve.loadedData).toBeDefined();

    });

});
