package com.example.aj.databasecustomer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddOrder extends Activity {

    private EditText oid, cid, oamt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        oid = (EditText) findViewById(R.id.oid);
        cid = (EditText) findViewById(R.id.custid);
        oamt = (EditText) findViewById(R.id.oamt);
    }

    private void goback() {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }

    public void addOrder(View v) {

        //"CREATE TABLE IF NOT EXISTS orders(oid int,cid int,odate date,oamt float,primary key(oid),foreign key(cid) references customer(cid));
        SQLiteDatabase db = this.openOrCreateDatabase("CustomerDB", MODE_PRIVATE, null);

        ContentValues orderDetails = new ContentValues();

        orderDetails.put("oid", Integer.parseInt(oid.getText().toString()));
        orderDetails.put("cid", Integer.parseInt(cid.getText().toString()));
        orderDetails.put("odate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        orderDetails.put("oamt", Float.parseFloat(oamt.getText().toString()));


        try {
            db.insert("orders", null, orderDetails);
            Toast.makeText(this, "Insertion Success. New order added.", Toast.LENGTH_LONG).show();
            db.close();
            goback();
        } catch (Exception e) {
            Toast.makeText(this, "Insertion Error", Toast.LENGTH_LONG).show();
        }
        goback();
    }
}
