package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    private ReceiptModel receipt;

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

        Format dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        NumberFormat numFormatter = new DecimalFormat("#0.00");

        Intent intent = getIntent();
        String serialNo = intent.getStringExtra("SERIAL_NO");

        Database db = new Database(this);
        ReceiptModel receipt = db.getOne(serialNo);

        String merchant = receipt.getMerchantName();
        String address = receipt.getMerchantAddress();
        String date = dateFormatter.format(receipt.getDate());
        String vatable = numFormatter.format(receipt.getVatablePrice());
        String vat = numFormatter.format(receipt.getVatPrice());

        storeTv.setText(merchant);
        addressTv.setText(address);
        dateTv.setText(date);
        vatableTv.setText(vatable);
        vatTv.setText(vat);

    }
}