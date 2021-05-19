package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class ReceiptModel {

    private int id;
    private String merchantName;
    private String merchantAddress;
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<Float> unitPrices = new ArrayList<>();
    private ArrayList<Integer> itemQuantities = new ArrayList<>();
    private Float vatPrice;
    private Float vatablePrice;
    private Date date;
    private String serialNumber;

    public ReceiptModel(int id, String merchantName, String merchantAddress, String items, String unitPrices, String itemQuantities, Float vatPrice, Float vatablePrice, Date date, String serialNumber) {

        StringTokenizer stItems = new StringTokenizer(items, ",");
        StringTokenizer stUnitPrices = new StringTokenizer(unitPrices, ",");
        StringTokenizer stItemQuantities = new StringTokenizer(itemQuantities, ",");


        this.id = id;
        this.merchantName = merchantName;
        this.merchantAddress = merchantAddress;

        while (stItems.countTokens() >0){
            this.items.add(stItems.nextToken());
        }

        while (stUnitPrices.countTokens() >0){
            this.unitPrices.add(Float.parseFloat(stUnitPrices.nextToken()));
        }

        while (stItemQuantities.countTokens() >0){
            this.itemQuantities.add(Integer.parseInt(stItemQuantities.nextToken()));
        }

        this.vatPrice = vatPrice;
        this.vatablePrice = vatablePrice;
        this.date = date;
        this.serialNumber = serialNumber;
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

    public ArrayList<String> getItems() {
        return items;
    }

    public ArrayList<Float> getUnitPrices() {
        return unitPrices;
    }

    public ArrayList<Integer> getItemQuantities() {
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

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public void setUnitPrices(ArrayList<Float> unitPrices) {
        this.unitPrices = unitPrices;
    }

    public void setItemQuantities(ArrayList<Integer> itemQuantities) {
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
}