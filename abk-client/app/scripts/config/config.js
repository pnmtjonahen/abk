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

/**
 * @ngdoc service
 * @name abkConfig.backendConfig
 * @description
 * holds the backend configuration items
 */
angular.module('abkConfig').constant('backendConfig', {
    /**
     * @ngdoc property
     * @name abkConfig.backendConfig#resourcePath
     * @propertyOf abkConfig.backendConfig
     * @returns {string} the server url
     */
    resourcePath: 'http://localhost:8080/abk-backend/rest',

    /**
     * @ngdoc property
     * @name abkConfig.backendConfig#uploadPath
     * @propertyOf abkConfig.backendConfig
     * @returns {string} the server url where uploads should be posted
     */
    uploadPath: 'http://localhost:8080/abk-backend/upload'
});


