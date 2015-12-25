package com.example.aj.databasecustomer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCustomer extends Activity {

    private EditText cid, name, phone, city;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        cid = (EditText) findViewById(R.id.custid);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        city = (EditText) findViewById(R.id.city);
    }

    private void goback() {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }

    public void addUser(View v) {
        //db.execSQL("CREATE TABLE IF NOT EXISTS customer(cid int,cname varchar,phone int,city varchar, primary key(cid));");
        SQLiteDatabase db = this.openOrCreateDatabase("CustomerDB", MODE_PRIVATE, null);

        ContentValues customerDetails = new ContentValues();

        customerDetails.put("cid", Integer.parseInt(cid.getText().toString()));
        customerDetails.put("cname", name.getText().toString());
        customerDetails.put("phone", Integer.parseInt(phone.getText().toString()));
        customerDetails.put("city", city.getText().toString());

        try {
            db.insert("customer", null, customerDetails);
            Toast.makeText(this, "Insertion Success. New Customer added.", Toast.LENGTH_LONG).show();
            db.close();
            goback();
        } catch (Exception e) {
            Toast.makeText(this, "Insertion Error", Toast.LENGTH_LONG).show();
        }
    }
}
