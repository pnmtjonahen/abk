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
var myApp = angular.module('abkApp', ['abkServices', 'ngRoute', 'treeGrid']);

myApp.config(['$routeProvider', function($routeProvider) {
        $routeProvider.
                when('/home', {templateUrl: 'partials/home.html', controller: HomeCtrl}).
                when('/rekeningen', {templateUrl: 'partials/rekeningen.html', controller: RekeningenListCtrl}).
                when('/kostenplaatsen', {templateUrl: 'partials/kostenplaatsen.html', controller: KostenplaatsenCtrl}).
                when('/transacties', {templateUrl: 'partials/transacties.html', controller: TransactiesCtrl}).
                when('/upload', {templateUrl: 'partials/upload.html', controller: UploadCtrl}).
                when('/yearreport', {templateUrl: 'partials/yearreport.html', controller: YearReportCtrl}).
                when('/rekeningenpermaand', {templateUrl: 'partials/rekeningenpermaand.html', controller: RekeningenpermaandCtrl}).
                otherwise({redirectTo: '/home'});
    }]);

myApp.run(function($rootScope) {
    $rootScope.showDebug = false;
});

myApp.directive('ngcFocus', ['$parse', function($parse) {
        return function(scope, element, attr) {
            var fn = $parse(attr['ngcFocus']);
            element.bind('focus', function(event) {
                scope.$apply(function() {
                    fn(scope, {$event: event});
                });
            });
        };
    }]);

myApp.directive('ngcBlur', ['$parse', function($parse) {
        return function(scope, element, attr) {
            var fn = $parse(attr['ngcBlur']);
            element.bind('blur', function(event) {
                scope.$apply(function() {
                    fn(scope, {$event: event});
                });
            });
        };
    }]);


myApp.directive('upload', ['uploadManager', function factory(uploadManager) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                $(element).fileupload({
                    dataType: 'text',
                    add: function(e, data) {
                        uploadManager.add(data);
                    },
                    progressall: function(e, data) {
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        uploadManager.setProgress(progress);
                    },
                    done: function(e, data) {
                        uploadManager.setProgress(0);
                    }
                });
            }
        };
    }]);

myApp.directive('abkD3Graph', [
    function() {
        return {
            restrict: 'E',
            scope: {
                data: '='
            },
            link: function(scope, element) {
                var margin = {top: 20, right: 20, bottom: 50, left: 60},
                width = 1024,
                        height = 600;

                var svg = d3.select(element[0])
                        .append("svg")
                        .attr('width', width)
                        .attr('height', height)
                        .append("g")
                        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


                //Render graph based on 'data'
                scope.render = function(data) {
                    
                    xRange = d3.scale.linear().range([margin.left, width - margin.right]).domain([0, 31]);
                    yRange = d3.scale.linear().range([height - margin.top, margin.bottom]).domain([
                        Math.min(d3.min(data.kosten, function(d) {
                            return d.bedrag;
                        }),d3.min(data.current, function(d) {
                            return d.bedrag;
                        })), Math.max(d3.max(data.kosten, function(d) {
                            return d.bedrag;
                        }), d3.max(data.current, function(d) {
                            return d.bedrag;
                        }))]);

                    xAxis = d3.svg.axis()
                            .scale(xRange)
                            .tickSize(1)
                            .tickSubdivide(true);
                    
                    yAxis = d3.svg.axis()
                            .scale(yRange)
                            .tickSize(5)
                            .orient('left')
                            .tickSubdivide(true);

                    svg.append('svg:g')
                            .attr('class', 'x axis')
                            .attr('transform', 'translate(0,' + (height - margin.bottom) + ')')
                            .call(xAxis);

                    svg.append('svg:g')
                            .attr('class', 'y axis')
                            .attr('transform', 'translate(' + (margin.left) + ',0)')
                            .call(yAxis);
                    
                    var lineFunc = d3.svg.line()
                            .x(function(d) {
                                return xRange(d.dag);
                            })
                            .y(function(d) {
                                return yRange(d.bedrag);
                            })
                            .interpolate('linear');

                    svg.append("svg:path")
                            .attr("d", lineFunc(data.kosten))
                            .attr("stroke", "blue")
                            .attr("stroke-width", 2)
                            .attr("fill", "none");
                    svg.append("svg:path")
                            .attr("d", lineFunc(data.current))
                            .attr("stroke", "red")
                            .attr("stroke-width", 2)
                            .attr("fill", "none");

                };

                //Watch 'data' and run scope.render(newVal) whenever it changes
                //Use true for 'objectEquality' property so comparisons are done on equality and not reference
                scope.$watch('data', function() {
                    scope.render(scope.data);
                }, true);
            }
        };
    }
]);

myApp.filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return input.slice(start);
    };
});


