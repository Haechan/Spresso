package com.Appeyroad.Spresso;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class cafeListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<theCafe> cafes=new ArrayList<theCafe>();
	long now;
	int img_open,img_close;
	Typeface cre, fanta;
	
	public cafeListAdapter(Context context, long nowtime){
		this.context=context;
		this.now=nowtime;
		img_open=R.drawable.open;
		img_close=R.drawable.closed;
		cre=Typeface.createFromAsset(this.context.getAssets(), "fonts/4.ttf");
		fanta=Typeface.createFromAsset(this.context.getAssets(), "fonts/1.ttf");
	}
	
	public void addCafe(theCafe cafe){
		cafes.add(cafe);
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return cafes.size();
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
			v=inflater.inflate(R.layout.cafename_cell, null);
		}
		TextView text=(TextView)v.findViewById(R.id.text);
		ImageView image=(ImageView)v.findViewById(R.id.imageView1);
		//if cell is header(section name)
		if(cafes.get(position).index==-1){
			text.setTypeface(cre);//this is font setter
			text.setTextSize(25);
			if(text!=null){
				if(position==0) text.setText(cafes.get(position).name);
				else text.setText("\n"+cafes.get(position).name);
			}
			if(image!=null) image.setVisibility(View.GONE);
		}
		//if cell is cafe
		else{
			text.setTypeface(fanta);//this is font setter
			text.setTextSize(18);
			if(text!=null) text.setText("   "+cafes.get(position).name);
			if(image!=null){
				image.setVisibility(View.VISIBLE);
				if(cafes.get(position).isOpen) image.setImageResource(img_open);
				else image.setImageResource(img_close);
				//else image.setImageResource(R.drawable.closed);
				//setImageResource가 나은가?
			}
		}
		v.setTag(""+cafes.get(position).index);
		return v;
	}
}