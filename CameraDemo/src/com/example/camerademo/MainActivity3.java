package com.example.camerademo;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity3 extends Activity implements SurfaceHolder.Callback {

	private static final String TAG = "MainActivity";
	private SurfaceView mSurfaceview;
	private ImageButton mBtnStartStop;
	private boolean mStartedFlg = false;
	private MediaRecorder mRecorder;
	private SurfaceHolder mSurfaceHolder;

	private View mHintView;
	private TextView mHintTime;

	private boolean isFocus;

	private Camera camera;

	private ImageView iv;

	private MyOrientationEventListener mOrientationListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// // 选择支持半透明模式,在有surfaceview的activity中使用。
		// getWindow().setFormat(PixelFormat.TRANSLUCENT);

		setContentView(R.layout.activity_main_3);

		mHintView = findViewById(R.id.hintview);
		mHintTime = (TextView) findViewById(R.id.hinttime);

		iv = (ImageView) findViewById(R.id.imageView);

		mSurfaceview = (SurfaceView) findViewById(R.id.surfaceview);
		mBtnStartStop = (ImageButton) findViewById(R.id.btnStartStop);

		mBtnStartStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!mStartedFlg) {

					recode();
					mStartedFlg = true;
					mBtnStartStop.setSelected(true);
					mHintView.setVisibility(View.VISIBLE);
					startTimer();

				} else {
					stop();
					mStartedFlg = false;
					mBtnStartStop.setSelected(false);
					mHintView.setVisibility(View.GONE);
					stopTimer();
				}
			}
		});

		SurfaceHolder holder = mSurfaceview.getHolder();// 取得holder

		holder.addCallback(this); // holder加入回调接口

		// setType必须设置，要不出错.
		// holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mOrientationListener = new MyOrientationEventListener(this);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		initCamera();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		camera.release();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mOrientationListener.enable();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mOrientationListener.disable();
	}

	private void initCamera() {

		try {
			camera = Camera.open();
			camera.setDisplayOrientation(90);

			setCameraParams();

			if (isFocus) {
				camera.autoFocus(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setCameraParams() {

		if (camera != null) {
			Parameters params = camera.getParameters();

			List<String> list = params.getSupportedFocusModes();
			if (list.contains(Parameters.FOCUS_MODE_AUTO)) {
				isFocus = true;
				params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			}
			/*
			List<Camera.Size> previewSizes = params.getSupportedPictureSizes();
			if (previewSizes.size() > 1) {
				
				Iterator<Camera.Size> cei = previewSizes.iterator();
				while (cei.hasNext()) {
					Camera.Size aSize = cei.next();
					System.out.println(aSize.width + "," + aSize.height);
				}
			}*/
			
			params.setPreviewSize(1280,720);

			camera.setParameters(params);
		}
	}

	private boolean timerFlag = true;
	private int totalTime = 30;
	private int curTime = 0;

	private void startTimer() {
		timerFlag = true;
		curTime = 0;
		mHintTime.setText("00:00:00");

		mHintTime.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (curTime > totalTime) {
					stop();
					mStartedFlg = false;
					mBtnStartStop.setSelected(false);
					mHintView.setVisibility(View.GONE);
					stopTimer();

					return;
				}

				if (timerFlag) {
					mHintTime.postDelayed(this, 1000);
				}
				curTime++;
				mHintTime.setText("00:00:"
						+ (curTime < 10 ? "0" + curTime : curTime + ""));
			}
		}, 1000);
	}

	private void stopTimer() {
		timerFlag = false;
	}

	private void recode() {

		mRecorder = new MediaRecorder();
		mRecorder.setCamera(camera);

		camera.unlock();

		// Set audio and video source and encoder
		// 这两项需要放在setOutputFormat之前
		mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		mRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

		// mRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

		// Set output file format
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

		// 这两项需要放在setOutputFormat之后
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

		int angle = (mOrientation + 90) % 360;
		mRecorder.setOrientationHint(angle);
		// mRecorder.setVideoSize(640, 480);
		mRecorder.setVideoSize(1280, 720);
		mRecorder.setVideoFrameRate(30);
		mRecorder.setVideoEncodingBitRate(1024 * 1024);
		mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

		// mRecorder.setMaxDuration(10000);//ms为单位

		// Set output file path
		String path = getSDPath();
		if (path != null) {

			File dir = new File(path + "/recordtest");
			if (!dir.exists()) {
				dir.mkdir();
			}
			path = dir + "/" + getDate() + ".mp4";

			mPath = path;

			mRecorder.setOutputFile(path);

			Log.d(TAG, "bf mRecorder.prepare()");
			try {
				mRecorder.prepare();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d(TAG, "af mRecorder.prepare()");
			Log.d(TAG, "bf mRecorder.start()");
			mRecorder.start(); // Recording is now started
		}
	}

	private String mPath;

	private void stop() {
		try {
			mRecorder.stop();
			mRecorder.reset();

			iv.setImageBitmap(createVideoThumbnail(mPath, null, 100));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static String getDate() {
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR); // 获取年份
		int month = ca.get(Calendar.MONTH); // 获取月份
		int day = ca.get(Calendar.DATE); // 获取日
		int minute = ca.get(Calendar.MINUTE); // 分
		int hour = ca.get(Calendar.HOUR); // 小时
		int second = ca.get(Calendar.SECOND); // 秒

		String date = "" + year + (month + 1) + day + hour + minute + second;
		Log.d(TAG, "date:" + date);

		return date;
	}

	/**
	 * 获取SD path
	 * 
	 * @return
	 */
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		}

		return null;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		// 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
		mSurfaceHolder = holder;
		Log.d(TAG, "surfaceChanged 1");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
		mSurfaceHolder = holder;

		startPreview();

		Log.d(TAG, "surfaceChanged 2");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// surfaceDestroyed的时候同时对象设置为null
		mSurfaceview = null;
		mSurfaceHolder = null;
		if (mRecorder != null) {
			mRecorder.release(); // Now the object cannot be reused
			mRecorder = null;
			Log.d(TAG, "surfaceDestroyed release mRecorder");
		}
	}

	 public static final int LARGEST_WIDTH = 300;
	 public static final int LARGEST_HEIGHT = 300;
	
	private void startPreview() {

		if (camera == null) {
			return;
		}

		camera.stopPreview();

		try {
			camera.setPreviewDisplay(mSurfaceHolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 设置预览监听
		
		camera.startPreview();
	}

	private static Bitmap createVideoThumbnail(String filePath,
			FileDescriptor fd, int targetWidth) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			if (filePath != null) {
				retriever.setDataSource(filePath);
			} else {
				retriever.setDataSource(fd);
			}
			bitmap = retriever.getFrameAtTime(-1);
		} catch (IllegalArgumentException ex) {
			// Assume this is a corrupt video file
		} catch (RuntimeException ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
				// Ignore failures while cleaning up.
			}
		}
		if (bitmap == null)
			return null;

		// Scale down the bitmap if it is bigger than we need.
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		if (width > targetWidth) {
			float scale = (float) targetWidth / width;
			int w = Math.round(scale * width);
			int h = Math.round(scale * height);
			bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
		}
		return bitmap;
	}

	public static int getDisplayRotation(Activity activity) {
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		switch (rotation) {
		case Surface.ROTATION_0:
			return 0;
		case Surface.ROTATION_90:
			return 90;
		case Surface.ROTATION_180:
			return 180;
		case Surface.ROTATION_270:
			return 270;
		}
		return 0;
	}

	// Orientation hysteresis amount used in rounding, in degrees
	public static final int ORIENTATION_HYSTERESIS = 5;

	public static int roundOrientation(int orientation, int orientationHistory) {
		boolean changeOrientation = false;
		if (orientationHistory == OrientationEventListener.ORIENTATION_UNKNOWN) {
			changeOrientation = true;
		} else {
			int dist = Math.abs(orientation - orientationHistory);
			dist = Math.min(dist, 360 - dist);
			changeOrientation = (dist >= 45 + ORIENTATION_HYSTERESIS);
		}
		if (changeOrientation) {
			return ((orientation + 45) / 90 * 90) % 360;
		}
		return orientationHistory;
	}

	int mOrientation = 0;

	private class MyOrientationEventListener extends OrientationEventListener {
		public MyOrientationEventListener(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(int orientation) {
			// We keep the last known orientation. So if the user first orient
			// the camera then point the camera to floor or sky, we still have
			// the correct orientation.

			Log.d("yanjun", "orientation=" + orientation);

			if (orientation == ORIENTATION_UNKNOWN)
				return;

			mOrientation = roundOrientation(orientation, mOrientation);
			Log.d("yanjun", "mOrientation=" + mOrientation);

			// When the screen is unlocked, display rotation may change. Always
			// calculate the up-to-date orientationCompensation.
			// int orientationCompensation = mOrientation +
			// getDisplayRotation(MainActivity3.this);
			// Log.d("yanjun","orientationCompensation="+
			// orientationCompensation % 360);

		}
	}
}
