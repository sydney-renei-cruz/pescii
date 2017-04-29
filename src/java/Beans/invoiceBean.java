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
public class invoiceBean implements Serializable{
    
    public int invoiceID;
    public String invoiceName;
    public String PRCID;
    public int customerID;
    public String customerName;
    public int clinicID;
    public String clinicName;
    public String provinceName;
    public String provinceDivision;
    public Date invoiceDate;
    public Date deliveryDate;
    public String termsOfPayment;
    public Date paymentDueDate;
    public Date datePaid;
    public Date dateClosed;
    public int statusID;
    public String statusName;
    public float overdueFee;
    public float amountDue;
    public float amountPaid;
    public float discount;
    public Timestamp dateCreated;
    public String lastEdittedBy;
    public Date lastEdittedDate;
    public Date dateDelivered;
    
    public invoiceBean(int iid, String iname, String cid, int custID, String custName, int clinid, String clinName, String provName, 
                        String provDiv, Date invdate, Date deldate,
                        String tofpay, Date payduedate, Date dpaid, Date dclosed, int statID, 
                        String statName, float overfee, float ad, float ap, float disc, Timestamp dcreated,
                        String leb, Date led, Date dd){
        this.invoiceID=iid;
        this.invoiceName=iname;
        this.PRCID=cid;
        this.customerID=custID;
        this.customerName=custName;
        this.clinicID=clinid;
        this.clinicName=clinName;
        this.provinceName=provName;
        this.provinceDivision=provDiv;
        this.invoiceDate=invdate;
        this.deliveryDate=deldate;
        this.termsOfPayment=tofpay;
        this.paymentDueDate=payduedate;
        this.datePaid=dpaid;
        this.dateClosed=dclosed;
        this.statusID=statID;
        this.statusName=statName;
        this.overdueFee=overfee;
        this.amountDue=ad;
        this.amountPaid=ap;
        this.discount=disc;
        this.dateCreated=dcreated;
        this.lastEdittedBy=leb;
        this.lastEdittedDate=led;
        this.dateDelivered=dd;
    }
    
    public invoiceBean(){}
    
    public void setInvoiceID(int iid){this.invoiceID=iid;}
    public void setInvoiceName(String iname){this.invoiceName=iname;}
    public void setPRCID(String cid){this.PRCID=cid;}
    public void setCustomerID(int custID){this.customerID=custID;}
    public void setCustomerName(String custName){this.customerName=custName;}
    public void setClinicID(int clinid){this.clinicID=clinid;}
    public void setClinicName(String clinName){this.clinicName=clinName;}
    public void setProvinceName(String provName){this.provinceName=provName;}
    public void setProvinceDivision(String provDiv){this.provinceDivision=provDiv;}
    public void setInvoiceDate(Date invdate){this.invoiceDate=invdate;}
    public void setDeliveryDate(Date deldate){this.deliveryDate=deldate;}
    public void setTermsOfPayment(String tofpay){this.termsOfPayment=tofpay;}
    public void setPaymentDueDate(Date payduedate){this.paymentDueDate=payduedate;}
    public void setDatePaid(Date dpaid){this.datePaid=dpaid;}
    public void setDateClosed(Date dclosed){this.dateClosed=dclosed;}
    public void setStatusID(int statID){this.statusID=statID;}
    public void setStatusName(String statName){this.statusName=statName;}
    public void setOverdueFee(float overfee){this.overdueFee=overfee;}
    public void setAmountDue(float ad){this.amountDue=ad;}
    public void setAmountPaid(float ap){this.amountPaid=ap;}
    public void setDiscount(float disc){this.discount=disc;}
    public void setDateCreated(Timestamp dc){this.dateCreated=dc;}
    public void setLastEdittedBy(String leb){this.lastEdittedBy=leb;}
    public void setLastEdittedDate(Date led){this.lastEdittedDate=led;}
    public void setDateDelivered(Date dd){this.dateDelivered=dd;}
    
    public int getInvoiceID(){return this.invoiceID;}
    public String getInvoiceName(){return this.invoiceName;}
    public String getPRCID(){return this.PRCID;}
    public int getCustomerID(){return this.customerID;}
    public String getCustomerName(){return this.customerName;}
    public int getClinicID(){return this.clinicID;}
    public String getClinicName(){return this.clinicName;}
    public String getProvinceName(){return this.provinceName;}
    public String getProvinceDivision(){return this.provinceDivision;}
    public Date getInvoiceDate(){return this.invoiceDate;}
    public Date getDeliveryDate(){return this.deliveryDate;}
    public String getTermsOfPayment(){return this.termsOfPayment;}
    public Date getPaymentDueDate(){return this.paymentDueDate;}
    public Date getDatePaid(){return this.datePaid;}
    public Date getDateClosed(){return this.dateClosed;}
    public int getStatusID(){return this.statusID;}
    public String getStatusName(){return this.statusName;}
    public float getOverdueFee(){return this.overdueFee;}
    public float getAmountDue(){return this.amountDue;}
    public float getAmountPaid(){return this.amountPaid;}
    public float getDiscount(){return this.discount;}
    public Timestamp getDateCreated(){return this.dateCreated;}
    public String getLastEdittedBy(){return this.lastEdittedBy;}
    public Date getLastEdittedDate(){return this.lastEdittedDate;}
    public Date getDateDelivered(){return this.dateDelivered;}
    
}
