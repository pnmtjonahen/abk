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

angular.module('abkClientApp').service('loginModal', function (ngDialog) {

    return function () {
        var instance = ngDialog.openConfirm({
            templateUrl: "views/login.html",
            controller: 'LoginController',
            controllerAs: 'LoginController'
        });

        return instance;
    };

});


angular.module('abkClientApp').controller('LoginController', function ($scope, $rootScope, $localstorage, userLoginService) {
    this.name = '';
    this.password = '';

    var that = this;


    $scope.cancel = function () {
        $scope.closeThisDialog();
    };

    $scope.submit = function () {
        // call backend login method
        var user = {'username': that.name, 'password': that.password};
        userLoginService.login(user, function (data, status) {
            console.log(data);

            var token = status('Authorization');
            if (token) {
                user.token = token;
                $localstorage.setObject("user", user);
                $rootScope.currentUser = user;
            }
            $scope.confirm();
        }, function (error) {
            console.log(error);
        });
    };
});


//angular.module('abkClientApp').run(function ($rootScope, $state, loginModal, userCheckService) {
//
//    $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
//        var requireLogin = toState.data.requireLogin;
//
//        if (requireLogin) {
//            event.preventDefault();
//            var result = null;
//            userCheckService.check("", function (data) {
//                result = $state.go(toState.name, toParams);
//            }).$promise.catch(function (response) {
//                loginModal().then(function () {
//                    result = $state.go(toState.name, toParams);
//                }, function () {
//                    result = $state.go('home');
//                });
//            });
//            // wait for result
//            while (!result) {};
//            return result;
//        }
//    });
//
//});

angular.module('abkClientApp').config(function ($httpProvider) {

    $httpProvider.interceptors.push(function ($timeout, $q, $injector) {
        var loginModal, loginModalInstance, $http, $state, ngDialog;
        var rejections = [];
        var deferres = [];

        // this trick must be done so that we don't receive
        // `Uncaught Error: [$injector:cdep] Circular dependency found`
        $timeout(function () {
            loginModal = $injector.get('loginModal');
            $http = $injector.get('$http');
            $state = $injector.get('$state');
            ngDialog = $injector.get('ngDialog');
        });

        return {
            responseError: function (rejection) {
                if (rejection.status !== 401) {
                    return rejection;
                }


                var deferred = $q.defer();
                loginModal()
                        .then(function () {
                            deferred.resolve($http(rejection.config));
                        })
                        .catch(function () {
                            $state.go('home');
                            deferred.reject(rejection);
                        });

                return deferred.promise;
            }
        };
    });

    // Injects an HTTP interceptor that replaces a "Bearer" authorization header
// with the current Bearer token.
    $httpProvider.interceptors.push(function ($localstorage) {
        return {
            request: function (config) {
                var user = $localstorage.getObject('user');
                if (user) {
                    config.headers.Authorization = user.token;
                }
                return config;
            }
        };
    });


});


