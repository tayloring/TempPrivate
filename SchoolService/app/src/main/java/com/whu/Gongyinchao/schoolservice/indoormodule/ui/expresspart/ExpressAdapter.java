package com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;

import java.util.List;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/10/26.
 */

public class ExpressAdapter extends BaseAdapter {

    private List<Map<String,Object>>  data;
    Context context;

 public ExpressAdapter(Context context,List<Map<String,Object>>  data){
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


        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.circle_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView= (ImageView) convertView
                    .findViewById(R.id.usershare);
            // gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            //viewHolder.imageView.setAdapter(new ImageAdapter(context));
            viewHolder.content= (TextView) convertView
                    .findViewById(R.id.content);
           // viewHolder.more = (TextView) convertView
                  //  .findViewById(R.id.more);
           // viewHolder.button = (ImageButton) convertView
                   // .findViewById(R.id.imgButton);



            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ExpressAdapter.ViewHolder) convertView.getTag();
        }



        viewHolder.imageView.setBackgroundResource((Integer)data.get(position).get("usershare"));
        viewHolder.content.setText((String)data.get(position).get("content"));



        return convertView;





    }


    private static class ViewHolder {
        ImageView imageView;
        TextView content;
        TextView more;
        ImageButton button;

    }



}
