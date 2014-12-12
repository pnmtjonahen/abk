'use strict';

describe('Controller: ContactController', function () {

    // load the controller's module
    beforeEach(module('abkServices'));
    beforeEach(module('abkClientApp'));

    var createController,
            scopeMock;

    beforeEach(inject(function ($controller) {
        scopeMock = {};
        createController = function () {
            $controller('ContactController');
        };
    }));
    it('should have be able to create controller', function () {
        createController();
    });
});

