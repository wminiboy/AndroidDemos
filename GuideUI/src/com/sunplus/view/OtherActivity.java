package com.sunplus.view;

import yanbin.switchDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 引导界面启动以后要启动的activity
 * @author jin.ma
 *
 */
public class OtherActivity extends Activity {
	Button mButton;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);
        mButton = (Button)findViewById(R.id.bu);
        mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }
}
