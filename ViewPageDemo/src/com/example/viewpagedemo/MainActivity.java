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
        
        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.view1, null);
        View view2 = mLi.inflate(R.layout.view2, null);
        View view3 = mLi.inflate(R.layout.view3, null);
        
        views.add(view1);
        views.add(view2);
        views.add(view3);
        
        //点击添加一个page
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
        
        //点击删除一个page
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				views.remove(views.size()-1);
				
				mPagerAdapter.notifyDataSetChanged();
			}
		});

        //填充ViewPager的数据适配器
        mPagerAdapter = new PagerAdapter() {
        	
        	/**
        	 * ViewPage 使用说明：当有多页page时
        	 * 正常态仅缓存3页page内容，当前页，缓存左页和右页
        	 * 遇左右边界时缓存2页内容，当前页，左页或右页
        	 */
        	
        	/**
        	 * PageView内部没有直接保存一个View ,而是保存一个object然后和View建立关系
        	 * 为了完成删除一个page的任务，所以要把这个关系保存起来，将来作为判断，查找
        	 */
        	private HashMap<Object, View> mHashMap = new HashMap<Object, View> ();

			@Override
			public int getCount() {
				Log.d("MainActivity", "in getCount size = " + views.size());
				return views.size();
			}

			/**
			 * 规律：adapter 中的list的是对象是谁，就将它返回，作为该View的object.
			 * 这样，在getItemPosition的时候，1，将object强转为原始类型，然后，当前list ，contains ,无就返回POSITION_NONE， 否则，返回contains中的位置
			 */
			@Override
			public Object instantiateItem(View container, int position) {
				Log.d(TAG, "in instantiateItem position = " + position);	

				/* 注意：
				 * addView 后面的布局参数不起作用
				 * 即使传ViewPage.Layotuparmas也不起作用，不解
				 * */
                ((ViewPager)container).addView(views.get(position));
				

                
				//new 一个 Object 对象作为key
				Object object = new Object();
				
				mHashMap.put(object, views.get(position));
				
				return object;
				
				//如果只做简单固定数目的viewpage，可以直接返回view对象作为自己的key
				//这样可以省去保存view与object的映射关系
				//return views.get(position);
			}
			
			@Override
			public void destroyItem(View container, int position, Object object) {
				Log.d("MainActivity", "in destroyItem pos = " +   position);
				//删除page上显示的view
				((ViewPager)container).removeView(mHashMap.get(object));
				
				mHashMap.remove(object);
			}
			
			/**
			 *  内部会通过调用该方法，判断参数veiw是不是保存的object key所对应的view
			 */
			@Override
			public boolean isViewFromObject(View view, Object object) {
				// TODO Auto-generated method stub
				Log.d(TAG, "isViewFromObject object=" + object);
				//Log.d(TAG, "isViewFromObject view = " + view + " object=" + object);
				
				return view == mHashMap.get(object);
				
				//如果把view自己作为key时，与自己判断即可
				//return arg0 == arg1;
			}
			
			/**
			 *  该方法是一个很重要的方法，特别是在删除数据，然后
			 *  通过adapter notifiyDataChange通知数据改变时该函数会被调用
			 *  
			 *  如果该object对应的View的位置没有发生改变，返回PagerAdapter.POSITION_UNCHANGED;
			 *  如果该object对应的View已经不存在，返回PagerAdapter.POSITION_NONE
			 *  否则，返回该object的位置。
			 */
			public int getItemPosition (Object object){	
				
				Log.d(TAG, "in getItemPosition  object = " + object);
				
				//如果是将view本身，作为object返回，那么这个地方也可以：View view = (View)ojbect. 就不需要单独的map保存
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

