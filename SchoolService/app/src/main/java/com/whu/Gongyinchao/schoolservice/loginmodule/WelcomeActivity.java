package com.whu.Gongyinchao.schoolservice.loginmodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SharePreferenceUtil;
import com.whu.Gongyinchao.schoolservice.common.app.SchoolServiceApplication;
import com.whu.Gongyinchao.schoolservice.loginmodule.login.MainLoginActivity;

public class WelcomeActivity extends Activity {
	private SharePreferenceUtil spUtil;
	private Handler handler;

	Runnable startAct = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
		/*	if (!TextUtils.isEmpty(spUtil.getUserId())) {
				Intent intent = new Intent(WelcomeActivity.this,
						MainLoginActivity.class);
				startActivity(intent);
			} else {
				startActivity(new Intent(WelcomeActivity.this,
						FirstSetActivity.class));
			}
			finish();*/


			startActivity(new Intent(WelcomeActivity.this, MainLoginActivity.class));


			}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		spUtil = SchoolServiceApplication.getInstance().getSpUtil();
		handler = new Handler();
		handler.postDelayed(startAct, 2000);

	}

}
