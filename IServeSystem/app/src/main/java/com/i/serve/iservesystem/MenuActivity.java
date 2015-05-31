package com.i.serve.iservesystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.i.serve.iservesystem.adapter.MenuListViewAdapter;
import com.i.serve.iservesystem.service.MenuService;
import com.i.serve.iservesystem.uitls.Utils;

import java.util.List;

public class MenuActivity extends Activity implements View.OnClickListener {

    ListView lsvMnuList;
    Button btnMenuGoiMon;
    List<com.i.serve.iservesystem.dto.MenuItem> mnuItems;
    MenuListViewAdapter menuListViewAdapter;
    private int tableSelected = -1;
    TextView tvMnuTotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tableSelected = extras.getInt("tableId");
        }
        btnMenuGoiMon = (Button)findViewById(R.id.btnMenuGoiMon);
        lsvMnuList = (ListView)findViewById(R.id.lsvMnuList);

        tvMnuTotalCost = (TextView)findViewById(R.id.tvMnuTotalCost);

        lsvMnuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "setOnItemClickListener:     " + i, Toast.LENGTH_LONG).show();
                performSelect(view, i);
            }
        });

        btnMenuGoiMon.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mnuItems = MenuService.getMenuItems();
        for (com.i.serve.iservesystem.dto.MenuItem item: mnuItems){
            item.setQuantity(0);
        }
        Log.d("mnuItems.size(): ", mnuItems.size() + "");

        //Toast.makeText(this,  customers.size() + "", Toast.LENGTH_SHORT).show();
        menuListViewAdapter = new MenuListViewAdapter(this, mnuItems);
        menuListViewAdapter.setNotifyOnChange(true);
        lsvMnuList.setAdapter(menuListViewAdapter);
        Utils.setListViewHeightBasedOnChildren(lsvMnuList);

        updateTotalCost();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMenuGoiMon:
                if (!hasAnyItem()){
                    Toast.makeText(getApplicationContext(), getString(R.string.menu_goi_mon_tiep_tuc), Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(this, MenuConfirmActivity.class);
                    intent.putExtra("tableId", tableSelected);
                    startActivity(intent);
                }
                break;
        }
    }

    public void performSelect(View view, int position){
        final MenuListViewAdapter.ItemMenuListView itemMenuListView = (MenuListViewAdapter.ItemMenuListView) view;
        final Dialog d = new Dialog(MenuActivity.this);
        d.setTitle(getResources().getString(R.string.title_sl));
        d.setContentView(R.layout.number_picker_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(50);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        final int pos = position;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (np.getValue() > 0) {
                    itemMenuListView.etMnuItemQuantity.setText(String.valueOf(np.getValue()));
                    itemMenuListView.etMnuItemQuantity.setTextColor(Color.RED);
                    itemMenuListView.tvMnuName.setTextColor(Color.RED);


                }
                com.i.serve.iservesystem.dto.MenuItem item = mnuItems.get(pos);
                item.setQuantity(np.getValue());
                updateTotalCost();

                d.dismiss();
                //Toast.makeText(getApplicationContext(), "setOnItemClickListener:     " + pos, Toast.LENGTH_LONG).show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
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

    private boolean hasAnyItem() {
        for (com.i.serve.iservesystem.dto.MenuItem item: MenuService.getMenuItems()){
            if (item.getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }
}
