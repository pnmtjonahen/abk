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
function YearReportCtrl($scope, $location, yearreportService) {
    $scope.tree_data = [{name:"", current:"", currentMin1:"", currentMin2:"", currentMin3:"", currentMin4:"", currentMin5:"", currentMin6:"", currentMin7:"", currentMin8:"", currentMin9:"", currentMin10:"", currentMin11:""}]
    
    yearreportService.query(function(data) {
        $scope.tree_data = data;
    });

    $scope.navClass = function(page) {
        var currentRoute = $location.path().substring(1) || 'yearreport';
        return page === currentRoute ? 'active' : '';
    };


}

