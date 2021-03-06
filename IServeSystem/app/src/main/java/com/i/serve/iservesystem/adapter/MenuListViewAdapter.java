package com.i.serve.iservesystem.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.i.serve.iservesystem.R;
import com.i.serve.iservesystem.dto.MenuItem;
import com.i.serve.iservesystem.uitls.Utils;

import java.util.ArrayList;
import java.util.List;
/**
 * CustomerListViewAdapter is used to manage items in the list
 */
public class MenuListViewAdapter extends ArrayAdapter<MenuItem> {

	ArrayList<MenuItem> objects;
	Context context;

	public MenuListViewAdapter(Context context, List<MenuItem> objects) {
		super(context, R.layout.item_menu_listview, objects);
		this.objects = (ArrayList<MenuItem>) objects;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null)
			view = new ItemMenuListView(context);

		final MenuItem c = objects.get(position);
		if (c != null) {
			TextView tvMnuName = ((ItemMenuListView) view).tvMnuName;
			ImageView ivMnuImage = ((ItemMenuListView) view).ivMnuImage;
            TextView tvMnuDesc = ((ItemMenuListView) view).tvMnuDesc;
            TextView tvMnuPrice = ((ItemMenuListView) view).tvMnuPrice;
			TextView etMnuItemQuantity = ((ItemMenuListView) view).etMnuItemQuantity;
			tvMnuName.setText(c.getName());
			tvMnuName.setTextColor(Color.BLUE);
			switch (c.getId()){
				case 1:
					ivMnuImage.setImageResource(R.drawable.img1);
					break;
				case 2:
					ivMnuImage.setImageResource(R.drawable.img2);
					break;
				case 3:
					ivMnuImage.setImageResource(R.drawable.img3);
					break;
				case 4:
					ivMnuImage.setImageResource(R.drawable.img4);
					break;
				case 5:
					ivMnuImage.setImageResource(R.drawable.img5);
					break;
				default:
					ivMnuImage.setImageResource(R.drawable.img6);
					break;
			}
			tvMnuDesc.setText(c.getDescription());
			tvMnuPrice.setText(Utils.formatPrice(c.getPrice()));
			etMnuItemQuantity.setText("");
		}
		return view;
	}

	public class ItemMenuListView extends LinearLayout {

		public TextView tvMnuName;
		ImageView ivMnuImage;
        TextView tvMnuDesc;
        TextView tvMnuPrice;
		public TextView etMnuItemQuantity;
		public ItemMenuListView(Context context) {
			super(context);
			LayoutInflater listItem = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listItem.inflate(R.layout.item_menu_listview, this, true);
			this.tvMnuName =(TextView) findViewById(R.id.tvMnuName);
			this.ivMnuImage = (ImageView) findViewById(R.id.ivMnuImage);

            this.tvMnuDesc = (TextView) findViewById(R.id.tvMnuDesc);
            this.tvMnuPrice = (TextView) findViewById(R.id.tvMnuPrice);
            this.etMnuItemQuantity = (TextView) findViewById(R.id.etMnuItemQuantity);
		}
	}
}
