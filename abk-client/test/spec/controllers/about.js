'use strict';

describe('Controller: AboutController', function () {

    // load the controller's module
    beforeEach(module('abkServices'));
    beforeEach(module('abkClientApp'));

    var createController,
            scopeMock;

    beforeEach(inject(function ($controller) {
        scopeMock = {};
        createController = function () {
            $controller('AboutController');
        };
    }));
    it('should have be able to create controller', function () {
        createController();
    });
});

