package com.hsx.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
	private SwipeRefreshLayout swipeLayout;
	List<String> data = new ArrayList<String>();
	MyAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		ListView listView = (ListView) findViewById(R.id.test_listview);
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
	}

	private void initData() {
		data.add("苏州");
		data.add("北京");
		data.add("上海");
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			swipeLayout.setRefreshing(false);
			adapter.notifyDataSetChanged();
		}

	};

	public void onRefresh() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Random random = new Random();
				data.add("天津" + random.nextInt(100));
				handler.sendEmptyMessage(1000);
			}
		}).start();
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return data.size();
		}

		@Override
		public Object getItem(int position) {

			return data.get(position);
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item, null);
			TextView tvItem = (TextView) convertView.findViewById(R.id.item_tv);
			tvItem.setText(data.get(position));
			return convertView;
		}
	}

}