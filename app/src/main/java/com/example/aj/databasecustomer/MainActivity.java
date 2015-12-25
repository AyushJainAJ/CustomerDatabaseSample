package com.example.aj.databasecustomer;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private SQLiteDatabase db;
    private EditText custID;

    protected void onCreate(Bundle savedInstanceState) throws SQLiteException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        custID = (EditText) findViewById(R.id.editText);

        db = this.openOrCreateDatabase("CustomerDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customer(cid int,cname varchar,phone int,city varchar, primary key(cid));");
        db.execSQL("CREATE TABLE IF NOT EXISTS orders(oid int,cid int,odate date,oamt float,primary key(oid),foreign key(cid) references customer(cid));");
    }

    private void common(Intent i){
        db.close();
        finish();
        startActivity(i);
    }
    public void addUser(View v) {
        Intent i = new Intent(this, AddCustomer.class);
        common(i);
    }

    public void addOrder(View v) {
        Intent i = new Intent(this, AddOrder.class);
        common(i);
    }

    public void viewCustomers(View v) {
        Intent i = new Intent(this, ViewCustomers.class);
        common(i);
    }

    public void deleteUser(View v){
        int id = Integer.parseInt(custID.getText() + "");
        db.execSQL("DELETE from orders where cid = "+id);
        db.execSQL("DELETE from customer where cid = "+id);
        Toast.makeText(this,"Customer with ID = "+id+" has been deleted",Toast.LENGTH_SHORT).show();
    }
}