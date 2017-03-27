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
(function () {
    'use strict';

    /**
     * @ngdoc function
     * @name abkClientApp.controller:TransactionController
     * @description
     * # TransactionController
     * Controller of the abkClientApp
     */
    angular.module('abkClientApp').controller("TransactionController", transactionController);

    var updateCostcenterFilter = function (e) {
        if (e.filter) {
            e.filter = new RegExp(e.filter.trim(), 'i');
        }
        if (e.list) {
            e.list.forEach(updateCostcenterFilter);
        }
    };

    var matchCostCenter = function (t, c) {
        t.matched = true;
        if (t.costcenter !== undefined)
            t.costcenter = t.costcenter + ' / ' + c.name;
        else
            t.costcenter = c.name;

    };

    var matchSubCostCenters = function (t, sc) {
        if (sc.filter &&
                (sc.filter.test(t.description) || sc.filter.test(t.contraAccountName))) {
            matchCostCenter(t, sc);
        }
    };
    var matchTransaction = function (t, c) {
        if (c.filter &&
                (c.filter.test(t.description) || c.filter.test(t.contraAccountName))) {
            matchCostCenter(t, c);
        }
        if (c.list) {
            c.list.forEach(matchSubCostCenters.bind(null, t));
        }
    };

    var processTransaction = function (costcenters, t) {
        t.matched = false;
        if (t.debitCreditIndicator === 'debit') {
            t.amount = {amount: -parseFloat(t.amount)};
        } else {
            t.amount = {amount: parseFloat(t.amount)};
        }

        costcenters.list.forEach(matchTransaction.bind(null, t));
    };

    function transactionController($q, currentDate, transactionsService, costCentersService, userCheckService) {
        this.data = undefined;
        this.showall = false;
        this.range = currentDate.range();

        var that = this;

        var processTransactionsAndCosteCenters = function (result) {

            that.data = result[0];
            var costcenters = result[1];

            costcenters.list.forEach(updateCostcenterFilter);

            that.data.list.forEach(processTransaction.bind(null, costcenters));
        };

        var retrieveData = function () {
            userCheckService.check().$promise.then(function () {
                $q.all([transactionsService.get({q: 'date=[' + that.range.start.toJSON() + ' ' + that.range.end.toJSON() + ']', limit: 9999,
                        fields: 'date,debitCreditIndicator,amount,description,contraAccountName', orderby: 'date desc'}).$promise,
                    costCentersService.get({expand: 3}).$promise]).then(processTransactionsAndCosteCenters);
            });

        };

        retrieveData();

        this.previous = function () {
            that.range.previous();
            that.data = undefined;

            retrieveData();
        };

        this.next = function () {
            that.range.next();
            that.data = undefined;

            retrieveData();
        };

    }
    ;
})();
