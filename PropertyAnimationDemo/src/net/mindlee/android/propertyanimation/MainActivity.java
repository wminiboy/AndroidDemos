package net.mindlee.android.propertyanimation;

import android.os.Bundle;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.util.Log;
import android.view.View;

/*
 *  另外的一个独立测试Activity，主要是通过ValueAnimator去了解整个属性动画的实现过程
 *  如果要使用，可以修改manifest文件，然后去运行。
 *  通过该例，让你可以自己实现ObjectAnimator
 */

class MData{
    private int age=88;
    private String name="hehe";
    
    public MData(int a, String n){
        age = a;
        name= n;
    }
    
    public String toString(){
        return "xixi" + name;
    }
}

class myType implements TypeEvaluator<Object>{

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        // TODO Auto-generated method stub
        Log.d(MainActivity.TAG, "startV= " + startValue + " endV" + endValue);
        return new MData(99, "funu");
    }
}


public class MainActivity extends Activity {
    public final static String TAG = "PropertyDemo";
    
    int num = 0;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void test(View v){
        //ValueAnimator va =  ValueAnimator.ofFloat(0.5f, 1f);
        ValueAnimator va =  ValueAnimator.ofObject(new myType(), new MData(0, "wawa"), new MData(100, "laotou"), new MData(50,  "zongnian") );
        va.setDuration(600);
        va.addListener(new AnimatorListener() {
            
            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onAnimationStart...");
            }
            
            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onAnimationRepeat...");
                
            }
            
            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onAnimationEnd...");
            }
            
            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onAnimationCancel...");
            }
        });
        
        va.addUpdateListener(new AnimatorUpdateListener() {
            
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                Log.d(TAG, animation.getAnimatedFraction() +"@@" + animation.getCurrentPlayTime() + " time = " + num++);
                Log.d(TAG, animation.getAnimatedValue().toString());
            }
        });
        
        va.start();
    }
}
