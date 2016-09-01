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
public class accountBean implements Serializable{
    
    public String accountID;
    public String userName;
    public String password;
    public String accountStatus;
    public String accountType;
    
    
    public accountBean(String accID, String un, String pword, String accStat, String accType){
        this.accountID=accID;
        this.userName=un;
        this.password=pword;
        this.accountStatus=accStat;
        this.accountType=accType;
    }
    
    public void setAccountID(String accID){this.accountID=accID;}
    public void setUserName(String un){this.userName=un;}
    public void setPassword(String pword){this.password=pword;}
    public void setAccountStatus(String accStat){this.accountStatus=accStat;}
    public void setAccountType(String accType){this.accountType=accType;}
    
    public String getAccountID(){return this.accountID;}
    public String getUserName(){return this.userName;}
    public String getPassword(){return this.password;}
    public String getAccountStatus(){return this.accountStatus;}
    public String getAccountType(){return this.accountType;}
    
    public accountBean(){}
}
