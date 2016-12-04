package com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.whu.Gongyinchao.schoolservice.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Integer> data;

	public ImageAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.image_adapter_item, null);
			viewHolder.view1 = (ImageView) convertView.findViewById(R.id.iv_image1);
			//viewHolder.view2=(ImageView) convertView.findViewById(R.id.iv_image2);
			//viewHolder.view3=(ImageView) convertView.findViewById(R.id.iv_image3);
//			ImageView view = new ImageView(context);
//			view.setImageResource(R.drawable.user_icon);
//			convertView = view;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}
	class ViewHolder{
		ImageView view1,view2,view3;
	}

}
