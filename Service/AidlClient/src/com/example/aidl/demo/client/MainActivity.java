package com.example.aidl.demo.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aidl.demo.MyAIDLInterface;

public class MainActivity extends Activity {

	
	private Button boundAIDLServiceBtn;
	private Button getBoundAIDLServiceProBtn;
	private Button unboundAIDLServiceBtn;

	private EditText countET;
	private Button setCountBtn;

	private MyAIDLInterface binder = null;
	private Intent myAIDLServiceIntent;
	private ServiceConnection con = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = MyAIDLInterface.Stub.asInterface(service);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);

		countET = (EditText) findViewById(R.id.count);
		setCountBtn = (Button) findViewById(R.id.setCount);

		boundAIDLServiceBtn = (Button) findViewById(R.id.boundAIDLService);
		myAIDLServiceIntent = new Intent(
				"com.example.demo.remote.MyAIDLServiceAction");
		
		
		boundAIDLServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean result = bindService(myAIDLServiceIntent, con,
						Context.BIND_AUTO_CREATE);
				if (!result) {
					binder = null;
					Toast.makeText(getApplicationContext(), "bind false...",
							Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "bind true...",
							Toast.LENGTH_SHORT).show();				
				}
			}
		});

		getBoundAIDLServiceProBtn = (Button) findViewById(R.id.getBoundAIDLServicePro);
		getBoundAIDLServiceProBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (binder != null) {
						Toast.makeText(getApplicationContext(),
								"Service count:" + binder.getCount(),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "do not get the count...",
								Toast.LENGTH_SHORT).show();
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		unboundAIDLServiceBtn = (Button) findViewById(R.id.unboundAIDLService);
		unboundAIDLServiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (binder != null) {
					unbindService(con);
					binder = null;
				}
			}
		});

		setCountBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (binder != null) {
						if (countET.getText() == null) {
							Toast.makeText(getApplicationContext(), "input number into edit...",
									Toast.LENGTH_SHORT).show();
						} else {
							try {
								binder.setCount(Integer.parseInt(countET
										.getText().toString()));
							} catch (NumberFormatException e) {
								Toast.makeText(getApplicationContext(),
										"NumberFormatException...", Toast.LENGTH_SHORT).show();
							}
						}
					} else {
						Toast.makeText(getApplicationContext(), "binder is null...",
								Toast.LENGTH_SHORT).show();
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
