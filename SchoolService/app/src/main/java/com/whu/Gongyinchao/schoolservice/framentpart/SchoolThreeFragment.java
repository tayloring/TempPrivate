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

public class SchoolThreeFragment extends BaseFragment implements View.OnClickListener{


    private TextView g1,g2,g3;

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
        System.out.println("SchoolthreeFragment");
        View v = inflater.inflate(R.layout.index_tuan_list4, container, false);
        g1=(TextView)v.findViewById(R.id.textView4);
        g2=(TextView)v.findViewById(R.id.textView5);
        g3=(TextView)v.findViewById(R.id.textView6);

        g1.setOnClickListener(this);
        g2.setOnClickListener(this);
        g3.setOnClickListener(this);

        return v;

    }
    private String[] schoolUrls = new String[]{ "http://www.whu.edu.cn/xxgk/xxjj.htm", "http://www.whu.edu.cn/jgsz/yxsz.htm",
            "http://www.whu.edu.cn/szdw/lyys.htm", "http://www.whu.edu.cn/rcpy/bksjy.htm"};

    @Override
    public void onClick(View v) {

        int i =v.getId();
        if(i==R.id.textView4){
            Intent intent = new Intent();
            intent.putExtra("url", schoolUrls[0]);
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);


        }

        if(i==R.id.textView5){

            Intent intent = new Intent();
            intent.putExtra("url", schoolUrls[1]);
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);

        }

        if(i==R.id.textView6){
            Intent intent = new Intent();
            intent.putExtra("url", schoolUrls[2]);
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);



        }


    }
}
