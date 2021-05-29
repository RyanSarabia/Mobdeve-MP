package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private MenuItemView receiptsMv;
    private MenuItemView scanMv;
    private MenuItemView tagMv;

    private Button QRButton;
    private Button exportButtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiptsMv = findViewById(R.id.receiptsMv);
        scanMv = findViewById(R.id.scanMv);
        tagMv = findViewById(R.id.tagMv);


        Database db = new Database(this);
        db.deleteAll();
        db.addDummyReceipt();

        receiptsMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReceiptListActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        scanMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToScanner = new Intent(getApplicationContext(), QRScanner.class);
                MainActivity.this.startActivity(goToScanner);
            }
        });

        tagMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TagActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


//        exportButtton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Database database = new Database(MainActivity.this);
//                database.close();
//            }
//        });

    }

}