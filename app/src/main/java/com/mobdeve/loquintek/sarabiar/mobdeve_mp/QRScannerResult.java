package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

public class QRScannerResult extends AppCompatActivity {

    private String jsonString;
    private JSONObject jsonObject;

    private TextView merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice;

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
        }
        catch(Throwable t){
            Log.e("My App", "Could not parse malformed JSON: \"" + jsonString + "\"");
        }


    }
}