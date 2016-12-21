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
public class productClassBean implements Serializable{
    
    public int productClassID;
    public String productClassName;
    
    public productClassBean(int pcID, String pcName){
        this.productClassID=pcID;
        this.productClassName=pcName;
    }
    
    public productClassBean(){}
    
    public void setProductClassID(int pcID){this.productClassID=pcID;}
    public void setProductClassName(String pcName){this.productClassName=pcName;}
    
    public int getProductClassID(){return this.productClassID;}
    public String getProductClassName(){return this.productClassName;}
    
}
