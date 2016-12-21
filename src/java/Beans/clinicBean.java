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
public class clinicBean implements Serializable{
    
    public String clinicID;
    public String PRCID;
    public String clinicAddress;
    public String clinicPhoneNumber;
    public String clinicName;
    public int provinceID;
    public String provinceName;
    public String provinceDivision;
    public Timestamp dateCreated;
    public String lastEdittedBy;
    public Date lastEdittedDate;
    
    public clinicBean(String clinid, String cid, String clinadd, String clinphonenum, String clinname, 
            int provid, String provName, String provDiv, Timestamp dc, String leb, Date led){
        this.clinicID=clinid;
        this.PRCID=cid;
        this.clinicAddress=clinadd;
        this.clinicPhoneNumber=clinphonenum;
        this.clinicName=clinname;
        this.provinceID=provid;
        this.provinceName=provName;
        this.provinceDivision=provDiv;
        this.dateCreated=dc;
        this.lastEdittedBy=leb;
        this.lastEdittedDate=led;
    }
    
    public clinicBean(){}
    
    public void setClinicID(String clinid){this.clinicID=clinid;}
    public void setPRCID(String cid){this.PRCID=cid;}
    public void setClinicAddress(String clinadd){this.clinicAddress=clinadd;}
    public void setClinicPhoneNumber(String clinphonenum){this.clinicPhoneNumber=clinphonenum;}
    public void setClinicName(String clinname){this.clinicName=clinname;}
    public void setProvinceID(int provid){this.provinceID=provid;}
    public void setProvinceName(String provName){this.provinceName=provName;}
    public void setProvinceDivision(String provDiv){this.provinceDivision=provDiv;}
    public void setDateCreated(Timestamp dc){this.dateCreated=dc;}
    public void setLastEdittedBy(String leb){this.lastEdittedBy=leb;}
    public void setLastEdittedDate(Date led){this.lastEdittedDate=led;}
    
    public String getClinicID(){return this.clinicID;}
    public String getPRCID(){return this.PRCID;}
    public String getClinicAddress(){return this.clinicAddress;}
    public String getClinicPhoneNumber(){return this.clinicPhoneNumber;}
    public String getClinicName(){return this.clinicName;}
    public int getProvinceID(){return this.provinceID;}
    public String getProvinceName(){return this.provinceName;}
    public String getProvinceDivision(){return this.provinceDivision;}
    public Timestamp getDateCreated(){return this.dateCreated;}
    public String getLastEdittedBy(){return this.lastEdittedBy;}
    public Date getLastEdittedDate(){return this.lastEdittedDate;}
}
