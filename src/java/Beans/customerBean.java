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
public class customerBean implements Serializable{
    
    public String PRCID;
    public String customerName;
    public String customerMobileNumber;
    public String customerTelephoneNumber;
    
    public customerBean(String cid, String cname, String cmnum, String ctnum){
        this.PRCID=cid;
        this.customerName=cname;
        this.customerMobileNumber=cmnum;
        this.customerTelephoneNumber=ctnum;
    }
    
    public customerBean(){}
    
    public void setPRCID(String cid){this.PRCID=cid;}
    public void setCustomerName(String cname){this.customerName=cname;}
    public void setCustomerMobileNumber(String cmnum){this.customerMobileNumber=cmnum;}
    public void setCustomerTelephoneNumber(String ctnum){this.customerTelephoneNumber=ctnum;}
    
    public String getPRCID(){return this.PRCID;}
    public String getCustomerName(){return this.customerName;}
    public String getCustomerMobileNumber(){return this.customerMobileNumber;}
    public String getCustomerTelephoneNumber(){return this.customerTelephoneNumber;}
}
