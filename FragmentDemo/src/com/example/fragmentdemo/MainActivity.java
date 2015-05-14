package com.example.fragmentdemo;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    FragmentManager fragmentManager;

    ArticleListFragment fragmentA ;
    ArticleReaderFragment fragmentR;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    fragmentManager = getFragmentManager();
	    
        fragmentA = new ArticleListFragment();
        fragmentR = new ArticleReaderFragment();
        
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();       
        fragmentTransaction.add(R.id.container, fragmentA);
        fragmentTransaction.add(R.id.container, fragmentR);
        
        fragmentTransaction.hide(fragmentA);
        fragmentTransaction.hide(fragmentR);
        
        fragmentTransaction.commit();
        
        
        View view = findViewById(R.id.container);
        System.out.println("view add" + view);
	    
		this.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    
			    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			    
			    if(fragmentR.isVisible()){
		             fragmentTransaction.hide(fragmentR);
			    }
                fragmentTransaction.show(fragmentA);
                fragmentTransaction.commit();
			}
		});
		
		this.findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


			    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			    
                if(fragmentA.isVisible()){
                    fragmentTransaction.hide(fragmentA);
               }
               fragmentTransaction.show(fragmentR);
               fragmentTransaction.commit();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
