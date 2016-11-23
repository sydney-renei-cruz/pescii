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
public class invoiceItemBean implements Serializable{
    
    public int invoiceItemID;
    public int invoiceID;
    public int productID;
    public String productName;
    public int quantityPurchased;
    
    public invoiceItemBean(int invitemid, int iid, int pid, String pn, int qp){
        this.invoiceItemID=invitemid;
        this.invoiceID=iid;
        this.productID=pid;
        this.productName=pn;
        this.quantityPurchased=qp;
    }
    
    public invoiceItemBean(){}
    
    public void setInvoiceItemID(int invitemid){this.invoiceItemID=invitemid;}
    public void setInvoiceID(int iid){this.invoiceID=iid;}
    public void setProductID(int pid){this.productID=pid;}
    public void setProductName(String pn){this.productName=pn;}
    public void setQuantityPurchased(int qp){this.quantityPurchased=qp;}
    
    public int getInvoiceItemID(){return this.invoiceItemID;}
    public int getInvoiceID(){return this.invoiceID;}
    public int getProductID(){return this.productID;}
    public String getProductName(){return this.productName;}
    public int getQuantityPurchased(){return this.quantityPurchased;}
    
}
