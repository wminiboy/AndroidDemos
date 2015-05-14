package com.example.memerydemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity {
	
	private ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("yanjun", "onCreate savedInstanceState="+savedInstanceState);
		
		iv = (ImageView) findViewById(R.id.imageView1);
		
		if(savedInstanceState == null){
			attachFragment("hehe");
		}
	}

	public void onButtonClicked(View v){
		//startActivity(new Intent(this ,MainActivity2.class));
		attachFragment("haha");
	}
	
	public void onButtonClicked2(View v){
		FragmentManager fm =  getSupportFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		Fragment f = fm.findFragmentByTag("hehe");
		
		//ft.remove(f);
		ft.detach(f);
		
		ft.commit();
	}
	
	public void onButtonClicked3(View v){
		FragmentManager fm =  getSupportFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		Fragment f = fm.findFragmentByTag("hehe");

		ft.attach(f);
		
		ft.commit();
	}
	
	private void attachFragment(String tag){
		FragmentManager fm =  getSupportFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		
		Fragment f = new MainFragment1();
		Bundle b = new Bundle();
		b.putString("param", tag);
		f.setArguments(b);
		
		ft.add(R.id.contains, f, tag);
		ft.commit();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onLowMemory");
		super.onLowMemory();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onResume");
		super.onResume();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onRestart");
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onStart");
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onStop");
		super.onStop();
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onAttachFragment");
		super.onAttachFragment(fragment);
	}

	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onAttachedToWindow");
		super.onAttachedToWindow();
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onDetachedFromWindow");
		super.onDetachedFromWindow();
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		
		// Create every view, inlcude actionBar
		// Log.d("yanjun", "onCreateView "+name);
		return super.onCreateView(name, context, attrs);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onNewIntent");
		super.onNewIntent(intent);
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onPostResume");
		super.onPostResume();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		
		// Called when trim memory .
		// Log.d("yanjun", "onTrimMemory");
		super.onTrimMemory(level);
	}

}

