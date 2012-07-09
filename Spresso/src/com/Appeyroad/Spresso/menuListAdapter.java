package com.Appeyroad.Spresso;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class menuListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<String[]> menus=new ArrayList<String[]>();
	Typeface fanta;
	
	public menuListAdapter(Context context, ArrayList<String[]> manymenu){
		this.context=context;
		menus.addAll(manymenu);
		fanta=Typeface.createFromAsset(this.context.getAssets(), "fonts/1.ttf");
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return menus.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=convertView;
		if(v==null){
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=inflater.inflate(R.layout.menuname_cell, null);
		}
		TextView textname=(TextView)v.findViewById(R.id.textView1);
		textname.setTypeface(fanta);
		textname.setTextSize(17);
		if(textname!=null) textname.setText(menus.get(position)[0]);
		TextView textprice=(TextView)v.findViewById(R.id.textView2);
		textprice.setTypeface(fanta);
		textprice.setTextSize(17);
		if(textprice!=null) textprice.setText(menus.get(position)[1]);
		return v;
	}

}
