package com.example.viewpagedemo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
    private final static String TAG = "ViewPagerDemo";
    
    private ViewPager mViewPager;
	private PagerTitleStrip mPagerTitleStrip;
	
    final ArrayList<View> views = new ArrayList<View>();
    
    private PagerAdapter mPagerAdapter ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        //mPagerTitleStrip = (PagerTitleStrip)findViewById(R.id.pagertitle);
        
        //��Ҫ��ҳ��ʾ��Viewװ��������
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.view1, null);
        View view2 = mLi.inflate(R.layout.view2, null);
        View view3 = mLi.inflate(R.layout.view3, null);
        
        views.add(view1);
        views.add(view2);
        views.add(view3);
        
        //������һ��page
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		        LayoutInflater mLi = LayoutInflater.from(MainActivity.this);
		        View view = mLi.inflate(R.layout.view2, null);
		        views.add(view);
				
				mPagerAdapter.notifyDataSetChanged();
			}
		});
        
        //���ɾ��һ��page
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				views.remove(views.size()-1);
				
				mPagerAdapter.notifyDataSetChanged();
			}
		});

        //���ViewPager������������
        mPagerAdapter = new PagerAdapter() {
        	
        	/**
        	 * ViewPage ʹ��˵�������ж�ҳpageʱ
        	 * ����̬������3ҳpage���ݣ���ǰҳ��������ҳ����ҳ
        	 * �����ұ߽�ʱ����2ҳ���ݣ���ǰҳ����ҳ����ҳ
        	 */
        	
        	/**
        	 * PageView�ڲ�û��ֱ�ӱ���һ��View ,���Ǳ���һ��objectȻ���View������ϵ
        	 * Ϊ�����ɾ��һ��page����������Ҫ�������ϵ����������������Ϊ�жϣ�����
        	 */
        	private HashMap<Object, View> mHashMap = new HashMap<Object, View> ();

			@Override
			public int getCount() {
				Log.d("MainActivity", "in getCount size = " + views.size());
				return views.size();
			}

			/**
			 * ���ɣ�adapter �е�list���Ƕ�����˭���ͽ������أ���Ϊ��View��object.
			 * ��������getItemPosition��ʱ��1����objectǿתΪԭʼ���ͣ�Ȼ�󣬵�ǰlist ��contains ,�޾ͷ���POSITION_NONE�� ���򣬷���contains�е�λ��
			 */
			@Override
			public Object instantiateItem(View container, int position) {
				Log.d(TAG, "in instantiateItem position = " + position);	

				/* ע�⣺
				 * addView ����Ĳ��ֲ�����������
				 * ��ʹ��ViewPage.LayotuparmasҲ�������ã�����
				 * */
                ((ViewPager)container).addView(views.get(position));
				

                
				//new һ�� Object ������Ϊkey
				Object object = new Object();
				
				mHashMap.put(object, views.get(position));
				
				return object;
				
				//���ֻ���򵥹̶���Ŀ��viewpage������ֱ�ӷ���view������Ϊ�Լ���key
				//��������ʡȥ����view��object��ӳ���ϵ
				//return views.get(position);
			}
			
			@Override
			public void destroyItem(View container, int position, Object object) {
				Log.d("MainActivity", "in destroyItem pos = " +   position);
				//ɾ��page����ʾ��view
				((ViewPager)container).removeView(mHashMap.get(object));
				
				mHashMap.remove(object);
			}
			
			/**
			 *  �ڲ���ͨ�����ø÷������жϲ���veiw�ǲ��Ǳ����object key����Ӧ��view
			 */
			@Override
			public boolean isViewFromObject(View view, Object object) {
				// TODO Auto-generated method stub
				Log.d(TAG, "isViewFromObject object=" + object);
				//Log.d(TAG, "isViewFromObject view = " + view + " object=" + object);
				
				return view == mHashMap.get(object);
				
				//�����view�Լ���Ϊkeyʱ�����Լ��жϼ���
				//return arg0 == arg1;
			}
			
			/**
			 *  �÷�����һ������Ҫ�ķ������ر�����ɾ�����ݣ�Ȼ��
			 *  ͨ��adapter notifiyDataChange֪ͨ���ݸı�ʱ�ú����ᱻ����
			 *  
			 *  �����object��Ӧ��View��λ��û�з����ı䣬����PagerAdapter.POSITION_UNCHANGED;
			 *  �����object��Ӧ��View�Ѿ������ڣ�����PagerAdapter.POSITION_NONE
			 *  ���򣬷��ظ�object��λ�á�
			 */
			public int getItemPosition (Object object){	
				
				Log.d(TAG, "in getItemPosition  object = " + object);
				
				//����ǽ�view������Ϊobject���أ���ô����ط�Ҳ���ԣ�View view = (View)ojbect. �Ͳ���Ҫ������map����
				//
				View view = mHashMap.get(object);
				
				if(views.contains(view)){
					return views.lastIndexOf(view);
					//return PagerAdapter.POSITION_UNCHANGED;
				}else {
					return PagerAdapter.POSITION_NONE;
				}

			}
        };		
		
		mViewPager.setAdapter(mPagerAdapter);
    }
}

