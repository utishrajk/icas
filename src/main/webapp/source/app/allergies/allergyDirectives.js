'use strict';

angular.module("icas.allergyDirectives", [])
    .directive("deleteAllergy", function () {
        return {
            restrict: 'E',

            scope: {
                allergyid: '@',
                name: '@',
                ondelete: '&'
            },
            template: '<a class="red" href="#">' +
                '<i class="icon-trash bigger-130"></i>' +
                '</a>',
            link: function (scope, element, attrs) {

                element.on('click', function (e) {
                    e.preventDefault();

                    var msg = 'Allergy <b>' + scope.name + '</b> will be permanently deleted and cannot be recovered.';
                    $('#dialog-confirm-msg').html(msg);
                    $("#dialog-confirm").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: ' Delete Allergy',
                        title_html: true,
                        buttons: [
                            {
                                html: "<i class='icon-trash bigger-110'></i>&nbsp; Delete",
                                "class": "btn btn-danger btn-xs",
                                click: function () {
                                    scope.ondelete({allergyId: scope.allergyid});
                                    $(this).dialog("close");
                                }
                            },
                            {
                                html: "<i class='icon-remove bigger-110'></i>&nbsp; Cancel",
                                "class": "btn btn-xs",
                                click: function () {
                                    $(this).dialog("close");
                                }
                            }
                        ]
                    });
                });
            }
        };
    })
    .directive("showAllergy", function () {
        return {
            restrict: 'E', //only matches element name
            scope: {
                allergy: '='
            },
            template: '<a class="blue" href="#">' +
                '<i class="icon-zoom-in bigger-130"></i>' +
                '</a>',
            link: function (scope, element, attrs) {
                element.on('click', function (e) {
                    e.preventDefault();

                    var selectedallergy = scope.allergy || "";
                    var adverseEventTypeName = selectedallergy.adverseEventTypeName || "";
                    var allergenName = selectedallergy.allergenName || "";
                    var allergyReactionName = selectedallergy.allergyReactionName || "";
                    var allergySeverityName = selectedallergy.allergySeverityName || "";
                    var allergyStartDate = selectedallergy.allergyStartDate || "";
                    var allergyEndDate = selectedallergy.allergyEndDate || "";

                    var allergyDetails = '<table class="table table-striped table-hover no-bottom-margin" >' +
                        '<thead></thead>' +
                        '<tbody>' +
                        '<tr><td class="captionID"><b>Adverse Event</b></td><td class="dataID">' + adverseEventTypeName + '</td></tr>' +
                        '<tr><td class="captionID"><b>Product</b></td><td class="dataID">' + allergenName + '</td></tr>' +
                        '<tr><td class="captionID"><b>Reaction</b></td><td class="dataID">' + allergyReactionName + '</td></tr>' +
                        '<tr><td class="captionID"><b>Severity</b></td><td class="dataID"> ' + allergySeverityName + '</td></tr>' +
                        '<tr><td class="captionID"><b>Start Date</b></td><td class="dataID"> ' + allergyStartDate + '</td></tr>' +
                        '<tr><td class="captionID"><b>End Date</b></td><td class="dataID"> ' + allergyEndDate + '</td></tr>' +
                        '</tbody>' +
                        '</table>';

                    $('#dialog-allergy-details').html(allergyDetails);

                    $("#dialog-allergy-details").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: ' Allergy Details',
                        title_html: true,
                        buttons: [
                            {
                                html: "<i class='icon-remove bigger-110'></i>&nbsp; Close",
                                "class": "btn btn-xs",
                                click: function () {
                                    $(this).dialog("close");
                                }
                            }
                        ]
                    });
                });
            }
        };
    });
