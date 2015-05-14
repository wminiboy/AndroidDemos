package com.example.applistmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/*
 * ListDemoʹ���ĵ�˵����
 * �����´����Ĺ����У����Ƹ�MainActivity.java�����̡�
 * 
 * ��Manifest��ע��Activityʱ�������<intent-filter>���ɣ�
 * 
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.sunplusedu.category.TEST" />
            </intent-filter>
 * 
 * 
 * ע�⣺List������ʾ������Ĭ��Ϊjava�ļ��������붨������������ֻ��Ҫ��ע��activityʱ���label���Լ���
 * 
 * */
public class MainActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new SimpleAdapter(this, getData(),
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 }));
		
		getListView().setTextFilterEnabled(true);
	}

	protected List<Map<String, Object>> getData() {
		
		/* ʵ����һ��List��List���еĽڵ�ֵ��Map */
		List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

		/* ���IntentӦ����һ���ص� ...*/
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory("android.sunplusedu.category.TEST");
		
		/* ���᷵��������ִ�����intent������ */
		PackageManager pm = getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

		if (null == list)
			return myData;

		int len = list.size();

		for (int i = 0; i < len; i++) {
			ResolveInfo info = list.get(i);
			
			String labelSeq = info.loadLabel(pm).toString();				
			String label = null;
			
			if(labelSeq.equals(getString(R.string.app_name))){
				String name = info.activityInfo.name;
				String array[] = name.split("\\.");
				label = array[array.length - 1];			
			} else {
				label = labelSeq;
			}
			
			addItem(myData,
					label,
					activityIntent(
							info.activityInfo.applicationInfo.packageName,
							info.activityInfo.name));
		}

		return myData;
	}

	/**
	 * 
	 * �õ�intent��ָ��ClassName
	 * 
	 * */
	protected Intent activityIntent(String pkg, String componentName) {
	
		System.out.println("pkg:" + pkg + " name:" + componentName);
		
		Intent result = new Intent();
		result.setClassName(pkg, componentName);
		return result;
	}

	/**
	 * 
	 * ��Item�����Map
	 * 
	 * */
	protected void addItem(List<Map<String, Object>> data, String name,
			Intent intent) {
		
		/*
		 * 
		 * Map�а���Intent��name
		 * 
		 * */
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("title", name);
		temp.put("intent", intent);
		data.add(temp);
	}

	/**
	 * 
	 * List Item ����¼�����
	 * 
	 * ��Item�л�ȡMap
	 * 
	 * */
	@Override
	@SuppressWarnings("unchecked")
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		Map<String, Object> map = (Map<String, Object>) l
				.getItemAtPosition(position);

		Intent intent = (Intent) map.get("intent");
		startActivity(intent);
	}

}
