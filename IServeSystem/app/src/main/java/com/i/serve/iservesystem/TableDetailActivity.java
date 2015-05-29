package com.i.serve.iservesystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.i.serve.iservesystem.adapter.MenuListViewAdapter;
import com.i.serve.iservesystem.adapter.OrderListViewAdapter;
import com.i.serve.iservesystem.dto.TableDetailItem;
import com.i.serve.iservesystem.layout.WrapLayout;
import com.i.serve.iservesystem.service.TableDetailService;
import com.i.serve.iservesystem.uitls.Utils;

import java.util.ArrayList;
import java.util.List;


public class TableDetailActivity extends Activity implements View.OnClickListener {

    private ListView lsvOrderList;
    private ListView lsvWaitingList;
    private OrderListViewAdapter ordersAdapter;
    private OrderListViewAdapter waitingsAdapter;
    private Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);
        Bundle extras = getIntent().getExtras();
        int value = -1;
        if (extras != null) {
            value = extras.getInt("tableId");
        }
        List<TableDetailItem> orders = new ArrayList<>();
        List<TableDetailItem> waitings = new ArrayList<>();
        //get order details by table id
        List<TableDetailItem> foods= TableDetailService.getTableDetailById(value);
        for (TableDetailItem item : foods){
            if(item.getStatus() == 1){
                orders.add(item);
            }else if(item.getStatus() == 2){
                waitings.add(item);
            }

        }

        TextView lblHeader = (TextView) this.findViewById(R.id.lblHeader);
        lblHeader.setText(getResources().getString(R.string.table) + " " + value);

        lsvOrderList = (ListView)findViewById(R.id.lsvOrderList);
        lsvWaitingList = (ListView)findViewById(R.id.lsvWaitingList);

        ordersAdapter = new OrderListViewAdapter(this, orders);
        ordersAdapter.setNotifyOnChange(true);
        lsvOrderList.setAdapter(ordersAdapter);
        Utils.setListViewHeightBasedOnChildren(lsvOrderList);

        waitingsAdapter = new OrderListViewAdapter(this, waitings);
        waitingsAdapter.setNotifyOnChange(true);
        lsvWaitingList.setAdapter(waitingsAdapter);
        Utils.setListViewHeightBasedOnChildren(lsvWaitingList);

        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnOrder:
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;
        }
    }

}
