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
 * @name abkClientApp.controller:CostCenterController
 * @description
 * # CostCenterController
 * Controller forcost centers
 */
angular.module('abkClientApp').controller("CostCenterController", function(costCentersService, ngDialog) {
    this.data = undefined;
    this.costcenters;
    this.current;
    this.storing = false;
    this.costcenter = {name: '', filter: ''};
    this.parent = {costcenter: undefined};

    var that = this;
    var processCostcenters = function (e) {
        that.data.push(e);
        if (e.list) {
            e.list.forEach(processCostcenters);
        }
    };

    var retrieveCostCenters = function () {
        costCentersService.get({expand: 3}, function (data) {
            that.data = [];
            data.list.forEach(processCostcenters);
            that.costcenters = data.list;
        });
    };
    retrieveCostCenters();

    this.showRow = function (row) {
        if (row.parent !== undefined) {
            if (that.current !== undefined) {
                return that.current.id === row.parent.id;
            }
            return false;
        }
        return true;
    };

    this.selectRow = function (row) {
        if (that.current === row) {
            that.current = undefined;
        } else {
            that.current = row;
        }
    };

    this.store = function () {
        that.storing = true;
        costCentersService.post(that.costcenters, function () {
            that.storing = false;
            retrieveCostCenters();
        }, function () {
            that.storing = false;
            retrieveCostCenters();
        });
    };


    this.add = function () {
        ngDialog.openConfirm({
            template: "addTemplateId",
            scope: $scope
        }).then(function () {
            if (that.parent.costcenter === undefined) {
                that.costcenters.push(that.costcenter);
            } else {
                that.parent.costcenter.list.push(that.costcenter);
                that.costcenter.parent = {id:that.parent.costcenter.id};
            }
            that.costcenter = {name: '', filter: ''};
            that.parent = {costcenter: undefined};
            that.data = [];
            that.costcenters.forEach(processCostcenters);

        }, function () {

        });
    };

    var removeFromList = function (row, list) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].id === row.id) {
                list.splice(i, 1);
            }
        }

    }
    this.delete = function (row) {
        if (row.parent === undefined) {
            removeFromList(row, that.costcenters);
        } else {
            for (var i = 0; i < that.costcenters.length; i++) {
                if (that.costcenters[i].id === row.parent.id) {
                    removeFromList(row, that.costcenters[i].list);
                }
            }
        }
        that.data = [];
        that.costcenters.forEach(processCostcenters);
    };
});