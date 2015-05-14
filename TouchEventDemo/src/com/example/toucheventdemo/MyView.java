package com.example.toucheventdemo;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyView extends TextView{

	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	private boolean flag = false;
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "View onTouchEvent "+ ev.getAction());
		switch(ev.getAction()){
		case 0:{
			int index = MotionEventCompat.getActionIndex(ev);
			int PointerId = MotionEventCompat.getPointerId(ev, index);
			int index2 = MotionEventCompat.findPointerIndex(ev, PointerId);
			
			Log.d("yanjun","0 index="+index + " PointerId="+PointerId + " index2="+index2);
			
			return true;}
		case 2:{
			int index = MotionEventCompat.getActionIndex(ev);
			int PointerId = MotionEventCompat.getPointerId(ev, index);
			int index2 = MotionEventCompat.findPointerIndex(ev, PointerId);
			
			Log.d("yanjun","2 index="+index + " PointerId="+PointerId + " index2="+index2);

			return false;}
		case 1:{
			int index = MotionEventCompat.getActionIndex(ev);
			int PointerId = MotionEventCompat.getPointerId(ev, index);
			int index2 = MotionEventCompat.findPointerIndex(ev, PointerId);
			
			Log.d("yanjun","1 index="+index + " PointerId="+PointerId + " index2="+index2);
			break;
			}
		}

		return false;
		//return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "View dispatchTouchEvent "+ event.getAction());
		//return true;
		return super.dispatchTouchEvent(event);
	}
}
