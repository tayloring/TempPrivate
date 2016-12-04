package com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.ui;

import android.app.Activity;
import android.os.Bundle;

import com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.ui.MyListView.IXListViewListener;

import java.util.ArrayList;

public class MainExpressUI extends Activity implements IXListViewListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<String> list = new ArrayList<String>();
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");


		//MyListView lv = new MyListView(this, list);
	//	lv.setXListViewListener(this);
		MyListView lv = new MyListView(this,list);
		lv.setXListViewListener(this);
		setContentView(lv);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, list);
		//lv.setAdapter(new MyAdapter(list, this));
		lv.setAdapter(new MyAdapter(list,this));
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		System.out.println("lln---onRefresh");
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		System.out.println("lln---onLoadMore");
	}

}
