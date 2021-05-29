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

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class QRScannerResult extends AppCompatActivity {

    private String jsonString;
    private JSONObject jsonObject;

    private TextView merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice, date, serialNumber, amountPaid;
    private Button saveReceipt;

    private Date receiptDate;

    private String rawItems, rawUnitPrices, rawQuantities, rawVat, rawVatable, rawAmountPaid;

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
        this.date = findViewById(R.id.date);
        this.serialNumber = findViewById(R.id.serialNumber);
        this.amountPaid = findViewById(R.id.amountPaid);


        saveReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptModel newReceipt = new ReceiptModel(-1, merchantName.getText().toString(),
                                                                merchantAddress.getText().toString(),
                                                                rawItems,
                                                                rawUnitPrices,
                                                                rawQuantities,
                                                                Float.parseFloat(rawVat),
                                                                Float.parseFloat(rawVatable),
                                                                receiptDate,
                                                                serialNumber.getText().toString(),
                                                                Float.parseFloat(rawAmountPaid));

                Database database = new Database(QRScannerResult.this);

                boolean success = database.addReceipt(newReceipt);
                Toast.makeText(QRScannerResult.this, "Receipt has been saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Intent fromScanner = getIntent();
        this.jsonString = fromScanner.getStringExtra("JSONString");
        try{

            jsonObject = new JSONObject(jsonString);
            Log.d("My App", jsonObject.toString());

            merchantName.setText(jsonObject.getString("merchantName"));
            merchantAddress.setText(jsonObject.getString("merchantAddress"));
            StringTokenizer stItem = new StringTokenizer(jsonObject.getString("items"), ",");
            rawItems = jsonObject.getString("items");
            ArrayList<String> itemList = new ArrayList<>();
            String itemText = "";
            String currToken;
            while (stItem.countTokens() > 0){
                currToken = stItem.nextToken();
                if (stItem.countTokens() == 0)
                    itemText =  itemText.concat(currToken);
                else
                    itemText =  itemText.concat(currToken+", ");
                itemList.add(currToken);
            }
            items.setText(itemText);

            StringTokenizer stUnitPrices = new StringTokenizer(jsonObject.getString("unitPrices"), ",");
            rawUnitPrices = jsonObject.getString("unitPrices");
            int i =0;
            String unitPricesText = "";
            while (stUnitPrices.countTokens() > 0){
                unitPricesText = unitPricesText.concat(itemList.get(i) +":  " + stUnitPrices.nextToken() + "\n");
                i++;
            }
            unitPrices.setText(unitPricesText);

            StringTokenizer stQuantities = new StringTokenizer(jsonObject.getString("itemQuantities"), ",");
            rawQuantities = jsonObject.getString("itemQuantities");
            i =0;
            String quantitiesText = "";
            while (stQuantities.countTokens() > 0){
                quantitiesText = quantitiesText.concat(itemList.get(i) +":  " + stQuantities.nextToken() + "\n");
                i++;
            }
            itemQuantities.setText(quantitiesText);
            vatPrice.setText("PHP: " + jsonObject.getString("vatPrice"));
            rawVat = jsonObject.getString("vatPrice");
            vatablePrice.setText("PHP: " + jsonObject.getString("vatablePrice"));
            rawVatable = jsonObject.getString("vatablePrice");
            date.setText(jsonObject.getString("date"));
            serialNumber.setText(jsonObject.getString("serialNumber"));
            amountPaid.setText("PHP: " + jsonObject.getString("amountPaid"));
            rawAmountPaid = jsonObject.getString("amountPaid");
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