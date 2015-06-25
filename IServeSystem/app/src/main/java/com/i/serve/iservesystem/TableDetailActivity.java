package com.i.serve.iservesystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.i.serve.iservesystem.adapter.MenuListViewAdapter;
import com.i.serve.iservesystem.adapter.OrderListViewAdapter;
import com.i.serve.iservesystem.dto.TableDetailItem;
import com.i.serve.iservesystem.layout.WrapLayout;
import com.i.serve.iservesystem.service.TableDetailService;
import com.i.serve.iservesystem.uitls.Utils;

import java.util.ArrayList;
import java.util.List;


public class TableDetailActivity extends AbstractActivity implements View.OnClickListener {

    private ListView lsvOrderList;
    private ListView lsvWaitingList;
    private OrderListViewAdapter ordersAdapter;
    private OrderListViewAdapter waitingsAdapter;
    private Button btnOrder;
    private TextView txtSoKhach;
    private TextView lblPhucvu;
    private TextView lblDangcho;
    private int tableSelected = -1;

    private Button btnSave;
    private Button btnCancel;

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

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

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

    /**
     * onClick label cac mon da phuc vu handler
     */
    public void lblPhucvuClick(View v){
        lsvOrderList.setVisibility( lsvOrderList.isShown()
                ? View.GONE
        : View.VISIBLE );
    }

    /**
     * onClick label cac mon da phuc vu handler
     */
    public void lblDangchoClick(View v){
        lsvWaitingList.setVisibility( lsvWaitingList.isShown()
                ? View.GONE
                : View.VISIBLE );
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
        final com.i.serve.iservesystem.dto.TableDetailItem item = waitings.get(position);

        if (item.getStatus() == TableDetailItem.ORDER_STATE_CREATED ||
                item.getStatus() == TableDetailItem.ORDER_STATE_DONE) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                            //Toast.makeText(TableDetailActivity.this, "Yes Clicked", Toast.LENGTH_LONG).show();
                            if(TableDetailService.changeTableStatus(item)) {
                                if (item.getStatus() == TableDetailItem.ORDER_STATE_CREATED) {
                                    item.setStatus(TableDetailItem.ORDER_STATE_REMOVE);
                                } else if (item.getStatus() == TableDetailItem.ORDER_STATE_DONE) {
                                    item.setStatus(TableDetailItem.ORDER_STATE_DELIVERED);
                                }
                                refreshData();
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE: // No button clicked
                            // do nothing
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(getResources().getString(R.string.table_confirm_xacnhanthaydoi)).
                    setPositiveButton(getResources().getString(R.string.table_confirm_dung),
                            dialogClickListener).
                    setNegativeButton(getResources().
                            getString(R.string.table_confirm_sai), dialogClickListener).show();

        }
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
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnOrder:
                intent = new Intent(this, MenuActivity.class);
                intent.putExtra("tableId", tableSelected);
                startActivity(intent);
                break;
            case R.id.btnSave:
                TableDetailService.saveTable(tableSelected);
                break;
            case R.id.btnCancel:
                intent = new Intent(this, TableActivity.class);
                startActivity(intent);
                break;
        }
    }

}
