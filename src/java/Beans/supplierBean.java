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
public class supplierBean implements Serializable{
    
    public int supplierID;
    public String supplierName;
    public String supplierAddress;
    public String supplierContactNumber;
    public int productClassID;
    public String productClassName;
    public Timestamp dateCreated;
    public String lastEdittedBy;
    public Date lastEdittedDate;
    
    public supplierBean(int supID, String supName, String supAdd, String supCN, int pcID, String pcName,
                        Timestamp dc, String leb, Date led){
        this.supplierID=supID;
        this.supplierName=supName;
        this.supplierAddress=supAdd;
        this.supplierContactNumber=supCN;
        this.productClassID=pcID;
        this.productClassName=pcName;
        this.dateCreated=dc;
        this.lastEdittedBy=leb;
        this.lastEdittedDate=led;
    }
    
    public supplierBean(){}
    
    public void setSupplierID(int supID){this.supplierID=supID;}
    public void setSupplierName(String supName){this.supplierName=supName;}
    public void setSupplierAddress(String supAdd){this.supplierAddress=supAdd;}
    public void setSupplierContactNumber(String supCN){this.supplierContactNumber=supCN;}
    public void setProductClassID(int pcID){this.productClassID=pcID;}
    public void setProductClassName(String pcName){this.productClassName=pcName;}
    public void setDateCreated(Timestamp dc){this.dateCreated=dc;}
    public void setLastEdittedBy(String leb){this.lastEdittedBy=leb;}
    public void setLastEdittedDate(Date led){this.lastEdittedDate=led;}
    
    public int getSupplierID(){return this.supplierID;}
    public String getSupplierName(){return this.supplierName;}
    public String getSupplierAddress(){return this.supplierAddress;}
    public String getSupplierContactNumber(){return this.supplierContactNumber;}
    public int getProductClassID(){return this.productClassID;}
    public String getProductClassName(){return this.productClassName;}
    public Timestamp getDateCreated(){return this.dateCreated;}
    public String getLastEdittedBy(){return this.lastEdittedBy;}
    public Date getLastEdittedDate(){return this.lastEdittedDate;}
    
}
