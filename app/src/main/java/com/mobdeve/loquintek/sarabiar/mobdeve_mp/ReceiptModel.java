package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.content.Intent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class ReceiptModel {

    private int id;
    private String merchantName;
    private String merchantAddress;
    private String items;
    private String unitPrices;
    private String itemQuantities;
    private Float vatPrice;
    private Float vatablePrice;
    private Float amountPaid;
    private Date date;
    private String serialNumber;
    private Tag receiptTag;

    public ReceiptModel(int id, String merchantName, String merchantAddress, String items, String unitPrices, String itemQuantities, Float vatPrice, Float vatablePrice, Date date, String serialNumber, Float amountPaid) {

//        StringTokenizer stItems = new StringTokenizer(items, ",");
//        StringTokenizer stUnitPrices = new StringTokenizer(unitPrices, ",");
//        StringTokenizer stItemQuantities = new StringTokenizer(itemQuantities, ",");


        this.id = id;
        this.merchantName = merchantName;
        this.merchantAddress = merchantAddress;

 //       while (stItems.countTokens() >0){
 //           this.items.add(stItems.nextToken());
//        }
//
//        while (stUnitPrices.countTokens() >0){
//            this.unitPrices.add(Float.parseFloat(stUnitPrices.nextToken()));
//        }
//
//        while (stItemQuantities.countTokens() >0){
//            this.itemQuantities.add(Integer.parseInt(stItemQuantities.nextToken()));
//        }
        this.items = items;
        this.unitPrices = unitPrices;
        this.itemQuantities = itemQuantities;
        this.vatPrice = vatPrice;
        this.vatablePrice = vatablePrice;
        this.date = date;
        this.serialNumber = serialNumber;
        this.amountPaid = amountPaid;
    }

    @Override
    public String toString() {
        return "receiptModel{" +
                "id=" + id +
                ", merchantName='" + merchantName + '\'' +
                ", merchantAddress='" + merchantAddress + '\'' +
                ", items=" + items +
                ", unitPrices=" + unitPrices +
                ", itemQuantities=" + itemQuantities +
                ", vatPrice=" + vatPrice +
                ", vatablePrice=" + vatablePrice +
                ", date=" + date +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public String getItems() {
        return items;
    }

    public String getUnitPrices() {
        return unitPrices;
    }

    public String getItemQuantities() {
        return itemQuantities;
    }

    public Float getVatPrice() {
        return vatPrice;
    }

    public Float getVatablePrice() {
        return vatablePrice;
    }

    public Date getDate() {
        return date;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setUnitPrices(String unitPrices) {
        this.unitPrices = unitPrices;
    }

    public void setItemQuantities(String itemQuantities) {
        this.itemQuantities = itemQuantities;
    }

    public void setVatPrice(Float vatPrice) {
        this.vatPrice = vatPrice;
    }

    public void setVatablePrice(Float vatablePrice) {
        this.vatablePrice = vatablePrice;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public String getTagAsString(){
        if (receiptTag == null) {
            return "";
        }
        return receiptTag.getTagName();
    }

    public void setTag(Tag newTag){
        this.receiptTag = newTag;
    }

    public void addTag(Tag newTag){
        receiptTag = newTag;
    }

    public Tag getTag(){
        return this.receiptTag;
    }

    public void deleteTag(Tag deleteTag){
        this.receiptTag = null;
    }

    public Float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Float amountPaid) {
        this.amountPaid = amountPaid;
    }
}
