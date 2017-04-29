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
public class restockOrderStatusBean implements Serializable{
    
    public int statusID;
    public String statusName;
    
    public restockOrderStatusBean(int sid, String sn){
        this.statusID=sid;
        this.statusName=sn;
    }
    
    public restockOrderStatusBean(){};
    
    public void setStatusID(int sid){this.statusID=sid;}
    public void setStatusName(String sn){this.statusName=sn;}
    
    public int getStatusID(){return this.statusID;}
    public String getStatusName(){return this.statusName;}
    
}
