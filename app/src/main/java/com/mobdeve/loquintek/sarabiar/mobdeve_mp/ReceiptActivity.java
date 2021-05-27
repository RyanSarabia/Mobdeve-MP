package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

public class ReceiptActivity extends AppCompatActivity {

    private TableLayout receiptTl;
    private TextView storeTv;
    private TextView addressTv;
    private TextView dateTv;
    private TextView serialTv;

    private TextView totalTv;
    private TextView cashTv;
    private TextView changeTv;
    private TextView vatableTv;
    private TextView vatTv;
    private TextView miniTotalTv;

    private Button deleteBtn;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button popupConfirmBtn;
    private Button popupCancelBtn;
    private TextView popupPromptTv;

    private Database db;
    private ReceiptModel receipt;
    private String serialNo;
    private int receipt_position;

    private NumberFormat numFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        receiptTl = findViewById(R.id.receiptTl);
        storeTv = findViewById(R.id.storeTv);
        addressTv = findViewById(R.id.addressTv);
        dateTv = findViewById(R.id.dateTv);
        serialTv = findViewById(R.id.serialTv);

        totalTv = findViewById(R.id.totalTv);
        cashTv = findViewById(R.id.cashTv);
        changeTv = findViewById(R.id.changeTv);
        vatableTv = findViewById(R.id.vatableTv);
        vatTv = findViewById(R.id.vatTv);
        miniTotalTv = findViewById(R.id.miniTotalTv);

        deleteBtn = findViewById(R.id.deleteBtn);

        Format dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        numFormatter =  new DecimalFormat("#0.00");

        Intent intent = getIntent();
        serialNo = intent.getStringExtra("SERIAL_NO");
        receipt_position = intent.getIntExtra("POSITION", 0);

        db = new Database(this);
        receipt = db.getOne(serialNo);

        String merchant = receipt.getMerchantName();
        String address = receipt.getMerchantAddress();
        String date = dateFormatter.format(receipt.getDate());
        String vatable = numFormatter.format(receipt.getVatablePrice());
        String vat = numFormatter.format(receipt.getVatPrice());


        serialTv.setText(serialNo);
        storeTv.setText(merchant);
        addressTv.setText(address);
        dateTv.setText(date);
        vatableTv.setText(vatable);
        vatTv.setText(vat);

        populateTable();
        Log.d("RECEIPT", "position " + receipt_position);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createConfirmDialog();
            }
        });
    }

    private void populateTable() {

        StringTokenizer stPrices = new StringTokenizer(receipt.getUnitPrices(), ",");
        StringTokenizer stQty = new StringTokenizer(receipt.getItemQuantities(), ",");
        StringTokenizer stItems = new StringTokenizer(receipt.getItems(), ",");

        int num_items = stItems.countTokens();

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);

        TableRow.LayoutParams itemParam = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                3f
        );

        TableRow.LayoutParams qtyParam = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                0.5f
        );

        TableRow.LayoutParams priceParam = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.5f
        );



        for (int i = 0; i < num_items; i++) {

            TableRow row= new TableRow(this);
            row.setLayoutParams(lp);


            TextView item = new TextView(this);
            item.setLayoutParams(itemParam);
            item.setGravity(Gravity.CENTER);
            item.setText(stItems.nextToken());

            TextView qty = new TextView(this);
            qty.setLayoutParams(qtyParam);
            qty.setGravity(Gravity.CENTER);
            qty.setText(stQty.nextToken());

            TextView price = new TextView(this);
            price.setLayoutParams(priceParam);
            price.setGravity(Gravity.CENTER);
            price.setText(numFormatter.format(Double.parseDouble(stPrices.nextToken())));

            row.addView(item);
            row.addView(qty);
            row.addView(price);
            receiptTl.addView(row, i+1);
        }
    }

    private void createConfirmDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View promptPopupView = getLayoutInflater().inflate(R.layout.confirmation_popup_layout, null);

        popupConfirmBtn = promptPopupView.findViewById(R.id.popupConfirmBtn);
        popupCancelBtn = promptPopupView.findViewById(R.id.popupCancelBtn);
        popupPromptTv = promptPopupView.findViewById(R.id.popupPromptTv);

        popupPromptTv.setText("Are you sure you want to delete this receipt?");

        dialogBuilder.setView(promptPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        popupCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        popupConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                db.deleteOne(serialNo);
                Intent intent = new Intent();
                intent.putExtra("IS_DELETE", true);
                intent.putExtra("POSITION", receipt_position);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }
}