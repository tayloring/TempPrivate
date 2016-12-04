package com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
	ArrayList<String> data;
	Context context;

	public MyAdapter(ArrayList<String> list, Context context) {
		data = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.circle_item, null);
			viewHolder = new ViewHolder();
			//viewHolder.gridView = (GridView) convertView
					//.findViewById(R.id.gridView);
			// gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
			viewHolder.gridView.setAdapter(new ImageAdapter(context));
			viewHolder.moment= (TextView) convertView
					.findViewById(R.id.content);
			viewHolder.more = (TextView) convertView
					.findViewById(R.id.more);
			//viewHolder.button = (ImageButton) convertView
				//	.findViewById(R.id.imgButton);
			viewHolder.button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});
			viewHolder.more.getViewTreeObserver().addOnPreDrawListener(
					new OnPreDrawListener() {

						@Override
						public boolean onPreDraw() {
							// TODO Auto-generated method stub
							if (viewHolder.moment.getLineCount() >= 4) {
								viewHolder.more.setVisibility(View.VISIBLE);
							}
							return true;
						}
					});
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	private static class ViewHolder {
		GridView gridView;
		TextView moment;
		TextView more;
		ImageButton button;
		
	}

}
