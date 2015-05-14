package com.example.toucheventdemo;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyGroup extends LinearLayout {

	public MyGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "Group onInterceptTouchEvent "+ ev.getAction());
		return false;
		//return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "Group onTouchEvent "+ event.getAction());
		return true;
		//return super.onTouchEvent(event);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "Group dispatchTouchEvent "+ event.getAction());
		//return true;
		return super.dispatchTouchEvent(event);
	}

}
