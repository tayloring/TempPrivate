package com.whu.Gongyinchao.schoolservice.framentpart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.common.data.CloudPoiResult;
import com.whu.Gongyinchao.schoolservice.common.uikit.Mbutton;
import com.whu.Gongyinchao.schoolservice.findmodule.ui.FindGeoView;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.IndoorClassActivity;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.IndoorCourseActivity;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.ui.MainExpressUI;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.lecture.indoorLecture;
import com.whu.Gongyinchao.schoolservice.searchmodule.SearchMainActivity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class IndoorFragment extends BaseFragment{
	private FindGeoView findGeoView;
	private CloudPoiResult mCurrentCloudPoiResult;
	private Fragment fragment;
	private TextView textView;
	private LinearLayout llButtonM1;
	private LinearLayout llButtonM2;
	private  RelativeLayout mtitle;
	private Mbutton[] buttonM1;
	private Mbutton[] buttonM2;
	private RelativeLayout mr1,mr2;
	private static final String[] colorList = {"#ffffff","#ffffff","#ffffff","#ffffff"};
	private static final String[] colorSelectedList = {"#3C3779","#88354C","#613E70","#00677D"};
    private static final String[] firstlinetext={"动态","讲座","快递"};
	private static final String[] secondlinetext={"课程","自习","更多"};
	private String[] titles = new String[] {"校园概况", "校园新闻", "吃喝玩乐", "生活黄页"};
	private String[][] details = new String[][]{
			{"学校简介", "机构设置", "师资队伍", "人才培养"},
			{"讲座信息", "珞珈新闻", "学术动态", "通知公告"},
			{"美食", "酒店", "旅游景点", "休闲娱乐"},
			{"查快递", "招聘信息", "客服售后", "公共热线"}
	};


	private String[] schoolUrls = new String[]{ "http://www.whu.edu.cn/xxgk/xxjj.htm", "http://www.whu.edu.cn/jgsz/yxsz.htm",
			"http://www.whu.edu.cn/szdw/lyys.htm", "http://www.whu.edu.cn/rcpy/bksjy.htm"};

	private String[] newsUrls = new String[] { "http://news.whu.edu.cn/ttxw.htm", "http://news.whu.edu.cn/zhxw.htm",
			"http://news.whu.edu.cn/kydt.htm", "http://www.whu.edu.cn/tzgg.htm"};

	private String[] lifeUrls = new String[] { "http://www.kiees.cn/", "http://www.xsjy.whu.edu.cn/default.html"
	};

	//private SSBanner mPicture;
	private FindGeoView mFind1;
	private int[] images = new int[] {R.drawable.find_school, R.drawable.find_news, R.drawable.find_play, R.drawable.find_book};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("IndoorFragment");
		View v= inflater.inflate(R.layout.main_indoor, container, false);

		//Fragment fragment = new Weathermen();
		mr1=(RelativeLayout)v.findViewById(R.id.top_relative);
		mr2=(RelativeLayout)v.findViewById(R.id.serachre);
		mr2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getContext(), SearchMainActivity.class);
				getContext().startActivity(intent);
			}
		});

		/*FragmentManager fm =getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if(fragment==null){

			//Fragment fragment = new Weathermen();
			ft.add(R.id.wea,fragment).show(fragment);

		}else{
			ft.show(fragment);

		}
		//ft.hide(R.id.wea, fragment);
		ft.commit();*/





        findGeoView=(FindGeoView)v.findViewById(R.id.find1);
		//mPicture = (SSBanner) v.findViewById(R.id.find_);
		mFind1 = ((FindGeoView)v.findViewById(R.id.find1)).setMainGeo(titles[0]).setGeos(details[0]).setIcon(images[0]).setUrl(schoolUrls);




		SharedPreferences ps=getContext().getSharedPreferences("location",0);//token是存储文件
		String loc=ps.getString("location", "");


