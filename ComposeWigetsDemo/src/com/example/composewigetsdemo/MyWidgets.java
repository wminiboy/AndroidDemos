package com.example.composewigetsdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyWidgets extends RelativeLayout {

    private Button mCommitButton;
    private ImageButton mClearButton;
    private EditText mEditText;

    // �Զ���ص�����,ͨ���ӿ�ʵ��
    public interface CommitCallBack {
        void commitText(String str);
    }

    // �ӿڵĶ���
    private CommitCallBack mCallback = null;

    public MyWidgets(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        // ����ͨ�����룬�Լ�ʵ������������ʵ�������ֲ�������ӵ�Group��
        // ����������������̫��

        // ͨ��xmlʵ������Ӳ���, ���ӵ����಼���С�
        LayoutInflater.from(context).inflate(R.layout.widgets_layout, this,
                true);
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        
        mCommitButton = (Button) findViewById(R.id.bt_commit);
        mCommitButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mCallback != null) {
                    mCallback
                            .commitText(mEditText.getEditableText().toString());
                }
            }
        });

        mClearButton = (ImageButton) findViewById(R.id.bt_clear);
        mClearButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mEditText.setText("");
            }
        });

        mEditText = (EditText) findViewById(R.id.editText1);
    }

    public void setCommitCallBack(CommitCallBack call) {
        this.mCallback = call;
    }
}
