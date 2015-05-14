package com.qin.selectionDrawable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.graphics.drawable.StateListDrawable;
/**
 * 
 * @author http://http://blog.csdn.net/qinjuning
 */
//�Զ���View
public class CustomView extends View   /*extends Button*/
{
    private static String TAG = "TackTextView";
    
    private Context mContext = null;

    private Drawable mBackground = null;
    private boolean mBGSizeChanged = true;;   //��ͼView����(layout)��С�Ƿ����仯
    
    public CustomView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;       
        initStateListDrawable(); // ��ʼ��ͼƬ��Դ
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mContext = context;        
        initStateListDrawable(); // ��ʼ��ͼƬ��Դ
    }

    public CustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;       
        initStateListDrawable(); // ��ʼ��ͼƬ��Դ
    }

    // ��ʼ��ͼƬ��Դ
    private void initStateListDrawable()
    {
        //�����ַ�ʽ��ȡ���ǵ�StateListDrawable����
        // ��ȡ��ʽһ���ֶ�����һ��StateListDrawable����
        StateListDrawable statelistDrawable = new StateListDrawable();
        
        int pressed = android.R.attr.state_pressed;
        int windowfocused = android.R.attr.state_window_focused;
        int enabled = android.R.attr.state_enabled;
        int stateFoucesd = android.R.attr.state_focused;
        //ƥ��״̬ʱ����һ�����Ȱ����Ĺ�ϵ��
        // "-"�ű�ʾ��״ֵ̬Ϊfalse .����ƥ��
        statelistDrawable.addState(new int[] { pressed, windowfocused }, mContext.getResources().getDrawable(R.drawable.btn_power_on_pressed));
        statelistDrawable.addState(new int[]{ -pressed, windowfocused }, mContext.getResources().getDrawable(R.drawable.btn_power_on_nor));    
        
        //��������״̬����
        //stateD.addState(new int[]{pressed , windowfocused },mContext.getResources().getDrawable(R.drawable.btn_dialog_selected) )
        //stateD.addState(View.EMPTY_STATE_SET, mContext.getResources().getDrawable(R.drawable.btn_dialog_normal));               
        //statelistDrawable.addState(new int[] { stateFoucesd, windowfocused }, null);
        mBackground = statelistDrawable;
        
        //�������ûص������ı�״̬ʱ����ص���View����invalidate()ˢ�²���.
        mBackground.setCallback(this);       
        //ȡ��Ĭ�ϵı���ͼƬ����Ϊ�����������Լ��ı���ͼƬ�ˣ����������ɱ���ͼƬ�ص���
        this.setBackgroundDrawable(null);
        
        // ��ȡ��ʽ������ʹ��XML��ȡStateListDrawable����
        // mBackground = mContext.getResources().getDrawable(R.drawable.view_background);

    }
//    //��View����setFrame�����ط��� @hide , ����TexitView/Button��ʱ���Լ̳и÷���
//    protected boolean setFrame(int l , int t , int r ,int b){
//        
//        if(getLeft() != l || getTop() != t || getRight()!= r || getBottom() != b){
//            mBGSizeChanged = true
//        }
//        
//        return super.setFrame(l ,  t ,  l , b);
//    }
    
    protected void drawableStateChanged()
    {
        Log.i(TAG, "drawableStateChanged");
        Drawable d = mBackground;
        if (d != null && d.isStateful())
        {
            d.setState(getDrawableState());
            //Log.i(TAG, "drawableStateChanged  and is 111");
        }
//         //��ӡ����״ֵ̬
//        int[] states = d.getState();
//
//        for (int i = 0; i < states.length; i++)
//        {
//            System.out.println("states[" + i + "]" + states[i]);
//        }
//        Log.i(TAG, "drawableStateChanged  and is 222");
        super.drawableStateChanged();
    }
    //��֤ͼƬ�Ƿ���� , ��invalidateDrawable()����ô˷�����������Ҫ��д�÷�����
    protected boolean verifyDrawable(Drawable who)
    {
        return who == mBackground || super.verifyDrawable(who);
    }
    //draw()���̣����Ʊ���ͼƬ...
    public void draw(Canvas canvas)
    {
        Log.i(TAG, " draw -----");
        if (mBackground != null)
        {
            if(mBGSizeChanged)
            {
                //���ñ߽緶Χ
                mBackground.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
                mBGSizeChanged = false ;
            }
            if ((getScrollX() | getScrollY()) == 0)  //�Ƿ�ƫ��
            {
                mBackground.draw(canvas); //���Ƶ�ǰ״̬��Ӧ��ͼƬ
            }
            else
            {
                canvas.translate(getScrollX(), getScrollY());
                mBackground.draw(canvas); //���Ƶ�ǰ״̬��Ӧ��ͼƬ
                canvas.translate(-getScrollX(), -getScrollY());
            }
        }
        super.draw(canvas);
    }
    
    public void onDraw(Canvas canvas) {    
        canvas.save();     
        Paint paint  = new Paint();
        paint.setTextSize(16);
        paint.setColor(Color.YELLOW);
        canvas.drawText("�Զ���View", 0, 20, paint);    
        canvas.drawText("�����ҿ��Ըı�ͼƬ����", 0, 40, paint);                     
        canvas.restore();
    }
}
