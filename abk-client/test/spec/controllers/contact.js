'use strict';

describe('Controller: ContactCtrl', function () {

    // load the controller's module
    beforeEach(module('abkServices'));
    beforeEach(module('abkClientApp'));

    var createController,
            scopeMock;

    beforeEach(inject(function ($controller) {
        scopeMock = {};
        createController = function () {
            $controller('ContactCtrl', {'$scope':scopeMock});
        };
    }));
    it('should have be able to create controller', function () {
        createController();
    });
});

