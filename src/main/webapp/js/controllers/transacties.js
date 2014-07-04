/* 
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
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
function TransactiesCtrl($scope, $location, transactiesService, transactiesFilterService) {
    $scope.query = "";
    
    $scope.filter = function() {
        if ($scope.query === "") {
            $scope.page = transactiesService.query(function() {
                $scope.transacties = $scope.page.transactie;
            });
            
        } else {
            transactiesFilterService.query({filter:$scope.query}, function(data) {
                $scope.transacties = data;
            });
        }
    };
    
    $scope.page = transactiesService.query(function() {
        $scope.transacties = $scope.page.transactie;
    });

    $scope.navClass = function(page) {
        var currentRoute = $location.path().substring(1) || 'transacties';
        return page === currentRoute ? 'active' : '';
    };

    $scope.dcClass = function(dc) {
        return dc;
    };

    $scope.nextPage = function() {
        $scope.page = transactiesService.page({page:$scope.page.huidigePagina + 1}, function() {
            $scope.transacties = $scope.page.transactie;
        });
    };

    $scope.prevPage = function() {
        $scope.page = transactiesService.page({page:$scope.page.huidigePagina - 1}, function() {
            $scope.transacties = $scope.page.transactie;
        });
    };
}


