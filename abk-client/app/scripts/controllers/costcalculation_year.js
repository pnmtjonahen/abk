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
     * @name abkClientApp.controller:HomeCtrl
     * @description
     * # HomeCtrl
     * Controller of the abkClientApp
     */
    angular.module('abkClientApp').controller("CostCalculationYearController", function ($q, currentDate, transactionsService, costCentersService) {

        this.range = currentDate.rangeYear();
        this.data = undefined;
        this.header = [];

        this.total = [];
        this.totalAmount = undefined;
        this.totalUnAccounted = undefined;

        this.current;

        var that = this;

        var buildDataRow = function () {
            var data = [];
            for (var i = 0; i < 12; i++) {
                data.push({month: i});
            }
            return data;
        };


        function init() {
            that.data = undefined;
            that.header = ["jan", "feb", "ma", "apr", "mei", "jun", "jul", "aug", "sep", "okt", "nov", "dec"];

            that.total = [];
            that.totalAmount = undefined;
            that.totalUnAccounted = undefined;

        }
        ;
        init();



        var processCostcenters = function (e) {
            if (e.filter) {
                e.filter = new RegExp(e.filter.trim(), 'i');
            }
            that.data.push({sum: {amount: 0}, costcenter: e, data: buildDataRow()});
            if (e.list) {
                e.list.forEach(processCostcenters);
            }
        };




        var retrieveData = function () {
            $q.all([transactionsService.get({q: 'date=[' + that.range.start.toJSON() + ' ' + that.range.end.toJSON() + ']', limit: 9999,
                    fields: 'date,debitCreditIndicator,amount,description,contraAccountName'}).$promise,
                costCentersService.get({expand: 3}).$promise]).then(processResult);
        };

        retrieveData();

        var updateAmount = function (data, t) {
            var transactionDate = new Date(t.date);
            var month = transactionDate.getMonth();
            if (data[month].amount === undefined) {
                data[month].amount = 0;
            }
            if (t.debitCreditIndicator === 'debit') {
                data[month].amount += -parseFloat(t.amount);
            } else {
                data[month].amount += parseFloat(t.amount);
            }
        };


        var sum = function (row) {
            return row.reduce(function (total, cell) {
                if (cell.amount) {
                    return {amount: total.amount + cell.amount};
                }
                return total;
            }, {amount: 0}).amount;
        };

        this.previous = function () {
            that.range.previous();
            init();
            retrieveData();
        };

        this.next = function () {
            that.range.next();
            init();
            retrieveData();
        };

        var processTransaction = function (t) {
            /* Update day column per costcenter */
            that.data.forEach(function (c) {
                if (c.costcenter.filter &&
                        (c.costcenter.filter.test(t.description) || c.costcenter.filter.test(t.contraAccountName))) {
                    updateAmount(c.data, t);

                }
                if (c.costcenter.list) {
                    c.costcenter.list.forEach(function (sc) {
                        if (sc.filter &&
                                (sc.filter.test(t.description) || sc.filter.test(t.contraAccountName))) {
                            updateAmount(c.data, t);
                        }
                    });
                }
            });
            /* update total per month */
            updateAmount(that.total, t);
        };

        function processResult(result) {



            that.total = buildDataRow();
            that.data = [];

            var transactions = result[0];
            var costcenters = result[1];

            costcenters.list.forEach(processCostcenters);

            transactions.list.forEach(processTransaction);

            /* sum totals per costcenter */
            that.data.forEach(function (c) {
                c.sum = {amount: sum(c.data)};
            });

            /* sum all day totals */
            that.totalAmount = sum(that.total);

            var totalAccounted = that.data.reduce(function (t, c) {
                if (c.costcenter.parent === undefined)
                    return {sum: {amount: t.sum.amount + c.sum.amount}};
                return t;
            }, {sum: {amount: 0}}).sum.amount;

            that.totalUnAccounted = that.totalAmount - totalAccounted;
        }
        ;

        this.showRow = function (row) {
            if (row.costcenter.parent !== undefined) {
                if (that.current !== undefined) {
                    return that.current.id === row.costcenter.parent.id;
                }
                return false;
            }
            return true;
        };

        this.selectRow = function (row) {
            if (that.current === row.costcenter) {
                that.current = undefined;
            } else {
                that.current = row.costcenter;
            }
        };

    });
})();