package com.whu.Gongyinchao.schoolservice.indoormodule.ui.adpter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.data.CourseData;
import com.whu.Gongyinchao.schoolservice.common.data.CourseData.CoursesBean;
import com.whu.Gongyinchao.schoolservice.common.uikit.MapUtilActivity;

import java.util.List;

/**
 * Created by panfei on 16/4/25.
 */
public class CourseAdapter extends BaseAdapter {

    private List<CourseData.CoursesBean> mDataSet;
    private String[] week = new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

    public void setDataSet(List<CoursesBean> mDataSet) {
        this.mDataSet = mDataSet;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mDataSet == null) {
            return 0;
        }

        return mDataSet.size() * 5;
    }

    @Override
    public CoursesBean getItem(int position) {
        if (mDataSet == null) {
            return null;
        }
        return mDataSet.get(position % mDataSet.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.course_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.course_name);
            holder.teacher = (TextView) convertView.findViewById(R.id.course_teacher);
            holder.member = (TextView) convertView.findViewById(R.id.course_members);
            holder.time = (TextView) convertView.findViewById(R.id.course_time);
            holder.route = (Button) convertView.findViewById(R.id.course_route);
            convertView.setTag(holder);
        }

        final CoursesBean item = getItem(position);
        holder = (ViewHolder) convertView.getTag();
        holder.name.setText(item.getName());
        holder.teacher.setText(item.getTeacher());
        holder.member.setText(memberFormat(item.getMember()));
        holder.time.setText(timeFormat(item.getCoursetime(), item.getAddress()));
        holder.route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), MapUtilActivity.class);
                intent.putExtra("use", "route");
                intent.putExtra("startend", new String[]{null, "武汉大学-" + item.getAddress().getDistrict() + item.getAddress().getBuild()});
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private String timeFormat(CoursesBean.CoursetimeBean coursetime, CoursesBean.AddressBean address) {
        return coursetime.getWeek().get(0) + "-" + coursetime.getWeek().get(1) + "  " + week[coursetime.getDay() - 1] + "  "
                + coursetime.getTime().get(0) + "-" + coursetime.getTime().get(1) + "节" + "  "
                + address.getDistrict() + ", " + address.getBuild() + "-" + address.getRoom();
    }

    private String memberFormat(int member) {
        return member + "人参加";
    }

    private static class ViewHolder{
        TextView name;
        TextView teacher;
        TextView member;
        TextView time;
        Button route;
    }
}
