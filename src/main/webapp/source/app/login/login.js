/**
 * Created by utish.rajkarnikar on 7/25/14.
 */

'use strict';

angular.module('icas.loginModule', [
        'ngResource',
        'icas.userService',
        'icas.security'
    ])

    .config(['$routeProvider', 'USER_ROLES', function ($routeProvider, USER_ROLES) {
        $routeProvider
            .when('/login', {
                templateUrl: 'login/login.tpl.html',
                controller: 'LoginController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/logout', {
                templateUrl: 'login/login.tpl.html',
                controller: 'LogoutController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/register', {
                templateUrl: 'login/register.tpl.html',
                controller: 'RegisterController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/forgotpasswordidentify', {
                templateUrl: 'login/forgot-password-identify.tpl.html',
                controller: 'ForgotPasswordIdentifyController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/forgotpasswordquestions', {
                templateUrl: 'login/forgot-password-questions.tpl.html',
                controller: 'ForgotPasswordVerifyAnswersController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/forgotpasswordverifiedmessage', {
                templateUrl: 'login/forgot-password-verified.tpl.html',
                controller: 'ForgotPasswordVerifiedController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/resetpassword', {
                templateUrl: 'login/reset-password.tpl.html',
                controller: 'ResetPasswordController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/resetpasswordsuccess', {
                templateUrl: 'login/reset-password-success.tpl.html',
                controller: 'ResetPasswordSuccessController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/resetpasswordandsecurityquestions', {
                templateUrl: 'login/forgot-password-and-security-questions.tpl.html',
                controller: 'ForgotPasswordAndSecurityQuestionsController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            });


    }])
    .controller('LoginController', ['$scope', '$location', 'AuthenticationSharedService', '$rootScope', '$http', '$cookieStore', 'authService', 'Session', 'Account' ,
        function ($scope, $location, AuthenticationSharedService, $rootScope, $http, $cookieStore, authService, Session, Account) {

        var success = function (data, status, headers, config) {
            Account.get(function(data) {
                Session.create(data.id, data.userName, data.firstName, data.lastName, data.email, data.roles);
                $cookieStore.put('account', JSON.stringify(Session));
                $rootScope.loginerror = false;
                authService.loginConfirmed(data);
            });
        };

        var error = function (data, status, headers, config) {
            Session.destroy();
            $rootScope.loginerror = true;
            $scope.loginErrorMsg = data;
        };

        $scope.login = function () {
            //If any patient has been selected, then set it to null;
            $scope.setSelectedPatient(null);

            AuthenticationSharedService.login({
                email: $scope.email,
                password: $scope.password
            }, success, error);
        };

        var successEnv = function (data) {
            $scope.environment = data.environment;
        };

        var errorEnv = function (error) {

        };

        AuthenticationSharedService.getEnvironment(successEnv, errorEnv);
    }])
    .controller('LogoutController', ['$scope', '$location', 'AuthenticationSharedService', function ($scope, $location, AuthenticationSharedService) {
        AuthenticationSharedService.logout({
            success: function () {
                $location.path('');
            }
        });
    }])
    .controller('ForgotPasswordIdentifyController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {

        $scope.resetPasswordIdentify = function () {

            var identity = $scope.forgotPasswordIdentity;

            var successCallback = function (data) {

                $scope.resetPassworderror = false;

                //since it's a success, make another call to retrieve the questions corresponding to the user
                var successRetrieveQuestions = function (questions) {

                    $rootScope.question1 = questions.question1;
                    $rootScope.question2 = questions.question2;
                    $location.path("/forgotpasswordquestions");
                };

                var errorRetrieveQuestions = function (error) {
                    $scope.resetPassworderror = true;
                    $scope.resetPasswordMsg = error.data.message;
                };

                UserService.retrieveQuestions(identity, successRetrieveQuestions, errorRetrieveQuestions);

                //set the identity in the scope of the AppCtrl
                $scope.forgotPasswordUser(identity);
            };

            var errorCallback = function (error) {
                $scope.resetPasswordMsg = "Username and/or date of birth is not valid";
                $scope.resetPassworderror = true;
            };

            UserService.identifyUser(identity, successCallback, errorCallback);
        };

    }])
    .controller('ForgotPasswordVerifyAnswersController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {
        $scope.verifyAnswers = function () {

            //retrieve the identity from the scope of the AppCtrl
            var identity = $scope.user;

            var securityAnswers = identity.securityAnswers = $scope.securityAnswers;

            var successVerifyAnswers = function (data) {

                $scope.verifyAnswerError = false;
                $location.path("/forgotpasswordverifiedmessage");
            };

            var errorVerifyAnswers = function (error) {

                $scope.verifyAnswersMsg = "One or more security answers are not valid";
                $scope.verifyAnswerError = true;
            };

            UserService.verifyAnswers(identity, successVerifyAnswers, errorVerifyAnswers);
        };

        $scope.forgotSecurityQuestions = function () {

            var identity = $scope.user;

            var successSecurityQuestions = function (data) {
                $location.path("/forgotpasswordverifiedmessage");
                $scope.forgotQuestionsError = false;
            };

            var errorSecurityQuestions = function (error) {
                $scope.forgotQuestionsMsg = 'Unable to process the request';
                $scope.forgotQuestionsError = true;
            };

            UserService.forgotSecurityQuestions(identity, successSecurityQuestions, errorSecurityQuestions);
        };

    }])
    .controller('ForgotPasswordVerifiedController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {

    }])
    .controller('ResetPasswordController', ['$routeParams', '$rootScope', '$scope', '$location', 'UserService', function ($routeParams, $rootScope, $scope, $location, UserService) {

        $scope.resetPassword = function () {

            var successResetPassword = function (data) {
                $location.path("/resetpasswordsuccess");
            };

            var errorResetPassword = function (error) {
                $scope.resetPasswordMsg = error.data.message;
                $scope.resetPasswordError = true;
            };

            var password = $scope.password;

            var data = {
                token: $routeParams.token,
                credential: password.password1,
                confirmPassword: password.password2

            };
            UserService.resetPassword(data, successResetPassword, errorResetPassword);
        };

        $scope.comparePasswords = function () {
            var user = $scope.password;
            $scope.passwordsMatch = user.password1 !== user.password2;
        };

    }])
    .controller('ResetPasswordSuccessController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {

    }])
    .controller('ForgotPasswordAndSecurityQuestionsController', ['$routeParams', '$rootScope', '$scope', '$location', 'UserService', function ($routeParams, $rootScope, $scope, $location, UserService) {

        var successQuestions = function (data) {
            $scope.securityQuestions = data;
        };

        var errorQuestions = function (error) {
            $scope.resetPasswordAndQuestionsMsg = error.data.message;
            $scope.resetPasswordAndQuestionsError = true;
        };

        UserService.getSecurityQuestionsCodes(successQuestions, errorQuestions);

        $scope.resetPasswordAndSecurityQuestions = function () {

            var form = $scope.resetPasswordAndQuestions;
            var successResetPwdAndQuestions = function (data) {
                $location.path("/resetpasswordsuccess");
            };

            var errorResetPwdAndQuestions = function (error) {
                $scope.resetPasswordAndQuestionsError = true;
                $scope.resetPasswordAndQuestionsMsg = error.data.message;
            };

            var data = {
                token: $routeParams.token,
                credential: form.password1,
                confirmPassword: form.password2,
                securityQuestion1: form.securityQuestion1,
                securityAnswer1: form.securityAnswer1,
                securityQuestion2: form.securityQuestion2,
                securityAnswer2: form.securityAnswer2
            };

            UserService.resetPasswordAndSecurityQuestions(data, successResetPwdAndQuestions, errorResetPwdAndQuestions);
        };

        $scope.comparePasswords = function () {
            var form = $scope.resetPasswordAndQuestions;
            $scope.passwordsMatch = form.password1 !== form.password2;
        };

        $scope.compareSecurityQuestions = function () {
            var form = $scope.resetPasswordAndQuestions;
            $scope.questionsMatch = form.securityQuestion1 === form.securityQuestion2;
        };
    }])
    .controller('RegisterController', ['$rootScope', '$scope', '$location', 'UserService', function ($rootScope, $scope, $location, UserService) {

        var successCallback = function (data) {
            $scope.registererror = false;
            $location.path("/login");
            $rootScope.checkEmailMessage = true;
        };

        var errorCallback = function (error) {
            $scope.registerErrorMsg = error.data.message;
            $scope.registererror = true;
        };

        var successQuestions = function (data) {
            $scope.securityQuestions = data;
        };

        var errorQuestions = function (error) {
            $scope.registerErrorMsg = error.data.message;
        };

        UserService.getSecurityQuestionsCodes(successQuestions, errorQuestions);

        $scope.register = function () {
            var registerUser = $scope.registerUser;
            UserService.create(registerUser, successCallback, errorCallback);
        };

        $scope.resetMessages = function () {
            $rootScope.checkEmailMessage = false;
        };

        $scope.comparePasswords = function () {
            var user = $scope.registerUser;
            $scope.passwordsMatch = (user.credential !== user.confirmPassword);
        };

        $scope.compareSecurityQuestions = function () {
            var user = $scope.registerUser;
            $scope.questionsMatch = (user.securityQuestion1 === user.securityQuestion2);
        };

    }]);
