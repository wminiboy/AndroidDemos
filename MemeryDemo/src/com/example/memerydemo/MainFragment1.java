package com.example.memerydemo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

public class MainFragment1 extends Fragment {

	private int[] array = new int[1024000];
	
	private ImageView iv;
	private Button bt;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onActivityCreated savedInstanceState="+savedInstanceState);
		
		//iv= (ImageView) getView().findViewById(R.id.imageView1);
		bt = (Button)getView().findViewById(R.id.button1);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onAttach "+ getArguments().getString("param"));
		super.onAttach(activity);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onAttach");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onCreate savedInstanceState="+savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onCreateAnimation");
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onCreateView savedInstanceState="+savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_main, null);
		return v;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onDestroy " + getArguments().getString("param"));
		super.onDestroy();
	}

	@Override
	public void onDestroyOptionsMenu() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onDestroyOptionsMenu");
		super.onDestroyOptionsMenu();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onDestroyView");
		iv = null;
		//bt = null;
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onDetach");
		super.onDetach();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onHiddenChanged");
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onInflate(Activity activity, AttributeSet attrs,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onInflate");
		super.onInflate(activity, attrs, savedInstanceState);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onLowMemory");
		super.onLowMemory();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onOptionsItemSelected");
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onOptionsMenuClosed");
		super.onOptionsMenuClosed(menu);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onPause");
		super.onPause();
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onPrepareOptionsMenu");
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onResume");
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onStart "+ getArguments().getString("param"));
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onStop "+ getArguments().getString("param"));
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("yanjun", "fragment onViewStateRestored");
		super.onViewStateRestored(savedInstanceState);
	}
	
}
