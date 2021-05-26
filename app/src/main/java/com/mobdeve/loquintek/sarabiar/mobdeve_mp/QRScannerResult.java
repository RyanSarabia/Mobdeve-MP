package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QRScannerResult extends AppCompatActivity {

    private String jsonString;
    private JSONObject jsonObject;

    private TextView merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice, date, serialNumber;
    private Button saveReceipt;
    private Button showReceipt;
    private Button showReceiptByDate;
    private Button deleteAll;
    private Date receiptDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scanner_result);

        this.merchantName = findViewById(R.id.merchantName);
        this.merchantAddress = findViewById(R.id.merchantAddress);
        this.items = findViewById(R.id.items);
        this.unitPrices = findViewById(R.id.unitPrices);
        this.itemQuantities = findViewById(R.id.itemQuantities);
        this.vatPrice = findViewById(R.id.vatPrice);
        this.vatablePrice = findViewById(R.id.vatablePrice);
        this.saveReceipt = findViewById(R.id.saveReceipt);
        this.showReceipt = findViewById(R.id.temporaryViewSaved);
        this.date = findViewById(R.id.date);
        this.serialNumber = findViewById(R.id.serialNumber);
        this.deleteAll = findViewById(R.id.deleteAll);
        this.showReceiptByDate = findViewById(R.id.sortedByDate);

        saveReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptModel newReceipt = new ReceiptModel(-1, merchantName.getText().toString(),
                                                                merchantAddress.getText().toString(),
                                                                items.getText().toString(),
                                                                unitPrices.getText().toString(),
                                                                itemQuantities.getText().toString(),
                                                                Float.parseFloat(vatPrice.getText().toString()),
                                                                Float.parseFloat(vatablePrice.getText().toString()),
                                                                 receiptDate,
                                                                serialNumber.getText().toString());

                Database database = new Database(QRScannerResult.this);

                boolean success = database.addReceipt(newReceipt);
                Toast.makeText(QRScannerResult.this, "Success = " + success, Toast.LENGTH_SHORT).show();
            }

        });

        showReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(QRScannerResult.this);
                List<ReceiptModel> receipts = db.getAll();

                Toast.makeText(QRScannerResult.this, receipts.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        showReceiptByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(QRScannerResult.this);
                List<ReceiptModel> receipts = db.getAllByDateAscending();

                Toast.makeText(QRScannerResult.this, receipts.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(QRScannerResult.this);
                boolean result = db.deleteAll();

            }
        });
        //deleteAll.setEnabled(false);

        Intent fromScanner = getIntent();
        this.jsonString = fromScanner.getStringExtra("JSONString");
        try{
            jsonObject = new JSONObject(jsonString);
            Log.d("My App", jsonObject.toString());

            merchantName.setText(jsonObject.getString("merchantName"));
            merchantAddress.setText(jsonObject.getString("merchantAddress"));
            items.setText(jsonObject.getString("items"));
            unitPrices.setText(jsonObject.getString("unitPrices"));
            itemQuantities.setText(jsonObject.getString("itemQuantities"));
            vatPrice.setText(jsonObject.getString("vatPrice"));
            vatablePrice.setText(jsonObject.getString("vatablePrice"));
            date.setText(jsonObject.getString("date"));
            serialNumber.setText(jsonObject.getString("serialNumber"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try{
                this.receiptDate = dateFormat.parse(date.getText().toString());
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        }
        catch(Throwable t){
            Log.e("My App", "Could not parse malformed JSON: \"" + jsonString + "\"");
        }


    }
}