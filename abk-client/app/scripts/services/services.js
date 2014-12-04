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
 * @name abk.services
 * @description
 * 
 * Module defines the services the application uses.
 */
angular.module('abk.services', ['ngResource', 'abk.config']);

/**
 * @ngdoc service 
 * @name abk.services.transactionService
 * @requires $resource
 * @requires abk.config.backendConfig
**/
angular.module('abk.services').factory('transactionsService', function($resource, backendConfig) {
    return $resource(backendConfig.resourcePath + '/transactions', {}, {
        get:  {method:'GET', params:{}, isArray:false}
    });
});

/**
 * @ngdoc service 
 * @name abk.services.costCentersService
 * @requires $resource
 * @requires abk.config.backendConfig
**/
angular.module('abk.services').factory('costCentersService', function($resource, backendConfig) {
    return $resource(backendConfig.resourcePath + '/costcenters/:id', {id:'@id'}, {
        get:  {method:'GET', params:{}, isArray:false},
        post: {method: 'PUT', params: {}, isArray: true},
        delete: {method: 'DELETE', params: {}, isArray:false}
    });
});
