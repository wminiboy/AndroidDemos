package com.example.camerademo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * Android自带的Camera应用程序可以完成很多功能。但是当其不能满足我们需要的时候
 * 我们可以定制自己的Camera。Android提供了Camera类来辅助我们实现自己的Camera。 这个例子就来定义一个自己的Camera
 * 首先，在Manifest中需要引入权限<uses-permission
 * android:name="android:permission.CAMERA"/> 我们需要用来存放取景器的容器，这个容器就是SurfaceView。
 * 使用SurfaceView的同时，我们还需要使用到SurfaceHolder，SurfaceHolder相当于一个监听器，可以监听
 * Surface上的变化,通过其内部类CallBack来实现。
 * 为了可以获取图片，我们需要使用Camera的takePicture方法同时我们需要实现Camera
 * .PictureCallBack类，实现onPictureTaken方法
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends Activity implements SurfaceHolder.Callback,
		Camera.PictureCallback {
	
	public static final int MAX_WIDTH = 200;
	public static final int MAX_HEIGHT = 200;

	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;

	private ImageView imageView;

	private Camera camera; // 这个是hardare的Camera对象

	private ToneGenerator tone;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		imageView = (ImageView) findViewById(R.id.imageView1);

		// 初始化CameraView
		initCameraSurfaceView();

		// 实例化Camera对象，也可以传参打开后置Camera
		initCamera(Camera.CameraInfo.CAMERA_FACING_BACK);

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				camera.release();

				// 切换为前置摄像头
				initCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
				startPreview();
			}
		});
	}

	private void initCameraSurfaceView() {

		mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);
		mSurfaceView.setFocusable(true);
		mSurfaceView.setFocusableInTouchMode(true);
		mSurfaceView.setClickable(true);
		mSurfaceView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				camera.takePicture(shutterCallback, null, null,
						MainActivity.this);
			}
		});

		// SurfaceView中的getHolder方法可以获取到一个SurfaceHolder实例
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.setFixedSize(640, 720);

		// 设置Surface回调
		mSurfaceHolder.addCallback(this);

		Log.d("MainActivity", "mSurfaceHolder=" + mSurfaceHolder);
	}

	private void initCamera(int cameraId) {

		camera = Camera.open(cameraId);
		
		// 设置摄像头的角度 0, 90, 180 ,270
		camera.setDisplayOrientation(90);

		// 设置摄像头参数
		/**
		 * Camera对象中含有一个内部类Camera.Parameters.该类可以对Camera的特性进行定制
		 * 在Parameters中设置完成后，需要调用Camera.setParameters()方法，相应的设置才会生效
		 * 由于不同的设备，Camera的特性是不同的，所以在设置时，需要首先判断设备对应的特性，再加以设置
		 * 比如在调用setEffects之前最好先调用getSupportedColorEffects。如果设备不支持颜色特性，那么该方法将
		 * 返回一个null
		 */
		try {

			Camera.Parameters param = camera.getParameters();

			// 首先获取系统设备支持的所有颜色特效，有符合我们的，则设置；否则不设置
			List<String> colorEffects = param.getSupportedColorEffects();

			for (String currColor : colorEffects) {
				if (currColor.equals(Camera.Parameters.EFFECT_BLACKBOARD)) {
					param.setColorEffect(Camera.Parameters.EFFECT_BLACKBOARD);
					break;
				}
			}

			// 设置照片格式及大小
			param.setPictureFormat(ImageFormat.JPEG);
//			param.setPictureSize(480, 320);

			// 设置无效 ，没起到作用，也没有写到文件内。GaGa...
			param.setRotation(90);

			//param.set("jpeg-quality", 85);
			
			camera.setParameters(param);

		} catch (Exception e) {
			// 如果出现异常，则释放Camera对象
			e.printStackTrace();
			camera.release();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 启动预览功能
		Log.d("MainActivity", "surfaceCreated...holder=" + holder);
		startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// 当Surface被销毁的时候，该方法被调用
		// 在这里需要停止预览
		camera.stopPreview();
		camera.release();
	}

	private void startPreview() {

		// 设置预览的显示
		try {
			camera.setPreviewDisplay(mSurfaceHolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		camera.startPreview();
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// data是一个原始的JPEG图像数据，
		// 在这里我们可以存储图片，很显然可以采用MediaStore
		// 注意保存图片后，再次调用startPreview()回到预览

		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

		// 因为照片默认旋转90度，所以判断如果屏幕为竖立时，旋转图片。
		// 该情况仅仅处理后置摄像头，前置式，又要转180

		/*
		 * int rotation = getWindowManager().getDefaultDisplay().getRotation();
		 * if (rotation == Surface.ROTATION_0 || rotation ==
		 * Surface.ROTATION_180) {
		 * 
		 * Matrix m = new Matrix(); m.setRotate(90, (float) bitmap.getWidth() /
		 * 2, (float) bitmap.getHeight() / 2); bitmap =
		 * Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
		 * bitmap.getHeight(), m, true); }
		 */
		imageView.setImageBitmap(bitmap);

		// 保存到文件
		save(bitmap);

		camera.startPreview();
	}

	// 快门按下的时候onShutter()被回调
	private ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			if (tone == null) {
				// 发出提示用户的声音
				tone = new ToneGenerator(AudioManager.STREAM_MUSIC,
						ToneGenerator.MAX_VOLUME);
			}

			tone.startTone(ToneGenerator.TONE_PROP_PROMPT);
		}
	};

	private String save(Bitmap bmp) {
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/" + System.currentTimeMillis() + ".jpg";

		try {
			File file = new File(path);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 将图片压缩到流中

			bos.flush();// 输出
			bos.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
