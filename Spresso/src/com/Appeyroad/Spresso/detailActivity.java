package com.Appeyroad.Spresso;

import org.xmlpull.v1.XmlPullParser;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class detailActivity extends Activity {
	SQLiteDatabase db;
	Typeface cre,fanta;
	
	//Runtime rt=Runtime.getRuntime();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"start detail");
        
        cre=Typeface.createFromAsset(this.getAssets(), "fonts/4.ttf");
		fanta=Typeface.createFromAsset(this.getAssets(), "fonts/1.ttf");
        XmlPullParser parser=getResources().getXml(R.xml.tests);
        final theCafe thisCafe= new theCafe(parser,getIntent().getIntExtra("selected", 12));//is there anything other than 12?
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"load data");
		
        TextView nametxt=(TextView) this.findViewById(R.id.textView1);
        nametxt.setTypeface(cre);
        nametxt.setTextSize(32);
        nametxt.setText(thisCafe.name);
        nametxt=null;
        
        TextView locationtxt=(TextView) this.findViewById(R.id.textView2);
        locationtxt.setTypeface(fanta);
        locationtxt.setTextSize(17);
        locationtxt.setText(thisCafe.location);
        locationtxt=null;
        
        TextView phonetxt=(TextView) this.findViewById(R.id.textView3);
        phonetxt.setTypeface(fanta);
        phonetxt.setTextSize(17);
        phonetxt.setText(thisCafe.phone);
        phonetxt=null;
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"simple text");
        
        //arrange time data
        TextView timetxt=(TextView) this.findViewById(R.id.textView4);
        timetxt.setTypeface(fanta);
        timetxt.setTextSize(16);
        String[] timestr=new String[2], tempstr=new String[2];
        if(thisCafe.time.get("monthu")==null) tempstr[0]="CLOSED";
        else tempstr[0]=thisCafe.time.get("monthu")[0].replaceFirst(":00", "")+" - "+thisCafe.time.get("monthu")[1].replaceFirst(":00", "");
        if(thisCafe.time.get("fri")==null) tempstr[1]="CLOSED";
        else tempstr[1]=thisCafe.time.get("fri")[0].replaceFirst(":00", "")+" - "+thisCafe.time.get("fri")[1].replaceFirst(":00", "");
        if(tempstr[0].equals(tempstr[1])) timestr[0]="\n[MON-FRI] "+tempstr[0];
        else timestr[0]="\n[MON-THU] "+tempstr[0]+"\n[FRI] "+tempstr[1];
        tempstr=new String[2];
        if(thisCafe.time.get("sat")==null) tempstr[0]="CLOSED";
        else tempstr[0]=thisCafe.time.get("sat")[0].replaceFirst(":00", "")+" - "+thisCafe.time.get("sat")[1].replaceFirst(":00", "");
        if(thisCafe.time.get("sun")==null) tempstr[1]="CLOSED";
        else tempstr[1]=thisCafe.time.get("sun")[0].replaceFirst(":00", "")+" - "+thisCafe.time.get("sun")[1].replaceFirst(":00", "");
        if(tempstr[0].equals(tempstr[1])) timestr[1]="\n[WEEKEND] "+tempstr[0];
        else timestr[1]="\n[SAT] "+tempstr[0]+"\n[SUN] "+tempstr[1];
        timetxt.setText(timestr[0]+timestr[1]);
        tempstr=null;
        timestr=null;
        timetxt=null;
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"time text");
        
        TextView etctxt=(TextView) this.findViewById(R.id.textView5);
        etctxt.setTypeface(fanta);
        etctxt.setTextSize(15);
        String[] etcstr=new String[3];
        if(thisCafe.dclist==null) etcstr[0]="[할인사항] ";
        else etcstr[0]="[할인사항] "+thisCafe.dclist;
        if(thisCafe.wifilist==null) etcstr[1]="\n\n[wifi] ";
        else etcstr[1]="\n\n[wifi] "+thisCafe.wifilist;
        if(thisCafe.etcnote==null) etcstr[2]="\n\n[특이사항] ";
        else etcstr[2]="\n\n[특이사항] "+thisCafe.etcnote;
        etctxt.setText(etcstr[0]+etcstr[1]+etcstr[2]+"\n ");
        etcstr=null;
        etctxt=null;
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"etc text");
        
        ImageView wifiimg=(ImageView) this.findViewById(R.id.imageView1);
        if(thisCafe.wifi) wifiimg.setImageResource(R.drawable.wifi);
        wifiimg=null;
        
        ImageView seatimg=(ImageView) this.findViewById(R.id.imageView2);
        if(thisCafe.seat) seatimg.setImageResource(R.drawable.seat);
        wifiimg=null;
        
        ImageView consentimg=(ImageView) this.findViewById(R.id.imageView3);
        if(thisCafe.consent) consentimg.setImageResource(R.drawable.plug);
        consentimg=null;
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"info image");
        
        ImageView mapimg=(ImageView) this.findViewById(R.id.imageView4);
        setMapImage(mapimg,thisCafe.index);
        //mapimg.setImageResource(getResources().getIdentifier("com.Appeyroad.Spresso:id/map"+thisCafe.index, null, null));
        //above is code that i believed can replace stupid method 'setMapImage', but it failed
        mapimg=null;
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"map image");
        
        //favorite check button
        final CheckBox favoritecheck=(CheckBox) this.findViewById(R.id.checkBox6);
        SQLiteOpenHelper dbHelper=new DBHelper(this);
        db=dbHelper.getWritableDatabase();//is final ok?
        Cursor favor=db.query("favorites", null, null, null, null, null, null);
        favor.moveToPosition(getIntent().getIntExtra("selected", 12));
        if(favor.getInt(favor.getColumnIndex("favorite"))==1) favoritecheck.setChecked(true);
        else if(favor.getInt(favor.getColumnIndex("favorite"))==0) favoritecheck.setChecked(false);
        favor.close();
        favoritecheck.setOnClickListener(new View.OnClickListener(){	
        	public void onClick(View v){ 
        		ContentValues newValues=new ContentValues();
        		if(favoritecheck.isChecked()) newValues.put("favorite", 1);
        		else newValues.put("favorite", 0);
        		String[] whereArgs={""+(getIntent().getIntExtra("selected", 12))};//maybe gg
        		db.update("favorites", newValues, "number = ?", whereArgs);
        	}
        });
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"set favorite");
        
        ImageButton homebtn = (ImageButton)findViewById(R.id.imageButton1);
        homebtn.setOnClickListener(new View.OnClickListener(){	
        	public void onClick(View v){ 
        		db.close();
        		finish();
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        	}
        });
        homebtn=null;
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"homebtn(start menu)");
        
        //code that manages menu and etcview
        //i think this one is pretty heavy
        final RadioGroup listtab=(RadioGroup)findViewById(R.id.radioGroup1);
        final ListView list1=(ListView)findViewById(R.id.listView1);
        final ListView list2=(ListView)findViewById(R.id.listView2);
        final ListView list3=(ListView)findViewById(R.id.listView3);
        final View etcview=findViewById(R.id.scrollView1);
    	TextView footertxt=new TextView(this);
     	footertxt.setText(thisCafe.note);
     	footertxt.setTypeface(cre);
     	footertxt.setTextSize(18);
    	list1.addFooterView(footertxt);
    	list2.addFooterView(footertxt);
    	list3.addFooterView(footertxt);
    	footertxt=null;
    	list1.setAdapter(new menuListAdapter(this, thisCafe.menu.get("coffee")));
    	list2.setAdapter(new menuListAdapter(this, thisCafe.menu.get("non-coffee")));
    	list3.setAdapter(new menuListAdapter(this, thisCafe.menu.get("side")));
        list2.setVisibility(View.GONE);
        list3.setVisibility(View.GONE);
    	etcview.setVisibility(View.GONE);
    	list1.setVisibility(View.VISIBLE);
    	
    	//System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"init menu end");
    	
        listtab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rgroup, int id) {
            	
            	//System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"tab action");
            	
            	RadioButton rButton = (RadioButton) findViewById(id);
                int n = Integer.parseInt(rButton.getTag().toString());
                
                //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"start exchange");
                
                if(n==0){
                    list2.setVisibility(View.GONE);
                    list3.setVisibility(View.GONE);
                	etcview.setVisibility(View.GONE);
                	list1.setVisibility(View.VISIBLE);
                }
                else if(n==1){
                	list1.setVisibility(View.GONE);
                    list3.setVisibility(View.GONE);
                	etcview.setVisibility(View.GONE);
                	list2.setVisibility(View.VISIBLE);
                }
                else if(n==2){
                	list1.setVisibility(View.GONE);
                    list2.setVisibility(View.GONE);
                	etcview.setVisibility(View.GONE);
                	list3.setVisibility(View.VISIBLE);
                }
                else if(n==3){
                	list1.setVisibility(View.GONE);
                    list2.setVisibility(View.GONE);
                    list3.setVisibility(View.GONE);
                	etcview.setVisibility(View.VISIBLE);
                }
                
                //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"end exchange");
            }
        });
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"set menu tab");
      
        //scale adjust
        DisplayMetrics dim=getResources().getDisplayMetrics();
        LinearLayout.LayoutParams para;
        
        View view1=findViewById(R.id.view1);
        para=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, sincScale(16, dim));
        para.setMargins(0, 0, 0, 0);
        view1.setLayoutParams(para);
        
        LinearLayout linearLayout2=(LinearLayout) findViewById(R.id.linearLayout2);
        para=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, sincScale(40, dim));
        para.setMargins(sincScale(5, dim), 0, 0, sincScale(6, dim));
        linearLayout2.setLayoutParams(para);
        
        LinearLayout linearLayout3=(LinearLayout) findViewById(R.id.linearLayout3);
        para=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, sincScale(150, dim));
        para.setMargins(0, 0, 0, 0);
        linearLayout3.setLayoutParams(para);
        
        LinearLayout linearLayout6=(LinearLayout) findViewById(R.id.linearLayout6);
        para=new LinearLayout.LayoutParams(sincScale(252, dim), LayoutParams.MATCH_PARENT);
        para.setMargins(sincScale(10, dim), sincScale(8, dim), 0, 0);
        linearLayout6.setLayoutParams(para);
        
        LinearLayout linearLayout4=(LinearLayout) findViewById(R.id.linearLayout4);
        para=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        para.setMargins(sincScale(5, dim), 0, sincScale(15, dim), 0);
        linearLayout4.setLayoutParams(para);
        
        LinearLayout linearLayout5=(LinearLayout) findViewById(R.id.linearLayout5);
        para=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        para.setMargins(sincScale(15, dim), 0, sincScale(15, dim), 0);
        linearLayout5.setLayoutParams(para);
        
        FrameLayout frameLayout1=(FrameLayout) findViewById(R.id.frameLayout1);
        para=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        para.setMargins(sincScale(15, dim), sincScale(7, dim), sincScale(15, dim), sincScale(9, dim));
        frameLayout1.setLayoutParams(para);
    }
    
	//scale calculator
	public int sincScale(int value, DisplayMetrics dm){
		//return (int) (value*dm.density);
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
	}
	
    //to close database
    public void onBackPressed(){
    	db.close();
    	super.onBackPressed();
    }
	
    //crazy code
    public void setMapImage(ImageView map,int index){
    	if(index==0) map.setImageResource(R.drawable.map0);
    	else if(index==1) map.setImageResource(R.drawable.map1);
    	else if(index==2) map.setImageResource(R.drawable.map2);
    	else if(index==3) map.setImageResource(R.drawable.map3);
    	else if(index==4) map.setImageResource(R.drawable.map4);
    	else if(index==5) map.setImageResource(R.drawable.map5);
    	else if(index==6) map.setImageResource(R.drawable.map6);
    	else if(index==7) map.setImageResource(R.drawable.map7);
    	else if(index==8) map.setImageResource(R.drawable.map8);
    	else if(index==9) map.setImageResource(R.drawable.map9);
    	else if(index==10) map.setImageResource(R.drawable.map10);
    	else if(index==11) map.setImageResource(R.drawable.map11);
    	else if(index==12) map.setImageResource(R.drawable.map12);
    	else if(index==13) map.setImageResource(R.drawable.map13);
    	else if(index==14) map.setImageResource(R.drawable.map14);
    	else if(index==15) map.setImageResource(R.drawable.map15);
    	else if(index==16) map.setImageResource(R.drawable.map16);
    	else if(index==17) map.setImageResource(R.drawable.map17);
    	else if(index==18) map.setImageResource(R.drawable.map18);
    	else if(index==19) map.setImageResource(R.drawable.map19);
    	else if(index==20) map.setImageResource(R.drawable.map20);
    	else if(index==21) map.setImageResource(R.drawable.map21);
    	else if(index==22) map.setImageResource(R.drawable.map22);
    }
}
