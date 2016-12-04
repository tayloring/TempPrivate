package com.whu.Gongyinchao.schoolservice.searchmodule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.uikit.WebViewActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/10/27.
 */

public class SearchAdapter extends BaseAdapter {


    private List<Map<String,Object>> data;
    Context context;

    public SearchAdapter(Context context,List<Map<String,Object>>  data){
        super();
        this.data=data;
        this.context=context;

    }

    @Override
    public int getCount() {


        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final SearchAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.lecture_search_item ,null);
            viewHolder = new SearchAdapter.ViewHolder();
            viewHolder.content= (TextView) convertView
                    .findViewById(R.id.content);
            // gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            //viewHolder.imageView.setAdapter(new ImageAdapter(context));
            viewHolder.teacher= (TextView) convertView
                    .findViewById(R.id.teacher);
            viewHolder.time = (TextView) convertView
                    .findViewById(R.id.time);
            // viewHolder.button = (ImageButton) convertView
            // .findViewById(R.id.imgButton);



            convertView.setTag(viewHolder);
        }else{
            viewHolder = (SearchAdapter.ViewHolder) convertView.getTag();
        }



        // viewHolder.imageView.setBackgroundResource((Integer)data.get(position).get("usershare"));
        viewHolder.content.setText((String)data.get(position).get("content"));
        viewHolder.teacher.setText((String)data.get(position).get("teacher"));
        viewHolder.time.setText((String)data.get(position).get("time"));
        viewHolder.murl=((String)data.get(position).get("url"));
        viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volley_get(viewHolder.id);

                Intent intent = new Intent();
               // intent.putExtra("url", );
                intent.setClass(context, WebViewActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;


    }


    private static class ViewHolder {
        ImageView imageView;
        TextView content;
        TextView teacher;
        TextView time;
        String murl;
        ImageButton button;

    }


}
