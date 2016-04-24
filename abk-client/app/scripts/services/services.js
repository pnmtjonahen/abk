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
/**
 * @ngdoc overview
 * @name abkServices
 * @description
 *
 * Module defines the services the application uses.
 */
angular.module('abkServices', ['ngResource', 'abkConfig']);

/**
 * @ngdoc service
 * @name abkServices.transactionService
 * @requires $resource
 * @requires abkConfig.backendConfig
**/
angular.module('abkServices').factory('transactionsService', function($resource, backendConfig) {
    return $resource(backendConfig.transactionsPath + '/transactions', {}, {
        get:  {method:'GET', params:{}, isArray:false}
    });
});

/**
 * @ngdoc service
 * @name abkServices.costCentersService
 * @requires $resource
 * @requires abkConfig.backendConfig
**/
angular.module('abkServices').factory('costCentersService', function($resource, backendConfig) {
    return $resource(backendConfig.costCentersPath + '/costcenters/:id', {id:'@id'}, {
        get:  {method:'GET', params:{}, isArray:false},
        post: {method: 'PUT', params: {}, isArray: true},
        delete: {method: 'DELETE', params: {}, isArray:false}
    });
});

angular.module('abkServices').factory('csvReaderService', function($resource, backendConfig) {
    return $resource(backendConfig.adminPath + '/admin/csvreader', {}, {
        get: {method: 'GET', params: {}, isArray: false}
    });
});

angular.module('abkServices').factory('UsersApi', function($resource, backendConfig) {
    return $resource(backendConfig.resourcePath + '/login', {}, {
        login: {method: 'PUT', params: {}, isArray: true}
    });
});

