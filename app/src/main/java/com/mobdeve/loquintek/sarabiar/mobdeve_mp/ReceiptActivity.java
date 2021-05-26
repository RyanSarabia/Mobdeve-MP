package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

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




    }
}