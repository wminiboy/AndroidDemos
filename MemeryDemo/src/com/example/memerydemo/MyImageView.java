package com.example.memerydemo;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class MyImageView extends ImageView {


	public MyImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "view onAttachedToWindow");
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "view onDetachedFromWindow");
		super.onDetachedFromWindow();
	}

	@Override
	public void onFinishTemporaryDetach() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "view onFinishTemporaryDetach");
		super.onFinishTemporaryDetach();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "view onSaveInstanceState");
		return super.onSaveInstanceState();
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "view onRestoreInstanceState");
		super.onRestoreInstanceState(state);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "view onFinishInflate");
		super.onFinishInflate();
	}
}
