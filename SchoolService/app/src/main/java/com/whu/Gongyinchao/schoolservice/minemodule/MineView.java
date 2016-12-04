package com.whu.Gongyinchao.schoolservice.minemodule;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.app.BaseLayout;
import com.whu.Gongyinchao.schoolservice.minemodule.ui.MineOfflineActivity;
import com.whu.Gongyinchao.schoolservice.outdoormodule.OutdoorView;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;

/**
 * Created by panfei on 16/4/27.
 */
public class MineView extends BaseLayout implements DestoryListener{

   // private SSRelativeLayout mOffline;
  //  private SSRelativeLayout mAbout;
    private TextView about,offine_map;

    public MineView(Context context) {
        this(context, null);
    }

    public MineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.main_mine, this);
      //  mOffline = (SSRelativeLayout) findViewById(R.id.mine_offline);
     //   mAbout = (SSRelativeLayout) findViewById(R.id.mine_about);

    /*    mOffline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), MineOfflineActivity.class));
            }
        });

        mAbout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), WelcomeActivity.class));
            }
        });*/

        about=(TextView)findViewById(R.id.about);
        offine_map=(TextView)findViewById(R.id.offine_map);
        offine_map.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), MineOfflineActivity.class));
            }
        });


        about.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), OutdoorView.class));
            }
        });



    }

    @Override
    public void onDestory() {

    }
}
