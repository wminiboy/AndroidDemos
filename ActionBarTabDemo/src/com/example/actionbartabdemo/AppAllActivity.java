package com.example.actionbartabdemo;

import com.example.fragmenttabdemo.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppAllActivity extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	    System.out.println("onCreateView... All");
	    
		View view = inflater.inflate(R.layout.activity_test, container, false);
		
		return view;
	}

}
