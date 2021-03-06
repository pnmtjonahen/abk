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
 * @name abkConfig
 * @description
 * A module containing configuration for the application.
 */
angular.module('abkConfig', []);

///**
// * @ngdoc service
// * @name abkConfig.backendConfig
// * @description
// * holds the backend configuration items
// */
//angular.module('abkConfig').constant('backendConfig', {
//    /**
//     * @ngdoc property
//     * @name abkConfig.backendConfig#costCentersPath
//     * @propertyOf abkConfig.backendConfig
//     * @returns {string} the cost-centers server url
//     */
//    costCentersPath: 'http://localhost:3002',
//    /**
//     * @ngdoc property
//     * @name abkConfig.backendConfig#transactionsPath
//     * @propertyOf abkConfig.backendConfig
//     * @returns {string} the transaction server url
//     */
//    transactionsPath: 'http://localhost:3003',
//    /**
//     * @ngdoc property
//     * @name abkConfig.backendConfig#userPath
//     * @propertyOf abkConfig.backendConfig
//     * @returns {string} the user server url
//     */
//    userPath: 'http://localhost:3005',
//    /**
//     * @ngdoc property
//     * @name abkConfig.backendConfig#adminPath
//     * @propertyOf abkConfig.backendConfig
//     * @returns {string} the admin server url
//     */
//    adminPath:'http://localhost:3000',
//    /**
//     * @ngdoc property
//     * @name abkConfig.backendConfig#uploadPath
//     * @propertyOf abkConfig.backendConfig
//     * @returns {string} the server url where uploads should be posted
//     */
//    uploadPath: 'http://localhost:3004/upload'
//});


// Import variables if present (from env.js)
if(window){  
    angular.module('abkConfig').constant('backendConfig',window.__env);
}



