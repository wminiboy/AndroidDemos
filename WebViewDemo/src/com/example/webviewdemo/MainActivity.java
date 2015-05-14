package com.example.webviewdemo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Toast;

public class MainActivity extends Activity {

	private WebView mWeb;

	private final static int FILECHOOSER_RESULTCODE = 1;

	private ValueCallback<Uri> mUploadMessage;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mWeb = (WebView) findViewById(R.id.webView1);
		
		WebSettings webSettings = mWeb.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setAllowContentAccess(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
		webSettings.setAppCacheEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		//webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setSaveFormData(true);
		
		//sendGetStringTest();
		//localcityTest();
		domUrlShowTest();
	}
	
	private void domUrlShowTest(){
		String url = "http://m.ctrip.com/webapp/train/index.html#index?bus=1";
		mWeb.loadUrl(url);
	}
	
	private void sendGetStringTest(){
		
		//mWeb.loadUrl("http://192.168.1.130/share/templets/zhaopin.html");

		// Html to Local
		mWeb.addJavascriptInterface(this, "ha_ha");
		
		mWeb.loadUrl("file:///android_asset/index.html");
		
		// Local to Html 
		String data = "hehe";
		mWeb.loadUrl("javascript:htmlFunction()");
		
		// Cann't get return value by loadUrl, must use html callback
		//mWeb.loadUrl("javascript:getData()");

		Log.d("yanjun", "main activity load"+data);
	}
	
	/**
	 * For java script to callback metheod
	 */
	@JavascriptInterface
	public void getPicCallBack(String data){
		Log.d("yanjun", "call data=" + data );
	}
	
	@JavascriptInterface
	public void getData(String data){
		
	}
	
	private void localcityTest(){
		mWeb.loadUrl("http://192.168.1.119:8180/jizhou/appHtml/add_recruit.html");
		
		//mWeb.loadUrl("javascript:getSummary('"+ data + "')");
	}
	
	
	private void openFileTest(){
		
		mWeb.setWebChromeClient(new WebChromeClient() {
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
		
		mWeb.loadUrl("http://www.script-tutorials.com/demos/199/index.html");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage)
				return;
			Uri result = intent == null || resultCode != RESULT_OK ? null: intent.getData();
			
			String path = PicPathUtil.getPath(this, result);
			
			if(TextUtils.isEmpty(path)){
				Toast.makeText(this, "文件选择出错", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Log.d("yanjun", "Select file="+path);
			
			mUploadMessage.onReceiveValue(Uri.fromFile(new File(path)));
			mUploadMessage = null;
		}
	}
}
