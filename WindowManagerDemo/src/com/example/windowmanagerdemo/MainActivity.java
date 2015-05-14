package com.example.windowmanagerdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams param;
    private FloatView mLayout;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        showView();
    }

    private void showView() {
        mLayout = new FloatView(getApplicationContext());

        mLayout.setBackgroundResource(R.drawable.ic_launcher);
        // ��ȡWindowManager
        mWindowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        // ����LayoutParams(ȫ�ֱ�������ز���
        param = ((MyApplication) getApplication()).getMywmParams();

        param.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // ϵͳ��ʾ����,��Ҫ
        param.format = 1;
        param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // ������ռ�۽���
        param.flags = param.flags
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        param.flags = param.flags
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // �Ű治������

        param.alpha = 1.0f;

        param.gravity = Gravity.LEFT | Gravity.TOP; // �����������������Ͻ�
        // ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
        param.x = 0;
        param.y = 0;

        // �����������ڳ�������
        param.width = 140;
        param.height = 140;

        // ��ʾmyFloatViewͼ��
        mWindowManager.addView(mLayout, param);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // �ڳ����˳�(Activity���٣�ʱ������������
        mWindowManager.removeView(mLayout);
    }
}
