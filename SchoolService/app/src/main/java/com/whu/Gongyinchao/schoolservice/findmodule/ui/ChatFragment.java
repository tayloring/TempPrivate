package com.whu.Gongyinchao.schoolservice.findmodule.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whu.Gongyinchao.schoolservice.R;

/**
 * Created by 龚银超 on 2016/9/19.
 */
public class ChatFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View chatView = inflater.inflate(R.layout.main_left_fragment_content, container,false);
        return chatView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

}
