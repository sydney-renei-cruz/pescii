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
public class restockOrderBean implements Serializable{
    
    public int restockOrderID;
    public int productID;
    public int numberOfPiecesOrdered;
    public int numberOfPiecesReceived;
    public String supplier;
    public String purpose;
    public String RODateCreated;
    public String RODateDelivered;
    
    public restockOrderBean(int roid, int pid, int nopo, int nopr, String sup, String purp, String rodc, String rodd){
        this.restockOrderID=roid;
        this.productID=pid;
        this.numberOfPiecesOrdered=nopo;
        this.numberOfPiecesReceived=nopr;
        this.supplier=sup;
        this.purpose=purp;
        this.RODateCreated=rodc;
        this.RODateDelivered=rodd;
        
    }
    
    public restockOrderBean(){}
    
    public void setRestockOrderID(int roid){this.restockOrderID=roid;}
    public void setProductID(int pid){this.productID=pid;}
    public void setNumberOfPiecesOrdered(int nopo){this.numberOfPiecesOrdered=nopo;}
    public void setNumberOfPiecesReceived(int nopr){this.numberOfPiecesReceived=nopr;}
    public void setSupplier(String sup){this.supplier=sup;}
    public void setPurpose(String purp){this.purpose=purp;}
    public void setRODateCreated(String rodc){this.RODateCreated=rodc;}
    public void setRODateDelivered(String rodd){this.RODateDelivered=rodd;}

    public int getRestockOrderID(){return this.restockOrderID;}
    public int getProductID(){return this.productID;}
    public int getNumberOfPiecesOrdered(){return this.numberOfPiecesOrdered;}
    public int getNumberOfPiecesReceived(){return this.numberOfPiecesReceived;}
    public String getSupplier(){return this.supplier;}
    public String getPurpose(){return this.purpose;}
    public String getRODateCreated(){return this.RODateCreated;}
    public String getRODateDelivered(){return this.RODateDelivered;}
        
        
}
