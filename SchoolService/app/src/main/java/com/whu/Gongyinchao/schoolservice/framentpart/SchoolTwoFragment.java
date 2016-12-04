package com.whu.Gongyinchao.schoolservice.framentpart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.uikit.WebViewActivity;

/**
 * Created by 龚银超 on 2016/10/24.
 */

public class SchoolTwoFragment extends BaseFragment implements View.OnClickListener {

    private TextView h1,h2,h3,h4;


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
        View v = inflater.inflate(R.layout.index_tuan_list3, container, false);

        h1=(TextView)v.findViewById(R.id.schoolabout);

        h2=(TextView)v.findViewById(R.id.schoolabout2);

        h3=(TextView)v.findViewById(R.id.schoolabout3);

        h4=(TextView)v.findViewById(R.id.schoolabout4);

        h1.setOnClickListener(this);
        h2.setOnClickListener(this);
        h3.setOnClickListener(this);
        h4.setOnClickListener(this);

        return  v;


    }
    private String[] newsUrls = new String[] { "http://news.whu.edu.cn/ttxw.htm", "http://news.whu.edu.cn/zhxw.htm",
            "http://news.whu.edu.cn/kydt.htm", "http://www.whu.edu.cn/tzgg.htm"};


    @Override
    public void onClick(View v) {
        int i=v.getId();
        if(i==R.id.schoolabout){

            Intent intent = new Intent();
            intent.putExtra("url", newsUrls[0]);
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);

        }

        if(i==R.id.schoolabout2){

            Intent intent = new Intent();
            intent.putExtra("url", newsUrls[1]);
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);

        }

        if(i==R.id.schoolabout3){
            Intent intent = new Intent();
            intent.putExtra("url", newsUrls[2]);
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);


        }

        if(i==R.id.schoolabout4){
            Intent intent = new Intent();
            intent.putExtra("url", newsUrls[3]);
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);


        }





    }
}
