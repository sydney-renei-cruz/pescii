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
public class productBean implements Serializable{
    public String productID;
    public String productName;
    public String productDescription;
    public float productPrice;
    public float restockPrice;
    public int stocksRemaining;
    public int lowStock;
    public String brand;
    public String productClass;
    public String color;
    
    public productBean(String pid, String pname, String pdesc, float pprice, float rprice,
                        int srem, int lstock, String b, String pclass, String c){
        this.productID=pid;
        this.productName=pname;
        this.productDescription=pdesc;
        this.productPrice=pprice;
        this.restockPrice=rprice;
        this.stocksRemaining=srem;
        this.lowStock=lstock;
        this.brand=b;
        this.productClass=pclass;
        this.color=c;
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
    public void setProductClass(String pclass){this.productClass=pclass;}
    public void setColor(String c){this.color=c;}
    
    public String getProductID(){return this.productID;}
    public String getProductName(){return this.productName;}
    public String getProductDescription(){return this.productDescription;}
    public float getProductPrice(){return this.productPrice;}
    public float getRestockPrice(){return this.restockPrice;}
    public int getStocksRemaining(){return this.stocksRemaining;}
    public int getLowStock(){return this.lowStock;}
    public String getBrand(){return this.brand;}
    public String getProductClass(){return this.productClass;}
    public String getColor(){return this.color;}
}
