/**
 * Created by tomson.ngassa on 4/11/14.
 */

/*
*  This is an implementation the natural sort algorithm. This algorithm is used to
*  sort all the alphanumeric rows in that variuos table.
* */

angular.module("icas.naturalSort", [])

// The core natural service
    .factory("NaturalService", ["$locale", function($locale) {
        'use strict';

        // the cache prevents re-creating the values every time, at the expense of
        // storing the results forever. Not recommended for highly changing data
        // on long-term applications.
        var natCache = {},
        // amount of extra zeros to padd for sorting
            padding = function(value) {
                if(value !== null && value !== undefined){
                    return '00000000000000000000'.slice(value.length);
                }
            };

        // Calculate the default out-of-order date format (d/m/yyyy vs m/d/yyyy)
         var natDateMonthFirst = $locale.DATETIME_FORMATS.shortDate.charAt(0) === 'M';

        // Replaces all suspected dates with a standardized yyyy-m-d, which is fixed below
        var fixDates = function(value) {
            if(value !== null && value !== undefined){
                // first look for dd?-dd?-dddd, where "-" can be one of "-", "/", or "."
                return value.replace(/(\d\d?)[-\/\.](\d\d?)[-\/\.](\d{4})/, function($0, $m, $d, $y) {
                    // temporary holder for swapping below
                    var t = $d;
                    // if the month is not first, we'll swap month and day...
                    if(!natDateMonthFirst) {
                        // ...but only if the day value is under 13.
                        if(Number($d) < 13) {
                            $d = $m;
                            $m = t;
                        }
                    } else if(Number($m) > 12) {
                        // Otherwise, we might still swap the values if the month value is currently over 12.
                        $d = $m;
                        $m = t;
                    }
                    // return a standardized format.
                    return $y+'-'+$m+'-'+$d;
                });
            }
        };

            // Fix numbers to be correctly padded
          var  fixNumbers = function(value) {
                if(value !== null && value !== undefined){
                    // First, look for anything in the form of d.d or d.d.d...
                    return value.replace(/(\d+)((\.\d+)+)?/g, function ($0, integer, decimal, $3) {
                        // If there's more than 2 sets of numbers...
                        if (decimal !== $3) {
                            // treat as a series of integers, like versioning,
                            // rather than a decimal
                            return $0.replace(/(\d+)/g, function ($d) {
                                return padding($d) + $d;
                            });
                        } else {
                            // add a decimal if necessary to ensure decimal sorting
                            decimal = decimal || ".0";
                            return padding(integer) + integer + decimal + padding(decimal);
                        }
                    });
                }
            };

            // Finally, this function puts it all together.
         var natValue = function (value) {
                if(natCache[value]) {
                    return natCache[value];
                }
                var newValue = fixNumbers(fixDates(value));
                natCache[value] = newValue;
                return natCache;
            };

        // The actual object used by this service
        return {
            naturalValue: natValue,
            naturalSort: function(a, b) {
                a = natValue(a);
                b = natValue(b);
                return (a < b) ? -1 : ((a > b) ? 1 : 0);
            }
        };
    }]);
