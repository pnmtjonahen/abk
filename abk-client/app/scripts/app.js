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
 * @name abkClientApp
 * @description
 * # abkClientApp
 *
 * Main module of the application.
 */
var app = angular
        .module('abkClientApp', [
            'ngAnimate',
            'ngCookies',
            'ngSanitize',
            'ngTouch',
            'pasvaz.bindonce',
            'abkServices',
            'd3Graph',
            'abkComponents',
            'angularFileUpload',
            'ngDialog',
            'ui.router'
        ]);
 