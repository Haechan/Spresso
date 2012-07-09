package com.Appeyroad.Spresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class mapActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        View.OnClickListener sectionClick=new View.OnClickListener(){	
        	public void onClick(View v){ 
        		Intent intent = new Intent();
        		if(v.getTag()!=null) intent.putExtra("section", v.getTag().toString());//is this ok? Object to String?
        		else intent.putExtra("section", "-1");
        		//-1 : touching out side
        		//0 : no section selected(show every section)
        		//n(>0) : section number(view's tag)
        		setResult(1,intent);
        		finish();
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        	}
        };
        
        for(int i=0;i<43;i++) findViewById(getResources().getIdentifier("com.Appeyroad.Spresso:id/view"+(i+1), null, null)).setOnClickListener(sectionClick);
        /*findViewById(R.id.view1).setOnClickListener(sectionClick);
        findViewById(R.id.view2).setOnClickListener(sectionClick);
        findViewById(R.id.view3).setOnClickListener(sectionClick);
        findViewById(R.id.view4).setOnClickListener(sectionClick);
        findViewById(R.id.view5).setOnClickListener(sectionClick);
        findViewById(R.id.view6).setOnClickListener(sectionClick);
        findViewById(R.id.view7).setOnClickListener(sectionClick);
        findViewById(R.id.view8).setOnClickListener(sectionClick);
        findViewById(R.id.view9).setOnClickListener(sectionClick);
        findViewById(R.id.view10).setOnClickListener(sectionClick);
        findViewById(R.id.view11).setOnClickListener(sectionClick);
        findViewById(R.id.view12).setOnClickListener(sectionClick);
        findViewById(R.id.view13).setOnClickListener(sectionClick);
        findViewById(R.id.view14).setOnClickListener(sectionClick);
        findViewById(R.id.view15).setOnClickListener(sectionClick);
        findViewById(R.id.view16).setOnClickListener(sectionClick);
        findViewById(R.id.view17).setOnClickListener(sectionClick);
        findViewById(R.id.view18).setOnClickListener(sectionClick);
        findViewById(R.id.view19).setOnClickListener(sectionClick);
        findViewById(R.id.view20).setOnClickListener(sectionClick);
        findViewById(R.id.view21).setOnClickListener(sectionClick);
        findViewById(R.id.view22).setOnClickListener(sectionClick);
        findViewById(R.id.view23).setOnClickListener(sectionClick);
        findViewById(R.id.view24).setOnClickListener(sectionClick);
        findViewById(R.id.view25).setOnClickListener(sectionClick);
        findViewById(R.id.view26).setOnClickListener(sectionClick);
        findViewById(R.id.view27).setOnClickListener(sectionClick);
        findViewById(R.id.view28).setOnClickListener(sectionClick);
        findViewById(R.id.view29).setOnClickListener(sectionClick);
        findViewById(R.id.view30).setOnClickListener(sectionClick);
        findViewById(R.id.view31).setOnClickListener(sectionClick);
        findViewById(R.id.view32).setOnClickListener(sectionClick);
        findViewById(R.id.view33).setOnClickListener(sectionClick);
        findViewById(R.id.view34).setOnClickListener(sectionClick);
        findViewById(R.id.view35).setOnClickListener(sectionClick);
        findViewById(R.id.view36).setOnClickListener(sectionClick);
        findViewById(R.id.view37).setOnClickListener(sectionClick);
        findViewById(R.id.view38).setOnClickListener(sectionClick);
        findViewById(R.id.view39).setOnClickListener(sectionClick);
        findViewById(R.id.view40).setOnClickListener(sectionClick);
        findViewById(R.id.view41).setOnClickListener(sectionClick);
        findViewById(R.id.view42).setOnClickListener(sectionClick);
        findViewById(R.id.view43).setOnClickListener(sectionClick);*/
	}
}