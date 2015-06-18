package com.i.serve.iservesystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.i.serve.iservesystem.dto.TableItem;
import com.i.serve.iservesystem.layout.WrapLayout;
import com.i.serve.iservesystem.service.TableService;

import java.util.List;
import java.util.Random;


public class TableActivity extends AbstractActivity {

    private static final int SIZE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        WrapLayout wl = (WrapLayout) this.findViewById(R.id.table_relative_layout);
        List<TableItem> tables = TableService.getTables();
        for(final TableItem table: tables){
            Button button = new Button(this);
            button.setBackgroundResource(R.drawable.round_button);
            button.setWidth(SIZE);
            button.setHeight(SIZE);
            String styledText = "<big> <font color='#008000'>"
                    + getResources().getString(R.string.table) + " " + table.getTableId() + "</font> </big>" + "<br />"
                    + "<small>" + getDescription(table.getStatus()) + "</small>";
            button.setText(Html.fromHtml(styledText));
            GradientDrawable myGrad = (GradientDrawable)button.getBackground();
            myGrad.setColor(table.getColor());
            wl.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), TableDetailActivity.class);
                intent.putExtra("tableId", table.getTableId());

                    //clear menu for one table


                startActivity(intent);
                }
            });
        }
    }

    private String getDescription(int status){
        switch (status){
            case 1:
                return getResources().getString(R.string.status_1);
            case 2:
                return getResources().getString(R.string.status_2);
            case 3:
                return getResources().getString(R.string.status_3);
            case 4:
                return getResources().getString(R.string.status_4);
            default:
                return "";
        }
    }
}
