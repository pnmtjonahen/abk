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
angular.module('abkClientApp').config(['$routeProvider', function($routeProvider) {
        $routeProvider.
                when('/home', {templateUrl: 'views/home.html', controller: 'HomeController', controllerAs:'homeCtrl'}).
                when('/about', {templateUrl: 'views/about.html', controller: 'AboutController', controllerAs:'aboutCtrl'}).
                when('/contact', {templateUrl: 'views/contact.html', controller: 'ContactController', controllerAs:'ContactCtrl'}).
                when('/transaction', {templateUrl: 'views/transaction.html', controller: 'TransactionController', controllerAs:'transactionCtrl'}).
                when('/reports', {templateUrl: 'views/reports.html', controller: 'ReportsController', controllerAs:'reportCtrl'}).
                when('/costcenter', {templateUrl: 'views/costcenter.html', controller: 'CostCenterController', controllerAs:'costCenterCtrl'}).
                when('/upload', {templateUrl: 'views/upload.html', controller: 'UploadController'})
        ;
//                .
//                otherwise({redirectTo: '/home'});
    }]);