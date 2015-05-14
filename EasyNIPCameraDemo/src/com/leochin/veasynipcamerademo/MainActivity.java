package com.leochin.veasynipcamerademo;

import net.reecam.IpCamera;
import net.reecam.IpCamera.PTZ_COMMAND;
import net.reecam.SimpleAudioTrack;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.misc.objc.NSData;
import com.misc.objc.NSNotification;
import com.misc.objc.NSNotificationCenter;
import com.misc.objc.NSSelector;

public class MainActivity extends Activity {
	
	private SurfaceView mSurface;
	private IpCamera mIPCamera;
	private SimpleAudioTrack mAudioTrack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSurface = (SurfaceView) findViewById(R.id.surfaceView);
		
		mIPCamera = new IpCamera("", "", "172.20.224.120", "90", "admin", "", 512);
		
		mAudioTrack = new SimpleAudioTrack(8000, 
		        AudioFormat.CHANNEL_OUT_MONO, 
		        AudioFormat.ENCODING_PCM_16BIT);
		
		mAudioTrack.init();

		NSNotificationCenter.defaultCenter().addObserver(
		        this, new NSSelector("onImageCallback", new Class[] {NSNotification.class}), IpCamera.IPCamera_Image_Notification, mIPCamera);
		
		NSNotificationCenter.defaultCenter().addObserver(
		        this, new NSSelector("onAudioCallback", new Class[] {NSNotification.class}), IpCamera.IPCamera_Audio_Notification, mIPCamera);
	}
	
	public void onImageCallback(NSNotification aNotification){
	    
	    NSData data = (NSData)aNotification.userInfo().get("data");
	    
	    Canvas  canvas  =  mSurface.getHolder().lockCanvas();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data.bytes(), 0, data.length());
        
        //Log.d("yanjun", "length="+data.length() + " bitmap.w="+bitmap.getWidth() + "bitmap.h="+bitmap.getHeight());
        
        canvas.drawBitmap(bitmap, 0, 0, new Paint());
        mSurface.getHolder().unlockCanvasAndPost(canvas);
	}
	
	public void onAudioCallback(NSNotification aNotification){
	    NSData data = (NSData)aNotification.userInfo().get("data");
	    mAudioTrack.playAudioTrack(data.bytes(), 0, data.length());
	    
	    Log.d("yanjun", "audio  length="+data.length());
	}

	public void onLoginClicked(View v){
	       
	    //NSNotificationCenter.defaultCenter().postNotification("IPCamera_Image_Notification", mIPCamera);

        IpCamera.CAMERA_ERROR error = mIPCamera.start();
        Log.d("yanjun", "start error = " + error);
        showState();
	}
	
	public void onStartClicked(View v){
	       showState();
	        IpCamera.CAMERA_ERROR error = mIPCamera.play_video();

	        Log.d("yanjun", "play error = " + error);
	}
	
     public void onStopClicked(View v){
         mIPCamera.stop();
    }
     
    public void onPTZClicked(View v){
        mIPCamera.ptz_control(PTZ_COMMAND.T_UP);
    }

	private void showState(){
	       IpCamera.CAMERA_STATUS state  = mIPCamera.camera_status;
	       Log.d("yanjun", "state = " + state);
	}
}
