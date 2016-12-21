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
public class accountTypeBean implements Serializable{
    
    public int accountTypeID;
    public String accountTypeName;
    
    public accountTypeBean(int atID, String atName){
        this.accountTypeID=atID;
        this.accountTypeName=atName;
    }
    
    public accountTypeBean(){}
    
    public void setAccountTypeID(int atID){this.accountTypeID=atID;}
    public void setAccountTypeName(String atName){this.accountTypeName=atName;}
    
    public int getAccountTypeID(){return this.accountTypeID;}
    public String getAccountTypeName(){return this.accountTypeName;}
    
}
