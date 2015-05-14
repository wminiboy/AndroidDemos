package com.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import java.util.Random;

public class TextSwitcherActivity extends Activity implements ViewFactory,OnClickListener{
    TextSwitcher switcher ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textswitcher);
        Button btn = (Button) this.findViewById(R.id.btnText);  
        switcher = (TextSwitcher) this.findViewById(R.id.textSwitcher);  
        switcher.setFactory(this);// 指定转换器的 ViewSwitcher.ViewFactory  
        // 设置淡入和淡出的动画效果  
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);  
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);  
        switcher.setInAnimation(in);  
        switcher.setOutAnimation(out);  
        btn.setOnClickListener(this);
    }  
      // 重写 ViewSwitcher.ViewFactory 的 makeView()，返回一个 View  
    public View makeView() {  
        TextView tv = new TextView(this);  
        tv.setTextSize(36);  
        return tv;  
    }  
    int i =0;
    @Override
    public void onClick(View v) {
      switcher.setText(i+++"");
    }
    
    
}
