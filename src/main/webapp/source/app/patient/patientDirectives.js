/**
 * Created by tomson.ngassa on 2/19/14.
 */

'use strict';

angular.module("icas.patientDirectives", [])

    .directive("deleteTreatmentplan", function () {
        return {
            restrict: 'E',
            scope: {
                planid: '@',
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
                        title: 'Delete Treatment Plan',
                        title_html: true,
                        buttons: [
                            {
                                html: "<i class='icon-trash bigger-110'></i>&nbsp; Delete plan",
                                "class": "btn btn-danger btn-xs",
                                "id": "plan-delete-btn",
                                click: function () {
                                    scope.ondelete({planId: scope.planid});
                                    $(this).dialog("close");
                                }
                            },
                            {
                                html: "<i class='icon-remove bigger-110'></i>&nbsp; Cancel",
                                "class": "btn btn-xs",
                                "id": "plan-cancel-btn",
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

    .directive("deletePatient", function () {
        return {
            restrict: 'E',
            scope: {
                patientid: '@',
                name: '@',
                ondelete: '&'
            },
            template: '<a class="red" href="#">' +
            '<i class="icon-trash bigger-130"></i>' +
            '</a>',
            link: function (scope, element, attrs) {

                element.on('click', function (e) {
                    e.preventDefault();
                    //                    var msg = 'Patient <b>' + scope.name + '</b> will be permanently deleted and cannot be recovered.';
                    //                    $('#dialog-confirm-msg').html(msg);
                    $("#dialog-confirm").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: 'Delete patient',
                        title_html: true,
                        buttons: [
                            {
                                html: "<i class='icon-trash bigger-110'></i>&nbsp; Delete patient",
                                "class": "btn btn-danger btn-xs",
                                "id": "patient-delete-btn",
                                click: function () {
                                    scope.ondelete({patientId: scope.patientid});
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

    .directive("showPatient", function () {
        return {
            restrict: 'E',
            scope: {
                patient: '='
            },
            template: '<a class="blue" href="#">' +
                           '<i class="icon-zoom-in bigger-130"></i>' +
                      '</a>',
            link: function (scope, element, attrs) {

                element.on('click', function (e) {
                    e.preventDefault();

                    var selectedPatient = scope.patient;
                    var mrn = selectedPatient.medicalRecordNumber || "";
                    var fullName = selectedPatient.fullName || "";
                    var gender = selectedPatient.administrativeGenderCodeDisplayName || "";
                    var raceDisplayName = selectedPatient.raceCodeDisplayName || "";
                    var dob = selectedPatient.birthDate || "";
                    var zipcode = selectedPatient.addressPostalCode || "";
                    var state = selectedPatient.addressStateCode || "";
                    var telephone = selectedPatient.telephone || "";


                    var patientDetails = '<div class="row">' +
                                            '<div class="col-xs-12">' +
                                                '<div class="table-responsive">' +
                                                    '<div class="modal-body no-padding" >' +
                                                       ' <table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
                                                          '  <tbody>' +
                                                                ' <tr > <td  class="captionID" >  <b>Patient ID</b> </td> <td class="dataID"> '+ mrn + ' </td></tr>' +
                                                                ' <tr > <td  class="captionID" >  <b>Full Name</b> </td> <td class="dataID"> '+ fullName + ' </td></tr>' +
                                                                ' <tr > <td  class="captionID" >  <b>Gender</b> </td> <td class="dataID"> '+ gender + ' </td></tr>' +
                                                                ' <tr > <td  class="captionID" >  <b>Race</b> </td> <td class="dataID"> '+ raceDisplayName + ' </td></tr>' +
                                                                ' <tr > <td  class="captionID" >  <b>DOB</b> </td> <td class="dataID"> '+ dob + ' </td></tr>' +
                                                                ' <tr > <td  class="captionID" >  <b>Zip Code</b> </td> <td class="dataID"> '+ zipcode + ' </td></tr>' +
                                                                ' <tr > <td  class="captionID" >  <b>State</b> </td> <td class="dataID"> '+ state + ' </td></tr>' +
                                                                ' <tr > <td  class="captionID" >  <b>Telephone</b> </td> <td class="dataID"> '+ telephone + ' </td></tr>' +
                                                          '  </tbody>' +
                                                       ' </table>' +
                                                   ' </div>' +
                                               ' </div>' +
                                           ' </div>' +
                                       ' </div>' ;
                   $('#dialog-patient-details').html(patientDetails);
                    $("#dialog-patient-details").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: 'Patient Details',
                        title_html: true,
                        id:"close-btn",
                        width:400
                    });
                });
            }
        };
    })

    .directive("showTreatmentplan", function () {
        return {
            restrict: 'E',
            scope: {
                treatmentplan: '=',
                socialhistory: '=',
                conditions: '='
            },
            template: '<a class="blue" href="#">' +
                '<i class="icon-zoom-in bigger-130"></i>' +
                '</a>',
            controller:['$scope', function($scope){
                $scope.composeInterventionRows = function(records){
                    var rows ="";

                    if(records.length >0){
                        for(var i = 0; i<records.length; i++){
                            rows += '<tr>' +
                                '<td>'+ (records[i].cptCode || "") + '</td>' +
                                '<td>'+ (records[i].description || "") + '</td>' +
                                '<td>'+ (records[i].targetDate  || "")+ '</td>' +
                                '</tr>';
                        }
                    }else{
                        rows = '<tr ><td colspan="4" class="center"> No intervention found </td></tr>';
                    }

                    return rows;
                };

                $scope.composeObjectivesRows = function(records){
                    var rows ="";
                    if(records.length > 0){
                        for(var i = 0; i<records.length; i++){
                            rows += '<tr><td>' +( records[i] || "")+ '</td></tr>' ;
                        }
                    }else{
                        rows = '<tr ><td colspan="1" class="center"> No Objective found </td></tr>';
                    }
                    return rows;
                };

                $scope.composeConditionRows= function(records){
                    var rows ="";
                    if(records.length > 0){
                        for(var i = 0; i<records.length; i++){
                            rows += '<tr>' +
                                '<td>'+ (records[i].problemDisplayName || "") + '</td>' +
                                '<td>'+ (records[i].problemStatusCode || "") + '</td>' +
                                '<td>'+ (records[i].startDate|| "")  + '</td>' +
                                '<td>'+ (records[i].endDate || "") + '</td>' +
                                '</tr>';
                        }
                    }else{
                        rows = '<tr><td colspan="4" class="center"> No Condition found </td></tr>';
                    }
                    return rows;
                };

                $scope.composeSocialHistoryRows= function(records){
                    var rows ="";

                    if(records.length > 0){
                        for(var i = 0; i<records.length; i++){
                            rows += '<tr>' +
                                '<td>'+ ( records[i].socialHistoryTypeName || "") + '</td>' +
                                '<td>'+ ( records[i].socialHistoryStatusName || "") + '</td>' +
                                '<td>'+ ( records[i].startDate || "") + '</td>' +
                                '<td>'+ ( records[i].endDate || "") + '</td>' +
                                '</tr>';
                        }
                    }else{
                        rows = '<tr><td colspan="4" class="center"> No Social History found </td></tr>';
                    }
                    return rows;
                };


            }],
            link: function (scope, element, attrs) {

                element.on('click', function (e) {
                    e.preventDefault();

                    var selectedPreatmentPlan = scope.treatmentplan;

                    var  treatmentplanDetails = '<h2>Conditions</h2>'+
                                                '<div class="row" >' +
                                                    '<div class="col-xs-12">' +
                                                        '<div class="table-responsive">' +
                                                            '<div class="modal-body no-padding" >' +
                                                                ' <table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
                                                                    '<tr>' +
                                                                        '<th>Name</th>' +
                                                                        '<th>Status</th>' +
                                                                        '<th>Start Date</th>' +
                                                                        '<th>End Date</th>' +
                                                                    '</tr>'+
                                                                    ' <tbody>' + scope.composeConditionRows(scope.conditions) +
                                                                    '</tbody>' +
                                                                ' </table>' +
                                                            ' </div>' +
                                                        ' </div>' +
                                                    ' </div>' +
                                                ' </div>';

                    treatmentplanDetails += '<br/>'+
                                            '<h2>Social History</h2>'+
                                            '<div class="row">' +
                                                '<div class="col-xs-12">' +
                                                    '<div class="table-responsive">' +
                                                        '<div class="modal-body no-padding" >' +
                                                            '<table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
                                                                '<tr>' +
                                                                    '<th>Name</th>' +
                                                                    '<th>Status</th>' +
                                                                    '<th>Start Date</th>' +
                                                                    '<th>End Date</th>' +
                                                                '</tr>'+
                                                                ' <tbody>' + scope.composeSocialHistoryRows(scope.socialhistory) +'</tbody>' +
                                                            ' </table>' +
                                                        ' </div>' +
                                                    ' </div>' +
                                                ' </div>' +
                                            ' </div>';

                    treatmentplanDetails += '<br/>'+
                                            '<h2>Goals</h2>'+
                                            '<div class="row">' +
                                                '<div class="col-xs-12">' +
                                                    '<div class="table-responsive">' +
                                                        '<div class="modal-body no-padding" >' +
                                                            ' <table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
                                                                ' <tbody>' +
                                                                    ' <tr > <td  class="captionID" style="width:90px;">  <b>Short Term</b> </td> <td class="dataID"> '+ selectedPreatmentPlan.longTermGoal + ' </td></tr>' +
                                                                    ' <tr > <td  class="captionID" style="width:90px;">  <b>Long term</b> </td> <td class="dataID"> '+ selectedPreatmentPlan.shortTermGoal + ' </td></tr>' +
                                                                '  </tbody>' +
                                                            ' </table>' +
                                                        ' </div>' +
                                                    ' </div>' +
                                                ' </div>' +
                                            ' </div>';

                    treatmentplanDetails += '<br/>'+
                                            '<h2>Objectives</h2>'+
                                            '<div class="row">' +
                                                '<div class="col-xs-12">' +
                                                    '<div class="table-responsive">' +
                                                        '<div class="modal-body no-padding" >' +
                                                            ' <table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
                                                                 ' <tbody>' + scope.composeObjectivesRows(selectedPreatmentPlan.objectives) +'  </tbody>' +
                                                            ' </table>' +
                                                        ' </div>' +
                                                    ' </div>' +
                                                ' </div>' +
                                            ' </div>';

                    treatmentplanDetails += '<br/>'+
                                            '<h2>Interventions</h2>'+
                                            '<div class="row">' +
                                                '<div class="col-xs-12">' +
                                                    '<div class="table-responsive">' +
                                                        '<div class="modal-body no-padding" >' +
                                                            ' <table  class="table table-striped table-bordered table-hover no-bottom-margin">' +
                                                                '<tr>' +
                                                                    '<th>CPT Code</th>' +
                                                                    '<th>Description</th>' +
                                                                    '<th>Target Date</th>' +
                                                                '</tr>'+
                                                                ' <tbody>' + scope.composeInterventionRows(selectedPreatmentPlan.interventions) +
                                                                '  </tbody>' +
                                                            ' </table>' +
                                                        ' </div>' +
                                                    ' </div>' +
                                                ' </div>' +
                                            ' </div>';



                        $('#dialog-treatmentplan-details').html(treatmentplanDetails);
                        $("#dialog-treatmentplan-details").removeClass('hide').dialog({
                        resizable: false,
                        modal: true,
                        title: 'Treatment Plan Details',
                        title_html: true,
                        id:"close-btn",
                        width:400
                    });
                });
            }
        };
    }) ;



