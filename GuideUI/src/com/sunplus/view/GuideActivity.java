package com.sunplus.view;

import com.sunplus.control.OnViewChangeListener;
import com.sunplus.control.SwitchLayout;

import yanbin.switchDemo.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends Activity{
    /** Called when the activity is first created. */
	SwitchLayout mSwitchLayout;//�Զ���Ŀؼ�
	LinearLayout mLinearLayout;
	SharedPreferences mSharedPreferences;
	int mViewCount;//�Զ���ؼ����ӿؼ��ĸ���
	ImageView mImageView[];//�ײ���imageView
	int mCurSel;//��ǰѡ�е�imageView
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //����SharedPreferencesʹGuideActivityֻ�ڰ�װӦ�ú�ĵ�һ�β��У�
        mSharedPreferences = getSharedPreferences("count",MODE_WORLD_READABLE);
        int count = mSharedPreferences.getInt("count", 0);
        //ÿ����һ��Ӧ�ã�count +1������ǵ�һ�ξ�����GuideActivity�����ǵ�һ��������activity
//        if (count == 0) {
//        	 init();
//        }else{
//        	Intent intent = new Intent();
//        	intent.setClass(GuideActivity.this, com.sunplus.view.OtherActivity.class);
//        	this.startActivity(intent);
//        	this.finish();
//        }
        init();
        Editor editor =  mSharedPreferences.edit();
        editor.putInt("count", ++count);    //��������
        editor.commit();   //�ύ�޸�
       
    }

	private void init() {
		mSwitchLayout = (SwitchLayout) findViewById(R.id.switchLayoutID);
		mLinearLayout = (LinearLayout) findViewById(R.id.linerLayoutID);
		
		//�õ��ӿؼ��ĸ���
		mViewCount = mSwitchLayout.getChildCount();
		mImageView = new ImageView[mViewCount];
		//����imageView
		for(int i = 0;i < mViewCount;i++){
			//�õ�LinearLayout�е��ӿؼ�
			mImageView[i] = (ImageView) mLinearLayout.getChildAt(i);
			mImageView[i].setEnabled(true);//�ؼ�����
			mImageView[i].setOnClickListener(new MOnClickListener());
			mImageView[i].setTag(i);//������view��صı�ǩ
		}
		//���õ�һ��imageView��������
		mCurSel = 0;
		mImageView[mCurSel].setEnabled(false);
		mSwitchLayout.setOnViewChangeListener(new MOnViewChangeListener());
		
	}
	
	//�ײ����������¼��ļ�����
	private class MOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			int pos = (Integer) v.getTag();
			System.out.println("pos:--" + pos);
			//���õ�ǰ��ʾ��ImageView
			setCurPoint(pos);
			//�����Զ���ؼ��е��ĸ��ӿؼ�չʾ�ڵ�ǰ��Ļ��
			mSwitchLayout.snapToScreen(pos);
		}
	}
	

	/**
	 * ���õ�ǰ��ʾ��ImageView����ǰ��ʾ�ڼ�ҳ�ײ��������ǰ�ɫ�����ɵ����������ҳ�ǻ�ɫ�����Ե����
	 * @param pos
	 */
	private void setCurPoint(int pos) {
		if(pos < 0 || pos > mViewCount -1 || mCurSel == pos)
			return;
		//��ǰ��imgaeView�����Ա�����
		mImageView[mCurSel].setEnabled(true);
		//��Ҫ��ת��ȥ���Ǹ�imageView��ɲ��ɼ���
		mImageView[pos].setEnabled(false);
		mCurSel = pos;
	}
	
	//�Զ���ؼ���View�ı���¼�����
	private class MOnViewChangeListener implements OnViewChangeListener{
		@Override
		public void onViewChange(int view) {
			System.out.println("view:--" + view);
			if(view < 0 || mCurSel == view){
				return ;
			}else if(view > mViewCount - 1){
				//���������������ʱ��activity�ᱻ�ر�
				System.out.println("finish activity");
				finish();
			}
			setCurPoint(view);//���õײ����������ʾ
		}
		
	}
	
}