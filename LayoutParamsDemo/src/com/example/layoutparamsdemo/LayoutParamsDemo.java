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
         * ��������xml�ļ� ������ͨ��xml�ļ�������getXml()��ȡ
         */
        XmlResourceParser xrp = this.getResources().getLayout(
                R.layout.test_layout);

        try {
            /* �ж��Ƿ����ļ��Ľ�β */
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                // �ļ������ݵ���ʼ��ǩ��ʼ��ע���������ʼ��ǩ��test.xml�ļ�����<resources>��ǩ����ĵ�һ����ǩ
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {

                    /* ��ȡһ����ǩ��name */
                    String tagname = xrp.getName();

                    /* �ĵ�һ�����Լ��� */
                    AttributeSet attrs = Xml.asAttributeSet(xrp);

                    Log.d(TAG, "count = " + attrs.getAttributeCount() + "\n");
                    Log.d(TAG, "tagname = " + tagname);

                    Log.d(TAG, "0 = " + xrp.getAttributeValue(0));
                    Log.d(TAG, "1 = " + xrp.getAttributeValue(1));

                    /* �������Լ��ϴ��ݸ����ֲ��� */
                    tv.setLayoutParams(new LinearLayout.LayoutParams(this,
                            attrs));

                } else if (xrp.getEventType() == XmlResourceParser.END_TAG) {

                } else if (xrp.getEventType() == XmlResourceParser.TEXT) {

                }

                /* ������һ����ǩ */
                xrp.next();
            }

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.ID_ROOT_VIEW);
        layout.addView(tv);

        /* ��addviewָ������ʱ���Ḳ��view�Լ��Ĳ��ֲ��� */
        //layout.addView(tv, new LinearLayout.LayoutParams(50,50));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
