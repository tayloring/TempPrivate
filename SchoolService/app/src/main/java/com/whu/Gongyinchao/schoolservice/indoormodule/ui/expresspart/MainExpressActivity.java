package com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart;

import android.app.ListActivity;
import android.os.Bundle;

import com.whu.Gongyinchao.schoolservice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 龚银超 on 2016/10/20.
 */

public class MainExpressActivity extends ListActivity {
    private List<Map<String,Object>>  data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.main_expressactivity);


       data=getdata();
  ExpressAdapter expressAdapter=new ExpressAdapter(this,data);
        setListAdapter(expressAdapter);




    }




    public  List<Map<String,Object>> getdata(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        int h=15;
        for(int i=0;i<h;i++) {

            Map<String, Object> map= new HashMap<String, Object>();
            map.put("usershare", R.drawable.h0);
            map.put("content", "我在工学部,可以帮忙带快递到信息学部 ");
            list.add(map);

        }

        return list;


    }



    private void IniView(){





    }



}
