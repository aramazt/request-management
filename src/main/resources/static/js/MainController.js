angular.module("client-app")
    .controller('MainController',
        ['UserService', 'RequestService', '$scope', 'urls', function (UserService, RequestService, $scope, urls) {

            var self = this;
            self.users = [];
            self.allRequests = [];

            self.newUser = {};
            self.newRequest = {};

            self.infoMessage = '';

            self.init = function () {
                self.loadAllUsers();
                self.loadAllRequests();
            };

            self.currentUser = {};
            self.fetchedRequests = [];


            /**********************************************/
            /*************User methods*********************/

            self.loadAllUsers = function () {
                console.log("LOADING ALL USERS...");
                UserService.loadAllUsers()
                    .then(function (response) {
                        self.users = response.data;
                    });
            };

            self.isObjectExist = function (obj) {
                return Object.keys(obj).length;
            };

            self.selectUser = function (user) {
                self.currentUser = user;
                self.getRequestsForUser(user);
                self.infoMessage = '';
            };

            self.changeStatus = function (user, index) {
                statusCondition(user);
                UserService.updateUser(user)
                    .then(
                        function (response) {
                            user = response.data;
                            if (user.status === 'OFFLINE') {
                                self.infoMessage = user.fullname + ' goes offline. Open requests were distributed among others.';
                            } else {
                                self.infoMessage = user.fullname + ' goes online.';
                            }
                            self.getRequestsForUser(self.currentUser);
                        },
                        function (errResponse) {
                            console.log("ERROR on status updating: " + errResponse);
                        }
                    )
            };

            function statusCondition(user) {
                if (user.status === 'ONLINE') {
                    user.status = 'OFFLINE';
                } else {
                    user.status = 'ONLINE'
                }
            }

            function isLoginNameExist(login) {
                var flag = false;
                self.users.forEach(function (user) {
                    if (user.login === login) flag = true;
                });
                return flag;
            }

            self.createUser = function (form) {
                if (isLoginNameExist(self.newUser.login)) {
                    alert("User with specified login name exist!");
                    return;
                }
                UserService.createUser(self.newUser)
                    .then(
                        function (response) {
                            self.users.push(response.data);
                            form.$setPristine();
                            self.newUser = {};
                            self.infoMessage = 'User with name ' + response.data.fullname + ' was created';
                        },
                        function (errResponse) {
                            console.log('can not create a new user: ' + errResponse);
                        }
                    );
            };

            self.deleteUser = function (user, index) {
                UserService.deleteUser(user.id)
                    .then(
                        function (value) {
                            self.users.splice(index, 1);
                            self.allRequests = [];
                            self.infoMessage = 'User ' + user.fullname + ' deleted. Open requests were distributed to others..'
                        },
                        function (errResponse) {
                            self.infoMessage = "Can't delete the user " + user.fullname + ', as all requests in the system\n' +
                                ' related to this user. And there is no other online user to acquire the requests';
                        }
                    );
            };

            /**********************************************/
            /*************Request methods******************/

            self.loadAllRequests = function (status) {
                if (!status) status = '';
                RequestService.loadAllRequests(status)
                    .then(function (response) {
                        self.allRequests = response.data;
                    });
                self.currentUser = {};
            };

            self.getAllRequests = function (status) {
                if (!status) status = '';
                RequestService.loadAllRequests(status)
                    .then(function (response) {
                        self.fetchedRequests = response.data;
                    });
            };

            self.getRequestsForUser = function (user) {
                RequestService.getRequestsForUser(user.id)
                    .then(function (response) {
                        self.allRequests = response.data;
                    });
            };

            self.createRequest = function (form) {
                self.newRequest.status = 'OPEN';
                RequestService.createRequest(self.newRequest)
                    .then(
                        function (response) {
                            var createdRequest = response.data;
                            if (self.currentUser.id === createdRequest.responsible.id) {
                                self.allRequests.push(createdRequest);
                            }
                            self.newRequest = {};
                            form.$setPristine();
                            self.infoMessage = 'Request with name ' + response.data.name + ' was created for user ' +
                                response.data.responsible.fullname;
                        },
                        function (errResponse) {
                            alert("Request with specified name exist!");
                        });
            };

            self.changeRequestStatus = function (request, newStatus) {
                request.status = newStatus;
                RequestService.updateRequest(request)
                    .then(function (response) {
                            self.infoMessage = "Status of request " + request.name + " has been changed to " + request.status;
                            self.getRequestsForUser(self.currentUser);
                        },
                        function (errResponse) {
                            console.log('Error while updating request: ' + errResponse);
                        });
            };

            self.deleteRequest = function (request) {
                RequestService.deleteRequest(request.id)
                    .then(function (response) {
                            self.infoMessage = "Request with name " + request.name + " was deleted.";
                            self.getRequestsForUser(self.currentUser);
                        },
                        function (errResponse) {
                            console.log('Error while deleting request' + errResponse);
                        });
            };

        }]);
