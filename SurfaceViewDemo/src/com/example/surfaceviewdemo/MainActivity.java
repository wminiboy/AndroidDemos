package com.example.surfaceviewdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
    
    private SurfaceHolder mSurfaceHolder;
    private Thread mRenderThread;
    private boolean renderFlag = false;
    
    private int count = 0;
    private String TAG = "MainActivity";
    
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;    
  
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
       
        // 画在背景之上，默认为false在背景之下,但是其上面的其他View也会被覆盖
        surfaceView.setZOrderOnTop(true);
        
        mSurfaceHolder = surfaceView.getHolder();
        //该大小不等于surfaceView的大小，只是画布的大小，如果不一致，则会伸缩内容至等于view的大小
        mSurfaceHolder.setFixedSize(300, 300);
        
        mSurfaceHolder.addCallback(new Callback() {
            
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                Log.d("TAG", "surfaceDestroyed...");
                renderFlag = false;
            }
            
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                Log.d("TAG", "surfaceCreated...");
                renderFlag = true;
                mRenderThread.start();
            }
            
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                    int height) {
                // TODO Auto-generated method stub
                Log.d("TAG", "surfaceChanged..." +"format="+format+" width="+width+" height="+height);
            }
        });
        
        mRenderThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Canvas canvas;
                Paint paint = new Paint();
                
                Bitmap bmp = bitmap1;

                while(true){
                    
                    Log.d("TAG", "in thread run...");
                    if(!renderFlag){
                        break;
                    }
                    
                    //默认返回整个画布
                    canvas = mSurfaceHolder.lockCanvas();
                    
                    //返回指定区域的画布，则，只刷新该部分
                    //canvas = mSurfaceHolder.lockCanvas(new Rect(10,145,100,150));
                    
                    paint.setColor(Color.BLUE);
                    //canvas.drawRect(10, 10, 110, 110, paint);
                    canvas.drawRect(10, 10, 110, 150, paint);
                    
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(20);
                    canvas.drawText("第"+(count++) +"次执行", 10 , 150, paint);
                    
                    switch(count%4){
                    case 0:
                        bmp = bitmap1;
                        break;
                    case 1:
                        bmp = bitmap2;
                        break;
                    case 2:
                        bmp = bitmap3;
                        break;
                    case 3:
                        bmp = bitmap4;
                        break;
                    }
                    
                    canvas.drawBitmap(bmp, 20  , 20, paint);
                    
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                    
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mSurfaceHolder.setFixedSize(200, 200);
            }
        });
        
        bitmap1 = ((BitmapDrawable)getResources().getDrawable(R.drawable.run_1)).getBitmap();
        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 40, 80, false);
        bitmap2 = ((BitmapDrawable)getResources().getDrawable(R.drawable.run_2)).getBitmap();
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, 40, 80, false);
        bitmap3 = ((BitmapDrawable)getResources().getDrawable(R.drawable.run_3)).getBitmap();
        bitmap3 = Bitmap.createScaledBitmap(bitmap3, 40, 80, false);
        bitmap4 = ((BitmapDrawable)getResources().getDrawable(R.drawable.run_4)).getBitmap();
        bitmap4 = Bitmap.createScaledBitmap(bitmap4, 40, 80, false);
    }

}