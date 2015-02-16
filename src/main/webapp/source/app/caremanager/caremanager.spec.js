///**
// * Created by tomson.ngassa on 3/10/14.
// */

'use strict';


xdescribe('icas.caremanagerModule', function(){
    var module;

    beforeEach(function() {
        module = angular.module("icas.caremanagerModule");
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
            dependencies = module.value('icas.caremanagerModule').requires;
        });

        it("should have icas.security as a dependency", function() {
            expect(hasModule('icas.security')).toEqual(true);
        });

        it("should have bhma.caremanagerService as a dependency", function() {
            expect(hasModule('icas.caremanagerService')).toEqual(true);
        });

    });
});


xdescribe("icas.caremanagerModule CaremanagerCtrl", function() {

    beforeEach(module('ngRoute'));
    beforeEach(module('icas.caremanagerModule'));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    var scope, MockCareManagerService, caremanager, loadedData, form , route, location, rootScope, httpBackend;

    beforeEach(inject(function($rootScope, $controller, $compile, $route, $location, $httpBackend){
        caremanager = {lastName: "Ngassa",firstName: "Tomson", id:2, userName: "tmngassa", website: "http://icas.com"};

        scope = $rootScope.$new();

        rootScope = $rootScope;
        route = $route;
        location = $location;
        httpBackend = $httpBackend;

        var successCb = function(){console.log('Success');};
        var errorCb =  function (){ console.log('Error');};

        MockCareManagerService = {
            update : function(id, newcaremanager, successCb, errorCb) {
                caremanager = newcaremanager;
            },
            get : function(id, successCb, errorCb) {
                return caremanager ;
            }
        };

        loadedData = [{}, {}, caremanager, {}];
        scope.states = loadedData[0];
        scope.specialities = loadedData[1];
        scope.caremanager = loadedData[2];
        scope.countries = loadedData[3];

        scope.isValidNumber = function(zipcode){
            if(isNaN(zipcode)){
                return false;
            }else if(zipcode.length > 0){
                return true;
            }
        };

        scope.isValidContactNumber = function(zipcode, countryCode, US_Zipcode_Length){
            if((countryCode === "US") && ( zipcode.length !== US_Zipcode_Length ) ){
                return true;
            }else{
                return false;
            }
        };

        var element = angular.element(
            '<form name="caremanagerForm">' +
                '<input ng-model="caremanager.mailingAddressPostalCode" name="zipCode" type="text" />' +
                '<input ng-model="caremanager.mailingAddressTelephoneNumber" name="phoneNumber"  type="text" />' +
                '</form>'
        );

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

        scope.caremanager = { mailingAddressPostalCode:null, mailingAddressTelephoneNumber: null};

        $compile(element)(scope);
        scope.$digest();
        form = scope.caremanagerForm;

        scope.isUnitedState = function(countryCode){
            return countryCode === "US";
        };

        
        $controller('CaremanagerCtrl', {
            $scope: scope,
            CareManagerService: MockCareManagerService,
            loadedData: loadedData
        });
    }));

    it("should have default values", function() {
        expect(scope.activeTab).toEqual('basic');
        expect(scope.states).toEqual({});
        expect(scope.specialities).toEqual({});
        expect(scope.caremanager).toEqual(caremanager);
    });

    it("should switch tab", function() {
        expect(scope.activeTab).toEqual('basic');
        scope.switchTabTo('tab1');
        expect(scope.activeTab).toEqual('tab1');
    });

    it("should update caremanager", function() {
        caremanager.lastName = "Ngassa1";
        scope.save(caremanager.id, caremanager, function(){console.log('Success');},  function (){ console.log('Error');});
        var updatedCarmanager = MockCareManagerService.get(2);
        expect(caremanager.lastName).toEqual(updatedCarmanager.lastName);
    });

    it("shoule validate zipcode", function(){
        expect(scope.validZipCodeLength).toBeUndefined();
        expect(scope.zipcodeIsANumber ).toBeUndefined();
        expect(scope.caremanager.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.caremanager.mailingAddressPostalCode ).toBeUndefined();

        //Invalid zip code
        scope.$apply();
        expect(scope.validZipCodeLength).toBeTruthy();
        expect(scope.zipcodeIsANumber ).toBeTruthy();

        //US zipcode
        scope.US_ZIPCODE_DIGIT_LENGTH = "5";
        scope.caremanager.mailingAddressCountryCode = "US";
        scope.caremanager.mailingAddressPostalCode = "21045"; // For valid US zip code
        scope.$apply();
        expect(scope.validZipCodeLength).toBeTruthy();
        expect(scope.zipcodeIsANumber ).toBeTruthy();

        scope.caremanager.mailingAddressPostalCode = "2104533e"; // Invalid US zip code
        scope.$apply();
        expect(scope.validZipCodeLength).toBeFalsy();
        expect(scope.zipcodeIsANumber ).toBeFalsy();
    });

    it("shoule validate phone number", function(){
        expect(scope.isValidPhoneNumberLength ).toBeUndefined();
        expect(scope.isPhoneNumber ).toBeUndefined();
        expect(scope.caremanager.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.caremanager.mailingAddressTelephoneNumber ).toBeUndefined();

        //Invalid phone number
        scope.$apply();
        expect(scope.isValidPhoneNumberLength).toBeTruthy();
        expect(scope.isPhoneNumber ).toBeTruthy();

        //US phone number
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";
        scope.caremanager.mailingAddressCountryCode = "US";
        scope.caremanager.mailingAddressTelephoneNumber = "1111111111"; // For valid US phone unmber
        scope.$apply();
        expect(scope.isValidPhoneNumberLength).toBeTruthy();
        expect(scope.isPhoneNumber ).toBeTruthy();

        scope.caremanager.mailingAddressTelephoneNumber = "2104533e"; // Invalid US phone number
        scope.$apply();
        expect(scope.isPhoneNumber ).toBeFalsy();
    });

    it("should validate state, zip code and phone number on country change", function(){

        expect(scope.validZipCodeLength).toBeUndefined();
        expect(scope.zipcodeIsANumber ).toBeUndefined();
        expect(scope.caremanager.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.caremanager.mailingAddressPostalCode ).toBeUndefined();

        expect(scope.isValidPhoneNumberLength ).toBeUndefined();
        expect(scope.isPhoneNumber ).toBeUndefined();
        expect(scope.caremanager.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.caremanager.mailingAddressTelephoneNumber ).toBeUndefined();

        scope.US_ZIPCODE_DIGIT_LENGTH = "5";
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";
        scope.caremanager.mailingAddressCountryCode = "US";
        scope.caremanager.mailingAddressPostalCode = "21045";
        scope.caremanager.mailingAddressTelephoneNumber = "1111111111";

        scope.onCountrySelected();

        expect(scope.validZipCodeLength).toBeTruthy();
        expect(scope.zipcodeIsANumber ).toBeTruthy();
        expect(scope.isValidPhoneNumberLength).toBeTruthy();
        expect(scope.isPhoneNumber ).toBeTruthy();
    });

    it("Should enable save button if validation pass", function(){
        expect(scope.isValidPhoneNumberLength).toBeUndefined();
        expect(scope.zipcodeIsANumber ).toBeUndefined();
        expect(scope.caremanager.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.caremanager.mailingAddressPostalCode ).toBeUndefined();

        expect(scope.isValidPhoneNumberLength ).toBeUndefined();
        expect(scope.isPhoneNumber ).toBeUndefined();
        expect(scope.caremanager.mailingAddressCountryCode ).toBeUndefined();
        expect(scope.caremanager.mailingAddressTelephoneNumber ).toBeUndefined();

        scope.US_ZIPCODE_DIGIT_LENGTH = "5";
        scope.US_TELEPHONE_DIGIT_LENGTH = "10";

        expect(form ).toBeDefined();

        form.zipCode.$setViewValue("21045");
        form.phoneNumber.$setViewValue("1111111111");

        scope.$digest();
        expect(scope.canSave()).toBeTruthy();
    });

    it("should load resolve data", function(){
        expect(route.current).toBeUndefined();
        httpBackend.expectGET('individualproviders/1').respond(200);
        httpBackend.expectGET('statecodes').respond(200);
        httpBackend.expectGET('providertaxonomycodes').respond(200);
        httpBackend.expectGET('countrycodes').respond(200);
        httpBackend.expectGET('prefixes').respond(200);

        httpBackend.expectGET('caremanager/caremanager-profile.tpl.html').respond(200);

        location.path('/caremanager/1');
        rootScope.$digest();

        expect(route.current.templateUrl).toBe('caremanager/caremanager-profile.tpl.html');
        expect(route.current.params.id).toEqual('1');
        expect(route.current.controller).toEqual('CaremanagerCtrl');
        expect(route.current.resolve.loadedData).toBeDefined();

        location.path('/caremanager');
        rootScope.$digest();
        expect(route.current).toBeUndefined();

    });

});

