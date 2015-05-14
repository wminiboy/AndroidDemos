package com.example.windowmanagerdemo;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class FloatView extends View {
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    private WindowManager wm = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    private WindowManager.LayoutParams wmParams = ((MyApplication) getContext()
            .getApplicationContext()).getMywmParams();

    public FloatView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��
        x = event.getRawX();
        y = event.getRawY() - 25; // 25��ϵͳ״̬���ĸ߶�
        Log.i("currP", "currX" + x + "====currY" + y);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            // ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��
            mTouchStartX = event.getX();
            mTouchStartY = event.getY();

            Log.i("startP", "startX" + mTouchStartX + "====startY"
                    + mTouchStartY);

            break;
        case MotionEvent.ACTION_MOVE:
            updateViewPosition();
            break;

        case MotionEvent.ACTION_UP:
            updateViewPosition();
            mTouchStartX = mTouchStartY = 0;
            break;
        }
        return true;
    }

    private void updateViewPosition() {
        // ���¸�������λ�ò���
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(this, wmParams);

    }

}
