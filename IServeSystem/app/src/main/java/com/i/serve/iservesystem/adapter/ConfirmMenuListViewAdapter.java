package com.i.serve.iservesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.i.serve.iservesystem.R;
import com.i.serve.iservesystem.dto.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * ConfirmMenuListViewAdapter is used to manage items in the list
 */
public class ConfirmMenuListViewAdapter extends ArrayAdapter<MenuItem> {

	ArrayList<MenuItem> objects;
	Context context;

	public ConfirmMenuListViewAdapter(Context context, List<MenuItem> objects) {
		super(context, R.layout.confirm_item_menu_listview, objects);
		this.objects = (ArrayList<MenuItem>) objects;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null)
			view = new ChosenItemMenuListView(context);

		final MenuItem c = objects.get(position);
		if (c != null) {
			TextView tvConfirmMnuName = ((ChosenItemMenuListView) view).tvConfirmMnuName;
            TextView tvConfirmMnuPrice = ((ChosenItemMenuListView) view).tvConfirmMnuPrice;
			EditText etConfirmMnuItemQuantity = ((ChosenItemMenuListView) view).etConfirmMnuItemQuantity;

			tvConfirmMnuName.setText(c.getName());
			tvConfirmMnuPrice.setText(String.format("%,.0f", c.getPrice()));
			etConfirmMnuItemQuantity.setText("" + c.getQuantity());
		}

		return view;
	}


	private class ChosenItemMenuListView extends LinearLayout {

		TextView tvConfirmMnuName;
        TextView tvConfirmMnuPrice;
        EditText etConfirmMnuItemQuantity;
		public ChosenItemMenuListView(Context context) {
			super(context);
			LayoutInflater listItem = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listItem.inflate(R.layout.confirm_item_menu_listview, this, true);
			this.tvConfirmMnuName =(TextView) findViewById(R.id.tvConfirmMnuName);
			this.tvConfirmMnuPrice = (TextView) findViewById(R.id.tvConfirmMnuPrice);
            this.etConfirmMnuItemQuantity = (EditText) findViewById(R.id.etConfirmMnuItemQuantity);
		}
	}
}
