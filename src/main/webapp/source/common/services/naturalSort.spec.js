/**
 * Created by tomson.ngassa on 4/18/14.
 */

'use strict';

describe('icas.naturalSort module', function(){
    var module;

    beforeEach(function() {
        module = angular.module("icas.naturalSort");
    });

    it("should be registered", function() {
        expect(module).not.toEqual(null);
    });
});


describe('icas.breadcrumbsModule module', function () {

    var NaturalService, patients, sortedList;

    beforeEach(module("icas.naturalSort"));

    beforeEach(function(){
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(inject(function(_NaturalService_){
        NaturalService = _NaturalService_;
        patients = [
            { "firstName": "Thomas","id": 214, "lastName": "Ngassa", "birthDate": "04/01/1991", "zipcode":"" },{ "firstName": "Thomas","id": 205,"lastName": "Ngassa", "birthDate":"04/01/1991",  "zipcode":"qww2w1" },
            {"firstName": "Tommy","id": 206,"lastName": "Ngassa", "birthDate":"01/01/1980",  "zipcode":"21045" },{ "firstName": "Tomson",id: 207,"lastName": "Ngassa","birthDate":"10/07/2011",  "zipcode":"1223wee" }
        ];
        patients = JSON.stringify(patients, null, 2);
    }));

    xit("should sort naturally", function(){
        console.log(patients);
        var field = 'id';
        var sortedList = NaturalService.naturalValue(patients[field]);

        console.log("\n\n");

        console.log(patients);
    });
});