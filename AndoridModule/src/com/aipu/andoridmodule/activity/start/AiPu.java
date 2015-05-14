package com.aipu.andoridmodule.activity.start;

import com.aipu.andoridmodule.R;
import com.aipu.andoridmodule.R.layout;
import com.aipu.andoridmodule.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AiPu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aipu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aipu, menu);
		return true;
	}

}
