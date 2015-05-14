package com.sunplus.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
/**
 * �Զ���һ�ֲ���
 * @author jin.ma
 *
 */
public class SwitchLayout extends ViewGroup {

	private int mCurScreen ;
	private static final int SNAP_VELOCITY = 600;//˲���ٶ�
	private Scroller mScroller ;//����������
	private VelocityTracker mVelocityTracker;//�϶����Ƶ����ʸ�����
	private float mLastMotionX;
	private Context context;
	private OnViewChangeListener onViewChangeListener;//�ı�imageView
	
	public SwitchLayout(Context context) {
		super(context);
		this.context = context;
		init(context);
	}
	
	public SwitchLayout(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    this.context = context;
	    init(context);
	}
	
	public SwitchLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(context);
    }
	//��ʼ���ؼ�
	private void init(Context context) {
		mCurScreen = 0;
		mScroller = new Scroller(context);
	}

	/**
	 * ����Ҫ���õķ���
	 * Ŀ�ģ���ǰ��view�ڸ��Լ����ӿؼ�ָ�ɴ�С��λ�ñ���Ҫ���õķ���
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(changed){
			int childLeft = 0;
			int childCount = getChildCount();
			for(int i = 0;i < childCount;i++){
				View childView = getChildAt(i);
				if(childView.getVisibility() != View.GONE){
					int childWidth = childView.getMeasuredWidth();
					//�ӿؼ�������ڸ��ؼ���(�ڶ����׶Σ���һ���׶�ΪonMeasure��������)
					childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
					childLeft += childWidth;//�ı���ߵ���ʼλ��
				}
			}
		}
	}
	/**
	 * ����ؼ��Ŀ�͸�
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int count = getChildCount();
		for(int i = 0;i < count;i++){
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		//��������һ����ͨ��width������
		scrollTo(mCurScreen*width, 0);//Set the scrolled position of your view.
	}

	public void setOnViewChangeListener(OnViewChangeListener changeListener){
		this.onViewChangeListener = changeListener;
	}

	/**
	 * �����Զ���ؼ��е��ĸ��ӿؼ�չʾ�ڵ�ǰ��Ļ��
	 * @param pos
	 */
	public void snapToScreen(int pos) {
		System.out.println("��ǰ��λ�ã�" + pos);
		if(getScrollX() != (pos * getWidth())){
			int destina = pos * getWidth() - getScrollX();//Ҫ�ƶ��ľ���
			//��ʼ����������ʼ�ؿ�ʼ������������Ϊ�����ľ��롣����������ʾ
			
			mScroller.startScroll(getScrollX(), 0, destina, 0);
			mCurScreen = pos;
			invalidate();//�����߳��е�������������ػ�view�����������߳��е���
			//�ı�imageView����ʾ(����guide_round.xml���Կ�������״̬enabled��
			//��ʱ���úڵ��ʾ��Ҳ���ǲ��ǵ�ǰѡ�С�)
			System.out.println("ImageView�ı����" + onViewChangeListener);
			if(onViewChangeListener != null){
				onViewChangeListener.onViewChange(pos);
			}
		}
		
	}

	
	@Override
	public void computeScroll() {
		if(mScroller.computeScrollOffset()){//computeScrollOffset()����false��ʾ��������
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());//Set the scrolled position of your view.
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Return the kind of action being performed -- 
		//one of either ACTION_DOWN, ACTION_MOVE, ACTION_UP, or ACTION_CANCEL.
		int sAction = event.getAction();
		/**
		 * ��Ļ�ϵ�ÿһ�δ������ᱻonTouchEvent���񵽣����Դ�event�õ���x��y��ֵ��
		 * �ر�ע�⣺���ҵõ������㵱ǰ�Ĵ����x��y�����ֵ��Ҳ����˵�����󻮶��Ļ���
		 * ���x��ֵ�Ǳ�С�ġ�
		 */
		float x = event.getX();
//		System.out.println("onTouchEvent--" + x);
		switch (sAction) {
		//����
		case MotionEvent.ACTION_DOWN:
			if(mVelocityTracker == null){
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(event);
			}
			if(!mScroller.isFinished())
				//��ֹscroller���������յ�x��y��λ��
				mScroller.abortAnimation();
			mLastMotionX = x;
			System.out.println("mLastMotionX--" + mLastMotionX);
			break;
		//�ƶ�
		case MotionEvent.ACTION_MOVE:
			int deltalX = (int)(mLastMotionX - x);//��ʼλ�õĴ����뵱ǰλ�õĲ�ֵ
			if(canMove(deltalX)){
				if(mVelocityTracker != null){
					mVelocityTracker.addMovement(event);
				}
				mLastMotionX = x;
				//�ؼ�������λ��
				scrollBy(deltalX, 0);
			}
			break;
		//����
			/**
			 * ���󻬶�����Ϊx���ڼ�С�����Ժ��������Ǹ�ֵ��
			 * ���һ�������Ϊx�����������Ժ�����������ֵ��
			 */
		case MotionEvent.ACTION_UP:
			int sVelocityX = 0;
			if(mVelocityTracker != null){
				mVelocityTracker.addMovement(event);
				mVelocityTracker.computeCurrentVelocity(1000);//����1s�������ٶ�
				System.out.println("tracker -- "+mVelocityTracker);
				sVelocityX = (int) mVelocityTracker.getXVelocity();//�õ����յĺ�������
				System.out.println("��������--" + sVelocityX);
				System.out.println("mCurScreen--" + mCurScreen);
			}
			//���ǵ�һ�����������һ���
			if(sVelocityX > SNAP_VELOCITY && mCurScreen >0)
				snapToScreen(mCurScreen - 1);//�����һ��Ļ
			//���󻬶����Ҳ������һ��
			else if(sVelocityX < -SNAP_VELOCITY && mCurScreen < (getChildCount() -1)){
				snapToScreen(mCurScreen + 1);//��ǰ��һ��Ļ
			}
			//���󻬶����������һ��,����ֱ��finish��activity
			else if(sVelocityX == 0 && mCurScreen == (getChildCount() -1)){
				System.out.println("-----------");
				Activity activity = (Activity) context;
				Intent intent = new Intent();
            	intent.setClass(context, com.sunplus.view.OtherActivity.class);
            	context.startActivity(intent);
            	activity.finish();
			}
			//���ʲ������ǣ���һ����ת��ʽ
			else{
				snapToDestination();
			}
			if(mVelocityTracker != null){
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			break;
		}
		return true;
	}

	//��������һ��
	private void snapToDestination() {
		int screenWidth = getWidth();
		//���ջ�����λ�ó���1\2ʱ���Ź���������destScreen�õ����ǵ�ǰ����ֵ��
		int destScreen = (getScrollX()  + screenWidth/2)/screenWidth;
		snapToScreen(destScreen);
		
	}

	//�ɹ���������
	private boolean canMove(int deltalX) {
		//getScrollX()���õ�����view����ߵ�x�������,����ֵ�Ǵӵ�һ��ͼ�����һ��ͼ��ģ�
		System.out.println("getScrollX() = "+getScrollX());
		//Ҳ����˵��getScrollX()�õ���ֵ��  ������ͼչ����Ȼ����Ե��Ǹ�x���ꡣ
		if(getScrollX() <= 0 && deltalX <0){//��ʾ���ǵ�һ��ͼ�����������һ�
			return false;
		}
		//��ʾ�������һ��ͼ����������
		if(getScrollX() >= (getChildCount()-1)*getWidth() && deltalX > 0){
			return false;
		}
		return true;
	}
	
	
	
	

}
