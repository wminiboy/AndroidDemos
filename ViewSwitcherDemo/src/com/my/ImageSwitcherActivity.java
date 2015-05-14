
package com.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class ImageSwitcherActivity extends Activity implements ViewFactory, OnClickListener {
    private ImageSwitcher imageSwitcher;

    private int[] imageSource = new int[] {
            R.drawable.image1, R.drawable.image2, R.drawable.image3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageswitcher);
        imageSwitcher = (ImageSwitcher)findViewById(R.id.imageswitcher);
        imageSwitcher.setFactory(this);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        Button btn = (Button)findViewById(R.id.btnImage);
        btn.setOnClickListener(this);
    }

    int i = 0;

    public View makeView() {
        // 将所有图片通过ImageView来显示
        return new ImageView(this);
    }

    @Override
    public void onClick(View v) {
        if (i >= imageSource.length)
            i = 0;
        //此处不能够为   imageSwitcher.setBackgroundResource(imageSource[i++]); 否怎看不到动画效果
        imageSwitcher.setImageResource(imageSource[i++]);
    }
}
