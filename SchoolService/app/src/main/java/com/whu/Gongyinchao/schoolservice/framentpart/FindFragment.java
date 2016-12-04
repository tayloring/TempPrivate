package com.whu.Gongyinchao.schoolservice.framentpart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whu.Gongyinchao.schoolservice.R;

public class FindFragment extends Fragment  {
	private Button message, friends;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		System.out.println("FindFragment");


		View view = inflater.inflate(R.layout.find_main, container, false);
      message=(Button)view.findViewById(R.id.messge);
		friends=(Button)view.findViewById(R.id.frieds);

	/*	message.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MainLoginActivity.class));
			}
		});*/

		friends.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), com.whu.Gongyinchao.schoolservice.chatmodule.activity.MainActivity.class));
			}
		});



  return view;
	}


	@Override
	public void onPause(){
		super.onPause();
		Log.d("TAG","indoorFragment暂停");

	}


}
