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
function KostenplaatsenCtrl($scope, kostenplaatsenService) {
    $scope.col_defs = [
        {field: "filter", displayName: "Filter"}
    ];
    $scope.tree_data = [{naam: "", filter: ""}];

    $scope.collection = kostenplaatsenService.query(function() {
        $scope.kostenplaatsen = $scope.collection.kostenplaats;
        $scope.tree_data = [];
        for (i = 0; i < $scope.kostenplaatsen.length; i++) {
            kp = {naam: $scope.kostenplaatsen[i].naam, filter: $scope.kostenplaatsen[i].filter};
            kp.children = [];
            for (j = 0; j < $scope.kostenplaatsen[i].subkostenplaatsen.length; j++) {
                kp.children.push({naam:$scope.kostenplaatsen[i].subkostenplaatsen[j].naam, filter: $scope.kostenplaatsen[i].subkostenplaatsen[j].filter});
            }
            $scope.tree_data.push(kp);
        }
    });
    $scope.my_tree_handler = function(branch){
        $scope.kostenplaats = branch;
    };
    $scope.save = function() {
        kostenplaatsenService.update({kostenplaats: $scope.kostenplaatsen}, function() {
            $scope.saveMessage = 'Saved...';
        }, function() {
            $scope.saveMessage = 'Error...';
        });
    };

    $scope.toggle = function(event) {
        $(event.target).parent().children('ul.tree').toggle(200);
    };

    $scope.hasSubkostenplaatsen = function(data) {
        if (undefined !== data) {
            return data.length !== 0;
        }
        return undefined !== data;
    };

    $scope.hasNoSubkostenplaatsen = function(data) {
        if (undefined !== data) {
            return data.length === 0;
        }
        return undefined === data;
    };

    $scope.hasNoKostenplaatsen = function() {
        return $scope.kostenplaatsen.length === 0;
    };

    $scope.removeKostenplaats = function(data) {
        kostenplaatsenService.delete({id: data.id}, function() {
            $scope.collection = kostenplaatsenService.query(function() {
                $scope.kostenplaatsen = $scope.collection.kostenplaats;
            });
        }, function() {
            $scope.saveMessage = "An Error Occurred...";
        });
    };


    $scope.addSubKostenplaats = function(data) {
        if (undefined === data.subkostenplaatsen) {
            data.subkostenplaatsen = [];
        }
        $scope.kostenplaats = {id: '', naam: '', filter: '', root: false};
        data.subkostenplaatsen.push($scope.kostenplaats);
    };
    $scope.addKostenplaats = function() {
        $scope.kostenplaats = {id: '', naam: '', filter: '', root: true};
        $scope.kostenplaatsen.push($scope.kostenplaats);
    };

    $scope.selectKostenplaats = function(data) {
        $scope.kostenplaats = data;
    };

    $scope.selected = function() {
        return undefined !== $scope.kostenplaats;
    };

    $scope.leaveEdit = function() {
        $scope.kostenplaats = undefined;
    };
}

