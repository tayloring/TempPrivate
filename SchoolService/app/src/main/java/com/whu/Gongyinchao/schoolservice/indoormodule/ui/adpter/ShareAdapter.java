package com.whu.Gongyinchao.schoolservice.indoormodule.ui.adpter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.data.ShareData;

import java.util.List;

/**
 * Created by panfei on 16/4/26.
 */
public class ShareAdapter extends BaseAdapter{

    private List<ShareData.ContentBean> mDataSet;

    public void setDataSet(List<ShareData.ContentBean> mDataSet) {
        this.mDataSet = mDataSet;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mDataSet == null) {
            return 0;
        }

        return mDataSet.size();
    }

    @Override
    public ShareData.ContentBean getItem(int position) {
        if (mDataSet == null) {
            return null;
        }

        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShareData.ContentBean item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.indoor_share_item, null);
            holder.mTitle = (TextView) convertView.findViewById(R.id.share_title);
            holder.mAbstract = (TextView) convertView.findViewById(R.id.share_abstract);
            holder.mReporter = (TextView) convertView.findViewById(R.id.share_reporter);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitle.setText(item.getTitle());
        holder.mAbstract.setText(item.getAbstractX());
        holder.mReporter.setText(item.getReporter());

        return convertView;
    }

    static class ViewHolder {
        TextView mTitle;
        TextView mAbstract;
        TextView mReporter;
    }
}
