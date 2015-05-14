package com.example.toucheventdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MyGroup group = new MyGroup(this);
		
		MyView view = new MyView(this);
		view.setBackgroundColor(Color.BLUE);
		
		LayoutParams param = new LayoutParams(200, 200);
		group.addView(view,param);
		
		setContentView(group);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "Activity dispatchTouchEvent "+ ev.getAction());
		return super.dispatchTouchEvent(ev);
	}

	private MotionEvent mEvent;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "Activity onTouchEvent "+ event.getAction());
		
		switch(event.getAction()){
		case 0:
			break;
		case 2:
			break;
		}
		
		return super.onTouchEvent(event);
	}

}
