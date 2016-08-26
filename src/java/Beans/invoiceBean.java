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
public class invoiceBean implements Serializable{
    
    public int invoiceID;
    public String PRCID;
    public int clinicID;
    public String invoiceDate;
    public String deliveryDate;
    public String additionalAccessories;
    public String termsOfPayment;
    public String paymentDueDate;
    public String datePaid;
    public String dateClosed;
    public String status;
    public float overdueFee;
    
    public invoiceBean(int iid, String cid, int clinid, String invdate, String deldate,
                        String addacc, String tofpay, String payduedate, String dpaid, String dclosed,
                        String stat, float overfee){
        this.invoiceID=iid;
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
    public void setPRCID(String cid){this.PRCID=cid;}
    public void setClinicID(int clinid){this.clinicID=clinid;}
    public void setInvoiceDate(String invdate){this.invoiceDate=invdate;}
    public void setDeliveryDate(String deldate){this.deliveryDate=deldate;}
    public void setAdditionalAccessories(String addacc){this.additionalAccessories=addacc;}
    public void setTermsOfPayment(String tofpay){this.termsOfPayment=tofpay;}
    public void setPaymentDueDate(String payduedate){this.paymentDueDate=payduedate;}
    public void setDatePaid(String dpaid){this.datePaid=dpaid;}
    public void setDateClosed(String dclosed){this.dateClosed=dclosed;}
    public void setStatus(String stat){this.status=stat;}
    public void setOverdueFee(float overfee){this.overdueFee=overfee;}
    
    public int getInvoiceID(){return this.invoiceID;}
    public String getPRCID(){return this.PRCID;}
    public int getClinicID(){return this.clinicID;}
    public String getInvoiceDate(){return this.invoiceDate;}
    public String getDeliveryDate(){return this.deliveryDate;}
    public String getAdditionalAccessories(){return this.additionalAccessories;}
    public String getTermsOfPayment(){return this.termsOfPayment;}
    public String getPaymentDueDate(){return this.paymentDueDate;}
    public String getDatePaid(){return this.datePaid;}
    public String getDateClosed(){return this.dateClosed;}
    public String getStatus(){return this.status;}
    public float getOverdueFee(){return this.overdueFee;}
    
}
