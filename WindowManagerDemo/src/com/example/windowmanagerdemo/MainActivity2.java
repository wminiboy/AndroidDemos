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
         * ���¶���WindowManager.LayoutParams��������� ������;��ο�SDK�ĵ�
         */
        wmParams.type = 2003; // �����ǹؼ�����Ҳ��������2003
        wmParams.format = 1;
        wmParams.x = 0;
        wmParams.y = 0;
        /**
         * �����flagsҲ�ܹؼ� ����ʵ����wmParams.flags |= FLAG_NOT_FOCUSABLE;
         * 40��������wmParams��Ĭ�����ԣ�32��+ FLAG_NOT_FOCUSABLE��8��
         */
        wmParams.flags = 40;
        wmParams.width = 100;
        wmParams.height = 200;
        wm.addView(mButton, wmParams); // ����View
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

        /* �����Լ���ӵ�View */
        WindowManager wm = this.getWindowManager();
        wm.removeView(mButton);

        super.onDestroy();
    }

}
