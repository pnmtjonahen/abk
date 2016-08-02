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
    * @name abkClientApp.controller:CostReportController
    * @description
    * # CostReportController
    * Controller of the abkClientApp
    */
   angular.module('abkClientApp').controller("CostReportController", costReportController);


   var processResult = function (that, result) {
      that.data = {
         "name": "costs",
         "parent" : that.data,
         "children": []
      };

      var transactions = result[0];
      var costcenters = result[1];

      costcenters.list.forEach(function (e) {
         if (e.filter) {
            e.filter = new RegExp(e.filter.trim(), 'i');
         }

         var pi = {"name": e.name, "costcenter": e, "parent":that.data};
         if (e.list) {
            pi.children = [];
            e.list.forEach(function (se) {
               if (se.filter) {
                  se.filter = new RegExp(se.filter.trim(), 'i');
               }
               pi.children.push({"name": se.name, "costcenter": se, "parent":e});
            });
         }

         that.data.children.push(pi);

      });

      transactions.list.forEach(function (t) {
         that.data.children.forEach(function (c) {
            if (t.debitCreditIndicator !== 'debit') {
               return;
            }
            if (c.costcenter.filter && (c.costcenter.filter.test(t.description) || c.costcenter.filter.test(t.contraAccountName))) {
               if (c.size === undefined) {
                  c.size = 0;
               }
               c.size += parseFloat(t.amount);
            }
            if (c.children) {
               c.children.forEach(function (sc) {
                  if (sc.costcenter.filter && (sc.costcenter.filter.test(t.description) || sc.costcenter.filter.test(t.contraAccountName))) {
                     if (sc.size === undefined) {
                        sc.size = 0;
                     }
                     sc.size += parseFloat(t.amount);
                  }
               });
            }
         });

      });
   };

   function retrieveData($q, that, currentDate, transactionsService, costCentersService) {
      var now = currentDate.current();
      var pref = currentDate.current();
      pref.setFullYear(now.getFullYear() - 1);

      $q.all([transactionsService.get({q: 'date=[' + pref.toJSON() + ' ' + now.toJSON() + ']', limit: 9999,
            fields: 'date,debitCreditIndicator,amount,description,contraAccountName'}).$promise,
         costCentersService.get({expand: 3}).$promise]).then(processResult.bind(null, that));
   }
   ;

   function costReportController($q, currentDate, transactionsService, costCentersService) {

      this.xdomain = [0, 31];
      this.ydomain = [1000, -1000];
      this.month = [];
      this.data = {};
      var that = this;


      retrieveData($q, that, currentDate, transactionsService, costCentersService);

      that.month = {
         "name": "flare",
         "children": [
            {
               "name": "analytics",
               "children": [
                  {
                     "name": "cluster",
                     "children": [
                        {"name": "AgglomerativeCluster", "size": 3938},
                        {"name": "CommunityStructure", "size": 3812},
                        {"name": "HierarchicalCluster", "size": 6714},
                        {"name": "MergeEdge", "size": 743}
                     ]
                  },
                  {
                     "name": "graph",
                     "children": [
                        {"name": "BetweennessCentrality", "size": 3534},
                        {"name": "LinkDistance", "size": 5731},
                        {"name": "MaxFlowMinCut", "size": 7840},
                        {"name": "ShortestPaths", "size": 5914},
                        {"name": "SpanningTree", "size": 3416}
                     ]
                  },
                  {
                     "name": "optimization",
                     "children": [
                        {"name": "AspectRatioBanker", "size": 7074}
                     ]
                  }
               ]
            }
         ]
      };

   }
   ;

})();