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

describe('KostenplaatsenPerMaand Ctrl test', function() {
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
        $httpBackend.when('GET', 'rest/kostenplaatsenpermaand').
                respond([]);

        createController = function() {
            $httpBackend.expectGET('rest/kostenplaatsenpermaand');
            $controller('KostenplaatsenpermaandCtrl', {$scope: scopeMock});
            $httpBackend.flush();
        };
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    
    it('shoud get financieelRapportPerMaand',  function() {
        createController();
        expect(scopeMock.financieelRapportPerMaand).toBeDefined();

    });
    
    it('should get active on current page', function() {
        createController();
        expect(scopeMock.navClass('kostenplaatsenpermaand')).toBe('active');
    });
});