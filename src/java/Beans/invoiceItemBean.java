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
    
    public String invoiceItemID;
    public String invoiceID;
    public String productID;
    public int quantityPurchased;
    
    public invoiceItemBean(String invitemid, String iid, String pid, int qp){
        this.invoiceItemID=invitemid;
        this.invoiceID=iid;
        this.productID=pid;
        this.quantityPurchased=qp;
    }
    
    public invoiceItemBean(){}
    
    public void setInvoiceItemID(String invitemid){this.invoiceItemID=invitemid;}
    public void setInvoiceID(String iid){this.invoiceID=iid;}
    public void setProductID(String pid){this.productID=pid;}
    public void setQuantityPurchased(int qp){this.quantityPurchased=qp;}
    
    public String getInvoiceItemID(){return this.invoiceItemID;}
    public String getInvoiceID(){return this.invoiceID;}
    public String getProductID(){return this.productID;}
    public int getQuantityPurchased(){return this.quantityPurchased;}
    
}
