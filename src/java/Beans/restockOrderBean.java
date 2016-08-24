/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author user
 */
public class restockOrderBean implements Serializable{
    
    public String restockOrderID;
    public Date restockDateCreated;
    public Date restockArriveDate;
    public Date restockCompletedDate;
    public float restockCost;   //you get the price of each product from Product table, but there might be extra charge and whatnot, so yeah.
    public String supplier;
    
    public restockOrderBean(String roid, Date rdcreate, Date rad, Date rcd, float rcost, String supp){
        this.restockOrderID=roid;
        this.restockDateCreated=rdcreate;
        this.restockArriveDate=rad;
        this.restockCompletedDate=rcd;
        this.restockCost=rcost;
        this.supplier=supp;
    }
    
    public restockOrderBean(){}
    
    public void setRestockOrderID(String roid){this.restockOrderID=roid;}
    public void setRestockDateCreated(Date rdcreate){this.restockDateCreated=rdcreate;}
    public void setRestockArriveDate(Date rad){this.restockArriveDate=rad;}
    public void setRestockCompletedDate(Date rcd){this.restockCompletedDate=rcd;}
    public void setRestockCost(float rcost){this.restockCost=rcost;}
    public void setSupplier(String supp){this.supplier=supp;}
    
    public String getRestockOrderID(){return this.restockOrderID;}
    public Date getRestockDateCreated(){return this.restockDateCreated;}
    public Date getRestockArriveDate(){return this.restockArriveDate;}
    public Date getRestockCompletedDate(){return this.restockCompletedDate;}
    public float getRestockCost(){return this.restockCost;}
    public String getSupplier(){return this.supplier;}
    
}
