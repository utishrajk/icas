'use strict';

angular.module('icas.userService', ['ngResource'])

    .factory('UserService', ['$resource', '$location',
        function($resource, $location){

            var IdentifyUser = $resource( 'app/public/identifyuser');
            var SecurityQuestions = $resource('securityquestions');
            var CreateUser = $resource('app/public/register');
            var RetrieveQuestions = $resource('app/public/retrieveSecurityQuestions/:userName/:dateOfBirth', {userName: '@userName', dateOfBirth: '@dateOfBirth'});
            var VerifyAnswers = $resource('app/public/verifyAnswers');
            var ResetPassword = $resource('app/public/resetpassword');
            var SendVerificationEmail = $resource('app/public/sendVerificationEmail');
            var ResetPasswordAndSecurityQuestions = $resource('app/public/resetpasswordandsecurityquestions');

            return {

                create : function(careManager, successCb, errorCb) {
                    CreateUser.save(careManager, successCb, errorCb);
                },

                identifyUser : function (user, successCb, errorCb) {
                    IdentifyUser.save({userName: user.userName, dateOfBirth: user.dateOfBirth}, successCb, errorCb );
                },

                getSecurityQuestionsCodes : function(successCb, errorCb) {
                    SecurityQuestions.query(successCb, errorCb);
                },

                retrieveQuestions: function(user, successCb, errorCb) {
                    RetrieveQuestions.get({userName: user.userName, dateOfBirth: 'dob'}, successCb, errorCb);
                },

                verifyAnswers: function(user, successCb, errorCb) {
                    VerifyAnswers.save({userName: user.userName, securityAnswer1 : user.securityAnswers.answer1, securityAnswer2: user.securityAnswers.answer2}, successCb, errorCb);
                },

                resetPassword: function(resetPasswordDto, successCb, errorCb) {
                    ResetPassword.save(resetPasswordDto, successCb, errorCb);
                },

                forgotSecurityQuestions: function(user, successCb, errorCb) {
                    SendVerificationEmail.save(user, successCb, errorCb);
                },

                resetPasswordAndSecurityQuestions: function(user, successCb, errorCb) {
                    ResetPasswordAndSecurityQuestions.save(user, successCb, errorCb);
                }

            };
        }]);