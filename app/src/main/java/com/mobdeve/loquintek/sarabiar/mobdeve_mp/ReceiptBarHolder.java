package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptBarHolder extends RecyclerView.ViewHolder{

    private TextView receiptBarStoreTv;
    private TextView receiptBarDateTv;
    private TextView receiptBarItemsTv;
    private TextView receiptBarTotalTv;
    private Button receiptBarSettingsBtn;
    private Button receiptBarTagBtn;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Context context;

    private EditText popupTagEt;
    private Button popupAddBtn;
    private Button popupDoneBtn;
    private TableLayout popupTagTl;

    public ReceiptBarHolder (View view, Context context) {
        super(view);

        receiptBarStoreTv = view.findViewById(R.id.receiptBarStoreTv);
        receiptBarDateTv = view.findViewById(R.id.receiptBarDateTv);
        receiptBarItemsTv = view.findViewById(R.id.receiptBarItemsTv);
        receiptBarTotalTv = view.findViewById(R.id.receiptBarTotalTv);
        receiptBarSettingsBtn = view.findViewById(R.id.receiptBarSettingsBtn);
        receiptBarTagBtn = view.findViewById(R.id.receiptBarTagBtn);
        this.context = context;


        receiptBarTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTagDialog();
            }
        });
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



    private void createTagDialog() {
        dialogBuilder = new AlertDialog.Builder(context);
        final View tagPopupView = LayoutInflater.from(context).inflate(R.layout.tag_popup_layout, null);

        popupTagEt = tagPopupView.findViewById(R.id.popupTagEt);
        popupDoneBtn = tagPopupView.findViewById(R.id.popupDoneBtn);
        popupAddBtn = tagPopupView.findViewById(R.id.popupAddBtn);
        popupTagTl = tagPopupView.findViewById(R.id.popupTagTl);


//        fill tablelayout with chips

        dialogBuilder.setView(tagPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        popupDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
}
