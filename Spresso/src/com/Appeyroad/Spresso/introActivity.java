package com.Appeyroad.Spresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class introActivity extends Activity {
    /** Called when the activity is first created. */
	Handler h;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        h= new Handler();
        h.postDelayed(irun, 1000);
        //show intro-view in purpose
        //it should be more delayed(to 3000?4000?) when large memory taking error is fixed
    }
    
    //change into mainActivity
    Runnable irun =new Runnable(){
    	public void run(){
    		Intent i=new Intent(introActivity.this,mainActivity.class);
    		startActivity(i);
    		finish();
    		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    	}
    };
    
    //if you press back button during loading(=delay in introActivity), dont call the mainActivity
    @Override
    public void onBackPressed(){
    	super.onBackPressed();
    	h.removeCallbacks(irun);
    }
}