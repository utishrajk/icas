/**
 * Created by tomson.ngassa on 3/10/14.
 */

'use strict';

xdescribe("icas.dashboarModule:", function() {
    var route, location, rootScope, httpBackend, TrainingCtrl, scope;

    beforeEach(module('ngRoute'));
    beforeEach(module('icas.dashboarModule'));

    beforeEach(inject(function($route, $location, $rootScope, $httpBackend, $controller){
        route = $route;
        location = $location;
        rootScope = $rootScope;
        scope = $rootScope.$new();
        httpBackend = $httpBackend;

        scope.onSelectmenu = function(menuItem){

        };

        TrainingCtrl = $controller('TrainingCtrl', {
            $scope: scope
        });

    }));

    it('should test training controller', function(){
        expect(scope.openCustomMenu).toBeFalsy();
        spyOn(scope, "onSelectmenu");
        scope.onSelectmenu('training');
        expect(scope.onSelectmenu).toHaveBeenCalled();
    });
});