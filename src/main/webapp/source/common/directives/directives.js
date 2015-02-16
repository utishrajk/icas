'use strict';

angular.module("icas.directives", [])

    .directive('datepicker', function () {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelCtrl) {
                element.datepicker({
                    dateFormat: 'mm/dd/yy',
                    autoclose: true,
                    onSelect: function (date) {
                        scope.$apply(function () {
                            ngModelCtrl.$setViewValue(date);
                        });

                    }
                });
            }
        };
    })

    .factory('DateUtils', function () {
        // validate if entered values are a real date
        function validateDate(date){
            if(isFebruary(date.month)){
                var leapYear = isLeapYear(date.year);
                if( leapYear&& date.day <= 29){
                        return true;
                }else if( !leapYear&& date.day <= 28){
                        return true;
                }else {
                    return false;
                }
            }else  {
                if(validNumberOfDaysInMonth(date.month, date.day)){
                    return true;
                }else{
                    return false;
                }
            }
        }

        function isFebruary(month){
            return (month === 2);
        }

        function isLeapYear(yr){
            return (yr%400)?((yr%100)?((yr%4)?false:true):false):true;
        }

        function validNumberOfDaysInMonth(month, day){
            var months = self.months;
            return ( day <= months[month-1].days);
        }

        var self = this;
        this.days = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
        this.months = [
            { value: 1, name: 'January',days:31 },
            { value: 2, name: 'February', days:28 },
            { value: 3, name: 'March', days:31 },
            { value: 4, name: 'April', days:30 },
            { value: 5, name: 'May', days:31 },
            { value: 6, name: 'June', days:30 },
            { value: 7, name: 'July', days:31 },
            { value: 8, name: 'August', days:31 },
            { value: 9, name: 'September', days:30 },
            { value: 10, name: 'October', days:31 },
            { value: 11, name: 'November', days:30 },
            { value: 12, name: 'December', days:31 }
        ];

        this.years = [];
        var currentYear = (new Date()).getFullYear();

        for(var i = currentYear; i >= 1900; i-- ){
            this.years.push(i);
        }

        return {
            checkDate: function(date) {
                if(!date.day || !date.month || !date.year){
                    return false;
                }
                if(validateDate(date)) {
                    return date;
                }
                else {
                    return false;
                }
            },
            get: function(name) {
                return self[name];
            },

            dateObjectToString: function(dateObject){
                return dateObject.month + "/" + dateObject.day + "/" + dateObject.year;
            },

            isUnderAge: function (dateString) {
                var today = new Date();
                var birthDate = new Date(dateString);
                var age = today.getFullYear() - birthDate.getFullYear();
                var m = today.getMonth() - birthDate.getMonth();
                if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }

                if(age >= 0 && age < 18){
                    return true;
                }else{
                    return false;
                }
            },

            isFutureDate: function (dateStr) {
                var result = false;
                if(angular.isUndefined(dateStr) || dateStr === null){
                    return result;
                }else if (dateStr) {
                    var today = new Date().getTime();
                    var newDate = new Date(dateStr).getTime();

                    if (newDate > today) {
                        result = true;
                    }
                }
                return result;
            }
        };
    })
    .directive('datedropdown', function () {
        return {
            restrict: 'EA',
            require: 'ngModel',
            scope: {
                datemodel:'=ngModel',
                isrequired: '=',
                futuredatevalidation: '=',
                agevalidation : '=',
                isvaliddate :'='
            },
            template:'<div style="display:block;">' +
                            '<select name="month" ng-model="dateFields.month" ng-options="month.value as month.name for month in months" ng-change="checkDate()" ng-required="isrequired" >' +
                                '<option value="" >Month</option>' +
                            '</select>&nbsp;'+
                            '<select name="day" ng-model="dateFields.day" ng-options="day for day in days" ng-change="checkDate()" ng-required="isrequired">' +
                                '<option value="" >Day</option>' +
                            '</select>&nbsp;'+
                            '<select name="year" ng-model="dateFields.year" ng-options="y for y in years" ng-change="checkDate()" ng-required="isrequired">' +
                                '<option value="" >Year</option>' +
                            '</select><br/>' +
                            '<span ng-show="requiredFieldError" class="red"> This field is required.</span>'+
                            '<span ng-show="invalidDateError" class="red"> Invalid date.</span>'+
                            '<span ng-show="isUnderAge" class="red"> Should be at least 18 years of age.</span>'+
                            '<span ng-show="isFutureDate" class="red"> Date should not be in the future.</span>' +
                    '</div>',

            controller: ['$scope', 'DateUtils', function($scope, DateUtils){
                // set up arrays of values
                $scope.days = DateUtils.get('days');
                $scope.months = DateUtils.get('months');
                $scope.years = DateUtils.get('years');

                //split the current date into sections
                $scope.dateFields = {};
                if($scope.datemodel){
                    var splitDate = ($scope.datemodel).split("/");
                    $scope.dateFields.month = parseInt(splitDate[0]);
                    $scope.dateFields.day = parseInt(splitDate[1]);
                    $scope.dateFields.year = parseInt(splitDate[2]);
                }else{
                    $scope.requiredFieldError = $scope.isrequired;
                }

                $scope.checkDate = function(){
                    var date = DateUtils.checkDate($scope.dateFields);
                    if(date){
                        $scope.datemodel = DateUtils.dateObjectToString($scope.dateFields);
                        $scope.requiredFieldError = false;
                        $scope.invalidDateError = false;
                        $scope.isvaliddate = true;

                        if($scope.agevalidation){
                            $scope.isUnderAge = DateUtils.isUnderAge($scope.datemodel);
                            if($scope.isUnderAge){
                                $scope.isvaliddate = false;
                            }
                        }else{
                            $scope.isUnderAge = false;
                        }

                        if($scope.futuredatevalidation){
                            $scope.isFutureDate = DateUtils.isFutureDate($scope.datemodel);
                            if($scope.isFutureDate){
                                $scope.isvaliddate = false;
                            }
                        }else{
                            $scope.isFutureDate = false;
                        }
                    }else{
                        $scope.invalidDateError = true;
                        $scope.isvaliddate = false;
                        if($scope.isrequired){
                            $scope.requiredFieldError = false;
                        }

                    }
                };
            }],
            link: function (scope, element, attrs, ngModelCtrl) {
            }
        };
    });
