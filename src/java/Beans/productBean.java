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
public class productBean implements Serializable{
    public String productID;
    public String productName;
    public String productDescription;
    public float productPrice;
    public float restockPrice;
    public int stocksRemaining;
    public int lowStock;
    public String brand;
    public int productClassID;
    public String productClassName;
    public String color;
    public Timestamp dateCreated;
    public String lastEdittedBy;
    public Date lastEdittedDate;
    
    public productBean(String pid, String pname, String pdesc, float pprice, float rprice,
                        int srem, int lstock, String b, int pclassID, String pclassName, String c, 
                        Timestamp dc, String leb, Date led){
        this.productID=pid;
        this.productName=pname;
        this.productDescription=pdesc;
        this.productPrice=pprice;
        this.restockPrice=rprice;
        this.stocksRemaining=srem;
        this.lowStock=lstock;
        this.brand=b;
        this.productClassID=pclassID;
        this.productClassName=pclassName;
        this.color=c;
        this.dateCreated=dc;
        this.lastEdittedBy=leb;
        this.lastEdittedDate=led;
    }
    
    public productBean(){}
    
    public void setProductID(String pid){this.productID=pid;}
    public void setProductName(String pname){this.productName=pname;}
    public void setProductDescription(String pdesc){this.productDescription=pdesc;}
    public void setProductPrice(float pprice){this.productPrice=pprice;}
    public void setRestockPrice(float rprice){this.restockPrice=rprice;}
    public void setStocksRemaining(int srem){this.stocksRemaining=srem;}
    public void setLowStock(int lstock){this.lowStock=lstock;}
    public void setBrand(String b){this.brand=b;}
    public void setProductClassID(int pclassID){this.productClassID=pclassID;}
    public void setProductClassName(String pclassName){this.productClassName=pclassName;}
    public void setColor(String c){this.color=c;}
    public void setDateCreated(Timestamp dc){this.dateCreated=dc;}
    public void setLastEdittedBy(String leb){this.lastEdittedBy=leb;}
    public void setLastEdittedDate(Date led){this.lastEdittedDate=led;}
    
    public String getProductID(){return this.productID;}
    public String getProductName(){return this.productName;}
    public String getProductDescription(){return this.productDescription;}
    public float getProductPrice(){return this.productPrice;}
    public float getRestockPrice(){return this.restockPrice;}
    public int getStocksRemaining(){return this.stocksRemaining;}
    public int getLowStock(){return this.lowStock;}
    public String getBrand(){return this.brand;}
    public int getProductClassID(){return this.productClassID;}
    public String getProductClassName(){return this.productClassName;}
    public String getColor(){return this.color;}
    public Timestamp getDateCreated(){return this.dateCreated;}
    public String getLastEdittedBy(){return this.lastEdittedBy;}
    public Date getLastEdittedDate(){return this.lastEdittedDate;}
}
