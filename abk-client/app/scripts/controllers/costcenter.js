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
 * @name abkClientApp.controller:CostCenterCtrl
 * @description
 * # CostCenterCtrl
 * Controller forcost centers
 */
function CostCenterCtrl($scope, $q, costCentersService, ngDialog) {
    $scope.data = undefined;
    $scope.costcenters;

    var processCostcenters = function (e) {
        $scope.data.push(e);
        if (e.list) {
            e.list.forEach(processCostcenters);
        }
    };

    var retrieveCostCenters = function() {
        costCentersService.get({expand: 3}, function (data) {
            $scope.data = [];
            data.list.forEach(processCostcenters);
            $scope.costcenters = data.list;
        });
    };
    retrieveCostCenters();

    $scope.current;
    $scope.showRow = function (row) {
        if (row.parent !== undefined) {
            if ($scope.current !== undefined) {
                return $scope.current.id === row.parent.id;
            }
            return false;
        }
        return true;
    };

    $scope.selectRow = function (row) {
        if ($scope.current === row) {
            $scope.current = undefined;
        } else {
            $scope.current = row;
        }
    };

    $scope.storing = false;
    $scope.store = function () {
        $scope.storing = true;
        costCentersService.post($scope.costcenters, function () {
            $scope.storing = false;
            retrieveCostCenters();
        }, function () {
            $scope.storing = false;
            retrieveCostCenters();
        });
    };

    $scope.add = function () {
        $scope.costcenter = {name: '', filter: ''};
        ngDialog.openConfirm({
            template: "addTemplateId",
            scope: $scope
        }).then(function() {
            $scope.costcenters.push($scope.costcenter);
            $scope.data = [];
            $scope.costcenters.forEach(processCostcenters);
            
        }, function() {
            
        });
    };
    
    $scope.delete = function(row) {
        costCentersService.delete({id:row.id}, function() {
            retrieveCostCenters(); 
        });
    };
}