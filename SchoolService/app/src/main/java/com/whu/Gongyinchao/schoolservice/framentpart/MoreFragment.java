package com.whu.Gongyinchao.schoolservice.framentpart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.chatmodule.activity.FirstSetActivity;
import com.whu.Gongyinchao.schoolservice.minemodule.ui.MineOfflineActivity;

public class MoreFragment extends BaseFragment{
    private TableRow tableRow0;
    private TableRow tableRow1;
	private TableRow tableRow4;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("MoreFragment");
		View v= inflater.inflate(R.layout.main_mine, container, false);
		tableRow0=(TableRow)v.findViewById(R.id.more_page_row0);
         tableRow1=(TableRow)v.findViewById(R.id.more_page_row1);
         tableRow4=(TableRow)v.findViewById(R.id.more_page_row4);
		tableRow0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(getContext(), FirstSetActivity.class);
				getContext().startActivity(intent);


			}
		});

		tableRow1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		tableRow4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getContext(), MineOfflineActivity.class);
				getContext().startActivity(intent);

			}
		});





  return v;

	}

	@Override
	public View initView() {
		return null;
	}

	@Override
	public void initData() {

	}

	@Override
	public void onPause(){
		super.onPause();
		Log.d("TAG","moreFragment暂停");

	}





}
