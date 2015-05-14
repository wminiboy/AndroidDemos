package com.example.layoutparamsdemo;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LayoutParamsDemo extends Activity {

    private final static String TAG = "LayoutParamsDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = new TextView(this);
        tv.setText("hehe");
        tv.setBackgroundColor(Color.BLUE);

        /*
         * 解析布局xml文件 对于普通的xml文件可以用getXml()获取
         */
        XmlResourceParser xrp = this.getResources().getLayout(
                R.layout.test_layout);

        try {
            /* 判断是否到了文件的结尾 */
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                // 文件的内容的起始标签开始，注意这里的起始标签是test.xml文件里面<resources>标签下面的第一个标签
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {

                    /* 获取一个标签的name */
                    String tagname = xrp.getName();

                    /* 的到一个属性集合 */
                    AttributeSet attrs = Xml.asAttributeSet(xrp);

                    Log.d(TAG, "count = " + attrs.getAttributeCount() + "\n");
                    Log.d(TAG, "tagname = " + tagname);

                    Log.d(TAG, "0 = " + xrp.getAttributeValue(0));
                    Log.d(TAG, "1 = " + xrp.getAttributeValue(1));

                    /* 将该属性集合传递给布局参数 */
                    tv.setLayoutParams(new LinearLayout.LayoutParams(this,
                            attrs));

                } else if (xrp.getEventType() == XmlResourceParser.END_TAG) {

                } else if (xrp.getEventType() == XmlResourceParser.TEXT) {

                }

                /* 解析下一个标签 */
                xrp.next();
            }

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.ID_ROOT_VIEW);
        layout.addView(tv);

        /* 当addview指定布局时，会覆盖view自己的布局参数 */
        //layout.addView(tv, new LinearLayout.LayoutParams(50,50));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
