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
public class restockOrderItemBean implements Serializable{
    
    public int ROIID;
    public int restockOrderID;
    public int productID;
    public String productName;
    public int supplierID;
    public String supplierName;
    public int quantityPurchased;
    public int quantityReceived;
    public float totalCost;
    
    public restockOrderItemBean(int roitemid, int roid, int pid, String pn, int suppID, String supp, int qp, int qr, float tc){
        this.ROIID=roitemid;
        this.restockOrderID=roid;
        this.productID=pid;
        this.productName=pn;
        this.supplierID=suppID;
        this.supplierName=supp;
        this.quantityPurchased=qp;
        this.quantityReceived=qr;
        this.totalCost=tc;
    }
    
    public restockOrderItemBean(){}
    
    public void setRestockOrderItemID(int roitemid){this.ROIID=roitemid;}
    public void setRestockOrderID(int roid){this.restockOrderID=roid;}
    public void setProductID(int pid){this.productID=pid;}
    public void setProductName(String pn){this.productName=pn;}
    public void setSupplierID(int suppID){this.supplierID=suppID;}
    public void setSupplierName(String supp){this.supplierName=supp;}
    public void setQuantityPurchased(int qp){this.quantityPurchased=qp;}
    public void setQuantityReceived(int qr){this.quantityReceived=qr;}
    public void setTotalCost(float tc){this.totalCost=tc;}
    
    public int getRestockOrderItemID(){return this.ROIID;}
    public int getRestockOrderID(){return this.restockOrderID;}
    public int getProductID(){return this.productID;}
    public String getProductName(){return this.productName;}
    public int getSupplierID(){return this.supplierID;}
    public String getSupplierName(){return this.supplierName;}
    public int getQuantityPurchased(){return this.quantityPurchased;}
    public int getQuantityReceived(){return this.quantityReceived;}
    public float getTotalCost(){return this.totalCost;}
    
}
