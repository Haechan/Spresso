package com.Appeyroad.Spresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class infoActivity extends Activity {
	int tutorialcount;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        
        tutorialcount=0;
        final View infoview=findViewById(R.id.LinearLayout1);
        final View tutorial1=findViewById(R.id.view1);
        //View tutorial2=findViewById(R.id.view2);
        ImageButton xbtn = (ImageButton)findViewById(R.id.imageButton1);
        ImageButton emailButtonClick = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton tutorialbtn = (ImageButton)findViewById(R.id.imageButton3);
        
        infoview.setVisibility(View.VISIBLE);
        tutorial1.setVisibility(View.INVISIBLE);
        //tutorial2.setVisibility(View.INVISIBLE);
        
        //back button
        xbtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		finish();
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        	}
        });
        
        //send email by calling emailActivity
        emailButtonClick.setOnClickListener(new View.OnClickListener(){	
        	public void onClick(View v){ 
        		Intent intent = new Intent(infoActivity.this,emailActivity.class);
        		//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("achan17@appeyroad.org"));
        		startActivity(intent);
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        	}
        });
        
        tutorialbtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		infoview.setVisibility(View.INVISIBLE);
                tutorial1.setVisibility(View.VISIBLE);
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        	}
        });
        
        tutorial1.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		if(tutorialcount==0){
        			tutorialcount=1;
        			tutorial1.setBackgroundResource(R.drawable.tutorial_main2);
        		}
        		else if(tutorialcount==1){
        			tutorialcount=2;
        			tutorial1.setBackgroundResource(R.drawable.tutorial_detail);
        		}
        		else if(tutorialcount==2){
        			tutorialcount=0;
        			tutorial1.setBackgroundResource(R.drawable.tutorial_main1);
        			infoview.setVisibility(View.VISIBLE);
                    tutorial1.setVisibility(View.INVISIBLE);
        		}
        	}
        });
        
        //scale adjust
        DisplayMetrics dim=getResources().getDisplayMetrics();
        
        LinearLayout.LayoutParams para;
        para=new LinearLayout.LayoutParams(sincScale(150, dim), sincScale(40, dim));
        para.setMargins(0, sincScale(5, dim), 0, 0);
        tutorialbtn.setLayoutParams(para);
        
        View view3=findViewById(R.id.view3);
        para=new LinearLayout.LayoutParams(sincScale(100, dim), sincScale(150, dim));
        para.setMargins(0, 0, 0, 0);
        view3.setLayoutParams(para);
	}
	
	//scale calculator
	public int sincScale(int value, DisplayMetrics dm){
		//return (int) (value*dm.density);
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
	}
}
