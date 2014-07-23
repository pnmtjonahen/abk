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

/* jasmine specs for home controller go here */
describe('Home Ctrl test', function() {
    beforeEach(module('abkServices'));
    beforeEach(module('abkApp'));
    
     it ('shoud flip debug mode in rootScope', function() {
        var scope = {}, 
            rootScope = { showDebug : false},
            ctrl = new HomeCtrl(scope, rootScope); 
            
        expect(rootScope.showDebug).toBe(false);
        scope.debug();
        expect(rootScope.showDebug).toBe(true);
    });
});

