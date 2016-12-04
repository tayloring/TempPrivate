package com.whu.Gongyinchao.schoolservice.common.uikit;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.EditText;

import com.whu.Gongyinchao.schoolservice.searchmodule.SearchMainActivity;

/**
 * Created by 龚银超 on 2016/10/23.
 */

public class GEditText extends EditText {
    public GEditText(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP) {

            Intent intent =new Intent();
            intent.setClass(getContext(), SearchMainActivity.class);
            getContext().startActivity(intent);


        }


        return super.onTouchEvent(event);
    }


}