//实例化布局控件
		llButtonM1 = (LinearLayout) v.findViewById(R.id.ll_button1);
		llButtonM2 = (LinearLayout) v.findViewById(R.id.ll_button2);


				InitView();


		return v;



	}

	@Override
	public View initView() {
		return null;
	}

	@Override
	public void initData() {

	}


	private void IniTitle(){

        int btnWidth;
        RelativeLayout.LayoutParams mrt1,mrt2;
        btnWidth = (getActivity().getWindowManager().getDefaultDisplay().getWidth())/5;
        mrt1 = new RelativeLayout.LayoutParams(btnWidth,(int) (btnWidth * 0.8));
        mrt2= new RelativeLayout.LayoutParams(btnWidth,(int)(btnWidth*0.4));
        mr1.setLayoutParams(mrt1);
        mr2.setLayoutParams(mrt2);



    }


	private  void InitView(){



		//实例化控件数组，各定义4个
		buttonM1 = new Mbutton[3];
		buttonM2 = new Mbutton[3];

		//获取屏幕的宽度，每行四个Button，间隙为60共300，除4为每个控件的宽度

		int btnWidth;
		btnWidth = (getActivity().getWindowManager().getDefaultDisplay().getWidth())/4;
		//定义第一个布局
		LinearLayout.LayoutParams lp1;
		for (int i = 0; i < 3; i++) {
			//为buttonM1设置样式，直角矩形
			buttonM1[i] = new Mbutton(getContext());
			buttonM1[i].setFillet(true);
			//设置圆角的半径大小



			//字体颜色
			buttonM1[i].setTextColori(android.graphics.Color.WHITE);
			//字体大小
			buttonM1[i].setTextSize(18);

			//背景色
			  buttonM1[i].setBackColor(Color.parseColor(colorList[i]));
			//buttonM1[2].setBackGroundImage(R.drawable.express);
			//选中的背景色
			buttonM1[i].setBackColorSelected(Color.parseColor(colorSelectedList[i]));
			//文字提示
			buttonM1[i].setText(firstlinetext[i]);
			//此处设置Id的值为i，否则onClick中v.getId()将永远为-1
			buttonM1[i].setId(i);
			//定义buttonM1的布局，宽度自适应，高度为宽度的0.6倍，权重为1
			//也可以写成lp1 = new LinearLayout.LayoutParams(btnWidth,(int) (btnWidth * 0.6));
			lp1 = new LinearLayout.LayoutParams(WRAP_CONTENT,(int) (btnWidth * 0.9), 1.0f);
			//控件距离其右侧控件的距离，此处为60
			lp1.setMargins(0,0,0,0);
			buttonM1[i].setLayoutParams(lp1);
			//设置buttonM1的点击事件
			buttonM1[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(v.getId()==0) {

						Intent intent = new Intent();
						intent.setClass(getContext(), SearchMainActivity.class);
						getContext().startActivity(intent);
					}

					else if(v.getId()==1){

						Intent intent = new Intent();
						intent.setClass(getContext(), indoorLecture.class);
						getContext().startActivity(intent);


					}
					else if(v.getId()==2){
						Intent intent = new Intent();
						intent.setClass(getContext(), MainExpressUI.class);
						getContext().startActivity(intent);


					}


				}
			});
			//设置PaddingLeft为60
			llButtonM1.setPadding(0, 0, 0, 0);
			//将buttonM1添加到llButtonM1中
			llButtonM1.addView(buttonM1[i]);
		}
		//定义第二个布局
		LinearLayout.LayoutParams lp2;
		for (int i = 0; i < 3; i++) {
			//为buttonM2设置样式，圆角矩形
			buttonM2[i] = new Mbutton(getContext());
			buttonM2[i].setTextColori(android.graphics.Color.WHITE);
			buttonM2[i].setTextSize(18);
			//设置是否为圆角
			buttonM2[i].setFillet(true);
			//设置圆角的半径大小
			//buttonM2[i].setRadius(18);
			buttonM2[i].setBackColor(Color.parseColor(colorList[i]));
			buttonM2[i].setBackColorSelected(Color.parseColor(colorSelectedList[i]));
			buttonM2[i].setText(secondlinetext[i]);
			buttonM2[i].setId(i);
			lp2 = new LinearLayout.LayoutParams(WRAP_CONTENT, (int) (btnWidth * 0.9), 1.0f);
			lp2.setMargins(0, 0, 0, 0);
			buttonM2[i].setLayoutParams(lp2);
			buttonM2[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (v.getId() == 0) {
						Intent intent = new Intent();
						intent.setClass(getContext(), IndoorClassActivity.class);
						getContext().startActivity(intent);


					} else if (v.getId() == 1) {

						Intent intent = new Intent(getContext(), com.whu.Gongyinchao.schoolservice.indoormodule.ui.
								expresspart.query.checkpackage.MainActivity.class);
						//intent.putExtra("poi", mCurrentCloudPoiResult);
							getContext().startActivity(intent);

					} else if (v.getId() == 2) {
						Intent intent = new Intent();
						intent.setClass(getContext(), IndoorCourseActivity.class);
						getContext().startActivity(intent);

					}


				}
			});
			llButtonM2.setPadding(0, 0, 0, 0);
			llButtonM2.addView(buttonM2[i]);


		}

	}







}
