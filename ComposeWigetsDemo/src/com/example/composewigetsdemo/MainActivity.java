package com.example.composewigetsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.composewigetsdemo.MyWidgets.CommitCallBack;

public class MainActivity extends Activity {

    private TextView  mTextView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTextView = (TextView) findViewById(R.id.textView1);
		
		MyWidgets v = (MyWidgets) findViewById(R.id.widget1);
		
		v.setCommitCallBack(new CommitCallBack(){

            @Override
            public void commitText(String str) {
                // TODO Auto-generated method stub
                mTextView.setText(str);
            }
        });
		
	      MyWidgets v2 = (MyWidgets) findViewById(R.id.widget2);
	        
	        v2.setCommitCallBack(new CommitCallBack(){

	            @Override
	            public void commitText(String str) {
	                // TODO Auto-generated method stub
	                mTextView.setText(str);
	            }
	        });
	}

}
