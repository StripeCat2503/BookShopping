/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dtos;

/**
 *
 * @author DuyNK
 */
public class PaymentMethodDTO {
    private String paymentMethodID;
    private String paymentMethodName;

    public PaymentMethodDTO() {
    }

    public PaymentMethodDTO(String paymentMethodID, String paymentMethodName) {
        this.paymentMethodID = paymentMethodID;
        this.paymentMethodName = paymentMethodName;
    }

    public String getPaymentMethodID() {
        return paymentMethodID;
    }

    public void setPaymentMethodID(String paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
    
    
}
