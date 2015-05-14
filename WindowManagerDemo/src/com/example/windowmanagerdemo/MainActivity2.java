package com.example.windowmanagerdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity2 extends Activity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // mButton=new Button(getApplicationContext());
        // WindowManager wm
        // =(WindowManager)getApplicationContext().getSystemService("window");

        mButton = new Button(this);
        mButton.setText("window manager test!");
        WindowManager wm = this.getWindowManager();// getSystemService("window");

        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

        /**
         * 以下都是WindowManager.LayoutParams的相关属性 具体用途请参考SDK文档
         */
        wmParams.type = 2003; // 这里是关键，你也可以试试2003
        wmParams.format = 1;
        wmParams.x = 0;
        wmParams.y = 0;
        /**
         * 这里的flags也很关键 代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
         * 40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
         */
        wmParams.flags = 40;
        wmParams.width = 100;
        wmParams.height = 200;
        wm.addView(mButton, wmParams); // 创建View
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        /* 销毁自己添加的View */
        WindowManager wm = this.getWindowManager();
        wm.removeView(mButton);

        super.onDestroy();
    }

}
