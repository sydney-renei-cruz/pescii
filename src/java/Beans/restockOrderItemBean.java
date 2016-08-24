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
    
    public String restockOrderItemID;
    public String restockOrderID;
    public String productID;
    public int restockQuantityOrdered;
    public int restockQuantityReceived;
    
    public restockOrderItemBean(String roitemid, String roid, String pid, int rqo, int rqr){
        this.restockOrderItemID=roitemid;
        this.restockOrderID=roid;
        this.productID=pid;
        this.restockQuantityOrdered=rqo;
        this.restockQuantityReceived=rqr;
    }
    
    public restockOrderItemBean(){}
    
    public void setRestockOrderItemID(String roitemid){this.restockOrderItemID=roitemid;}
    public void setRestockOrderID(String roid){this.restockOrderID=roid;}
    public void setProductID(String pid){this.productID=pid;}
    public void setRestockQuantityOrdered(int rqo){this.restockQuantityOrdered=rqo;}
    public void setRestockQuantityReceived(int rqr){this.restockQuantityReceived=rqr;}
    
    public String getRestockOrderItemID(){return this.restockOrderItemID;}
    public String getRestockOrderID(){return this.restockOrderID;}
    public String getProductID(){return this.productID;}
    public int getRestockQuantityOrdered(){return this.restockQuantityOrdered;}
    public int getRestockQuantityReceived(){return this.restockQuantityReceived;}
    
}
