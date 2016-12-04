package com.whu.Gongyinchao.schoolservice.framentpart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.data.CloudPoiResult;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.indoormodule.Weathermen;
import com.whu.Gongyinchao.schoolservice.indoormodule.interfaces.IndoorCallBack;
import com.whu.Gongyinchao.schoolservice.indoormodule.presenter.IndoorPresenter;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.IndoorClassActivity;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.IndoorCourseActivity;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.GetPublischedExpress;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.lecture.indoorLecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 龚银超 on 2016/10/24.
 */

public class SchoolOneFragment extends BaseFragment implements View.OnClickListener, IndoorCallBack {




    private TextView r1,r2,r3,r4,r5,r6,r7,r8;
    private CloudPoiResult  mCurrentCloudPoiResult;
    private IndoorPresenter  mpresenter;
    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        System.out.println("IndoorFragment");
        View v = inflater.inflate(R.layout.index_tuan_list2, container, false);
        r1=(TextView)v.findViewById(R.id.lecture);
        r2=(TextView)v.findViewById(R.id.fun);
        r3=(TextView)v.findViewById(R.id.express);
        r4=(TextView)v.findViewById(R.id.chaexpress);
        r5=(TextView)v.findViewById(R.id.study);
        r6=(TextView)v.findViewById(R.id.course);
        r7=(TextView)v.findViewById(R.id.bank);
        r8=(TextView)v.findViewById(R.id.more);


        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);
        r4.setOnClickListener(this);
        r5.setOnClickListener(this);
        r6.setOnClickListener(this);
        r7.setOnClickListener(this);
        r8.setOnClickListener(this);


        mpresenter=new IndoorPresenter(getContext(),this);

        return  v;


    }




    @Override
    public void onClick(View v) {

        int i =v.getId();
        if(i==R.id.lecture){

            Intent intent = new Intent();
            intent.setClass(getContext(), indoorLecture.class);
            getContext().startActivity(intent);

        }


        else  if(i==R.id.fun){

            Intent intent = new Intent();
            intent.setClass(getContext(), Weathermen.class);
            getContext().startActivity(intent);


        }
        else if(i==R.id.express){
            Intent intent = new Intent();
            intent.setClass(getContext(), GetPublischedExpress.class);
            getContext().startActivity(intent);


        }
        else if(i==R.id.chaexpress){

            Intent intent = new Intent(getContext(), com.whu.Gongyinchao.schoolservice.indoormodule.ui.
                    expresspart.query.checkpackage.MainActivity.class);
           // intent.putExtra("poi", mCurrentCloudPoiResult);
            getContext().startActivity(intent);



        }

        else if(i==R.id.study){

           // Intent intent = new Intent(getContext(), IndoorStudyActivity.class);
            // intent.putExtra("poi", mCurrentCloudPoiResult);
           // getContext().startActivity(intent);

        }
        else if(i==R.id.course){

            Intent intent = new Intent();
            intent.setClass(getContext(), IndoorCourseActivity.class);
            getContext().startActivity(intent);




        }
        else if(i==R.id.bank){

            Intent intent = new Intent();
            //intent.setClass(getContext(),.class);
           // getContext().startActivity(intent);


        }
        else if(i==R.id.more){

            Intent intent = new Intent();
            intent.setClass(getContext(), IndoorClassActivity.class);
            getContext().startActivity(intent);

        }






    }

    @Override
    public void indoorSuccess(CloudPoiResult cloudPoiResult) {
        if (cloudPoiResult == null
                || cloudPoiResult.getContents() == null
                || cloudPoiResult.getContents().size() == 0
                || TextUtils.isEmpty(cloudPoiResult.getContents().get(0).getTitle())
                || DistanceUtil.getDistance(BaseApi.getInstance().getCurrentLocLat(),
                new LatLng(cloudPoiResult.getContents().get(0).getLocation().get(1), cloudPoiResult.getContents().get(0).getLocation().get(0))) > 70) {
            indoorFailed(1001, "请检查您是否在室内");
            mCurrentCloudPoiResult = cloudPoiResult;

            if (mCurrentCloudPoiResult == null) {
                mCurrentCloudPoiResult = new CloudPoiResult();
            }

            if (mCurrentCloudPoiResult.getContents() == null) {
                List<CloudPoiResult.ContentsBean> list = new ArrayList<>();
                mCurrentCloudPoiResult.setContents(list);
            }

            CloudPoiResult.ContentsBean bean = new CloudPoiResult.ContentsBean();
            bean.setTitle("不在室内");
            mCurrentCloudPoiResult.getContents().add(0, bean);
            return;
        }

        mCurrentCloudPoiResult = cloudPoiResult;
      //  mPicker.setText(cloudPoiResult.getContents().get(0).getTitle());



    }

    @Override
    public void indoorFailed(int errorCode, String message) {

        mCurrentCloudPoiResult = null;
      //  mPicker.setText("请检查您是否在室内");


    }

    @Override
    public void locating() {

    }

    @Override
    public void locateFailed() {

    }

    @Override
    public void locateSuccess(BDLocation location) {
        mpresenter.request();

    }

    @Override
    public Object getRequestTag() {
        return null;
    }
}
