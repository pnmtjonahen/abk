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
(function () {
   'use strict';

   /**
    * @ngdoc function
    * @name abkClientApp.controller:ReportsController
    * @description
    * # ReportsController
    * Controller of the abkClientApp
    */
   angular.module('abkClientApp').controller("ReportsController", reportsController);


   var determineYDomain = function(that) {
      for (var mo = 0; mo < 12; mo++) {
         for (var day = 0; day < 32; ++day) {
            var ceilValue = Math.ceil(that.month[mo].data[day].value);
            if (ceilValue > 0) {
               if (that.ydomain[0] < ceilValue) {
                  that.ydomain[0] = ceilValue + 100;
               }
            } else {
               if (that.ydomain[1] > ceilValue) {
                  that.ydomain[1] = ceilValue - 100;
               }

            }
         }
      }
   };

   var processTransactions = function (that, data) {
      that.month = [{data: []},
         {data: []},
         {data: []},
         {data: []},
         {data: []},
         {data: []},
         {data: []},
         {data: []},
         {data: []},
         {data: []},
         {data: []},
         {data: []}];
      //clean the data (reset to zero)
      // month is 0-11 and day in month is 1-31
      for (var m = 0; m < 12; m++) {
         for (var index = 0; index < 32; ++index) {
            that.month[m].data.push({key: index, value: 0});
         }
      }

      data.list.forEach(function (t) {
         var transactionDate = new Date(t.date);
         var currentDay = transactionDate.getDate();
         var currentMonth = transactionDate.getMonth();
         if (t.debitCreditIndicator === 'debit') {
            that.month[currentMonth].data[currentDay].value += -parseFloat(t.amount);
         } else {
            that.month[currentMonth].data[currentDay].value += parseFloat(t.amount);
         }
      });

      for (var mo = 0; mo < 12; mo++) {
         for (var ix = 1; ix < 32; ++ix) {
            that.month[mo].data[ix].value += that.month[mo].data[ix - 1].value;
         }
      }
      determineYDomain(that);
   };

   function reportsController(currentDate, transactionsService) {

      this.colornames = ['Pink', 'Red', 'Orange', 'Yellow', 'Brown', 'Green', 'Cyan', 'Blue', 'Purple', 'Gray', 'Black', 'DeepPink', 'CadetBlue', 'Chartreuse', 'Chocolate', 'Coral', 'CornflowerBlue', 'Cornsilk', 'Crimson', 'Cyan', 'DarkBlue', 'DarkCyan', 'DarkGoldenRod', 'DarkGray', 'DarkGrey', 'DarkGreen', 'DarkKhaki', 'DarkMagenta', 'DarkOliveGreen', 'Darkorange', 'DarkOrchid', 'DarkRed', 'DarkSalmon', 'DarkSeaGreen', 'DarkSlateBlue', 'DarkSlateGray', 'DarkSlateGrey', 'DarkTurquoise', 'DarkViolet', 'DeepPink', 'DeepSkyBlue', 'DimGray', 'DimGrey', 'DodgerBlue', 'FireBrick', 'FloralWhite', 'ForestGreen', 'Fuchsia', 'Gainsboro', 'GhostWhite', 'Gold', 'GoldenRod', 'Gray', 'Grey', 'Green', 'GreenYellow', 'HoneyDew', 'HotPink', 'IndianRed', 'Indigo', 'Ivory', 'Khaki', 'Lavender', 'LavenderBlush', 'LawnGreen', 'LemonChiffon', 'LightBlue', 'LightCoral', 'LightCyan', 'LightGoldenRodYellow', 'LightGray', 'LightGrey', 'LightGreen', 'LightPink', 'LightSalmon', 'LightSeaGreen', 'LightSkyBlue', 'LightSlateGray', 'LightSlateGrey', 'LightSteelBlue', 'LightYellow', 'Lime', 'LimeGreen', 'Linen', 'Magenta', 'Maroon', 'MediumAquaMarine', 'MediumBlue', 'MediumOrchid', 'MediumPurple', 'MediumSeaGreen', 'MediumSlateBlue', 'MediumSpringGreen', 'MediumTurquoise', 'MediumVioletRed', 'MidnightBlue', 'MintCream', 'MistyRose', 'Moccasin', 'NavajoWhite', 'Navy', 'OldLace', 'Olive', 'OliveDrab', 'Orange', 'OrangeRed', 'Orchid', 'PaleGoldenRod', 'PaleGreen', 'PaleTurquoise', 'PaleVioletRed', 'PapayaWhip', 'PeachPuff', 'Peru', 'Pink', 'Plum', 'PowderBlue', 'Purple', 'Red', 'RosyBrown', 'RoyalBlue', 'SaddleBrown', 'Salmon', 'SandyBrown', 'SeaGreen', 'SeaShell', 'Sienna', 'Silver', 'SkyBlue', 'SlateBlue', 'SlateGray', 'SlateGrey', 'Snow', 'SpringGreen', 'SteelBlue', 'Tan', 'Teal', 'Thistle', 'Tomato', 'Turquoise', 'Violet', 'Wheat', 'White', 'WhiteSmoke', 'Yellow', 'YellowGreen'];
      this.labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
      this.xdomain = [0, 31];
      this.ydomain = [1000, -1000];
      this.month = [];
      var that = this;

      var now = currentDate.current();
      var pref = currentDate.current();
      pref.setFullYear(now.getFullYear() - 1);

      transactionsService.get({q: 'date=[' + pref.toJSON() + ' ' + now.toJSON() + ']',
                               limit: 9999,
                               fields: 'date,debitCreditIndicator,amount,contraAccountName'},
                           processTransactions.bind(null, that));
   }
   ;

})();