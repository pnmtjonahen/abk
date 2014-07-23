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
describe('Upload Ctrl test', function() {
    var scopeMock = {}, ctrl;
    var uploadManagerMock = {
        upload: function() {
        }
    };
    beforeEach(module('abkServices'));
    beforeEach(module('abkApp'));

    /* 
     * new matcher (see: http://docs.angularjs.org/tutorial/step_11 )
     */
    beforeEach(inject(function($controller, $rootScope) {
        this.addMatchers({
            toEqualData: function(expected) {
                return angular.equals(this.actual, expected);
            }
        });
        scopeMock = $rootScope.$new();
        ctrl = $controller('UploadCtrl', {$scope: scopeMock, uploadManager: uploadManagerMock }); 
    }));    


   it('should call the upload manager', function() {
      spyOn(uploadManagerMock, "upload");
      
      scopeMock.upload();
   });
});