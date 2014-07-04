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
function RekeningenListCtrl($scope, rekeningenService) {
    $scope.collection = rekeningenService.query(function() {
        $scope.rekeningen = $scope.collection.rekening;
    });
    $scope.add = function() {
        $scope.rekeningen.push({rekening: '', naam: '', beginSaldo:''});
    };
    
    $scope.remove = function(rekening) {
        for (var i = 0, ii = $scope.rekeningen.length; i < ii; i++) {
            if (rekening === $scope.rekeningen[i]) {
                $scope.rekeningen.splice(i, 1);
            }
        }
    };
    
    $scope.save = function() {
        rekeningenService.update({rekening:$scope.rekeningen}, function() {
            $scope.saveMessage = 'Saved.....';
        }, function() {
            $scope.saveMessage = 'Error.....';
        });
    };
}
