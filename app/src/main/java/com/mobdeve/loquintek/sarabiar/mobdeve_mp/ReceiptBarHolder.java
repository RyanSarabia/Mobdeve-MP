package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ReceiptBarHolder extends RecyclerView.ViewHolder{

    private TextView receiptBarStoreTv;
    private TextView receiptBarDateTv;
    private TextView receiptBarItemsTv;
    private TextView receiptBarTotalTv;
    private Chip receiptBarTagChp;
    private ImageButton receiptBarTagBtn;
    private String receiptBarSerialNo;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Context context;
    private List<Tag> tags;
    private List<String> tagNames;
    private ArrayAdapter<String> dataAdapter;

    private Database db;

    private Spinner popupTagSp;
    private Button popupSaveBtn;
    private Button popupTagCancelBtn;

    private NumberFormat numFormatter;

    public ReceiptBarHolder (View view, Context context) {
        super(view);

        receiptBarStoreTv = view.findViewById(R.id.receiptBarStoreTv);
        receiptBarDateTv = view.findViewById(R.id.receiptBarDateTv);
        receiptBarItemsTv = view.findViewById(R.id.receiptBarItemsTv);
        receiptBarTotalTv = view.findViewById(R.id.receiptBarTotalTv);
        receiptBarTagBtn = view.findViewById(R.id.receiptBarTagBtn);
        receiptBarTagChp = view.findViewById(R.id.receiptTagChp);

        db = new Database(context);
        tags = db.getAllTags();
        populateTagMenu();
        dataAdapter =  new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, tagNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numFormatter = new DecimalFormat("#0.00");
        this.context = context;


        receiptBarTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTagDialog();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReceiptActivity.class);
                intent.putExtra("SERIAL_NO", receiptBarSerialNo);
                intent.putExtra("TOTAL", receiptBarTotalTv.getText().toString());
                intent.putExtra("POSITION", ReceiptBarHolder.this.getAdapterPosition());
                ((Activity)v.getContext()).startActivityForResult(intent, 1);
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

        this.receiptBarTotalTv.setText("Php " + numFormatter.format(total));
    }

    public void setReceiptBarSerialNo(String serialNo) {
        this.receiptBarSerialNo = serialNo;
    }

    public void setReceiptBarTagChp(String tagName) {
        if (tagName != null && tagName.length() > 0)
            this.receiptBarTagChp.setText(tagName);
        else
            this.receiptBarTagChp.setText("N/A");
    }

    private void createTagDialog() {
        dialogBuilder = new AlertDialog.Builder(context);
        final View tagPopupView = LayoutInflater.from(context).inflate(R.layout.tag_popup_layout, null);

        popupTagSp = tagPopupView.findViewById(R.id.popupTagSp);
        popupTagCancelBtn = tagPopupView.findViewById(R.id.popupTagCancelBtn);
        popupSaveBtn = tagPopupView.findViewById(R.id.popupSaveBtn);

        popupTagSp.setAdapter(dataAdapter);

        String tagName = receiptBarTagChp.getText().toString();
        if (tagName.compareTo("N/A") != 0) {
            setTagMenuDefault(tagName);
        }

        dialogBuilder.setView(tagPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        popupTagCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        popupSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagName = popupTagSp.getSelectedItem().toString();
                db.addReceiptTag(receiptBarSerialNo, tagName);
                receiptBarTagChp.setText(tagName);
                dialog.dismiss();
            }
        });

    }

    private void populateTagMenu() {

        tagNames = new ArrayList<String>();

        for (int i = 0; i < tags.size(); i++) {
            tagNames.add(tags.get(i).getTagName());
        }
    }

    private void setTagMenuDefault(String tagName) {
        int i;
        for (i = 0; i < tagNames.size(); i++) {
            if (tagNames.get(i).compareTo(tagName) == 0)
                break;
        }

        popupTagSp.setSelection(i);
    }
}
