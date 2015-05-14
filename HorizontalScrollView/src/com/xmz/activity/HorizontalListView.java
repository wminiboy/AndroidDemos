package com.xmz.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class HorizontalListView extends HorizontalScrollView {

	private LinearLayout mLinearLayout;
	private BaseAdapter mAdapter;
	
	public static interface OnItemClickListener{
		void onItemClick(ViewGroup viewGroup, View view, int pos);
	}
	
	public static interface OnItemLongClickListener{
		boolean onItemLongClick(ViewGroup viewGroup, View view, int pos);
	}

	private OnItemClickListener mItemClickListener;
	private OnItemLongClickListener mItemLongClickListener;
	
	private View.OnClickListener mClickListener;
	private View.OnLongClickListener mLongClickListener;
	
	public HorizontalListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public HorizontalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {

		setHorizontalScrollBarEnabled(false);

		mLinearLayout = new LinearLayout(context);
		mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

		addView(mLinearLayout, new LayoutParams(WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		mClickListener =  new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mItemClickListener != null){
					int pos = (Integer) v.getTag();
					mItemClickListener.onItemClick(HorizontalListView.this, v, pos);
				}
			}
		};
		
		mLongClickListener = new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if(mItemLongClickListener != null){
					int pos = (Integer) v.getTag();
					return mItemLongClickListener.onItemLongClick(HorizontalListView.this, v, pos);
				}
				return false;
			}
		};
	}
	
	public void setAdapter(BaseAdapter adapter){
		mAdapter = adapter;
		notifyDataSetChanged();
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		mItemClickListener = listener;
	}
	
	public void setOnItemLongClickListener(OnItemLongClickListener listener){
		mItemLongClickListener = listener;
	}
	
	public void notifyDataSetChanged() {
		
		mLinearLayout.removeAllViews();

		int count = mAdapter.getCount();

		for (int i = 0; i < count; i++) {
			
			View view = mAdapter.getView(i, null, null);
			view.setOnClickListener(mClickListener);
			view.setOnLongClickListener(mLongClickListener);
			view.setTag(i);
			
			mLinearLayout.addView(view);
		}
		
		requestLayout();
	}

}
