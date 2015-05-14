package com.example.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ScreenDensityDemo extends Activity {
    
    private final static String TAG = "ScreenDensityDemo";
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 获取显示设备物理信息*/
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        
        /* 打印信息*/  
        printDisplayInfo(dm); 
        
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ImageView  vv = (ImageView) ScreenDensityDemo.this.findViewById(R.id.imageView1);
                
                Toast.makeText(getApplicationContext(), 
                        "image w = " + vv.getWidth() + "  h = " + vv.getHeight(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    void printDisplayInfo(DisplayMetrics dm){
        Log.d(TAG, "width=" + dm.widthPixels + " hight=" + dm.heightPixels);
        Log.d(TAG, "density=" + dm.density  + " scaledDensity=" + dm.scaledDensity );
        Log.d(TAG, "densityDpi=" + dm.densityDpi + " xdpi=" + dm.xdpi + " ydpi" + dm.ydpi);
    }

}
