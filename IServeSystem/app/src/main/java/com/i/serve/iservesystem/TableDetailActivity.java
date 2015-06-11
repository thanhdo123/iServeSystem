package com.i.serve.iservesystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
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
    private TextView txtSoKhach;
    private TextView lblPhucvu;
    private TextView lblDangcho;
    private int tableSelected = -1;

    private List<TableDetailItem> orders = new ArrayList<>();
    private List<TableDetailItem> waitings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tableSelected = extras.getInt("tableId");
        }


        lsvOrderList = (ListView)findViewById(R.id.lsvOrderList);
        lsvWaitingList = (ListView)findViewById(R.id.lsvWaitingList);

        TextView lblHeader = (TextView) this.findViewById(R.id.lblHeader);
        lblHeader.setText(getResources().getString(R.string.table) + " " + tableSelected);

        refreshData();

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

        txtSoKhach = (TextView)findViewById(R.id.txtSoKhach);

        //count food
        lblPhucvu = (TextView)findViewById(R.id.lblPhucvu);
        lblDangcho = (TextView)findViewById(R.id.lblDangcho);

        lblPhucvu.setText(getResources().getString(R.string.title_phucvu) + " (" + orders.size() + ")");
        lblDangcho.setText(getResources().getString(R.string.title_dangcho) + " (" + waitings.size() + ")");

        lsvWaitingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "setOnItemClickListener:     " + i, Toast.LENGTH_LONG).show();
                performOrderItemSelect(view, i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshData();
    }

    private void refreshData(){

        orders.clear();
        waitings.clear();

        //get order details by table id
        List<TableDetailItem> foods= TableDetailService.getTableDetailById(tableSelected);
        for (TableDetailItem item : foods){
            if(item.getStatus() == TableDetailItem.ORDER_STATE_DELIVERED){
                orders.add(item);
            }else if(item.getStatus() == TableDetailItem.ORDER_STATE_DONE ||
                    item.getStatus() == TableDetailItem.ORDER_STATE_PROGRESS ||
                    item.getStatus() == TableDetailItem.ORDER_STATE_CREATED){
                waitings.add(item);
            }
        }

        ordersAdapter = new OrderListViewAdapter(this, orders);
        ordersAdapter.setNotifyOnChange(true);
        lsvOrderList.setAdapter(ordersAdapter);
        Utils.setListViewHeightBasedOnChildren(lsvOrderList);

        waitingsAdapter = new OrderListViewAdapter(this, waitings);
        waitingsAdapter.setNotifyOnChange(true);
        lsvWaitingList.setAdapter(waitingsAdapter);
        Utils.setListViewHeightBasedOnChildren(lsvWaitingList);
    }
    public void performOrderItemSelect(View view, int position){
        final OrderListViewAdapter.ItemOrderListView itemMenuListView = (OrderListViewAdapter.ItemOrderListView) view;
        com.i.serve.iservesystem.dto.TableDetailItem item = waitings.get(position);
        if(TableDetailService.changeTableStatus(item)) {
            if (item.getStatus() == TableDetailItem.ORDER_STATE_CREATED) {
                item.setStatus(TableDetailItem.ORDER_STATE_REMOVE);
            } else if (item.getStatus() == TableDetailItem.ORDER_STATE_DONE) {
                item.setStatus(TableDetailItem.ORDER_STATE_DELIVERED);
            }
            refreshData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table_detail, menu);
        return true;
    }

    public void performSelect(View view){
        final Dialog d = new Dialog(TableDetailActivity.this);
        d.setTitle(getResources().getString(R.string.title_sokhach));
        d.setContentView(R.layout.number_picker_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(50);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                txtSoKhach.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
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
                intent.putExtra("tableId", tableSelected);
                startActivity(intent);
                break;
        }
    }

}
