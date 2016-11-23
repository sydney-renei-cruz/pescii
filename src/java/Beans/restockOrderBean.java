/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author user
 */
public class restockOrderBean implements Serializable{
    
    public int restockOrderID;
    public String ROName;
    public int productID;
    public String productName;
    public int numberOfPiecesOrdered;
    public int numberOfPiecesReceived;
    public String supplier;
    public String purpose;
    public Date RODateDue;
    public Date RODateDelivered;
    public float restockPrice;
    
    public restockOrderBean(int roid, String ron, int pid, String pn, int nopo, int nopr, String sup, String purp, Date rod, Date rodd, float rp){
        this.restockOrderID=roid;
        this.ROName=ron;
        this.productID=pid;
        this.productName=pn;
        this.numberOfPiecesOrdered=nopo;
        this.numberOfPiecesReceived=nopr;
        this.supplier=sup;
        this.purpose=purp;
        this.RODateDue=rod;
        this.RODateDelivered=rodd;
        this.restockPrice=rp;
    }
    
    public restockOrderBean(){}
    
    public void setRestockOrderID(int roid){this.restockOrderID=roid;}
    public void setRestockOrderName(String ron){this.ROName=ron;}
    public void setProductID(int pid){this.productID=pid;}
    public void setProductName(String pn){this.productName=pn;}
    public void setNumberOfPiecesOrdered(int nopo){this.numberOfPiecesOrdered=nopo;}
    public void setNumberOfPiecesReceived(int nopr){this.numberOfPiecesReceived=nopr;}
    public void setSupplier(String sup){this.supplier=sup;}
    public void setPurpose(String purp){this.purpose=purp;}
    public void setRODateDue(Date rod){this.RODateDue=rod;}
    public void setRODateDelivered(Date rodd){this.RODateDelivered=rodd;}
    public void setRestockPrice(float rp){this.restockPrice=rp;}

    public int getRestockOrderID(){return this.restockOrderID;}
    public String getRestockOrderName(){return this.ROName;}
    public int getProductID(){return this.productID;}
    public String getProductName(){return this.productName;}
    public int getNumberOfPiecesOrdered(){return this.numberOfPiecesOrdered;}
    public int getNumberOfPiecesReceived(){return this.numberOfPiecesReceived;}
    public String getSupplier(){return this.supplier;}
    public String getPurpose(){return this.purpose;}
    public Date getRODateDue(){return this.RODateDue;}
    public Date getRODateDelivered(){return this.RODateDelivered;}
    public float getRestockPrice(){return this.restockPrice;}    
        
}
