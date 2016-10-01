/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author user
 */
public class accountBean implements Serializable{
    
    public int accountID;
    public String userName;
    public String password;
    public String accountStatus;
    public String accountType;
    public Timestamp dateCreated;
    
    
    public accountBean(int accID, String un, String pword, String accStat, String accType, Timestamp dc){
        this.accountID=accID;
        this.userName=un;
        this.password=pword;
        this.accountStatus=accStat;
        this.accountType=accType;
        this.dateCreated=dc;
    }
    
    public void setAccountID(int accID){this.accountID=accID;}
    public void setUserName(String un){this.userName=un;}
    public void setPassword(String pword){this.password=pword;}
    public void setAccountStatus(String accStat){this.accountStatus=accStat;}
    public void setAccountType(String accType){this.accountType=accType;}
    public void setDateCreated(Timestamp dc){this.dateCreated=dc;}
    
    public int getAccountID(){return this.accountID;}
    public String getUserName(){return this.userName;}
    public String getPassword(){return this.password;}
    public String getAccountStatus(){return this.accountStatus;}
    public String getAccountType(){return this.accountType;}
    public Timestamp getDateCreated(){return this.dateCreated;}
    
    public accountBean(){}
}
