package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MenuItemView receiptsMv;
    private MenuItemView portMv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiptsMv = findViewById(R.id.receiptsMv);
        portMv = findViewById(R.id.portMv);

        receiptsMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReceiptListActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        portMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PortActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

}