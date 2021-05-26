package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class ReceiptListActivity extends AppCompatActivity {

    private RecyclerView receiptListItemsRv;
    private RecyclerView.LayoutManager manager;
    private ReceiptBarAdapter adapter;
    private ArrayList<Tag> tagList = new ArrayList<>();

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
        adapter = new ReceiptBarAdapter();
        receiptListItemsRv.setAdapter(adapter);

        addItem("Hello", "June 12, 2020", "Pagkain", 10.00);
    }

    public void addItem(String storeName, String date, String items, double total ){
        adapter.addItem(storeName, date, items, total);
    }


}