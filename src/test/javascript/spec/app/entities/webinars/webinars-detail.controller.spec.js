'use strict';

describe('Controller Tests', function() {

    describe('Webinars Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockWebinars;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockWebinars = jasmine.createSpy('MockWebinars');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Webinars': MockWebinars
            };
            createController = function() {
                $injector.get('$controller')("WebinarsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'just2tradeApp:webinarsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
