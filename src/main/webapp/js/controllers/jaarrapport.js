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
function JaarRapportCtrl($scope, $location, jaarrapportService) {
    $scope.col_defs = [
        {field: "currentMin11", displayName: "Maand-11"}
        ,{field: "currentMin10", displayName: "Maand-10"}
        ,{field: "currentMin9", displayName: "Maand-9"}
        ,{field: "currentMin8", displayName: "Maand-8"}
        ,{field: "currentMin7", displayName: "Maand-7"}
        ,{field: "currentMin6", displayName: "Maand-6"}
        ,{field: "currentMin5", displayName: "Maand-5"}
        ,{field: "currentMin4", displayName: "Maand-4"}
        ,{field: "currentMin3", displayName: "Maand-3"}
        ,{field: "currentMin2", displayName: "Maand-2"}
        ,{field: "currentMin1", displayName: "Maand-1"}
        ,{field: "current", displayName: "Maand"}
    ];

    $scope.tree_data = [{name:"", currentMin11:"", currentMin10:"", currentMin9:"", currentMin8:"", currentMin7:""
            , currentMin6:"", currentMin5:"", currentMin4:"", currentMin3:"", currentMin2:"", currentMin1:"", current:""}];
    
    jaarrapportService.query(function(data) {
        $scope.tree_data = data;
    });

    $scope.navClass = function(page) {
        var currentRoute = $location.path().substring(1) || 'yearreport';
        return page === currentRoute ? 'active' : '';
    };


}

