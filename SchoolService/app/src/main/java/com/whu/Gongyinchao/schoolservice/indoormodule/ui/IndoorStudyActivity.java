package com.whu.Gongyinchao.schoolservice.indoormodule.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.data.CloudPoiResult;
import com.whu.Gongyinchao.schoolservice.common.data.CloudPoiResult.ContentsBean;
import com.whu.Gongyinchao.schoolservice.common.data.CloudPoiResult.ContentsBean.StudyRoomBean;
import com.whu.Gongyinchao.schoolservice.common.uikit.SSPickerView;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.common.uikit.MapUtilActivity;

import java.util.List;

/**
 * Created by panfei on 16/4/22.
 */
public class IndoorStudyActivity extends BaseActivity{

    private ImageView mBack;
    private TextView mNearest;
    private TextView mNearestClassRoom;
    private TextView mNearestClassRoomInfo;
    private SSPickerView mNears;
    private ListView mNearList;
    private BaseAdapter listAdapter;

    private String mTitle;
    private List<StudyRoomBean> studyRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloudPoiResult result = (CloudPoiResult)(getIntent().getSerializableExtra("poi"));
        if (result == null) {
            finish();
        }

        setContentView(R.layout.indoor_study);
        mBack = (ImageView) findViewById(R.id.indoor_study_back);
        mNearest = (TextView) findViewById(R.id.indoor_study_nearest);
        mNearestClassRoom = (TextView) findViewById(R.id.indoor_study_nearclassroom);
        mNearestClassRoomInfo = (TextView) findViewById(R.id.indoor_study_nearclassinfo);
        mNears = (SSPickerView) findViewById(R.id.indoor_study_nears);
        mNears.setText("附近的自习室");
        mNearList = (ListView) findViewById(R.id.indoor_study_useful);
        listAdapter = new NearByAdapter(result.getContents());
        mNearList.setAdapter(listAdapter);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = result.getContents().get(0).getTitle().replaceAll("武汉大学-", "").replaceAll("武汉大学", "");
        studyRoom = result.getContents().get(0).getStudy_room();
        mNearest.setText("我现在位于 -- " + mTitle);
        mNearestClassRoom.setText(mTitle);
        mNearestClassRoomInfo.setText(format(studyRoom));
    }

    private String format(List<StudyRoomBean> studyRoom) {
        if (studyRoom == null || studyRoom.size() <= 0) {
            return "无可用自习室";
        }

        StringBuilder sb = new StringBuilder();
        for (StudyRoomBean bean : studyRoom) {
            sb.append(bean.getFreeTime()).append(" : ").append(bean.getClassroom()).append("\n");
        }

        return sb.toString();
    }

    private static class NearByAdapter extends BaseAdapter {

        private List<ContentsBean> mDataSet;

        NearByAdapter(List<ContentsBean> dataSet){
            this.mDataSet = dataSet;
        }

        @Override
        public int getCount() {
            return mDataSet.size() - 1;
        }

        @Override
        public ContentsBean getItem(int position) {
            return mDataSet.get(position + 1);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            final ContentsBean item = getItem(position);
            View view = View.inflate(parent.getContext(), R.layout.nearby_classroom_list, null);
            TextView classroomTitle = (TextView) view.findViewById(R.id.classroom_title);
            TextView classroomInstance = (TextView) view.findViewById(R.id.nearby_classroom_instance);
            Button classroomGo = (Button) view.findViewById(R.id.nearby_classroom_go);

            int distance =(int) DistanceUtil.getDistance(BaseApi.getInstance().getCurrentLocLat(), new LatLng(item.getLocation().get(1), item.getLocation().get(0)));
            classroomInstance.setText(distance + "m");
            classroomTitle.setText(item.getTitle().replaceAll("武汉大学-", "").replaceAll("武汉大学", ""));
            classroomGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), MapUtilActivity.class);
                    intent.putExtra("use", "route");
                    intent.putExtra("startend", new String[]{mDataSet.get(0).getTitle(), item.getTitle()});
                    parent.getContext().startActivity(intent);
                }
            });
            return view;
        }
    }
}
