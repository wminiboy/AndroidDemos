package com.qin.selectionDrawable;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author http://http://blog.csdn.net/qinjuning
 */
public class MainActivity extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);    

        LinearLayout ll  =  new LinearLayout(MainActivity.this);
        CustomView customView = new CustomView(MainActivity.this); 
        //������Ϊ width 200px - height 100px�� 
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(200 , 100);
        customView.setLayoutParams(lp);
        //��Ҫ����View����Ϊ�ɵ��/����״̬����������Viewû��Ч����
        customView.setClickable(true);
        
        ll.addView(customView);
        setContentView(ll); 
    }
}