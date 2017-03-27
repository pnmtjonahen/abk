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
(function () {
    'use strict';

    angular.module('abkClientApp').controller("UploadController", uploadController);

    var getJwt = function ($localstorage) {
        var user = $localstorage.getObject('user');
        if (user) {
            return user.token;
        }
        return "";
    };

    function uploadController($scope, FileUploader, backendConfig, uploadService, userCheckService, $localstorage) {

        uploadService.get({}, function (data) {
            console.info(data);
        });

        var uploader = $scope.uploader = new FileUploader({
            url: backendConfig.uploadPath,
            headers: {
                "Authorization" : getJwt($localstorage)
            }
        });

        // FILTERS

        uploader.filters.push({
            name: 'customFilter',
            fn: function (item, options) {
                return this.queue.length < 10;
            }
        });

        uploader.onErrorItem = function (fileItem, response, status, headers) {
            console.info('onErrorItem', fileItem, response, status, headers);
        };

        userCheckService.check();

    }
    ;

})();