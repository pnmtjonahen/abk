/* 
 * Copyright (C) 2014 Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


'use strict';

describe('rekeningen Ctrl test', function() {
var scopeMock = {};
    var $httpBackend;
    var createController;

    /* 
     * new matcher (see: http://docs.angularjs.org/tutorial/step_11 )
     */
    beforeEach(function() {
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(module('abkServices'));
    beforeEach(module('abkApp'));

    beforeEach(inject(function(_$httpBackend_, $controller) {
        scopeMock = {};
        $httpBackend = _$httpBackend_;
        $httpBackend.when('GET', 'rest/rekeningen').
                respond({rekening:[]});

        createController = function() {
            $httpBackend.expectGET('rest/rekeningen');
            $controller('RekeningenListCtrl', {$scope: scopeMock});
            $httpBackend.flush();
        };
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    
    it('shoud get rekeningen',  function() {
        createController();
        expect(scopeMock.collection).toBeDefined();
        expect(scopeMock.rekeningen).toBeDefined();
        expect(scopeMock.rekeningen.length).toBe(0);

    });
    
    it('should add rekening', function() {
        createController();
        expect(scopeMock.rekeningen.length).toBe(0);
        scopeMock.add();
        expect(scopeMock.rekeningen.length).toBe(1);
        
    });
    it('should remove a rekening', function() {
        createController();
        expect(scopeMock.rekeningen.length).toBe(0);
        scopeMock.add();
        expect(scopeMock.rekeningen.length).toBe(1);
        var rekening = scopeMock.rekeningen[0];
        scopeMock.remove(rekening);
        expect(scopeMock.rekeningen.length).toBe(0);
    });
    
 it ('shoud save rekeningen', inject(function($controller) {
        createController();

        $httpBackend.expectPOST('rest/rekeningen').respond(200, '');
        scopeMock.save();
        $httpBackend.flush();

        expect(scopeMock.saveMessage).toBe('Saved.....');
        
    }));
    it ('shoud get error on save rekeningen', inject(function($controller) {
        createController();

        $httpBackend.expectPOST('rest/rekeningen').respond(500, '');
        scopeMock.save();
        $httpBackend.flush();

        expect(scopeMock.saveMessage).toBe('Error.....');
        
    }));    
});