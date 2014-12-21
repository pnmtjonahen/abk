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
angular.module('abkClientApp').config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

            // HOME STATES AND NESTED VIEWS ========================================
            .state('home', {
                url: '/home',
                templateUrl: 'views/home.html',
                controller: 'HomeController',
                controllerAs: 'homeCtrl'
            })
            .state('costcalculation', {
                url: '/costcalculation',
                templateUrl: 'views/costcalculation.html',
                controller: 'CostCalculationController',
                controllerAs: 'costcalculationCtrl'
            })

            .state('about', {
                url: '/about',
                templateUrl: 'views/about.html',
                controller: 'AboutController',
                controllerAs: 'aboutCtrl'
            })

            .state('contact', {
                url: '/contact',
                templateUrl: 'views/contact.html',
                controller: 'ContactController',
                controllerAs: 'ContactCtrl'
            })
            .state('transaction', {
                url:'/transaction',
                templateUrl: 'views/transaction.html', 
                controller: 'TransactionController', 
                controllerAs: 'transactionCtrl'
            })
            .state('reports', {
                url:'/reports',
                templateUrl: 'views/reports.html', 
                controller: 'ReportsController', 
                controllerAs: 'reportCtrl'
            })
            .state('costcenter', {
                url:'/costcenter',
                templateUrl: 'views/costcenter.html', 
                controller: 'CostCenterController', 
                controllerAs: 'costCenterCtrl'
            })
            .state('upload', {
                url:'/upload',
                templateUrl: 'views/upload.html', 
                controller: 'UploadController'
            });
});