package com.example.memerydemo;

import android.view.View;

public class Person {
	private int[] array;
	
	private View.OnClickListener mListener;

	private int[] array2 = new int[2048000];
	
	User u;
	public Person(User uu){
		u = uu;
		array = new int[1024000];
	}
	
	public Person(){
		array = new int[1024000];
	}
	
	public void setListener(View.OnClickListener listener){
		mListener = listener;
	}
	
	public int[] getArray(){
		return array;
	}
	
	public void setUesr(User u){
		this.u = u;
	}
}

class User{
	private Person p;
	private int[] array;
	
	public User(){
		p = new Person();
		array = new int[512000];
	}
	
	public User(Person pp){
		p = pp;
		array = new int[512000];
	}
	
	public Person getPesrson(){
		return p;
	}
	
	public void release(){
		p = null;
	}
}

class Home{
	private User u;
	private Person p;
	private int[] array;
	
	public Home(){
		u = new User();
		array = new int[256000];
	}
	
	public User getUser(){
		return u;
	}
	
	public void setPseron(Person pp){
		p = pp;
	}
}

class Test{
	User u;
	
	public Test(User uu){
		u = uu;
	}
	
}

