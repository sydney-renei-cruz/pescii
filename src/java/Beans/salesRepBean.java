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
public class salesRepBean implements Serializable{
    
    public int salesRepID;
    public String salesRepName;
    public String salesRepMobileNumber;
    public String salesRepAddress;
    
    public salesRepBean(int srID, String srName, String srMobileNum, String srAdd){
        this.salesRepID=srID;
        this.salesRepName=srName;
        this.salesRepMobileNumber=srMobileNum;
        this.salesRepAddress=srAdd;
    }
    
    public salesRepBean(){}
    
    public void setSalesRepName(String srName){this.salesRepName=srName;}
    public void setSalesRepID(int srID){this.salesRepID=srID;}
    public void setSalesRepMobileNumber(String srMobileNum){this.salesRepMobileNumber=srMobileNum;}
    public void setSalesRepAddress(String srAdd){this.salesRepAddress=srAdd;}
    
    public String getSalesRepName(){return this.salesRepName;}
    public int getSalesRepID(){return this.salesRepID;}
    public String getSalesRepMobileNumber(){return this.salesRepMobileNumber;}
    public String getSalesRepAddress(){return this.salesRepAddress;}
}
