/**
 * Created by tomson.ngassa on 2/19/14.
 */

'use strict';

angular.module("icas.LabResultsDirectives", [])

    .directive("deleteLabresult", function () {
        return {
            restrict: 'E',
            scope: {
                labresultid: '@',
                name: '@',
                ondelete: '&'
            },
            template: '<a class="red" href="">' +
                         '<i class="icon-trash bigger-130"></i>' +
                       '</a>',
            link: function (scope, element, attrs) {

                element.on('click', function (e) {
                    e.preventDefault();
                    $("#dialog-confirm").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: 'Delete Lab Result',
                        title_html: true,
                        buttons: [
                            {
                                html: "<i class='icon-trash bigger-110'></i>&nbsp; Delete lab Result",
                                "class": "btn btn-danger btn-xs",
                                "id": "patient-delete-btn",
                                click: function () {
                                    scope.ondelete({labResultId: scope.labresultid});
                                    $(this).dialog("close");
                                }
                            },
                            {
                                html: "<i class='icon-remove bigger-110'></i>&nbsp; Cancel",
                                "class": "btn btn-xs",
                                "id": "patient-cancel-btn",
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
    .directive("showLabresult", function () {
        return {
            restrict: 'E',
            scope: {
                labresult: '='
            },
            template: '<a class="blue" href="#">' +
                '<i class="icon-zoom-in bigger-130"></i>' +
                '</a>',
            link: function (scope, element, attrs) {

                element.on('click', function (e) {
                    e.preventDefault();

                    var selectedLabresult = scope.labresult;
                    var resultOrganizerCodeDisplayName = selectedLabresult.resultOrganizerCodeDisplayName || "";
                    var resultOrganizerDateTime = selectedLabresult.resultOrganizerDateTime || "";
                    var resultOrganizerStatusCode = selectedLabresult.resultOrganizerStatusCode || "";

                    var labresultDetails = '<div class="row">' +
                                            '<div class="col-xs-12">' +
                                            '<div class="table-responsive">' +
                                            '<div class="modal-body no-padding" >' +
                                            ' <table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
                                            '  <tbody>' +
                                            ' <tr > <td  class="captionID" >  <b>Lab Panel Name</b> </td> <td class="dataID"> '+ resultOrganizerCodeDisplayName + ' </td></tr>' +
                                            ' <tr > <td  class="captionID" >  <b>Date</b> </td> <td class="dataID"> '+ resultOrganizerDateTime + ' </td></tr>' +
                                            ' <tr > <td  class="captionID" >  <b>Status</b> </td> <td class="dataID"> '+ resultOrganizerStatusCode + ' </td></tr>' +
                                            '  </tbody>' +
                                            ' </table>' +
                                            ' </div>' +
                                            ' </div>' +
                                            ' </div>' +
                                            ' </div>' ;
                    $('#dialog-labresult-details').html(labresultDetails);
                    $("#dialog-labresult-details").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: 'Lab Result Details',
                        title_html: true,
                        id:"close-btn",
                        width:400
                    });
                });
            }
        };
    })  ;



