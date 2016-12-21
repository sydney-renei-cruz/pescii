/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class accountStatusBean implements Serializable{
    
    public int accountStatusID;
    public String accountStatusName;
    
    public accountStatusBean(int asID, String asName){
        this.accountStatusID=asID;
        this.accountStatusName=asName;
    }
    
    public accountStatusBean(){}
    
    public void setAccountStatusID(int asID){this.accountStatusID=asID;}
    public void setAccountStatusName(String asName){this.accountStatusName=asName;}
    
    public int getAccountStatusID(){return this.accountStatusID;}
    public String getAccountStatusName(){return this.accountStatusName;}
    
}
