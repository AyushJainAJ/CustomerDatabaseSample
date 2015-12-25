package com.example.aj.databasecustomer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ViewCustomers extends Activity {

    private ListView customerList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers);

        customerList = (ListView) findViewById(R.id.customerList);
        fillListView();
    }

    private void fillListView() {
        SQLiteDatabase db = this.openOrCreateDatabase("CustomerDB", MODE_PRIVATE, null);

//        String column[] = {"cname"};

        final ArrayList<String> customerNames = new ArrayList<String>();  //list stores names of all customers
        final ArrayList<Integer> customerIds = new ArrayList<Integer>();  //list stores id of all customers


        Cursor c = db.rawQuery("SELECT cid,cname from Customer",null);

        //Traversing through cursor and adding the names and IDs to the lists
        while (c.moveToNext()) {
            customerIds.add(c.getInt(0));
            customerNames.add(c.getString(1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customerNames);

        customerList.setAdapter(adapter);

        //Setting up an on item click listener
        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Event triggered when any item of listview is clicked
            //pos is position of item which is clicked
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long l) {

                Intent i = new Intent(ViewCustomers.this,ViewCustomerDetails.class);
                //Here a simple "this" will not work as inside a function

                i.putExtra("Selected",customerIds.get(pos));
                i.putExtra("Name",customerNames.get(pos));

                finish();
                startActivity(i);
            }
        });
    }

    public void goHome(View v) {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        startActivity(i);
    }
}