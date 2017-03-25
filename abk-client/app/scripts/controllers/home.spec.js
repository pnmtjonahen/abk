'use strict';

describe('Controller: HomeController', function () {

    // load the controller's module
    beforeEach(module('abkServices'));
    beforeEach(module('abkClientApp'));

    var homeController,
            $httpBackend,
            createController,
            currentDateMock;

    beforeEach(inject(function (_$httpBackend_, $controller) {
        currentDateMock = {
            current: function() {
                return new Date(2014, 11, 19, 0, 0, 0, 0);
            },
            range: function () {
                return {'start':new Date(2014, 10, 19, 0, 0, 0, 0), 'end':new Date(2014, 11, 19, 0, 0, 0, 0)};
            }
        };
        $httpBackend = _$httpBackend_;
        createController = function () {
            homeController = $controller('HomeController', {currentDate: currentDateMock});
        };
    }));

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('should simply disploy home page', function () {
        createController();
    });

});
