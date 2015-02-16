/**
 * Created by tomson.ngassa on 2/19/14.
 */

'use strict';

angular.module("icas.medicationDirectives", [])

    .directive("deleteMedication", function () {
        return {
            restrict: 'E',
            scope: {
                medicationid: '@',
                name: '@',
                ondelete: '&'
            },
            template: '<a class="red" href="#">' +
                         '<i class="icon-trash bigger-130"></i>' +
                       '</a>',
            link: function (scope, element, attrs) {

                element.on('click', function (e) {
                    e.preventDefault();
                    $("#dialog-confirm").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: 'Delete Medication',
                        title_html: true,
                        buttons: [
                            {
                                html: "<i class='icon-trash bigger-110'></i>&nbsp; Delete Medicaiton",
                                "class": "btn btn-danger btn-xs",
                                "id": "medication-delete-btn",
                                click: function () {
                                    scope.ondelete({medicationId: scope.medicationid});
                                    $(this).dialog("close");
                                }
                            },
                            {
                                html: "<i class='icon-remove bigger-110'></i>&nbsp; Cancel",
                                "class": "btn btn-xs",
                                "id": "medication-cancel-btn",
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

    .directive("showMedication", function () {
        return {
            restrict: 'E',
            scope: {
                medication: '='
            },
            template: '<a class="blue" href="#">' +
                           '<i class="icon-zoom-in bigger-130"></i>' +
                      '</a>',
            link: function (scope, element, attrs) {

                element.on('click', function (e) {
                    e.preventDefault();

                    var selectedMedication = scope.medication;
                    var medicationCodeDisplayName = selectedMedication.medicationCodeDisplayName || "";
                    var dosage = (selectedMedication.doseQuantity.measuredValue !== null && selectedMedication.doseQuantity.unitOfMeasureCode !== null ) ? (selectedMedication.doseQuantity.measuredValue + " " +selectedMedication.doseQuantity.unitOfMeasureCode ): '';
                    var productFormCodeDisplayName = selectedMedication.productFormCodeDisplayName || "";
                    var routeCodeDisplayName = selectedMedication.routeCodeDisplayName || "";
                    var freeTextSig = selectedMedication.freeTextSig || "";
                    var medicationStartDate = selectedMedication.medicationStartDate || "";
                    var medicationEndDate = selectedMedication.medicationEndDate || "";
                    var medicationNote = selectedMedication.medicationNote || "";


                    var medicationDetails = '<div class="row">' +
                                                '<div class="col-xs-12">' +
                                                    '<div class="table-responsive">' +
                                                        '<div class="modal-body no-padding" >' +
                                                           ' <table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
                                                              '  <tbody>' +
                                                                    ' <tr > <td  class="captionID" >  <b>Medication Name</b> </td> <td class="dataID"> '+ medicationCodeDisplayName + ' </td></tr>' +
                                                                    ' <tr > <td  class="captionID" >  <b>Dosage</b> </td> <td class="dataID"> '+ dosage + ' </td></tr>' +
                                                                    ' <tr > <td  class="captionID" >  <b>Dosage Form</b> </td> <td class="dataID"> '+ productFormCodeDisplayName + ' </td></tr>' +
                                                                    ' <tr > <td  class="captionID" >  <b>Route</b> </td> <td class="dataID"> '+ routeCodeDisplayName + ' </td></tr>' +
                                                                    ' <tr > <td  class="captionID" >  <b>Frequency </b> </td> <td class="dataID"> '+ freeTextSig + ' </td></tr>' +
                                                                    ' <tr > <td  class="captionID" >  <b>Start Date </b> </td> <td class="dataID"> '+ medicationStartDate + ' </td></tr>' +
                                                                    ' <tr > <td  class="captionID" >  <b>End Date</b> </td> <td class="dataID"> '+ medicationEndDate + ' </td></tr>' +
                                                                    ' <tr > <td  class="captionID" >  <b>Note</b> </td> <td class="dataID"> '+ medicationNote + ' </td></tr>' +
                                                              '  </tbody>' +
                                                           ' </table>' +
                                                       ' </div>' +
                                                   ' </div>' +
                                               ' </div>' +
                                           ' </div>' ;

                    $('#dialog-medication-details').html(medicationDetails);
                    $("#dialog-medication-details").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: 'Medication Details',
                        title_html: true,
                        id:"close-btn",
                        width:400
                    });
                });
            }
        };
    })  ;



