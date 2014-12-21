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
 * @ngdoc function
 * @name abkClientApp.controller:HomeCtrl
 * @description
 * # HomeCtrl
 * Controller of the abkClientApp
 */
angular.module('abkClientApp').controller("CostCalculationController", function ($q, currentDate, transactionsService, costCentersService) {

    this.range = currentDate.range();
    this.lastDay = undefined;
    this.data = undefined;
    this.dayheader = [];
    this.header = [];

    this.total = [];
    this.totalAmount = undefined;
    this.totalUnAccounted = undefined;

    this.current;

    var that = this;

    var buildDataRow = function () {
        var data = [];
        for (var i = 0; i < that.lastDay; i++) {
            data.push({day: i});
        }
        return data;
    };


    function init() {
        that.lastDay = new Date(that.range.start.getFullYear(), that.range.start.getMonth() + 1, 0).getDate();
        that.data = undefined;
        that.dayheader = [];
        that.header = [];

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

    this.previous = function () {
        that.range.start.setMonth(that.range.start.getMonth() - 1);
        that.range.end.setMonth(that.range.end.getMonth() - 1);

        init();
        retrieveData();
    };

    this.next = function () {
        that.range.start.setMonth(that.range.start.getMonth() + 1);
        that.range.end.setMonth(that.range.end.getMonth() + 1);
        init();
        retrieveData();
    };


    var retrieveData = function () {
        $q.all([transactionsService.get({q: 'date=[' + that.range.start.toJSON() + ' ' + that.range.end.toJSON() + ']', limit: 9999,
                fields: 'date,debitCreditIndicator,amount,description,contraAccountName'}).$promise,
            costCentersService.get({expand: 3}).$promise]).then(function (result) {

            that.dayheader = function () {
                var weekdays = ["s", "m", "t", "w", "t", "f", "s"];
                var ddata = [];
                var today = new Date(that.range.start);
                for (var i = 0; i < that.lastDay; i++) {
                    today.setDate(i + 1);
                    ddata.push({day: weekdays[today.getDay()]});
                }

                return ddata;
            }();
            for (var j = 0; j < that.lastDay; j++) {
                that.header.push('' + (j + 1));
            }

            that.total = buildDataRow();

            that.data = [];

            var transactions = result[0];
            var costcenters = result[1];

            costcenters.list.forEach(processCostcenters);

            transactions.list.forEach(function (t) {
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
                updateAmount(that.total, t);
            });
            that.data.forEach(function (c) {
                c.sum = {amount: sum(c.data)};
            });
            that.totalAmount = sum(that.total);
            var totalAccounted = that.data.reduce(function (t, c) {
                return {sum: {amount: t.sum.amount + c.sum.amount}};
            }, {sum: {amount: 0}}).sum.amount;
            that.totalUnAccounted = that.totalAmount - totalAccounted;
        });
    };

    retrieveData();

    var updateAmount = function (data, t) {
        var transactionDate = new Date(t.date);
        if (transactionDate.getMonth() === that.range.start.getMonth()) {
            var day = transactionDate.getDate() - 1;
            if (data[day].amount === undefined) {
                data[day].amount = 0;
            }
            if (t.debitCreditIndicator === 'debit') {
                data[day].amount += -parseFloat(t.amount);
            } else {
                data[day].amount += parseFloat(t.amount);
            }
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

    this.weekday = function (index) {
        return that.dayheader[index].day !== 's';
    };
});