/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
   $("#add-product-form").validate({
       rules: {
            productNameInput: {
                required: true,
                maxlength: 255,
                remote:{
                    url: 'valaddproductname', //ValidateAddProductName
                    type: 'post',
                    data: {
                        'productNameInput': function() { return $('#productNameInput').val(); }
                    }
                }
            },
            productDescriptionInput: "required",
            productPriceInput: {
                required: true,
                number: true,
                min: 1
            },
            restockPriceInput: {
                required: true,
                number: true,
                min: 1
            },
            lowStockInput: {
                required: true,
                number: true,
                min: 1
            },
            brandInput: {
                required: true,
                maxlength: 255
            },
            colorInput: {
                required: true,
                maxlength: 20
            },
            productClassInput: "required",
            supplier: "required"
       },
       messages: {
           productNameInput: {
               required: "Product Name is required",
               maxlength: "Product Name length should not exceed 255",
               remote: "Product Name already exists"              
           },
           productDescriptionInput: "Product Description is required",
           productPriceInput: {
               required: "Product Price is required",
               number: "Product Price input should be numerical",
               min: "Product Price should not go below 1"
           },
           restockPriceInput: {
               required: "Restock Price is required",
               number: "Restock Price input should be numerical",
               min: "Restock Price should not go below 1"
           },
           lowStockInput: {
               required: "Low Stock Level is required",
               number: "Low Stock Level input should be numerical",
               min: "Low Stock Level should not go below 1"
           },
           brandInput: {
               required: "Brand is required",
               maxlength: "Brand length should not exceed 255"
           },
           colorInput: {
               required: "Color is required",
               maxlength: "Color length should not exceed 20"
           },
           productClassInput: "Product Class is required",
           supplierInput: "Supplier is required"
       }
   });
});