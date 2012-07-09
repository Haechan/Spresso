package com.Appeyroad.Spresso;

import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;

//failed to use progressive dialog and thread well
public class mainActivity extends Activity {
	//final int Dripper=0;
	//ProgressDialog progressDialog;
	//ProgressThread progressThread;
	
	long nowtime;
	int section=0;
	final ArrayList<theCafe> cafelist=new ArrayList<theCafe>();
	boolean wifi=false, seat=false, open=false, favorite=false;
	final static int numberOfCafes=23;
	
	//Runtime rt=Runtime.getRuntime();
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"start main");
        
        //set data in list
        nowtime=System.currentTimeMillis();//get 'nowtime' data
        if(Integer.parseInt(android.text.format.DateFormat.format("yyyyMMddhh", nowtime).toString())<2012032707){
        	SQLiteOpenHelper dbHelper=new DBHelper(this);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            Cursor counter=db.query("favorites", null, null, null, null, null, null);
            counter.moveToPosition(numberOfCafes);
            System.out.println(counter.getInt(counter.getColumnIndex("favorite")));
    		//counter.moveToPosition(orderOfCafes.get(i));
            if(counter.getInt(counter.getColumnIndex("favorite"))==0){
            	ContentValues newValues=new ContentValues();
        		newValues.put("favorite", 1);
        		String[] whereArgs={""+numberOfCafes};//maybe gg
        		db.update("favorites", newValues, "number = ?", whereArgs);
        		
        		showDialog(0);
            }
    		
            counter.close();
    		db.close();
        }
        	
        		
        XmlPullParser parser=getResources().getXml(R.xml.tests);
        for(int i=0; i<numberOfCafes;i++) cafelist.add(i, new theCafe(parser,i, nowtime));
        parser=null;
        
        Log.e("date", android.text.format.DateFormat.format("E", nowtime).toString());
        if(android.text.format.DateFormat.format("E", nowtime).toString().equals("Fri")) Log.e("date equal", "Fri");
        if(android.text.format.DateFormat.format("E", nowtime).toString().equals("Sun")) Log.e("date equal", "Sun");
        if(android.text.format.DateFormat.format("kk:mm:ss", nowtime).toString().compareTo("09:00:00")>0) Log.e("opencheck", "ok");
        
        sort(cafelist, wifi, seat, open, favorite, section);
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"init list");
        
        ListView list=(ListView)findViewById(R.id.listView1);
        list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView parent, View view, int position, long id){
        		int index=Integer.parseInt(view.getTag().toString());
        		if(index!=-1){
        			Intent intent = new Intent(mainActivity.this,detailActivity.class);
        			intent.putExtra("selected", index);
        			startActivity(intent);
        			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        		}
        	}
		});
        //list=null;
		
        ImageButton infoButtonClick = (ImageButton)findViewById(R.id.ImageButton2);
        infoButtonClick.setOnClickListener(new View.OnClickListener(){	
        	public void onClick(View v){ 
        		Intent intent = new Intent(mainActivity.this,infoActivity.class);
        		startActivity(intent);
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        	}
        });
        infoButtonClick=null;
        
        final CheckBox wifiButtonClick = (CheckBox)findViewById(R.id.checkBox3);
        final CheckBox seatButtonClick = (CheckBox)findViewById(R.id.checkBox4);
        final CheckBox openButtonClick = (CheckBox)findViewById(R.id.checkBox5);
        final CheckBox favoriteButtonClick = (CheckBox)findViewById(R.id.checkBox6);
        
        View.OnClickListener sorting =new View.OnClickListener(){	
        	public void onClick(View v){ 
        		wifi=wifiButtonClick.isChecked();
        		seat=seatButtonClick.isChecked();
        		open=openButtonClick.isChecked();
        		favorite=favoriteButtonClick.isChecked();
        		sort(cafelist, wifi, seat, open, favorite, section);
        	}
        };
        
        wifiButtonClick.setOnClickListener(sorting);
        seatButtonClick.setOnClickListener(sorting);
        openButtonClick.setOnClickListener(sorting);
        favoriteButtonClick.setOnClickListener(sorting);
        

        ImageButton refreshButtonClick = (ImageButton)findViewById(R.id.ImageButton1);
        refreshButtonClick.setOnClickListener(new View.OnClickListener(){	
        	public void onClick(View v){
        		
        		wifiButtonClick.setChecked(false);
        		seatButtonClick.setChecked(false);
        		openButtonClick.setChecked(false);
        		favoriteButtonClick.setChecked(false);
        		wifi=false;
        		seat=false;
        		open=false;
        		favorite=false;
        		section=0;
        		sort(cafelist, wifi, seat, open, favorite, section);
        	}
        });

        ImageButton mapButtonClick = (ImageButton)findViewById(R.id.ImageButton3);
        mapButtonClick.setOnClickListener(new View.OnClickListener(){	
        	public void onClick(View v){ 
        		Intent intent = new Intent(mainActivity.this,sectionActivity.class);
        		startActivityForResult(intent,1004);//to get 'section' user selected
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        	}
        });
        mapButtonClick=null;
        
        //System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"set button");
        
      //scale adjust
        DisplayMetrics dim=getResources().getDisplayMetrics();
        LinearLayout.LayoutParams para;
        
        View view3=findViewById(R.id.view3);
        para=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, sincScale(15, dim));
        para.setMargins(0, 0, 0, 0);
        view3.setLayoutParams(para);
        
        View view1=findViewById(R.id.view1);
        para=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, sincScale(13, dim));
        para.setMargins(0, 0, 0, 0);
        view1.setLayoutParams(para);
        
        View view2=findViewById(R.id.view2);
        para=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, sincScale(50, dim));
        para.setMargins(0, 0, 0, 0);
        view2.setLayoutParams(para);
        
        TableRow tableRow1=(TableRow) findViewById(R.id.tableRow1);
        para=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, sincScale(50, dim));
        para.setMargins(sincScale(5, dim), 0, 0, 0);
        tableRow1.setLayoutParams(para);
        /*
        para=new LinearLayout.LayoutParams(sincScale(480, dim), sincScale(292, dim));
        para.setMargins(sincScale(15, dim), 0, sincScale(15, dim), 0);
        list.setLayoutParams(para);
        */
        list=null;
        
        TableRow tableRow3=(TableRow) findViewById(R.id.tableRow3);
        para=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        para.setMargins(sincScale(15, dim), 0, sincScale(15, dim), 0);
        tableRow3.setLayoutParams(para);
        
    }
    
	//scale calculator
	public int sincScale(int value, DisplayMetrics dm){
		//return (int) (value*dm.density);
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
	}
    
    //set 'section' as user wants
    //data comes only from mapActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode==1004 && resultCode==1){
    		int sect=Integer.parseInt((data.getExtras().getString("section")));
    		if(sect!=-1){
    			section=sect;
    			sort(cafelist, wifi, seat, open, favorite,section);
    		}
    	}
    } 
    
    //call method 'sort' in cafeListAdapter and set the data by 'sort'ing result
    public void sort(ArrayList<theCafe> cafes, boolean wifi, boolean seat, boolean open, boolean favorite, int sect){
    	
    	//System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"start sort");
    	nowtime=System.currentTimeMillis();
    	//showDialog(Dripper);
    	cafeListAdapter adapter=new cafeListAdapter(this, nowtime);
    	ArrayList<Integer> orderOfCafes=new ArrayList<Integer>();
    	
    	//System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"set parameter");
    	
    	//filtering cafes
    	for(int i=0;i<cafes.size();i++){
    		if(wifi && !cafes.get(i).wifi) continue;//
    		else if(seat && !cafes.get(i).seat) continue;//
    		else if(open && !cafes.get(i).isOpen) continue;//
    		else orderOfCafes.add(new Integer(i));
    	}
    	
    	//System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"filter");
    	
    	//add favorite cafes on top
    	if(favorite){
    		SQLiteOpenHelper dbHelper=new DBHelper(this);
            SQLiteDatabase db=dbHelper.getWritableDatabase();//is final ok?
            Cursor favor=db.query("favorites", null, null, null, null, null, null);
            
            adapter.addCafe(new theCafe("즐겨찾기"));
    		for(int i=0; i<orderOfCafes.size();i++){
    			favor.moveToPosition(orderOfCafes.get(i));
            	if(favor.getInt(favor.getColumnIndex("favorite"))==1) adapter.addCafe(cafes.get(orderOfCafes.get(i)));
    		}
    		favor.close();
    		db.close();
    	}
    	
    	//System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"add favorite");
    	
    	//sort cafes by section and add sections selected
    	int[][] sectionkey={{1,2,3,4,5,6,7,8,9},{1,2,3,4},{2,1,3},{3,1,2,4,7},{4,1,3,5,6,7},{5,4},{6,4,7,8},{7,3,4,6,8,},{8,6,7,9},{9,8}};
    	String[] sectionlist={"ㅋㅋㅋ","음미대&경영대&생활대","수의대&정문","법대&사회대","인문대&사범대","기숙사","중도&학관","자연대&농생대","아랫공대","윗공대"};
    	for(int i=0;i<sectionkey[sect].length;i++){
    		adapter.addCafe(new theCafe(sectionlist[sectionkey[sect][i]]));
    		for(int j=0;j<orderOfCafes.size();j++)//
    			if(cafes.get(orderOfCafes.get(j)).section==sectionkey[sect][i]) adapter.addCafe(cafes.get(orderOfCafes.get(j)));// //
    	}
    	
    	//System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"sectionize");
    	
    	ListView list=(ListView) findViewById(R.id.listView1);
    	list.setAdapter(adapter);
    	//dismissDialog(Dripper);
    	
    	//System.out.println(rt.freeMemory()+"/"+rt.totalMemory()+"set listdata");
    }
    
    protected Dialog onCreateDialog(int id){
    	return new AlertDialog.Builder(mainActivity.this)
    			.setTitle("앱이로드 신입회원모집")
    			.setMessage("가입하실래요?")
    			.setPositiveButton("조으다",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(mainActivity.this,webActivity.class);
		        		startActivity(intent);
					}
				})
    			.setNegativeButton("시르다",null)
    			.create();
    }
    
    //following is remains of failure(progressive dialog and thread)
    /*
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Dripper:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Spresso dripping...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                //progressDialog.show();
                progressThread=new ProgressThread(handler, this);
                progressThread.start();
                return progressDialog;
            default:
                return null;
        }
    }
   
    final Handler handler=new Handler(){
    	public void handleMessage(Message msg){
    		if(msg.getData().getInt("end")==0){
    			dismissDialog(Dripper);
    			progressThread.setState(ProgressThread.STATE_DONE);
    		}
    	}
    };
    
    private class ProgressThread extends Thread{
    	Handler handler;
    	Context context;
    	final static int STATE_DONE=0;
    	final static int STATE_RUNNING=0;
    	int state;
    	ProgressThread(Handler hand, Context context){
    		handler=hand;
    		this.context=context;
    	}
    	public void run(){
    		state=STATE_RUNNING;
    		//while(state==STATE_RUNNING);
    		
        	
        	Message msg=progressThread.handler.obtainMessage();
            Bundle b=new Bundle();
            b.putInt("end",0);
            msg.setData(b);
            progressThread.handler.sendMessage(msg);
            
    	}
    	public void setState(int stt){
    		state=stt;
    	}
    }
    */
}
