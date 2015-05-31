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

import com.i.serve.iservesystem.adapter.ConfirmMenuListViewAdapter;
import com.i.serve.iservesystem.adapter.MenuListViewAdapter;
import com.i.serve.iservesystem.service.MenuService;
import com.i.serve.iservesystem.uitls.Utils;

import java.util.List;


public class MenuConfirmActivity extends Activity implements View.OnClickListener {

    ListView lsvConfirmMnuList;
    Button btnConfirmMenuXacNhan;
    List<com.i.serve.iservesystem.dto.MenuItem> chosenMnuItems;
    ConfirmMenuListViewAdapter confirmMenuListViewAdapter;
    private int tableSelected = -1;

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

        btnConfirmMenuXacNhan.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chosenMnuItems = MenuService.getChosenMenuItems();

        Log.d("chosenMnuItems.size(): ", chosenMnuItems.size() + "");

        //Toast.makeText(this,  customers.size() + "", Toast.LENGTH_SHORT).show();
        confirmMenuListViewAdapter = new ConfirmMenuListViewAdapter(this, chosenMnuItems);
        confirmMenuListViewAdapter.setNotifyOnChange(true);
        lsvConfirmMnuList.setAdapter(confirmMenuListViewAdapter);
        Utils.setListViewHeightBasedOnChildren(lsvConfirmMnuList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_confirm, menu);
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
            case R.id.btnConfirmMenuXacNhan:
                Intent intent = new Intent(this, TableDetailActivity.class);
                intent.putExtra("tableId", tableSelected);
                startActivity(intent);
                break;
        }
    }
}
