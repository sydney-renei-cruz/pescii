/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author user
 */
public class invoiceBean implements Serializable{
    
    public int invoiceID;
    public String invoiceName;
    public String PRCID;
    public int clinicID;
    public Date invoiceDate;
    public Date deliveryDate;
    public String additionalAccessories;
    public String termsOfPayment;
    public Date paymentDueDate;
    public Date datePaid;
    public Date dateClosed;
    public String status;
    public float overdueFee;
    
    public invoiceBean(int iid, String iname, String cid, int clinid, Date invdate, Date deldate,
                        String addacc, String tofpay, Date payduedate, Date dpaid, Date dclosed,
                        String stat, float overfee){
        this.invoiceID=iid;
        this.invoiceName=iname;
        this.PRCID=cid;
        this.clinicID=clinid;
        this.invoiceDate=invdate;
        this.deliveryDate=deldate;
        this.additionalAccessories=addacc;
        this.termsOfPayment=tofpay;
        this.paymentDueDate=payduedate;
        this.datePaid=dpaid;
        this.dateClosed=dclosed;
        this.status=stat;
        this.overdueFee=overfee;
    }
    
    public invoiceBean(){}
    
    public void setInvoiceID(int iid){this.invoiceID=iid;}
    public void setInvoiceName(String iname){this.invoiceName=iname;}
    public void setPRCID(String cid){this.PRCID=cid;}
    public void setClinicID(int clinid){this.clinicID=clinid;}
    public void setInvoiceDate(Date invdate){this.invoiceDate=invdate;}
    public void setDeliveryDate(Date deldate){this.deliveryDate=deldate;}
    public void setAdditionalAccessories(String addacc){this.additionalAccessories=addacc;}
    public void setTermsOfPayment(String tofpay){this.termsOfPayment=tofpay;}
    public void setPaymentDueDate(Date payduedate){this.paymentDueDate=payduedate;}
    public void setDatePaid(Date dpaid){this.datePaid=dpaid;}
    public void setDateClosed(Date dclosed){this.dateClosed=dclosed;}
    public void setStatus(String stat){this.status=stat;}
    public void setOverdueFee(float overfee){this.overdueFee=overfee;}
    
    public int getInvoiceID(){return this.invoiceID;}
    public String getInvoiceName(){return this.invoiceName;}
    public String getPRCID(){return this.PRCID;}
    public int getClinicID(){return this.clinicID;}
    public Date getInvoiceDate(){return this.invoiceDate;}
    public Date getDeliveryDate(){return this.deliveryDate;}
    public String getAdditionalAccessories(){return this.additionalAccessories;}
    public String getTermsOfPayment(){return this.termsOfPayment;}
    public Date getPaymentDueDate(){return this.paymentDueDate;}
    public Date getDatePaid(){return this.datePaid;}
    public Date getDateClosed(){return this.dateClosed;}
    public String getStatus(){return this.status;}
    public float getOverdueFee(){return this.overdueFee;}
    
}
