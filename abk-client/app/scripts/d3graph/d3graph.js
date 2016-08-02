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
(function() {
/**
 * @ngdoc overview
 * @name d3Graph
 * @description
 * A module containing D3 graph directives.
 */
var d3Graph = angular.module('d3Graph', []);

/**
 * @ngdoc directive
 * @name d3Graph.directive:lineGraph
 * @restrict E
 * @description
 * A multi line graph. Draws a x.y line graph using D3 library, make sure that you embed the graph into a bootstrap container-fluid div.
 * @example
 * @example
 <doc:example module="d3Graph">
 <doc:source>
 <div class="container-fluid">
 <div ng-controller="Ctrl">
 <line-graph xdomain='xdomain'
 ydomain='ydomain'
 linecolors='colornames'
 data='report'
 yaxislabel="Y-Axis" />
 </div>
 </div>
 <script>
 angular.module('d3Graph').controller('Ctrl',['$scope', function($scope) {
 $scope.xdomain = [0, 10];
 $scope.ydomain = [10, 0];
 $scope.colornames = ['black', 'red'];
 $scope.report = [{id: 0, data: [{key:0, value:0}, {key:1, value:5}, {key:2, value:5}, {key:3, value:6}, {key:4, value:2}, {key:5, value:8}]},
 {id: 1, data: [{key:1, value:1}, {key:5, value:5}, {key:7, value:3}]}];
 }]);
 </script>
 </doc:source>
 </doc:example>
 *
 *  @param {Array} xdomain an Array of a minimum and maximum scale of the x axis. Be aware that screen coordinates start with 0,0 in the upper left corner
 *  @param {Array} ydomain an Array of a minimum and maximum scale of the y axis. Be aware that screen coordinates start with 0,0 in the upper left corner
 *  @param {Array} colornames an Array of css color names. index of the array is the line color of the same index in the data.
 *  @param {Array} linenames an Array of line names. index of the array is the name of the same index in the data.
 *  @param {Array} data an Array of {id:id, data:[]}. each entry is a single line. The data points are in the form of {key:key, value:value}.
 *  @param {string} yaxislabel y axis label.
 *
 *
 */
d3Graph.directive('lineGraph', ['$window',
    function ($window) {
        return {
            restrict: 'E',
            scope: {
                data: '=',
                xdomain: '=',
                ydomain: '=',
                linecolors: '=',
                linenames: '='
            },
            link: function (scope, element, attributes) {
                var margin = {top: 10, bottom: 10, left: 0, right: 60};
                var width = 1024,
                        height = 600;


                var windowWidth = function () {
                    return parseInt(d3.select('.container-fluid').style('width')) - 50;
                };

                scope.$watch(windowWidth, function (newval, oldval) {
                    if (oldval !== newval) {
                        width = newval;
                        d3.select('#graph').attr('width', width);
                        scope.render();
                    }
                }, true);

                angular.element($window).bind('resize', function () {
                    scope.$apply();
                });

                width = windowWidth();
                var svg = d3.select(element[0])
                        .append('svg')
                        .attr('id', 'graph')
                        .attr('width', width)
                        .attr('height', height + 100)
                        .append('g')
                        .attr('transform', 'translate(' + margin.right + ',' + margin.top + ')');


                //Render graph based on 'data'
                scope.render = function () {

                    var xRange = d3.scale.linear().range([margin.left, width - margin.right - margin.left]).domain(scope.xdomain);
                    var yRange = d3.scale.linear().range([margin.top, height - margin.bottom - margin.top]).domain(scope.ydomain);

                    var xAxis = d3.svg.axis()
                            .scale(xRange)
                            .tickSize(2)
                            .orient('top')
                            .tickSubdivide(true);

                    var yAxis = d3.svg.axis()
                            .scale(yRange)
                            .tickSize(2)
                            .orient('left')
                            .tickSubdivide(true);

                    var lineFunc = d3.svg.line()
                            .x(function (d) {
                                return xRange(d.key);
                            })
                            .y(function (d) {
                                return yRange(d.value);
                            })
                            .interpolate('linear');

                    var lineColor = function(id) {
                        return scope.linecolors[id];
                    };
                    var lineName = function(id) {
                       return scope.linenames[id];
                    };
                    svg.selectAll('.x.axis').remove();
                    svg.selectAll('.y.axis').remove();
                    svg.selectAll('path').remove();
                    svg.selectAll('.legend').remove();


                    svg.append('svg:g')
                            .attr('class', 'x axis')
                            .attr('transform', 'translate(0,' + (height - margin.bottom - margin.top) + ')')
                            .call(xAxis);

                    svg.append('svg:g')
                            .attr('class', 'y axis')
                            .call(yAxis)
                            .append('text')
                            .attr('transform', 'rotate(-90)')
                            .attr('y', 6)
                            .attr('dy', '.71em')
                            .style('text-anchor', 'end')
                            .text(attributes['yaxislabel']);

                    scope.data.forEach(function (e, id) {
                        svg.append('svg:path')
                                .attr('d', lineFunc(e.data))
                                .attr('stroke', lineColor(id))
                                .attr('stroke-width', 2)
                                .attr('fill', 'none')
                                .attr('id', 'line-' + id);
                    });
                    svg.append('svg:g')
                         .attr('class', 'legend');

                    var legend = svg.selectAll('.legend');

                    scope.data.forEach(function (d, id) {

                    legend.append('rect')
                              .attr('y', height + 20)
                              .attr('x', id *  50)
                              .attr('width', 10)
                              .attr('height', 10)
                              .style('fill', lineColor(id));
                    legend.append('text')
                              .attr('y', height + 20)
                              .attr('x', (id *  50) + 15)
                              .text(lineName(id));
                      });

                };

                //Watch 'data' and run scope.render(newVal) whenever it changes
                //Use true for 'objectEquality' property so comparisons are done on equality and not reference
                scope.$watch('data', function () {
                    scope.render();
                }, false);

                scope.$watchCollection(['data', 'xdomain', 'ydomain', 'linecolors', 'linenames'], function () {
                    if (scope.data !== undefined
                            && scope.linecolors !== undefined
                            && scope.linenames !== undefined
                            && scope.xdomain !== undefined
                            && scope.ydomain !== undefined) {
                        scope.render();
                    }
                }, true);
            }
        };
    }
]);
d3Graph.directive('piGraph', ['$window',
    function ($window) {
        return {
            restrict: 'E',
            scope: {
                data: '=',
                xdomain: '=',
                ydomain: '='
            },
            link: function (scope, element, attributes) {
                var width = 1024,
                        height = 600,
                        radius = Math.min(width, height) / 2,
                        color = d3.scale.category20c();


                var windowWidth = function () {
                    return parseInt(d3.select('.container-fluid').style('width')) - 50;
                };

                scope.$watch(windowWidth, function (newval, oldval) {
                    if (oldval !== newval) {
                        width = newval;
                        d3.select('#graph').attr('width', width);
                        scope.render();
                    }
                }, true);

                angular.element($window).bind('resize', function () {
                    scope.$apply();
                });

                width = windowWidth();

                var svg = d3.select(element[0])
                        .append('svg')
                        .attr('id', 'graph')
                        .attr('width', width)
                        .attr('height', height + 100)
                        .append('g')
                        .attr('transform', 'translate(' + width / 2 + ',' + height * .52 + ')');

               function stash(d) {
                 d.x0 = d.x;
                 d.dx0 = d.dx;
               };
               var partition = d3.layout.partition()
                   .sort(null)
                   .size([2 * Math.PI, radius * radius])
                   .value(function(d) { return d.size; });

               var arc = d3.svg.arc()
                   .startAngle(function(d) { return d.x; })
                   .endAngle(function(d) { return d.x + d.dx; })
                   .innerRadius(function(d) { return Math.sqrt(d.y); })
                   .outerRadius(function(d) { return Math.sqrt(d.y + d.dy); });
               function getAngle(d) {
                   // Offset the angle by 90 deg since the '0' degree axis for arc is Y axis, while
                   // for text it is the X axis.
                   var thetaDeg = (180 / Math.PI * (arc.startAngle()(d) + arc.endAngle()(d)) / 2 - 90);
                   // If we are rotating the text by more than 90 deg, then "flip" it.
                   // This is why "text-anchor", "middle" is important, otherwise, this "flip" would
                   // a little harder.
                   return (thetaDeg > 90) ? thetaDeg - 180 : thetaDeg;
               }
               //Render graph based on 'data'
                scope.render = function () {

                  var path = svg.datum(scope.data).selectAll("path")
                       .data(partition.nodes)
                       .enter().append("g");
                  path.append("path")
                       .attr("display", function (d) {
                          return d.depth ? null : "none";
                       }) // hide inner ring
                       .attr("d", arc)
                       .style("stroke", "#fff")
                       .style("fill", function (d) {
                          return color((d.children ? d : (d.parent ? d.parent:d)).name);
                       })
                       .style("fill-rule", "evenodd")
                       .each(stash);

                  path.append("text")
                           .text(function(d) { return d.name})
                           .classed("label", true)
                           .attr("x", function(d) { return d.x; })
                           .attr("text-anchor", "middle")
                           // translate to the desired point and set the rotation
                           .attr("transform", function(d) {
                               if (d.depth > 0) {
                                   return "translate(" + arc.centroid(d) + ")" +
                                          "rotate(" + getAngle(d) + ")";
                               }  else {
                                   return null;
                               }
                           })
                           .attr("dx", "6") // margin
                           .attr("dy", ".35em") // vertical-align
                           .attr("pointer-events", "none");

                };

                //Watch 'data' and run scope.render(newVal) whenever it changes
                //Use true for 'objectEquality' property so comparisons are done on equality and not reference
                scope.$watch('data', function () {
                    scope.render();
                }, false);

                scope.$watchCollection(['data', 'xdomain', 'ydomain'], function () {
                    if (scope.data !== undefined
                            && scope.xdomain !== undefined
                            && scope.ydomain !== undefined) {
                        scope.render();
                    }
                }, true);
            }
        };
    }
]);

})();