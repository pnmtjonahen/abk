/* 
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
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
var abkServiceModule = angular.module('abkServices', ['ngResource']);
abkServiceModule.factory('transactiesService', function($resource) {
    return $resource('/abk/rest/transacties/:page', {page:'@page'}, {
        query:  {method: 'GET', params: {}, isArray: false},
        page:   {method: 'GET', params: {}, isArray: false}
    });
});

abkServiceModule.factory('transactiesFilterService', function($resource) {
    return $resource('/abk/rest/transacties/filter/:filter', {
        query:  {method: 'GET', params: {}, isArray: true}
    });
});

abkServiceModule.factory('rekeningenService', function($resource) {
    return $resource('/abk/rest/rekeningen/', {}, {
        query:  {method: 'GET', params: {}, isArray: false},
        update: {method: 'POST', params: {}, isArray: false}
    });
});

abkServiceModule.factory('tegenRekeningenService', function($resource) {
    return $resource('/abk/rest/tegenrekeningen/', {}, {
        query:  {method: 'GET', params: {}, isArray: false},
        update: {method: 'POST', params: {}, isArray: false}
    });
});

abkServiceModule.factory('kostenplaatsenService', function($resource) {
    return $resource('/abk/rest/kostenplaatsen/:id', {id:'@id'}, {
        query:  {method: 'GET', params: {}, isArray: false},
        update: {method: 'POST', params: {}, isArray: false},
        delete: {method: 'DELETE', params: {}, isArray: false}
    });
});


abkServiceModule.factory('jaarrapportService', function($resource) {
    return $resource('/abk/rest/jaarrapport/', {}, {
        query:  {method: 'GET', params: {}, isArray: true}
    });
});

abkServiceModule.factory('rekeningenpermaandService', function($resource) {
    return $resource('/abk/rest/rekeningenpermaand/', {}, {
        query:  {method: 'GET', params: {}, isArray: false}
    });
});



abkServiceModule.factory('uploadManager', function ($rootScope) {
    var _files = [];
    return {
        add: function (file) {
            _files.push(file);
            $rootScope.$broadcast('fileAdded', file.files[0].name);
        },
        clear: function () {
            _files = [];
        },
        files: function () {
            var fileNames = [];
            $.each(_files, function (index, file) {
                fileNames.push(file.files[0].name);
            });
            return fileNames;
        },
        upload: function () {
            $.each(_files, function (index, file) {
                file.submit();
            });
            this.clear();
        },
        setProgress: function (percentage) {
            $rootScope.$broadcast('uploadProgress', percentage);
        }
    };
});


