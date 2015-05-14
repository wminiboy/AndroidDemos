package com.example.aidl.demo.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.aidl.demo.service.MyService.MyBinder;

public class MainActivity extends Activity {

	private MyService myService;
	private Intent myServiceIntent;
	private Intent myAIDLServiceIntent;
	private Intent myIntentServiceIntent;
	private Intent myIntentService2Intent;

	private Button startServiceBtn;
	private Button stopServiceBtn;
	private Button startIntentServiceBtn;

	private Button boundServiceBtn;
	private Button operateBoundServiceBtn;
	private Button getBoundServiceProBtn;
	private Button unboundServiceBtn;

	private Button startAIDLServiceBtn;
	private Button stopAIDLServiceBtn;

	private ServiceConnection con = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(getApplicationContext(), "Service disconnect",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myService = ((MyBinder) service).getService();
			Toast.makeText(getApplicationContext(), "Service Connect",
					Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_demo);

		myServiceIntent = new Intent(this, MyService.class);
		startServiceBtn = (Button) findViewById(R.id.startService);
		startServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startService(myServiceIntent);
			}
		});

		stopServiceBtn = (Button) findViewById(R.id.stopService);
		stopServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopService(myServiceIntent);
			}
		});
		
		
		myIntentServiceIntent = new Intent(MainActivity.this,
				MyIntentService.class);		
		myIntentService2Intent = new Intent(MainActivity.this,
				MyIntentService2.class);
		startIntentServiceBtn = (Button) findViewById(R.id.startIntentService);
		startIntentServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startService(myIntentServiceIntent);
				startService(myIntentService2Intent);
			}
		});
		
		boundServiceBtn = (Button) findViewById(R.id.boundService);
		boundServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				bindService(myServiceIntent, con, Context.BIND_AUTO_CREATE);
			}
		});

		operateBoundServiceBtn = (Button) findViewById(R.id.operateBoundService);
		operateBoundServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (myService != null) {
					Toast.makeText(getApplicationContext(),
							"test 1" + myService.increaseCount(),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "test 2",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		getBoundServiceProBtn = (Button) findViewById(R.id.getBoundServicePro);
		getBoundServiceProBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (myService != null) {
					Toast.makeText(getApplicationContext(),
							"Service count:" + myService.getCount(),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "test 3",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		unboundServiceBtn = (Button) findViewById(R.id.unboundService);
		unboundServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (myService != null) {
					unbindService(con);
					myService = null;
				}
			}
		});
		
		myAIDLServiceIntent = new Intent(MainActivity.this, MyAIDLService.class);
		startAIDLServiceBtn = (Button) findViewById(R.id.startAIDLService);
		startAIDLServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "AIDL start...", 0).show();
				startService(myAIDLServiceIntent);
			}
		});

		stopAIDLServiceBtn = (Button) findViewById(R.id.stopAIDLService);
		stopAIDLServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "AIDL stop...", 0).show();
				stopService(myAIDLServiceIntent);
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
