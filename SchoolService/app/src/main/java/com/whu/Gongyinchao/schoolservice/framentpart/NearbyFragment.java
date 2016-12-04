package com.whu.Gongyinchao.schoolservice.framentpart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whu.Gongyinchao.schoolservice.R;

public class NearbyFragment extends Fragment	{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("NearbyFragment");
		return inflater.inflate(R.layout.main_nearby, container, false);
	}
}
