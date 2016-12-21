/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author user
 */
public class customerBean implements Serializable{
    
    public int customerID;
    public String PRCID;
    public String customerFirstName;
    public String customerLastName;
    public String customerMobileNumber;
    public String customerTelephoneNumber;
    public Timestamp dateCreated;
    public String salesRep;
    public int salesRepID;
    public String lastEdittedBy;
    public Date lastEdittedDate;
    
    public customerBean(int c, String cid, String cfname, String clname, String cmnum, String ctnum,
                        Timestamp dc, String sr, int srID, String leb, Date led){
        this.customerID=c;
        this.PRCID=cid;
        this.customerFirstName=cfname;
        this.customerLastName=clname;
        this.customerMobileNumber=cmnum;
        this.customerTelephoneNumber=ctnum;
        this.dateCreated=dc;
        this.salesRep=sr;
        this.salesRepID=srID;
        this.lastEdittedBy=leb;
        this.lastEdittedDate=led;
    }
    
    public customerBean(){}
    
    public void setCustomerID(int c){this.customerID=c;}
    public void setPRCID(String cid){this.PRCID=cid;}
    public void setCustomerFirstName(String cfname){this.customerFirstName=cfname;}
    public void setCustomerLastName(String clname){this.customerLastName=clname;}
    public void setCustomerMobileNumber(String cmnum){this.customerMobileNumber=cmnum;}
    public void setCustomerTelephoneNumber(String ctnum){this.customerTelephoneNumber=ctnum;}
    public void setDateCreated(Timestamp dc){this.dateCreated=dc;}
    public void setSalesRep(String sr){this.salesRep=sr;}
    public void setSalesRepID(int srID){this.salesRepID=srID;}
    public void setLastEdittedBy(String leb){this.lastEdittedBy=leb;}
    public void setLastEdittedDate(Date led){this.lastEdittedDate=led;}
    
    public int getCustomerID(){return this.customerID;}
    public String getPRCID(){return this.PRCID;}
    public String getCustomerFirstName(){return this.customerFirstName;}
    public String getCustomerLastName(){return this.customerLastName;}
    public String getCustomerMobileNumber(){return this.customerMobileNumber;}
    public String getCustomerTelephoneNumber(){return this.customerTelephoneNumber;}
    public Timestamp getDateCreated(){return this.dateCreated;}
    public String getSalesRep(){return this.salesRep;}
    public int getSalesRepID(){return this.salesRepID;}
    public String getLastEdittedBy(){return this.lastEdittedBy;}
    public Date getLastEdittedDate(){return this.lastEdittedDate;}
}
