package com.example.audiotracerecorddemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private AudioRecord record;
	private AudioTrack track;
	
	private final static int Sample_Rate = 8000;
	private final static int Channel_In_Configuration = AudioFormat.CHANNEL_IN_MONO;
	private final static int Channel_Out_Configuration = AudioFormat.CHANNEL_OUT_MONO;
	private final static int AudioEncoding = AudioFormat.ENCODING_PCM_16BIT;

	private boolean stopFlag = false;
	
	private int recBufferSize;
	private int playBufferSize;
	
	
	private FileOutputStream outStream; 
	private FileInputStream inStream; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initAudio();
	}
	
	private void initAudio(){
		recBufferSize = AudioRecord.getMinBufferSize(Sample_Rate,
				Channel_In_Configuration, AudioEncoding);
		playBufferSize = AudioTrack.getMinBufferSize(Sample_Rate,
				Channel_Out_Configuration, AudioEncoding);
		
		record = new AudioRecord(MediaRecorder.AudioSource.MIC, Sample_Rate,
				Channel_In_Configuration, AudioEncoding, recBufferSize);
		
		track = new AudioTrack(AudioManager.STREAM_VOICE_CALL, Sample_Rate,
				Channel_Out_Configuration, AudioEncoding, playBufferSize,
				AudioTrack.MODE_STREAM);
		
		
		File file = new File(Environment.getExternalStorageDirectory().getPath()
				+"/record.pcm");
		try {
			outStream = new FileOutputStream(file);
			inStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void record(View v){
		record.startRecording();
		stopFlag = false;

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while((!Thread.interrupted()) && !stopFlag){
					byte[] compressedVoice = new byte[recBufferSize];
					int b = record.read(compressedVoice, 0, recBufferSize);
					
					try {
						Log.d("MainActivity", "write=" + b);
						outStream.write(compressedVoice, 0, b);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void play(View v){
		track.play();
		stopFlag = false;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				byte[] gsmdata = new byte[recBufferSize];
				int numBytesRead = 0;
				
				while((!Thread.interrupted()) && !stopFlag){
					try {
						numBytesRead = inStream.read(gsmdata);
						Log.d("MainActivity", "read=" + numBytesRead);
						if (numBytesRead == -1) {
							break;
						}
						track.write(gsmdata, 0, numBytesRead);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void stop(View v){
		stopFlag = true;

		record.stop();
		track.stop();
	}
}
