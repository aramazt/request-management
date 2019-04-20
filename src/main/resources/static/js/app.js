console.log("Loading JS");

var app = angular.module("client-app", []);

var BASE_URL = 'http://localhost:8080';

app.constant('urls', {
    ALL_USERS_URL: BASE_URL + '/api/users',
    CREATE_USER: BASE_URL + '/api/users/create/',
    UPDATE_USER: BASE_URL + '/api/users/update/',
    DELETE_USER: BASE_URL + '/api/users/delete/',
    ALL_REQUESTS_URL: BASE_URL + '/api/requests',
    REQUESTS_BY_USER: BASE_URL + '/api/requests/forUser/',
    SAVE_REQUEST: BASE_URL + '/api/requests/save',
    DELETE_REQUEST: BASE_URL + '/api/requests/delete/'
});


app.directive('applicationForms', function () {
   return {
       templateUrl: '/templates/forms'
   };
});
