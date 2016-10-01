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
public class salesRepBean implements Serializable{
    
    public int salesRepID;
    public String salesRepName;
    
    public salesRepBean(int srID, String srName){
        this.salesRepID=srID;
        this.salesRepName=srName;
    }
    
    public salesRepBean(){}
    
    public void setSalesRepName(String srName){this.salesRepName=srName;}
    public void setSalesRepID(int srID){this.salesRepID=srID;}
    
    public String getSalesRepName(){return this.salesRepName;}
    public int getSalesRepID(){return this.salesRepID;}
}
