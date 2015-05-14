package com.example.themeandstyledemo;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    private static final int[] LL = new int[] {
        /* 0 */ android.R.attr.divider,
        /* 1 */ android.R.attr.showDividers,
        /* 2 */ android.R.attr.dividerPadding,
        /* 3 */ android.R.attr.text,
        /* 4 */ android.R.attr.layout_marginRight
    };
    private static final int LL_DIVIDER = 0;
    private static final int LL_SHOW_DIVIDER = 1;
    private static final int LL_DIVIDER_PADDING = 2;
    private static final int LL_TEXT = 3;
    private static final int LL_LAYOUT = 4;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TypedArray a = this.obtainStyledAttributes(null, R.styleable.MyAttr,  0, 0);
        Log.d("yanjun", "a="+a.getString(LL_TEXT));
        
        /*
        for(int i = 0; i<a.length(); i++){
        	Log.d("yanjun", "a="+a.getDimensionPixelSize(LL_DIVIDER_PADDING, 100));
        	Log.d("yanjun", "a="+a.getDimensionPixelSize(LL_LAYOUT, 100));
        	Log.d("yanjun", "a="+a.getString(LL_TEXT));
        }*/
        
        int resId = a.getResourceId(R.styleable.MyAttr_vpiIconPageIndicatorStyle, -1);
        
        
        Log.d("yanjun", "resId="+resId);
        
        a.recycle();
        
        if(resId != -1){
        	TypedArray b = this.obtainStyledAttributes(resId, LL);
        	Log.d("yanjun", "b="+b.getDimensionPixelSize(LL_LAYOUT, -1));
        	b.recycle();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
