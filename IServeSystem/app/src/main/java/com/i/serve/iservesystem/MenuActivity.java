package com.i.serve.iservesystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.i.serve.iservesystem.adapter.MenuListViewAdapter;
import com.i.serve.iservesystem.service.MenuService;
import com.i.serve.iservesystem.uitls.Utils;

import java.util.List;

public class MenuActivity extends Activity implements View.OnClickListener {

    ListView lsvMnuList;
    Button btnMenuGoiMon;
    List<com.i.serve.iservesystem.dto.MenuItem> mnuItems;
    MenuListViewAdapter menuListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnMenuGoiMon = (Button)findViewById(R.id.btnMenuGoiMon);
        lsvMnuList = (ListView)findViewById(R.id.lsvMnuList);

        btnMenuGoiMon.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mnuItems = MenuService.getMenuItems();

        Log.d("mnuItems.size(): ", mnuItems.size() + "");

        //Toast.makeText(this,  customers.size() + "", Toast.LENGTH_SHORT).show();
        menuListViewAdapter = new MenuListViewAdapter(this, mnuItems);
        menuListViewAdapter.setNotifyOnChange(true);
        lsvMnuList.setAdapter(menuListViewAdapter);
        Utils.setListViewHeightBasedOnChildren(lsvMnuList);
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
                Intent intent = new Intent(this, MenuConfirmActivity.class);
                startActivity(intent);
                break;
        }
    }
}
