package com.i.serve.iservesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.i.serve.iservesystem.R;
import com.i.serve.iservesystem.dto.MenuItem;
import com.i.serve.iservesystem.dto.TableDetailItem;

import java.util.ArrayList;
import java.util.List;
/**
 * CustomerListViewAdapter is used to manage items in the list
 */
public class OrderListViewAdapter extends ArrayAdapter<TableDetailItem> {

    ArrayList<TableDetailItem> objects;
    Context context;

    public OrderListViewAdapter(Context context, List<TableDetailItem> objects) {
        super(context, R.layout.item_menu_listview, objects);
        this.objects = (ArrayList<TableDetailItem>) objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null)
            view = new ItemOrderListView(context);

        final TableDetailItem c = objects.get(position);
        if (c != null) {
            TextView txtFoodName = ((ItemOrderListView) view).txtFoodName;
            TextView txtFoodPrice = ((ItemOrderListView) view).txtFoodPrice;
            TextView txtQuantity = ((ItemOrderListView) view).txtQuantity;

            txtFoodName.setText(c.getFoodName());
            txtFoodPrice.setText(String.valueOf(c.getPrice()));
            txtQuantity.setText(String.valueOf(c.getQuantity()));

        }
        return view;
    }

    private class ItemOrderListView extends LinearLayout {

        TextView txtFoodName;
        TextView txtFoodPrice;
        TextView txtQuantity;

        public ItemOrderListView(Context context) {
            super(context);
            LayoutInflater listItem = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItem.inflate(R.layout.item_menu_listview_1, this, true);
            this.txtFoodName =(TextView) findViewById(R.id.detaill_name);
            this.txtFoodPrice = (TextView) findViewById(R.id.detaill_price);
            this.txtQuantity = (TextView) findViewById(R.id.detaill_quantity);
        }
    }
}
