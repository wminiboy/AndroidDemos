package com.xmz.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
    private MovieLayout movieLayout;
    private MovieAdapter adapter;
    
    private HorizontalListView mHorizontalListView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        movieLayout=(MovieLayout)findViewById(R.id.movieLayout);
        adapter=new MovieAdapter(this);
        
        for(int i=0;i<10;i++){
        	
	        Map<String,Object> map=new HashMap<String,Object>();
	        map.put("image", getResources().getDrawable(R.drawable.image));
	        map.put("text", "µÁ”∞"+(i+1));
	        
	        adapter.addObject(map);
        }
        
        //movieLayout.setAdapter(adapter);
        
        
        mHorizontalListView = (HorizontalListView) findViewById(R.id.horizontalLayout);
        mHorizontalListView.setAdapter(adapter);

    }
}