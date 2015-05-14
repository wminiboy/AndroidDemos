package com.example.gradledemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private final static String REMOTE_FILE_URL = "http://www.gjt.org/download/time/java/tar/javatar-2.5.tar.gz";
	private final static int BUFFER = 1024;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try{
			HttpClientTest();
		}catch(Exception e){
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void HttpClientTest() {

		HttpClient client = new HttpClient();
		GetMethod httpGet = new GetMethod(REMOTE_FILE_URL);
		try {
			client.executeMethod(httpGet);

			InputStream in = httpGet.getResponseBodyAsStream();

			FileOutputStream out = new FileOutputStream(new File(
					"E:\\test_jar\\javatar-2.5.tar.gz"));

			byte[] b = new byte[BUFFER];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			in.close();
			out.close();

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		System.out.println("download, success!!");
	}
}