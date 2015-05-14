package com.example.bitmapdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private String TAG = "MainActivity";
    
    private  Bitmap bitmap1;
    private  Bitmap bitmap2;
    private  Bitmap bitmap3;
    
    private Paint paint = new Paint();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SimpleView(this));
    }
    
    class SimpleView extends View{

        public SimpleView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            canvas.drawBitmap(getBitmap2(), 10, 10, paint);
        } 
    }
    
    /**
     * 从图片数据中获取一个Bitmap对象
     */
    private  Bitmap getBitmap(){
        Bitmap bmp = null;
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        
        //根据文件路径直接获取
        bmp = BitmapFactory.decodeFile(sdPath + "/head.png");
        
        //根据资源ID获取
        bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
        
        // 使用opition参数
        BitmapFactory.Options options = new Options();
        
        // 该参数设为true,表示只获取图像的大小，不会解析图片内容
        options.inJustDecodeBounds=true;
        bmp = BitmapFactory.decodeFile(sdPath + "/head.png", options);
        Log.d(TAG,"width="+options.outWidth + " heght="+options.outHeight);
        
        // 重新获取，并且设置了缩放的大小
        options.inJustDecodeBounds=false;
        // 设置decode时的缩放比例
        options.inSampleSize=3;
        bmp = BitmapFactory.decodeFile(sdPath + "/head.png", options);
        
        //也可从图片流，或数据中获取
        InputStream is = getResources().openRawResource(R.drawable.ic_launcher);
        bmp = BitmapFactory.decodeStream(is);
        
        // 也可从数据中获取
        //bmp = BitmapFactory.decodeByteArray(data, offset, length);
        
        return bmp;
    }

    /**
     * Bitmap的缩放，旋转，裁剪
     */
    private Bitmap getBitmap2(){
        Bitmap bmp = null;
        bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
        Log.d(TAG,"bmp width="+bmp.getWidth() + " bmp heght="+bmp.getHeight());
        // 裁剪
        //bmp = Bitmap.createBitmap(bmp, 10, 10, bmp.getWidth()/2, bmp.getHeight()/2);
        
        // 缩放，旋转
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f);
        matrix.postRotate(90);         

        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        Log.d(TAG,"bmp width="+bmp.getWidth() + " bmp heght="+bmp.getHeight());
        
        // 保存Bitmap图片
        //saveBitmap(bmp);
        
        // 在Bitmap上二次绘图
        drawOnBitmap(bmp);
        
        return bmp;
    }
    
    /**
     * Bitmap的保存
     */
    
    private void saveBitmap(Bitmap  bmp){

        String sdPath = Environment.getExternalStorageDirectory().getPath();
        
        File file=  new File(sdPath +"/test.png");
        
        FileOutputStream os;
        try {
            os = new FileOutputStream(file);
            
            //80 是压缩率，表示压缩20%; 如果不压缩是100，表示压缩率为0  
            bmp.compress(CompressFormat.PNG, 100, os);
            os.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Bitmap二次绘图
     */
    private void drawOnBitmap(Bitmap bmp){
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setTextSize(50);
        canvas.drawText("hehe", 0, 100, p);
    }
}
