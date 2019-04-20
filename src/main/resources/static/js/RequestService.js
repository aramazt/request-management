'use strict';

angular.module('client-app').factory('RequestService',
    ['$http', '$q', 'urls', function ($http, $q, urls) {

        var factory = {
            'loadAllRequests': loadAllRequests,
            'getRequestsForUser': getRequestsForUser,
            'createRequest': createRequest,
            'updateRequest': updateRequest,
            'deleteRequest': deleteRequest
        };

        function createRequest(request) {
            return $http.post(urls.SAVE_REQUEST, request);
        }

        function updateRequest(request) {
            return $http.post(urls.SAVE_REQUEST, request);
        }

        function loadAllRequests(status) {
            return $http.get(urls.ALL_REQUESTS_URL + '?status=' + status)
        }

        function getRequestsForUser(id) {
            return $http.get(urls.REQUESTS_BY_USER + id);
        }

        function deleteRequest(id) {
            return $http.delete(urls.DELETE_REQUEST + id);
        }

        return factory;
    }
    ]);
