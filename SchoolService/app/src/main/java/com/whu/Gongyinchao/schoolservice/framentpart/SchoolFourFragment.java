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
 * Created by 龚银超 on 2016/10/28.
 */

public class SchoolFourFragment extends BaseFragment implements View.OnClickListener{

    private TextView tr1,tr2,tr3,tr4,tr5;

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
        View v = inflater.inflate(R.layout.index_tuan_list6, container, false);
         tr1=(TextView)v.findViewById(R.id.tx1);
        tr2=(TextView)v.findViewById(R.id.tx2);

        tr3=(TextView)v.findViewById(R.id.tx3);
        tr4=(TextView)v.findViewById(R.id.tx4);
        tr5=(TextView)v.findViewById(R.id.tx5);


        tr1.setOnClickListener(this);

        tr2.setOnClickListener(this);
        tr3.setOnClickListener(this);
        tr4.setOnClickListener(this);
        tr5.setOnClickListener(this);


   return v;
    }


    @Override
    public void onClick(View v) {
        int i =v.getId();
        if(i==R.id.tx1){

            Intent intent = new Intent();
            intent.putExtra("url", "http://cs.whu.edu.cn/a/xueshujiangzuofabu/2016/0629/6479.html");
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);



        }


        else  if(i==R.id.tx2){


            Intent intent = new Intent();
            intent.putExtra("url",  "http://cs.whu.edu.cn/a/xueshujiangzuofabu/2016/0620/6472.html");
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);




        }
        else if(i==R.id.tx3){
            Intent intent = new Intent();
            intent.putExtra("url", "http://cs.whu.edu.cn/a/xueshujiangzuofabu/2016/0614/6468.html" );
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);





        }
        else if(i==R.id.tx4){
            Intent intent = new Intent();
            intent.putExtra("url", "http://cs.whu.edu.cn/a/xueshujiangzuofabu/2016/0614/6467.html");
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);






        }

        else if(i==R.id.tx5){
            Intent intent = new Intent();
            intent.putExtra("url","http://cs.whu.edu.cn/a/xueshujiangzuofabu/2016/0614/6463.html" );
            intent.setClass(getContext(), WebViewActivity.class);
            getContext().startActivity(intent);



        }
    }
}
