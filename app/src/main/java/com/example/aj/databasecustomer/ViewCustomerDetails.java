package com.example.aj.databasecustomer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewCustomerDetails extends Activity {

    private int custid;
    private ListView ordersList;
    private EditText newname,newphone,newcity;

    private SQLiteDatabase db;

    private void fillOrdersListView() {
        String sql = "SELECT oid from orders where cid = " + custid;

        Cursor c = db.rawQuery(sql, null);

        //Orders id being filled
        ArrayList<String> orders = new ArrayList<String>();

        while (c.moveToNext())
            orders.add(c.getInt(0) + ""); //Converting int to string and adding to list

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, orders);

        ordersList.setAdapter(adapter);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_details);

        ordersList = (ListView) findViewById(R.id.ordersList);
        newname = (EditText) findViewById(R.id.updatedname);
        newphone = (EditText) findViewById(R.id.updatedphone);
        newcity = (EditText) findViewById(R.id.updatedCity);

        db = this.openOrCreateDatabase("CustomerDB", MODE_PRIVATE, null);

        //"customer(cid int,cname varchar,phone int,city varchar, primary key(cid));";
        //"orders(oid int,cid int,odate date,oamt float);";

        Intent i = getIntent();

        custid = i.getIntExtra("Selected", 0);

        ((TextView) findViewById(R.id.customerid)).setText(custid + "");    //Setting Cust ID

        ((TextView) findViewById(R.id.customername)).setText(i.getStringExtra("Name")); //Setting name

        String sql = "SELECT phone from customer where cid = " + custid;

        Cursor c = db.rawQuery(sql, null);

        c.moveToNext();

        ((TextView) findViewById(R.id.customerphone)).setText(c.getInt(0) + "");    //setting phone #

        fillOrdersListView();
    }

    public void deleteOrders(View v) {

        /*
        customer(cid int,cname varchar,phone int,city varchar, primary key(cid));
        orders(oid int,cid int,odate date,oamt float,primary key(oid),foreign key(cid) references customer(cid));
        */

        int len = ordersList.getCount(), i;
        SparseBooleanArray checked = ordersList.getCheckedItemPositions();

        for (i = 0; i < len; i++)
            if (checked.get(i))
                db.execSQL("DELETE FROM orders WHERE cid = " + custid + " and oid = "
                        + Integer.parseInt((String) ordersList.getItemAtPosition(i)));

        Toast.makeText(this, "Selected orders have been deleted!", Toast.LENGTH_SHORT).show();
        fillOrdersListView();
    }

    public void updateDetails(View v){

        String val = newname.getText()+"";
        if(!val.equals(""))
            db.execSQL("UPDATE customer SET cname = '"+val+"' WHERE cid = "+custid);

        val = newcity.getText()+"";
        if(!val.equals(""))
            db.execSQL("UPDATE customer SET city = '"+val+"' WHERE cid = "+custid);

        val = newphone.getText()+"";
        if(!val.equals(""))
            db.execSQL("UPDATE customer SET phone = "+Integer.parseInt(val)+" WHERE cid = "+custid);

        Toast.makeText(this, "Customer Details Updated!!", Toast.LENGTH_SHORT).show();
    }

    public void goBack(View v) {
        db.close();
        Intent i = new Intent(this, ViewCustomers.class);
        finish();
        startActivity(i);
    }
}
