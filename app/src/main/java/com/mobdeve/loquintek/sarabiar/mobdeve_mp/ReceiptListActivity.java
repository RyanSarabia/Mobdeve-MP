package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

public class ReceiptListActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private SearchView receiptListSv;
    private RadioGroup filterRg;
    private RadioGroup sortRg;
    private boolean isMerchant;
    private boolean isAscending;

    private EditText popupTagEt;
    private Button popupDoneBtn;


    private RecyclerView receiptListItemsRv;
    private RecyclerView.LayoutManager manager;
    private ReceiptBarAdapter adapter;
    private ArrayList<Tag> tagList = new ArrayList<>();
    private List<ReceiptModel> receipts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);

        //(1) The RecyclerView is where the recycling will be done. In this case, this has already
        //    been declared in the activity_main.xml
        receiptListItemsRv = findViewById(R.id.receiptListItemsRv);

        //(2) The LinearLayoutManager is in-charge of the layout of the RecyclerView
        manager = new LinearLayoutManager(this);
        receiptListItemsRv.setLayoutManager(manager);

        //(3) The RecyclerView.Adapter manages the internal content of the RecyclerView
        adapter = new ReceiptBarAdapter(this);
        receiptListItemsRv.setAdapter(adapter);

        receiptListSv = findViewById(R.id.receiptListSv);
        filterRg = findViewById(R.id.filterRg);
        sortRg = findViewById(R.id.sortRg);
        isMerchant = true;
        isAscending = false;
        Database db = new Database(this);


        receipts = db.getAllByDateDescending();
        populateAdapter(receipts);

        ((RadioButton) filterRg.getChildAt(0)).setChecked(true);
        filterRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton.getText().toString().compareTo("Merchant") == 0) {
                    isMerchant = true;
                }

                else
                    isMerchant = false;
            }
        });

        ((RadioButton) sortRg.getChildAt(0)).setChecked(true);
        sortRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton.getText().toString().compareTo("Oldest") == 0) {
                    isAscending = true;
                }

                else
                    isAscending = false;
            }
        });

        receiptListSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                receipts.clear();
                receipts = db.searchFilteredReceipts(query, isMerchant, -1, -1, isAscending);

                populateAdapter(receipts);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (receiptListSv.getQuery().length() == 0) {
                    receipts.clear();
                    receipts = db.searchFilteredReceipts("", isMerchant, -1, -1, isAscending);
                    populateAdapter(receipts);
                }
                return false;
            }
        });

    }

    private void populateAdapter(List<ReceiptModel> receipts) {
        @SuppressLint("SimpleDateFormat") Format formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        adapter.clearList();

        for (int i=0; i < receipts.size(); i++ ) {
            ReceiptModel curReceipt = receipts.get(i);
            String merchant = curReceipt.getMerchantName();
            String date = formatter.format(curReceipt.getDate());
            String items = curReceipt.getItems();
            String serial = curReceipt.getSerialNumber();
            String tag = curReceipt.getTagAsString();
            double total = 0;

            StringTokenizer stPrices = new StringTokenizer(curReceipt.getUnitPrices(), ",");

            while (stPrices.countTokens() > 0){
                total += Float.parseFloat(stPrices.nextToken());
            }

            addItem(merchant, date, items, total, serial, tag);
        }
    }

    public void addItem(String storeName, String date, String items, double total, String serial, String tag){
        adapter.addItem(storeName, date, items, total, serial, tag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (data.getBooleanExtra("IS_DELETE", false)) {
                    int receipt_position = data.getIntExtra("POSITION", 0);
                    Log.d("RECEIPT LIST", "position: " + receipt_position);
                    this.receipts.remove(receipt_position);
                    adapter.removeItem(receipt_position);
                    adapter.notifyItemRemoved(receipt_position);
//                    adapter.notifyItemRangeChanged(receipt_position, receipts.size());
                }

                else if (data.getBooleanExtra("IS_UPDATE", false)) {
                    int receipt_position = data.getIntExtra("POSITION", 0);
                    String tagName = data.getStringExtra("NEW_TAG");
                    Log.d("RECEIPT LIST", "position: " + receipt_position);
                    adapter.setTag(receipt_position, tagName);
                }
            }
        }
    }
}