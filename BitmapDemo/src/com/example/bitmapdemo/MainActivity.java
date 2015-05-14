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
     * ��ͼƬ�����л�ȡһ��Bitmap����
     */
    private  Bitmap getBitmap(){
        Bitmap bmp = null;
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        
        //�����ļ�·��ֱ�ӻ�ȡ
        bmp = BitmapFactory.decodeFile(sdPath + "/head.png");
        
        //������ԴID��ȡ
        bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
        
        // ʹ��opition����
        BitmapFactory.Options options = new Options();
        
        // �ò�����Ϊtrue,��ʾֻ��ȡͼ��Ĵ�С���������ͼƬ����
        options.inJustDecodeBounds=true;
        bmp = BitmapFactory.decodeFile(sdPath + "/head.png", options);
        Log.d(TAG,"width="+options.outWidth + " heght="+options.outHeight);
        
        // ���»�ȡ���������������ŵĴ�С
        options.inJustDecodeBounds=false;
        // ����decodeʱ�����ű���
        options.inSampleSize=3;
        bmp = BitmapFactory.decodeFile(sdPath + "/head.png", options);
        
        //Ҳ�ɴ�ͼƬ�����������л�ȡ
        InputStream is = getResources().openRawResource(R.drawable.ic_launcher);
        bmp = BitmapFactory.decodeStream(is);
        
        // Ҳ�ɴ������л�ȡ
        //bmp = BitmapFactory.decodeByteArray(data, offset, length);
        
        return bmp;
    }

    /**
     * Bitmap�����ţ���ת���ü�
     */
    private Bitmap getBitmap2(){
        Bitmap bmp = null;
        bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
        Log.d(TAG,"bmp width="+bmp.getWidth() + " bmp heght="+bmp.getHeight());
        // �ü�
        //bmp = Bitmap.createBitmap(bmp, 10, 10, bmp.getWidth()/2, bmp.getHeight()/2);
        
        // ���ţ���ת
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f);
        matrix.postRotate(90);         

        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        Log.d(TAG,"bmp width="+bmp.getWidth() + " bmp heght="+bmp.getHeight());
        
        // ����BitmapͼƬ
        //saveBitmap(bmp);
        
        // ��Bitmap�϶��λ�ͼ
        drawOnBitmap(bmp);
        
        return bmp;
    }
    
    /**
     * Bitmap�ı���
     */
    
    private void saveBitmap(Bitmap  bmp){

        String sdPath = Environment.getExternalStorageDirectory().getPath();
        
        File file=  new File(sdPath +"/test.png");
        
        FileOutputStream os;
        try {
            os = new FileOutputStream(file);
            
            //80 ��ѹ���ʣ���ʾѹ��20%; �����ѹ����100����ʾѹ����Ϊ0  
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
     * Bitmap���λ�ͼ
     */
    private void drawOnBitmap(Bitmap bmp){
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setTextSize(50);
        canvas.drawText("hehe", 0, 100, p);
    }
}
