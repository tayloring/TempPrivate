package com.whu.Gongyinchao.schoolservice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import static com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.query.utils.Common.closeWhenOncreate;

public class MainActivity extends FragmentActivity implements OnClickListener {
	public static final int TAB_NEARBY = 0;
	public static final int TAB_INDOOR = 1;
	public static final int TAB_FIND = 2;
	public static final int TAB_MINE = 3;
	private Handler handler;
	private ViewPager viewPager;
	private RadioButton main_tab_home, main_tab_catagory,
			main_tab_buy, main_tab_more;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentmain);
		initView();
		addListener();
		closeWhenOncreate(MainActivity.this);// 进入界面关闭软键盘

	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		main_tab_home = (RadioButton) findViewById(R.id.main_tab_nearby);
		main_tab_catagory = (RadioButton) findViewById(R.id.main_tab_indoor);
		main_tab_buy = (RadioButton) findViewById(R.id.main_tab_find);
		main_tab_more = (RadioButton) findViewById(R.id.main_tab_more);


		main_tab_home.setOnClickListener(this);
		main_tab_catagory.setOnClickListener(this);
		main_tab_buy.setOnClickListener(this);
		main_tab_more.setOnClickListener(this);

		FragmentAdapter adapter = new FragmentAdapter(
				getSupportFragmentManager());
		viewPager.setAdapter(adapter);
	}

	private void addListener() {
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int id) {
				if (id == TAB_NEARBY) {
					main_tab_home.setChecked(true);

				} else if (id == TAB_INDOOR) {
					main_tab_catagory.setChecked(true);

				} else if (id == TAB_FIND) {
					main_tab_buy.setChecked(true);

				} else if (id == TAB_MINE) {
					main_tab_more.setChecked(true);

				} else {
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}


	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.main_tab_nearby) {


			viewPager.setCurrentItem(TAB_NEARBY);




		} else if (i == R.id.main_tab_indoor) {
			viewPager.setCurrentItem(TAB_INDOOR);


		} else if (i == R.id.main_tab_find) {


			viewPager.setCurrentItem(TAB_FIND);

		} else if (i == R.id.main_tab_more) {


			viewPager.setCurrentItem(TAB_MINE);

		} else

		{
		}


	}








}