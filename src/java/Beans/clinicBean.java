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
public class clinicBean implements Serializable{
    
    public String clinicID;
    public String PRCID;
    public String clinicAddress;
    public String clinicPhoneNumber;
    public String clinicName;
    
    public clinicBean(String clinid, String cid, String clinadd, String clinphonenum, String clinname){
        this.clinicID=clinid;
        this.PRCID=cid;
        this.clinicAddress=clinadd;
        this.clinicPhoneNumber=clinphonenum;
        this.clinicName=clinname;
    }
    
    public clinicBean(){}
    
    public void setClinicID(String clinid){this.clinicID=clinid;}
    public void setPRCID(String cid){this.PRCID=cid;}
    public void setClinicAddress(String clinadd){this.clinicAddress=clinadd;}
    public void setClinicPhoneNumber(String clinphonenum){this.clinicPhoneNumber=clinphonenum;}
    public void setClinicName(String clinname){this.clinicName=clinname;}
    
    public String getClinicID(){return this.clinicID;}
    public String getPRCID(){return this.PRCID;}
    public String getClinicAddress(){return this.clinicAddress;}
    public String getClinicPhoneNumber(){return this.clinicPhoneNumber;}
    public String getClinicName(){return this.clinicName;}
}
