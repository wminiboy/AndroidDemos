package com.example.acitonbardemo;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ShareActionProvider;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 设置显示acitonBar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);
		
		// 在activity 中添加  android:uiOptions="splitActionBarWhenNarrow" 属性
		// 那么当空间不够时，会在底部，分出一个acitonBar 
		
		final ActionBar bar = getActionBar();
		
		/**
		 * 设置ActionBar 标题上的显示内容
		 * 
		 * DISPLAY_HOME_AS_UP		显示为回退，此时可以被点击，并回调onOptionsItemSelected()
		 * DISPLAY_SHOW_CUSTOM	显示自定义的View
		 * DISPLAY_SHOW_TITLE		      显示标题
		 * DISPLAY_SHOW_HOME		显示主程序标识
		 * DISPLAY_USE_LOGO			      显示LOGO，前提是DISPLAY_SHOW_HOME被设置
		 * 
		 * 设置方式：setDisplayOptions(int option, int mask);
		 * mask：不需要显示的项  XXX|XXX
		 * option：要显示的项并且包含在mask中 XXX|XXX
		 * 注：只有mask指定的option才会被改变，及要被清除然后才可以被设置。
		 */
		
//		bar.setDisplayOptions(0);			//清除之前的设置
//		bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
//		
//		bar.setDisplayOptions(0);
//		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM );
//		bar.setCustomView(R.layout.custom_view); 	//也可以调用带布局的方法，指定布局
//
//		bar.setDisplayOptions(0);
//		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_HOME);
//		
//		bar.setDisplayOptions(0);
//		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_SHOW_TITLE);
		
		bar.setDisplayOptions(0);
		bar.setLogo(R.drawable.blue_sunshine);
		bar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_HOME, 
				ActionBar.DISPLAY_USE_LOGO  | ActionBar.DISPLAY_SHOW_HOME);
		
		/**
		 * 设置ActionBar的显示模式
		 * 
		 * NAVIGATION_MODE_STANDARD	默认
		 * NAVIGATION_MODE_LIST				带一个spinner来进行导航
		 * NAVIGATION_MODE_TABS				设置为tab的导航
		 */
		
		/**
		 * 通过配合内部类ActionBar.OnNavigationListener实现
		 */
//		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		
//		String[] adapterData = new String[] { "HaHa", "HiaHia", "GaGa"};						
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_navigation, R.id.textView1, adapterData);
//		
//		bar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
//			
//			@Override
//			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//				// TODO Auto-generated method stub
//		        Toast.makeText(MainActivity.this, "ItemPosition: " + itemPosition,  Toast.LENGTH_SHORT).show();
//				return false;
//			} 
//		});
		
		/**
		 * 通过内部类 ActionBar.Tab与ActionBar.TabListener结合完成
		 * Tab下面的每一个内容为一个Fragment，所以会涉及到Fragment的一下操作
		 */
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		bar.addTab(bar.newTab()
				.setText("Tab1")
				.setTabListener(new TabListener(new TabContentFragment("One..."))));
		
		bar.addTab(bar.newTab()
				.setText("Tab2")
				.setTabListener(new TabListener(new TabContentFragment("Two..."))));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// 通过在xml资源文件中定义一个Item并添加到menu中，其中item的属性在xml中设定
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		// add中的内容作为title
        MenuItem actionItem = menu.add("Action Button delete...");
        // 设置Item如何显示在acitonBar中
        // MenuItem.SHOW_AS_ACTION_IF_ROOM : 如果有空间就显示
        // MenuItem.SHOW_AS_ACTION_WITH_TEXT : 和Text一起显示
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // 设置Item的Icon
        actionItem.setIcon(android.R.drawable.ic_menu_delete);
	
        // 添加一个带subMenu的Menu
        SubMenu subMenu = menu.addSubMenu("Action Button add...");
        MenuItem actionItem1 = subMenu.getItem();
        actionItem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        actionItem1.setIcon(android.R.drawable.ic_menu_add);
        
    	subMenu.add(Menu.NONE, Menu.FIRST + 1, 1, "本地");
		subMenu.add(Menu.NONE, Menu.FIRST + 2, 2, "网络");
		
		MenuItem actionItem2 = menu.add("Action Button share...");
        actionItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_ALWAYS);
        actionItem2.setIcon(android.R.drawable.ic_menu_share);
        
        /**
         * ShareActionProvider是一个特殊的ActionProvoider，也可以自己是实现一个Provider
         * (未完成，用的。。。)
         */
        ShareActionProvider actionProvider = new  ShareActionProvider(this);
        //actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        //actionProvider.setShareHistoryFileName("custom_share_history.xml");
        
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        //Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
        //shareIntent.putExtra(Intent.EXTRA_STREAM, uri.toString());
        actionProvider.setShareIntent(shareIntent);

        // 设置Item的
        actionItem2.setActionProvider(actionProvider);
       
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
    
    private class TabListener implements ActionBar.TabListener {
        private TabContentFragment mFragment;

        public TabListener(TabContentFragment fragment) {
            mFragment = fragment;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
        	Toast.makeText(MainActivity.this, "onTabSelected!", Toast.LENGTH_SHORT).show();
            ft.add(R.id.fragment_content, mFragment, mFragment.getText());
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        	Toast.makeText(MainActivity.this, "onTabUnselected!", Toast.LENGTH_SHORT).show();
            ft.remove(mFragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(MainActivity.this, "onTabReselected!", Toast.LENGTH_SHORT).show();
        }
    }

    public class TabContentFragment extends Fragment {
        private String mText;

        public TabContentFragment(String text) {
            mText = text;
        }

        public String getText() {
            return mText;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.action_bar_tab_content, container, false);

            TextView text = (TextView) fragView.findViewById(R.id.text);
            text.setText(mText);

            return fragView;
        }
    }
}

/* 两个函数的具体实现
 * 		 public void setDisplayOptions(int options) {
       			if ((options & DISPLAY_HOME_AS_UP) != 0) {
            	     mDisplayHomeAsUpSet = true;
                }
                mActionView.setDisplayOptions(options);
          }

    	public void setDisplayOptions(int options, int mask) {
        	final int current = mActionView.getDisplayOptions(); 
        	if ((mask & DISPLAY_HOME_AS_UP) != 0) {
            	mDisplayHomeAsUpSet = true;
        	}
        	mActionView.setDisplayOptions((options & mask) | (current & ~mask));
    	}
 * */
