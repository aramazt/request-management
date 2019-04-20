'use strict';

angular.module('client-app').factory('UserService',
    ['urls', '$http', '$q', function (urls, $http, $q) {

        var factory = {
            loadAllUsers: loadAllUsers,
            createUser: createUser,
            updateUser: updateUser,
            deleteUser: deleteUser
        };

        return factory;

        function loadAllUsers() {
            return $http.get(urls.ALL_USERS_URL);
        }

        function createUser(user) {
            return $http.post(urls.CREATE_USER, user);
        }

        function updateUser(user) {
            return $http.put(urls.UPDATE_USER, user);
        }

        function deleteUser(id) {
            return $http.delete(urls.DELETE_USER + id);
        }

    }
    ]);
