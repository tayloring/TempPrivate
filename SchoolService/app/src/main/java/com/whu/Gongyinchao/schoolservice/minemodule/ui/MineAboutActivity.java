package com.whu.Gongyinchao.schoolservice.minemodule.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.chatmodule.fragment.RightFragment;

/**
 * Created by panfei on 16/4/28.
 */
public class MineAboutActivity extends FragmentActivity implements RightFragment.OnPullRefreshSwitchListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mineabout);

        Fragment fragment = new RightFragment();
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.about, fragment);
        ft.commit();



        /*TextView view = new TextView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setText("基于定位的校园服务系统\n 武汉大学计算机学院 潘飞 \n 511616522@qq.com");
        view.setTextSize(16);
        view.setLineSpacing(0, 1.5f);
        view.setGravity(Gravity.CENTER);
        setContentView(view);*/
    }

    @Override
    public void onSwitchChange(boolean isChecked) {

    }
}
