package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

public class ReceiptBarModel {

    private String receiptBarStore;
    private String receiptBarDate;
    private String receiptBarItems;
    private String receiptBarSerial;
    private double receiptBarTotal;
//    private Button receiptBarSettings;


    public ReceiptBarModel (String storeName, String date, String items, double total, String serial) {
        receiptBarStore =  storeName;
        receiptBarDate = date;
        receiptBarItems = items;
        receiptBarTotal = total;
        receiptBarSerial = serial;
//        receiptBarSettings =
    }


    public String getReceiptBarStore() {
        return receiptBarStore;
    }

    public void setReceiptBarStore(String receiptBarStore) {
        this.receiptBarStore = receiptBarStore;
    }

    public String getReceiptBarDate() {
        return receiptBarDate;
    }

    public void setReceiptBarDate(String receiptBarDate) {
        this.receiptBarDate = receiptBarDate;
    }

    public String getReceiptBarItems() {
        return receiptBarItems;
    }

    public void setReceiptBarItems(String receiptBarItems) {
        this.receiptBarItems = receiptBarItems;
    }

    public double getReceiptBarTotal() {
        return receiptBarTotal;
    }

    public void setReceiptBarTotal(double receiptBarTotal) {
        this.receiptBarTotal = receiptBarTotal;
    }

    public String getReceiptBarSerial() {
        return receiptBarSerial;
    }

    public void setReceiptBarSerial(String receiptBarSerial) {
        this.receiptBarSerial = receiptBarSerial;
    }
}
