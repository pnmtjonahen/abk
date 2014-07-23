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

/* jasmine specs for kostenplaatsen controller go here */
describe('KostenPlaatsen Ctrl test', function() {
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
        $httpBackend.when('GET', 'rest/kostenplaatsen').
                respond({kostenplaats:[]});
        
        createController = function() {
            $httpBackend.expectGET('rest/kostenplaatsen');
            $controller('KostenplaatsenCtrl', {$scope: scopeMock});
            $httpBackend.flush(); 
        };

    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('shoud get all kostenplaatsen',  inject(function($controller) {
        createController();
        expect(scopeMock.collection).toBeDefined();
        expect(scopeMock.kostenplaatsen).toBeDefined();

    }));
    
    it('shoud add new kostenplaats', inject(function($controller) {
        createController();

        expect(scopeMock.hasNoKostenplaatsen()).toBe(true);
        scopeMock.addKostenplaats();
        expect(scopeMock.hasNoKostenplaatsen()).toBe(false);
        expect(scopeMock.kostenplaats).toBeDefined();
        
    }));
    
    it('shoud add new subkostenplaats', inject(function($controller) {
        createController();

        scopeMock.addKostenplaats();
        
        expect(scopeMock.hasSubkostenplaatsen(scopeMock.kostenplaats.subkostenplaatsen)).toBe(false);
        expect(scopeMock.hasNoSubkostenplaatsen(scopeMock.kostenplaats.subkostenplaatsen)).toBe(true);
        
        var current = scopeMock.kostenplaats;
        scopeMock.addSubKostenplaats(scopeMock.kostenplaats);
        expect(scopeMock.hasSubkostenplaatsen(scopeMock.kostenplaats.subkostenplaatsen)).toBe(false);
        expect(scopeMock.hasNoSubkostenplaatsen(scopeMock.kostenplaats.subkostenplaatsen)).toBe(true);

        expect(scopeMock.hasSubkostenplaatsen(current.subkostenplaatsen)).toBe(true);
        expect(scopeMock.hasNoSubkostenplaatsen(current.subkostenplaatsen)).toBe(false);
        
    }));
    
    it ('shoud select for edit and leave edit mode', inject(function($controller) {
        createController();

        expect(scopeMock.selected()).toBe(false);
        scopeMock.selectKostenplaats( {id:'', naam: '', filter: '', root: true});
        expect(scopeMock.selected()).toBe(true);
        scopeMock.leaveEdit();
        expect(scopeMock.selected()).toBe(false);
        
    }));

    it ('shoud save kostenplaatsen', inject(function($controller) {
        createController();

        $httpBackend.expectPOST('rest/kostenplaatsen').respond(200, '');
        scopeMock.save();
        $httpBackend.flush();

        expect(scopeMock.saveMessage).toBe('Saved.....');
        
    }));
    it ('shoud get error on save kostenplaatsen', inject(function($controller) {
        createController();

        $httpBackend.expectPOST('rest/kostenplaatsen').respond(500, '');
        scopeMock.save();
        $httpBackend.flush();

        expect(scopeMock.saveMessage).toBe('error.....');
        
    }));
    
    it('should delete kostenplaats from server', inject(function($controller) {
        createController();

        $httpBackend.expectDELETE('rest/kostenplaatsen/1').respond(200, '');
        $httpBackend.expectGET('rest/kostenplaatsen');

        scopeMock.removeKostenplaats({id:'1', naam: '', filter: '', root: true});
        $httpBackend.flush();

        expect(scopeMock.saveMessage).not.toBeDefined();
        
    }));
    it('should not be able to delete kostenplaats from server', inject(function($controller) {
        createController();

        $httpBackend.expectDELETE('rest/kostenplaatsen/1').respond(500, '');

        scopeMock.removeKostenplaats({id:'1', naam: '', filter: '', root: true});
        $httpBackend.flush();

        expect(scopeMock.saveMessage).toBe("an error occurred.......");
        
    }));
});



