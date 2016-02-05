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
 * @ngdoc overview
 * @name abkComponents 
 * @description
 * A module containing components used by the application.
 */
angular.module('abkComponents', []);

/**
 * @ngdoc directive
 * @name abkComponents.directive:amount
 * @restrict E
 * @description
 * Formats an double as an amount. 
 * 
 * Uses the following angular formatter {{data | number:2}}.
 * 
 * Negative numbers are displayed without the sign.
 *  
 * <pre>
//be sure to include debit/credit css style to style the amount
p.debit {
    font-style: italic;
    color: red;
    
}
p.credit {
    font-style: bold;
    color: blue;
    
}
 * </pre>
 *  
 * @example
    <doc:example module="abkComponents">
        <doc:source>
            <style>
                table.amount {
                    width:100%;
                    border: 1px solid gray;
                }
                table.amount th {
                    font-weight: bold;
                    text-align: right;
                    border: 1px solid black;
                    background-color:gray
                }
                table.amount td {
                    text-align: right;
                    border: 1px solid gray;
                }
                p.debit {
                    font-style: italic;
                    color: red;
                }
                p.credit {
                    font-style: bold;
                    color: blue;
                }
            </style>
            <script>
                function Ctrl($scope) {
                    $scope.debitAmount = { amount:-99.99 };
                    $scope.creditAmount = { amount:99.99 };
                }
            </script>
            <div ng-controller="Ctrl">
                <table class="amount">
                    <tr>
                        <th>&nbsp;</th>
                        <th>debit</th>
                        <th>credit</th>
                    </tr>
                    <tr>
                        <td>saldi</td>
                        <td> <amount data='debitAmount'/></td>
                        <td> <amount data='creditAmount'/></td>
                    </tr>
                </table>
            </div>
        </doc:source>
    </doc:example> 
 *
 * @param {Object} data an object with an amount property. 
 */
angular.module('abkComponents').directive('amount', [
    function () {
        return {
            restrict: 'E',
            replace:true,
            scope: {
                data: '='
            },
            template:  "<div><p ng-if='data.amount < 0'  class='debit  text-right'>{{(data.amount*-1) | number:2}}</p>\
                        <p ng-if='data.amount >= 0' class='credit text-right'>{{data.amount | number:2}}</p></div>"
        };
    }
]);

/**
 * @ngdoc service
 * @name abkComponents.service:currentDate
 * @description
 * factory to get the current date or the current date range (start of month to end of month)
 * @example 
 <pre>
    var now = currentDate.current();

    var range = currentDate.range();
 </pre>
 */
angular.module('abkComponents').factory('currentDate', function () {
    return {
        current: function () {
            return new Date();
        },
        range: function () {
            var now = this.current();
            var end = new Date(now.getFullYear(), now.getMonth() + 1, 0, 1, 0, 0);
            var start = new Date(now.getFullYear(), now.getMonth(), 1, 1, 0, 0);
            return {
                'start': start, 
                'end': end, 
                'previous' : function () {
                    end.setMonth(start.getMonth(), 0);
                    start.setMonth(start.getMonth() - 1);
                }, 
                'next': function () {
                    start.setMonth(start.getMonth() + 1);
                    end.setMonth(start.getMonth() + 1, 0);
                    end.setFullYear(start.getFullYear());
                }
            };
        },
        rangeYear: function () {
            var now = this.current();
            var end = new Date(now.getFullYear(), 11, 31, 1, 0, 0);
            var start = new Date(now.getFullYear(), 0, 1, 1, 0, 0);
            return {
                'start': start, 
                'end': end, 
                'previous' : function () {
                    end.setFullYear(end.getFullYear() -1);
                    start.setFullYear(start.getFullYear() -1);
                }, 
                'next': function () {
                    end.setFullYear(end.getFullYear() +1);
                    start.setFullYear(start.getFullYear() +1);
                }
            };
        }
    };
});

/**
 * @ngdoc directive
 * @name abkComponents.directive:contenteditable
 * 
 * @description
 * http://gaboesquivel.com/blog/2014/in-place-editing-with-contenteditable-and-angularjs/
 * 
 * @example
    <doc:example module="abkComponents">
        <doc:source>
            <script>
                function Ctrl() {
                    this.text = 'Click me to change';
                }
            </script>
            <div ng-controller="Ctrl as ctrl">
                <div contenteditable="true" ng-model="ctrl.text"></div>
            </div>

        </doc:source>
    </doc:example> 
 *
 * @param {Object} ngModel the model object. 
 */
angular.module('abkComponents').directive("contenteditable", function() {
  return {
    require: "ngModel",
    link: function(scope, element, attrs, ngModel) {

      function read() {
        ngModel.$setViewValue(element.html());
      }

      ngModel.$render = function() {
        element.html(ngModel.$viewValue || "");
      };

      element.bind("blur keyup change", function() {
        scope.$apply(read);
      });
    }
  };
});