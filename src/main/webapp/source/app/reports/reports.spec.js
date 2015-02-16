/**
 * Created by tomson.ngassa on 3/10/14.
 */

'use strict';

describe("icas.reportsModule:", function() {
    var $route, $location, $rootScope;

    beforeEach(module('ngRoute'));
    beforeEach(module('icas.reportsModule'));

    beforeEach(inject(function(_$route_, _$location_, _$rootScope_){
        $route = _$route_;
        $location = _$location_;
        $rootScope = _$rootScope_;
    }));

    xit('should route to the reports page', function(){
        expect($route.current).toBeUndefined();
        $location.path('/reports');
        $rootScope.$digest();

        expect($location.path()).toBe('/reports');
        expect($route.current.template).toBe('<h3>Reports</h3>');
    });
});