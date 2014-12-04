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

function UploadCtrl($scope, FileUploader, backendConfig) {
    var uploader = $scope.uploader = new FileUploader({
        url: backendConfig.uploadPath
    });

    // FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function (item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 10;
        }
    });

    // CALLBACKS

//    uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
//        console.info('onWhenAddingFileFailed', item, filter, options);
//    };
//    uploader.onAfterAddingFile = function (fileItem) {
//        console.info('onAfterAddingFile', fileItem);
//    };
//    uploader.onAfterAddingAll = function (addedFileItems) {
//        console.info('onAfterAddingAll', addedFileItems);
//    };
//    uploader.onBeforeUploadItem = function (item) {
//        console.info('onBeforeUploadItem', item);
//    };
    uploader.onProgressItem = function (fileItem, progress) {
        console.info('onProgressItem', fileItem, progress);
    };
//    uploader.onProgressAll = function (progress) {
//        console.info('onProgressAll', progress);
//    };
//    uploader.onSuccessItem = function (fileItem, response, status, headers) {
//        console.info('onSuccessItem', fileItem, response, status, headers);
//    };
    uploader.onErrorItem = function (fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
    };
//    uploader.onCancelItem = function (fileItem, response, status, headers) {
//        console.info('onCancelItem', fileItem, response, status, headers);
//    };
//    uploader.onCompleteItem = function (fileItem, response, status, headers) {
//        console.info('onCompleteItem', fileItem, response, status, headers);
//    };
//    uploader.onCompleteAll = function () {
//        console.info('onCompleteAll');
//    };

//    console.info('uploader', uploader);
};
