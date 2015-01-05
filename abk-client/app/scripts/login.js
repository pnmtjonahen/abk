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

angular.module('abkClientApp').service('loginModal', function (ngDialog, $rootScope) {


    return function () {
        var instance = ngDialog.openConfirm({
            templateUrl: "views/login.html",
            controller: 'LoginController'
        });

        return instance;
    };

});


angular.module('abkClientApp').controller('LoginController', function ($scope, $rootScope) {
    function assignCurrentUser(user) {
        $rootScope.currentUser = user;
        return user;
    }

  $scope.cancel = function() {
      $scope.closeThisDialog();
  };

  $scope.submit = function () {
      // call backend login method
      assignCurrentUser({user:'name'});
      $scope.confirm();
  };
});


angular.module('abkClientApp').run(function ($rootScope, $state, loginModal) {

    $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
        var requireLogin = toState.data.requireLogin;

        if (requireLogin && typeof $rootScope.currentUser === 'undefined') {
            event.preventDefault();
            loginModal().then(function () {
                return $state.go(toState.name, toParams);
            }, function () {
                return $state.go('home');

            });
        }
    });

});

angular.module('abkClientApp').config(function ($httpProvider) {

  $httpProvider.interceptors.push(function ($timeout, $q, $injector) {
    var loginModal, $http, $state;

    // this trick must be done so that we don't receive
    // `Uncaught Error: [$injector:cdep] Circular dependency found`
    $timeout(function () {
      loginModal = $injector.get('loginModal');
      $http = $injector.get('$http');
      $state = $injector.get('$state');
    });

    return {
      responseError: function (rejection) {
        if (rejection.status !== 401) {
          return rejection;
        }

        var deferred = $q.defer();

        loginModal()
          .then(function () {
            deferred.resolve( $http(rejection.config) );
          })
          .catch(function () {
            $state.go('home');
            deferred.reject(rejection);
          });
        return deferred.promise;
      }
    };
  });

});

