package com.i.serve.iservesystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.i.serve.iservesystem.adapter.ConfirmMenuListViewAdapter;
import com.i.serve.iservesystem.adapter.MenuListViewAdapter;
import com.i.serve.iservesystem.service.MenuService;
import com.i.serve.iservesystem.uitls.Utils;

import java.util.ArrayList;
import java.util.List;


public class MenuConfirmActivity extends AbstractActivity implements View.OnClickListener {

    ListView lsvConfirmMnuList;
    Button btnConfirmMenuXacNhan;
    List<com.i.serve.iservesystem.dto.MenuItem> chosenMnuItems;
    ConfirmMenuListViewAdapter confirmMenuListViewAdapter;
    private int tableSelected = -1;
    TextView tvMnuTotalCost;
    TextView tvMnuConfirmBan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_confirm);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tableSelected = extras.getInt("tableId");
        }

        btnConfirmMenuXacNhan = (Button)findViewById(R.id.btnConfirmMenuXacNhan);
        lsvConfirmMnuList = (ListView)findViewById(R.id.lsvConfirmMnuList);
        tvMnuTotalCost = (TextView)findViewById(R.id.tvMnuTotalCost);
        tvMnuConfirmBan = (TextView)findViewById(R.id.tvMnuConfirmBan);
        btnConfirmMenuXacNhan.setOnClickListener(this);

        tvMnuConfirmBan.setText(String.valueOf(tableSelected));
    }

    @Override
    protected void onResume() {
        super.onResume();
        chosenMnuItems = new ArrayList<>();

        for (com.i.serve.iservesystem.dto.MenuItem item: MenuService.getMenuItems()){
            if (item.getQuantity() > 0) {
                chosenMnuItems.add(item);
            }
        }


        Log.d("chosenMnuItems.size(): ", chosenMnuItems.size() + "");

        //Toast.makeText(this,  customers.size() + "", Toast.LENGTH_SHORT).show();
        confirmMenuListViewAdapter = new ConfirmMenuListViewAdapter(this, chosenMnuItems);
        confirmMenuListViewAdapter.setNotifyOnChange(true);
        lsvConfirmMnuList.setAdapter(confirmMenuListViewAdapter);
        Utils.setListViewHeightBasedOnChildren(lsvConfirmMnuList);
        updateTotalCost();


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirmMenuXacNhan:
                Intent intent = new Intent(this, TableDetailActivity.class);
                intent.putExtra("tableId", tableSelected);
                startActivity(intent);
                break;
        }
    }

    private void updateTotalCost() {
        double totalCost = 0;
        for (com.i.serve.iservesystem.dto.MenuItem item: MenuService.getMenuItems()){
            if (item.getQuantity() > 0) {
                totalCost += item.getQuantity() * item.getPrice();
            }
        }

        //display total cost
        tvMnuTotalCost.setText(String.format("%,.0f", totalCost));
    }
}
