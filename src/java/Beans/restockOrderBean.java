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
public class restockOrderBean implements Serializable{
    
    public int restockOrderID;
    public String ROName;
    public int statusID;
    public String statusName;
    public String purpose;
    public Date RODateDue;
    public Date RODateDelivered;
    public float restockPrice;
    public float amountPaid;
    public float discount;
    public Date datePaid;
    public Timestamp dateCreated;
    public String lastEdittedBy;
    public Date lastEdittedDate;
    
    public restockOrderBean(int roid, String ron,int supID,
                            String supName, int statID, String statName, String purp, Date rod, Date rodd, float rp, float ap,
                            float disc, Date dp, Timestamp dc, String leb, Date led){
        this.restockOrderID=roid;
        this.ROName=ron;
        this.statusID=statID;
        this.statusName=statName;
        this.purpose=purp;
        this.RODateDue=rod;
        this.RODateDelivered=rodd;
        this.restockPrice=rp;
        this.amountPaid=ap;
        this.discount=disc;
        this.datePaid=dp;
        this.dateCreated=dc;
        this.lastEdittedBy=leb;
        this.lastEdittedDate=led;
    }
    
    public restockOrderBean(){}
    
    public void setRestockOrderID(int roid){this.restockOrderID=roid;}
    public void setRestockOrderName(String ron){this.ROName=ron;}
    public void setStatusID(int statID){this.statusID=statID;}
    public void setStatusName(String statName){this.statusName=statName;}
    public void setPurpose(String purp){this.purpose=purp;}
    public void setRODateDue(Date rod){this.RODateDue=rod;}
    public void setRODateDelivered(Date rodd){this.RODateDelivered=rodd;}
    public void setRestockPrice(float rp){this.restockPrice=rp;}
    public void setAmountPaid(float ap){this.amountPaid=ap;}
    public void setDiscount(float disc){this.discount=disc;}
    public void setDatePaid(Date dp){this.datePaid=dp;}
    public void setDateCreated(Timestamp dc){this.dateCreated=dc;}
    public void setLastEdittedBy(String leb){this.lastEdittedBy=leb;}
    public void setLastEdittedDate(Date led){this.lastEdittedDate=led;}

    public int getRestockOrderID(){return this.restockOrderID;}
    public String getRestockOrderName(){return this.ROName;}
    public int getStatusID(){return this.statusID;}
    public String getStatusName(){return this.statusName;}
    public String getPurpose(){return this.purpose;}
    public Date getRODateDue(){return this.RODateDue;}
    public Date getRODateDelivered(){return this.RODateDelivered;}
    public float getRestockPrice(){return this.restockPrice;}
    public float getAmountPaid(){return this.amountPaid;}
    public float getDiscount(){return this.discount;}
    public Date getDatePaid(){return this.datePaid;}
    public Timestamp getDateCreated(){return this.dateCreated;}
    public String getLastEdittedBy(){return this.lastEdittedBy;}
    public Date getLastEdittedDate(){return this.lastEdittedDate;}
        
}
