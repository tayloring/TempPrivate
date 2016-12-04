package com.whu.Gongyinchao.schoolservice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.whu.Gongyinchao.schoolservice.chatmodule.fragment.LeftFragment;
import com.whu.Gongyinchao.schoolservice.framentpart.IndoorFragment;
import com.whu.Gongyinchao.schoolservice.framentpart.MoreFragment;
import com.whu.Gongyinchao.schoolservice.framentpart.SchoolFragment;
import com.whu.Gongyinchao.schoolservice.outdoormodule.OutdoorView;

public class FragmentAdapter extends FragmentPagerAdapter{


	public final static int TAB_COUNT = 4;
	private  FragmentManager  fragmentManager;
	private OutdoorView outdoorView;
	private IndoorFragment indoorFragment;
	private LeftFragment leftFragment;
	private MoreFragment moreFragment;
	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int id) {








		if (id == MainActivity.TAB_NEARBY) {

			SchoolFragment schoolFragment = new SchoolFragment();
			return schoolFragment;



		} else if (id == MainActivity.TAB_INDOOR) {

			OutdoorView outdoorView = new OutdoorView();
//			        outdoorView.getFragmentManager().beginTransaction().hide(outdoorView).add(outdoorView ,"1").commit();
			//outdoorView.setEnterTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			return outdoorView;

			//IndoorView indoorView =new IndoorView();
			//return indoorView;
			//Weathermen weathermen =new Weathermen();
			//return  weathermen;
			//IndoorFragment indoorFragment = new IndoorFragment();
			//indoorFragment.setEnterTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		} else if (id == MainActivity.TAB_FIND) {

			LeftFragment leftFragment =new LeftFragment();
			leftFragment.setEnterTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				return leftFragment;
			//FindFragment findFragment = new FindFragment();
			//return findFragment;
		} else if (id == MainActivity.TAB_MINE) {
			MoreFragment moreFragment = new MoreFragment();
			moreFragment.setEnterTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			return moreFragment;
		}



		//setTabSelection(id);



		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}


	/**
	 * 根据传入的index参数来设置选中的tab页。
	 *
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {

		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		if (index == MainActivity.TAB_NEARBY) {// 当点击了消息tab时，改变控件的图片和文字颜色

			if (outdoorView == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				outdoorView = new OutdoorView();
				transaction.add(R.id.content, outdoorView);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(outdoorView);
			}

		} else if (index == MainActivity.TAB_INDOOR) {// 当点击了联系人tab时，改变控件的图片和文字颜色

			if (indoorFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				indoorFragment = new IndoorFragment();
				transaction.add(R.id.content, indoorFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(indoorFragment);
			}

		} else if (index == MainActivity.TAB_FIND) {// 当点击了动态tab时，改变控件的图片和文字颜色

			if (leftFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				leftFragment = new LeftFragment();
				transaction.add(R.id.content, leftFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(leftFragment);
			}

		} else if(index == MainActivity.TAB_MINE) {// 当点击了设置tab时，改变控件的图片和文字颜色

			if (moreFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				moreFragment = new MoreFragment();
				transaction.add(R.id.content, moreFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(moreFragment);
			}

		}


		transaction.commit();
	}



	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (outdoorView!= null) {
			transaction.hide(outdoorView);
		}
		if (indoorFragment != null) {
			transaction.hide(indoorFragment);
		}
		if (leftFragment != null) {
			transaction.hide(leftFragment);
		}
		if (moreFragment != null) {
			transaction.hide(moreFragment);
		}
	}













}
