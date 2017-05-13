/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
    $('#add-clinic-form').validate({
        rules: {
            clinicNameInput: {
                required: true,
                maxlength: 255,
                remote: {
                    url: 'valaddclinicname', //ValidateAddClinicName
                    type: 'post',
                    data: {
                        'clinicNameInput': function() { return $('#clinicNameInput').val(); }
                    }
                }
            },
            clinicAddressInput: "required",
            clinicPhoneNumberInput: {
                required: true,
                maxlength: 20
            },
            chosenProvince: "required"
            
        },
        messages: {
            clinicNameInput: {
                required: "Clinic Name is required",
                maxlength: "Clinic Name length should not exceed 255",
                remote: "Clinic Name already exists"
            },
            clinicAddressInput: "Clinic Address is required",
           clinicPhoneNumberInput: {
               required: "Clinic Phone Number is required",
               maxlength: "Clinic Phone Number length should not exceed 20"
           },
           chosenProvince: "Province is required"
        }
    });
});