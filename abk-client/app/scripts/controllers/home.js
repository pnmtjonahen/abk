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
function HomeCtrl($scope, $q, currentDate, transactionsService, costCentersService) {

    $scope.range = currentDate.range();

    var buildDataRow = function () {
        var data = [];
        for (var i = 0; i < $scope.lastDay; i++) {
            data.push({day: i});
        }
        return data;
    };


    var init = function () {
        $scope.lastDay = new Date($scope.range.start.getFullYear(), $scope.range.start.getMonth() + 1, 0).getDate();
        $scope.data = undefined;
        $scope.dayheader = [];
        $scope.header = [];

        $scope.total = [];
        $scope.totalAmount = undefined;
        $scope.totalUnAccounted = undefined;

    };
    init();



    var processCostcenters = function (e) {
        if (e.filter) {
            e.filter = new RegExp(e.filter.trim(), 'i');
        }
        $scope.data.push({sum: {amount: 0}, costcenter: e, data: buildDataRow()});
        if (e.list) {
            e.list.forEach(processCostcenters);
        }
    };

    $scope.previous = function () {
        $scope.range.start.setMonth($scope.range.start.getMonth() - 1);
        $scope.range.end.setMonth($scope.range.end.getMonth() - 1);

        init();
        retrieveData();
    };

    $scope.next = function () {
        $scope.range.start.setMonth($scope.range.start.getMonth() + 1);
        $scope.range.end.setMonth($scope.range.end.getMonth() + 1);
        init();
        retrieveData();
    };


    var retrieveData = function () {
        $q.all([transactionsService.get({q: 'date=[' + $scope.range.start.toJSON() + ' ' + $scope.range.end.toJSON() + ']', limit: 9999,
                fields: 'date,debitCreditIndicator,amount,description,contraAccountName'}).$promise,
            costCentersService.get({expand: 3}).$promise]).then(function (result) {
            
            $scope.dayheader = function () {
                var weekdays = ["s", "m", "t", "w", "t", "f", "s"];
                var ddata = [];
                var today = new Date($scope.range.start);
                for (var i = 0; i < $scope.lastDay; i++) {
                    today.setDate(i + 1);
                    ddata.push({day: weekdays[today.getDay()]});
                }

                return ddata;
            }();
            for (var j = 0; j < $scope.lastDay; j++) {
                $scope.header.push('' + (j + 1));
            }

            $scope.total = buildDataRow();
            
            $scope.data = [];
            
            var transactions = result[0];
            var costcenters = result[1];

            costcenters.list.forEach(processCostcenters);

            transactions.list.forEach(function (t) {
                $scope.data.forEach(function (c) {
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
                updateAmount($scope.total, t);
            });
            $scope.data.forEach(function (c) {
                c.sum = {amount: sum(c.data)};
            });
            $scope.totalAmount = sum($scope.total);
            var totalAccounted = $scope.data.reduce(function (t, c) {
                return {sum: {amount: t.sum.amount + c.sum.amount}};
            }, {sum: {amount: 0}}).sum.amount;
            $scope.totalUnAccounted = $scope.totalAmount - totalAccounted;
        });
    };

    retrieveData();

    var updateAmount = function (data, t) {
        var transactionDate = new Date(t.date);
        if (transactionDate.getMonth() === $scope.range.start.getMonth()) {
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

    $scope.current;
    $scope.showRow = function (row) {
        if (row.costcenter.parent !== undefined) {
            if ($scope.current !== undefined) {
                return $scope.current.id === row.costcenter.parent.id;
            }
            return false;
        }
        return true;
    };

    $scope.selectRow = function (row) {
        if ($scope.current === row.costcenter) {
            $scope.current = undefined;
        } else {
            $scope.current = row.costcenter;
        }
    };

    $scope.weekday = function (index) {
        return $scope.dayheader[index].day !== 's';
    };
}