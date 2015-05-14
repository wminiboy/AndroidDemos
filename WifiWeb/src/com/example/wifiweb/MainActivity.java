package com.example.wifiweb;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wifiweb.WifiConnect.WifiCipherType;

public class MainActivity extends Activity {

	private static final String SSID = "theTech";
	private static final String PASSWORD = "taihe2014";
	private static final String URL = "http://www.script-tutorials.com/demos/199/index.html";
	
	//private static final String SSID = "octoprint";
	//private static final String PASSWORD = "12345678";
	//private static final String URL = "http://192.168.0.1";
	
	private EditText mEdtiText;
	private WebView mWebView;

	private WifiConnect mWifiConnect;
	private Dialog mPregressDialog;

	private int checkCount = 0;
	
	private final static int FILECHOOSER_RESULTCODE = 1;
	private ValueCallback<Uri> mUploadMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

		mEdtiText = (EditText) findViewById(R.id.editText1);
		mWebView = (WebView) findViewById(R.id.webView1);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setAllowFileAccess(true);		// ������������ļ�����
		webSettings.setJavaScriptEnabled(true);		// ����֧��javascript�ű�
		webSettings.setBuiltInZoomControls(true);	// ����֧������
		
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			
			// The undocumented magic method override
			// Eclipse will swear at you if you try to put @Override here
			// For Android 3.0+
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {

				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MainActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Chooser"),
						FILECHOOSER_RESULTCODE);

			}

			// For Android 3.0+
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MainActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Browser"),
						FILECHOOSER_RESULTCODE);
			}

			// For Android 4.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MainActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Chooser"),
						FILECHOOSER_RESULTCODE);

			}

		});

		mWifiConnect = new WifiConnect(this);
		mPregressDialog = getProgressDialog(this);
		mPregressDialog.show();
		
		if(savedInstanceState == null){
			
			mWebView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.d("yanjun", "load url="+URL);
					mWebView.loadUrl(URL);
				}
			}, 900);	
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		mWebView.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				checkWifi();
			}
		}, 500);
	}

	/**
	 * �����ҳ����ʷ��¼���򷵻���һҳ��
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ���wifi���Ƿ��Ѿ����ӵ�ָ����ssid�豸��
	 */
	private void checkWifi() {
		mPregressDialog.show();

		if (!mWifiConnect.isConnectedToSsid(SSID)) {
			
			// ���wifiû�����ӵ�ָ����ssid��������
			// WifiCipherType.WIFICIPHER_WPA ��ʾ�����õļ��ܷ�ʽ
			// ��������������ǣ�WIFICIPHER_WEP, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
			boolean flag = mWifiConnect.Connect(SSID, PASSWORD,WifiCipherType.WIFICIPHER_WPA);

			Log.d("yanjun", "connect ret flag=" + flag);

			if (!flag) {
				mPregressDialog.dismiss();
				getDialog(this, "WiFi��ʧ�ܣ����������",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								checkWifi();
							}
						}).show();
			} else {
				checkWifiConnect();
			}
		} else {
			checkWifiConnect();
		}
	}

	/**
	 * ���wifi�Ƿ��Ѿ������ϡ�
	 */
	public void checkWifiConnect() {

		final Handler handler = new Handler();

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Ҫ��������

				if (WifiConnect.isWifiNetworkConnected(MainActivity.this)) {
					mPregressDialog.dismiss();
					checkCount = 0;
					
				} else {
					if (checkCount >= 3) {
						checkCount = 0;

						getDialog(MainActivity.this, "��������ʧ�ܣ����������",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										checkWifiConnect();
									}
								}).show();
					} else {
						handler.postDelayed(this, 2000);
						checkCount++;
					}
				}
			}
		};

		handler.post(runnable);
	}

	/**
	 * �û����"��ת"Button�Ļص�
	 * @param v
	 */
	public void onJumpClicked(View v) {
//		String url = mEdtiText.getText().toString();
//		if (TextUtils.isEmpty(url)) {
//			Toast.makeText(this, "��������ȷ�ĵ�ַ��", Toast.LENGTH_SHORT).show();
//			return;
//		}

		// ������ҳ
		mWebView.loadUrl(URL);
	}

	public static Dialog getDialog(final Activity activity, String msg,
			DialogInterface.OnClickListener listenerOk) {

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("����");
		builder.setMessage(msg);
		builder.setPositiveButton("����", listenerOk);
		builder.setNegativeButton("�˳�", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				activity.finish();
			}
		});
		return builder.create();
	}

	public static ProgressDialog getProgressDialog(Activity activity) {

		ProgressDialog dialog = new ProgressDialog(activity);

		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���÷��ΪԲ�ν�����
		dialog.setIndeterminate(false);// ���ý������Ƿ�Ϊ����ȷ
		dialog.setMessage("���ڼ���...");
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage)
				return;
						
			Uri result = intent == null || resultCode != RESULT_OK ? null: intent.getData();
			
			if(result == null){
				mUploadMessage.onReceiveValue(null);
				mUploadMessage = null;
				return;
			}
			
			String path = PicPathUtil.getPath(this, result);
			
			if(TextUtils.isEmpty(path)){
				Toast.makeText(this, "�ļ�ѡ�����", Toast.LENGTH_SHORT).show();
				mUploadMessage.onReceiveValue(null);
				mUploadMessage = null;
				return;
			}
			
			Log.d("yanjun", "Select file="+path);
			
			mUploadMessage.onReceiveValue(Uri.fromFile(new File(path)));
			mUploadMessage = null;
		}
	}
}
