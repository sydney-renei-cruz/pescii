/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $('#add-customer-form').validate({
        rules: {
            customerIDInput: {
                required: true,
                number: true,
                maxlength: 50,
                remote: {
                    url: 'valaddcustomerprcid', //ValidateAddProductName
                    type: 'post',
                    data: {
                        'customerIDInput': function() { return $('#customerIDInput').val(); }
                    }
                },
                min: 1
            },
            customerFirstNameInput: {
                required: true,
                maxlength: 100
            },
            customerLastNameInput: {
                required: true,
                maxlength: 100
            },
            customerMobileNumberInput: {
                required: true,
                maxlength: 20
            },
            customerTelephoneNumberInput: {
                required: true,
                maxlength: 15
            },
            clinicNameInput: {
                required: true,
                maxlength: 255
            },
            clinicPhoneNumberInput: {
                required: true,
                maxlength: 20
            },
            clinicAddressInput: "required",
            chosenProvince: "required",
            chosenSalesRep: "required"
        },
        messages: {
            customerIDInput: {
                required: "Customer Professional Regulation Comission ID is required",
                number: "Customer Professional Regulation Comission ID input should be numerical",
                maxlength: "Customer Professional Regulation Comission ID length should not exceed 50",
                remote: "Customer Professional Regulation Comission ID already exists",
                min: "Customer Professional Regulation Comission ID should not go below 1"
            },
            customerFirstNameInput: {
               required: "First Name is required",
               maxlength: "First Name length should not exceed 100"
           },
           customerLastNameInput: {
               required: "Last Name is required",
               maxlength: "Last Name length should not exceed 100"
           },
           customerMobileNumberInput: {
               required: "Mobile Number is required",
               maxlength: "Mobile Number length should not exceed 20"
           },
           customerTelephoneNumberInput: {
               required: "Telephone Number is required",
               maxlength: "Telephone Number length should not exceed 15"
           },
           clinicNameInput: {
               required: "Clinic Name is required",
               maxlength: "Clinic Name length should not exceed 255"
           },
           clinicPhoneNumberInput: {
               required: "Clinic Phone Number is required",
               maxlength: "Clinic Phone Number length should not exceed 20"
           },
           clinicAddressInput: "Clinic Address is required",
           chosenProvince: "Province is required",
           choseSalesRep: "Sales Representative is required"
        }
    });
});
