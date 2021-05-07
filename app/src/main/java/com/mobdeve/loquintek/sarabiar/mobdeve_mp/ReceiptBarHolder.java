package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ReceiptBarHolder extends RecyclerView.ViewHolder{

    private TextView receiptBarStoreTv;
    private TextView receiptBarDateTv;
    private TextView receiptBarItemsTv;
    private TextView receiptBarTotalTv;
    private Button receiptBarSettingsBtn;


    public ReceiptBarHolder (View view) {
        super(view);

        receiptBarStoreTv = view.findViewById(R.id.receiptBarStoreTv);
        receiptBarDateTv = view.findViewById(R.id.receiptBarDateTv);
        receiptBarItemsTv = view.findViewById(R.id.receiptBarItemsTv);
        receiptBarTotalTv = view.findViewById(R.id.receiptBarTotalTv);
        receiptBarSettingsBtn = view.findViewById(R.id.receiptBarSettingsBtn);


    }

    public void setReceiptBarStoreTv(String storeName) {
        this.receiptBarStoreTv.setText(storeName);
    }

    public void setReceiptBarDateTv(String date) {
        this.receiptBarDateTv.setText(date);
    }

    public void setReceiptBarItemsTv(String items) {
        this.receiptBarItemsTv.setText(items);
    }

    public void setReceiptBarTotalTv(double total) {
        this.receiptBarTotalTv.setText(String.valueOf(total));
    }

    public void setReceiptBarSettingsBtn(Button receiptBarSettingsBtn) {
        this.receiptBarSettingsBtn = receiptBarSettingsBtn;
    }
}
