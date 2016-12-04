package com.whu.Gongyinchao.schoolservice.framentpart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whu.Gongyinchao.schoolservice.R;

/**
 * Created by 龚银超 on 2016/10/24.
 */

public class SchoolFragment extends BaseFragment {


    private FragmentManager fragmentManager;
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
        View v = inflater.inflate(R.layout.index_tuan, container, false);

        fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();


            Fragment fragment1 = new SchoolOneFragment();
            ft.add(R.id.school1,fragment1).show(fragment1);

            Fragment fragment2=new SchoolTwoFragment();
            ft.add(R.id.school2,fragment2).show(fragment2);
            Fragment fragment3=new SchoolThreeFragment();
           ft.add(R.id.school3,fragment3).show(fragment3);
            ft.commit();
        return  v;


    }






    }
