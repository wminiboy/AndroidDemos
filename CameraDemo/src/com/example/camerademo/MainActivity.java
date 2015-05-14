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
 * Android�Դ���CameraӦ�ó��������ɺܶ๦�ܡ����ǵ��䲻������������Ҫ��ʱ��
 * ���ǿ��Զ����Լ���Camera��Android�ṩ��Camera������������ʵ���Լ���Camera�� ������Ӿ�������һ���Լ���Camera
 * ���ȣ���Manifest����Ҫ����Ȩ��<uses-permission
 * android:name="android:permission.CAMERA"/> ������Ҫ�������ȡ�����������������������SurfaceView��
 * ʹ��SurfaceView��ͬʱ�����ǻ���Ҫʹ�õ�SurfaceHolder��SurfaceHolder�൱��һ�������������Լ���
 * Surface�ϵı仯,ͨ�����ڲ���CallBack��ʵ�֡�
 * Ϊ�˿��Ի�ȡͼƬ��������Ҫʹ��Camera��takePicture����ͬʱ������Ҫʵ��Camera
 * .PictureCallBack�࣬ʵ��onPictureTaken����
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

	private Camera camera; // �����hardare��Camera����

	private ToneGenerator tone;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		imageView = (ImageView) findViewById(R.id.imageView1);

		// ��ʼ��CameraView
		initCameraSurfaceView();

		// ʵ����Camera����Ҳ���Դ��δ򿪺���Camera
		initCamera(Camera.CameraInfo.CAMERA_FACING_BACK);

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				camera.release();

				// �л�Ϊǰ������ͷ
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

		// SurfaceView�е�getHolder�������Ի�ȡ��һ��SurfaceHolderʵ��
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.setFixedSize(640, 720);

		// ����Surface�ص�
		mSurfaceHolder.addCallback(this);

		Log.d("MainActivity", "mSurfaceHolder=" + mSurfaceHolder);
	}

	private void initCamera(int cameraId) {

		camera = Camera.open(cameraId);
		
		// ��������ͷ�ĽǶ� 0, 90, 180 ,270
		camera.setDisplayOrientation(90);

		// ��������ͷ����
		/**
		 * Camera�����к���һ���ڲ���Camera.Parameters.������Զ�Camera�����Խ��ж���
		 * ��Parameters��������ɺ���Ҫ����Camera.setParameters()��������Ӧ�����òŻ���Ч
		 * ���ڲ�ͬ���豸��Camera�������ǲ�ͬ�ģ�����������ʱ����Ҫ�����ж��豸��Ӧ�����ԣ��ټ�������
		 * �����ڵ���setEffects֮ǰ����ȵ���getSupportedColorEffects������豸��֧����ɫ���ԣ���ô�÷�����
		 * ����һ��null
		 */
		try {

			Camera.Parameters param = camera.getParameters();

			// ���Ȼ�ȡϵͳ�豸֧�ֵ�������ɫ��Ч���з������ǵģ������ã���������
			List<String> colorEffects = param.getSupportedColorEffects();

			for (String currColor : colorEffects) {
				if (currColor.equals(Camera.Parameters.EFFECT_BLACKBOARD)) {
					param.setColorEffect(Camera.Parameters.EFFECT_BLACKBOARD);
					break;
				}
			}

			// ������Ƭ��ʽ����С
			param.setPictureFormat(ImageFormat.JPEG);
//			param.setPictureSize(480, 320);

			// ������Ч ��û�����ã�Ҳû��д���ļ��ڡ�GaGa...
			param.setRotation(90);

			//param.set("jpeg-quality", 85);
			
			camera.setParameters(param);

		} catch (Exception e) {
			// ��������쳣�����ͷ�Camera����
			e.printStackTrace();
			camera.release();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// ����Ԥ������
		Log.d("MainActivity", "surfaceCreated...holder=" + holder);
		startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// ��Surface�����ٵ�ʱ�򣬸÷���������
		// ��������ҪֹͣԤ��
		camera.stopPreview();
		camera.release();
	}

	private void startPreview() {

		// ����Ԥ������ʾ
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
		// data��һ��ԭʼ��JPEGͼ�����ݣ�
		// ���������ǿ��Դ洢ͼƬ������Ȼ���Բ���MediaStore
		// ע�Ᵽ��ͼƬ���ٴε���startPreview()�ص�Ԥ��

		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

		// ��Ϊ��ƬĬ����ת90�ȣ������ж������ĻΪ����ʱ����תͼƬ��
		// ��������������������ͷ��ǰ��ʽ����Ҫת180

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

		// ���浽�ļ�
		save(bitmap);

		camera.startPreview();
	}

	// ���Ű��µ�ʱ��onShutter()���ص�
	private ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			if (tone == null) {
				// ������ʾ�û�������
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
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);// ��ͼƬѹ��������

			bos.flush();// ���
			bos.close();// �ر�
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
