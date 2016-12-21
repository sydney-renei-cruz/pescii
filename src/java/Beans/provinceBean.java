/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author user
 */
public class provinceBean {
    
    public int provinceID;
    public String provinceName;
    public String provinceDivision;
    
    public provinceBean(int provid, String provName, String provDiv){
        this.provinceID=provid;
        this.provinceName=provName;
        this.provinceDivision=provDiv;
    }
    
    public provinceBean(){}
    
    public void setProvinceID(int provid){this.provinceID=provid;}
    public void setProvinceName(String provName){this.provinceName=provName;}
    public void setProvinceDivision(String provDiv){this.provinceDivision=provDiv;}
    
    public int getProvinceID(){return this.provinceID;}
    public String getProvinceName(){return this.provinceName;}
    public String getProvinceDivision(){return this.provinceDivision;}
    
}
