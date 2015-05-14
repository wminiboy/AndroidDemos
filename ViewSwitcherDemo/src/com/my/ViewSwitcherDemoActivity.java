
package com.my;

import org.w3c.dom.Text;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class ViewSwitcherDemoActivity extends Activity implements OnClickListener {
    private ViewSwitcher bottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewswitcher);
        bottom = (ViewSwitcher)findViewById(R.id.bottom);
        Button btn1 = (Button)findViewById(R.id.btn_c1);
        Button btn2 = (Button)findViewById(R.id.btn_c2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (bottom.getDisplayedChild() != 0&&bottom.getDisplayedChild()==bottom.getChildCount()) {
            // 切换为第一个
            bottom.setDisplayedChild(0);        
        }else
        {
            // 切换到下一个
            bottom.showNext();
        }
    }  
}
