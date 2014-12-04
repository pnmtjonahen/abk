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
 * @name abkClientApp.controller:ReportsCtrl
 * @description
 * # ReportsCtrl
 * Controller of the abkClientApp
 */
function ReportsCtrl($scope, currentDate, transactionsService) {

    $scope.colornames = ['AliceBlue', 'AntiqueWhite', 'Aqua', 'Aquamarine', 'Azure', 'Beige', 'Bisque', 'Black', 'BlanchedAlmond', 'Blue', 'BlueViolet', 'Brown', 'BurlyWood', 'CadetBlue', 'Chartreuse', 'Chocolate', 'Coral', 'CornflowerBlue', 'Cornsilk', 'Crimson', 'Cyan', 'DarkBlue', 'DarkCyan', 'DarkGoldenRod', 'DarkGray', 'DarkGrey', 'DarkGreen', 'DarkKhaki', 'DarkMagenta', 'DarkOliveGreen', 'Darkorange', 'DarkOrchid', 'DarkRed', 'DarkSalmon', 'DarkSeaGreen', 'DarkSlateBlue', 'DarkSlateGray', 'DarkSlateGrey', 'DarkTurquoise', 'DarkViolet', 'DeepPink', 'DeepSkyBlue', 'DimGray', 'DimGrey', 'DodgerBlue', 'FireBrick', 'FloralWhite', 'ForestGreen', 'Fuchsia', 'Gainsboro', 'GhostWhite', 'Gold', 'GoldenRod', 'Gray', 'Grey', 'Green', 'GreenYellow', 'HoneyDew', 'HotPink', 'IndianRed', 'Indigo', 'Ivory', 'Khaki', 'Lavender', 'LavenderBlush', 'LawnGreen', 'LemonChiffon', 'LightBlue', 'LightCoral', 'LightCyan', 'LightGoldenRodYellow', 'LightGray', 'LightGrey', 'LightGreen', 'LightPink', 'LightSalmon', 'LightSeaGreen', 'LightSkyBlue', 'LightSlateGray', 'LightSlateGrey', 'LightSteelBlue', 'LightYellow', 'Lime', 'LimeGreen', 'Linen', 'Magenta', 'Maroon', 'MediumAquaMarine', 'MediumBlue', 'MediumOrchid', 'MediumPurple', 'MediumSeaGreen', 'MediumSlateBlue', 'MediumSpringGreen', 'MediumTurquoise', 'MediumVioletRed', 'MidnightBlue', 'MintCream', 'MistyRose', 'Moccasin', 'NavajoWhite', 'Navy', 'OldLace', 'Olive', 'OliveDrab', 'Orange', 'OrangeRed', 'Orchid', 'PaleGoldenRod', 'PaleGreen', 'PaleTurquoise', 'PaleVioletRed', 'PapayaWhip', 'PeachPuff', 'Peru', 'Pink', 'Plum', 'PowderBlue', 'Purple', 'Red', 'RosyBrown', 'RoyalBlue', 'SaddleBrown', 'Salmon', 'SandyBrown', 'SeaGreen', 'SeaShell', 'Sienna', 'Silver', 'SkyBlue', 'SlateBlue', 'SlateGray', 'SlateGrey', 'Snow', 'SpringGreen', 'SteelBlue', 'Tan', 'Teal', 'Thistle', 'Tomato', 'Turquoise', 'Violet', 'Wheat', 'White', 'WhiteSmoke', 'Yellow', 'YellowGreen'];
    $scope.xdomain = [0, 31];
    $scope.ydomain = [8000, -6000];
    $scope.month = [];




    var now = currentDate.current();
    var pref = currentDate.current();
    pref.setYear(now.getYear() - 1);

    transactionsService.get({q: 'date=[' + pref.toJSON() + ' ' + now.toJSON() + ']',
        limit: 9999,
        fields: 'date,debitCreditIndicator,amount,contraAccountName'},
    function (data) {
        $scope.month = [{id: 0, data: []},
            {id: 1, data: []},
            {id: 2, data: []},
            {id: 3, data: []},
            {id: 4, data: []},
            {id: 5, data: []},
            {id: 6, data: []},
            {id: 7, data: []},
            {id: 8, data: []},
            {id: 9, data: []},
            {id: 10, data: []},
            {id: 11, data: []},
            {id: 12, data: []}];
        //clean the data (reset to zero)
        for (var month = 0; month < 13; ++month) {
            for (var index = 0; index < 32; ++index) {
                $scope.month[month].data.push({key: index, value: 0});
            }
        }

        data.list.forEach(function (t) {
            var transactionDate = new Date(t.date);
            var currentDay = transactionDate.getDate();
            var currentMonth = transactionDate.getMonth();
            if (t.debitCreditIndicator === 'debit') {
                $scope.month[currentMonth].data[currentDay].value += -parseFloat(t.amount);
            } else {
                $scope.month[currentMonth].data[currentDay].value += parseFloat(t.amount);
            }
        });

        for (var month = 0; month < 13; ++month) {
            for (var index = 1; index < 32; ++index) {
                $scope.month[month].data[index].value += $scope.month[month].data[index - 1].value;
            }
        }

    });
}
