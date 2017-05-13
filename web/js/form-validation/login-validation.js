/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
   $("#login-form").validate({
       rules: {
            usernameInput: {
                required: true,
                minlength: 4
            },
            passwordInput: "required"
       },
       messages: {
           usernameInput:{
               required: "Username is required",
               minlength: "Username must be atleast 4 characters"
            },
           passwordInput: "Password is required"
       }
   });
});