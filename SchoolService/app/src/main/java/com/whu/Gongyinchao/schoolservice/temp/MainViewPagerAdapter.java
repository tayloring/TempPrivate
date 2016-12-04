package com.whu.Gongyinchao.schoolservice.temp;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.whu.Gongyinchao.schoolservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panfei on 16/4/9.
 */

public class MainViewPagerAdapter extends PagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {

    private String[] titles = new String[]{
            "附近",
            "室内",
            "发现",
    };

    int[] resId = new int[]{R.drawable.main_outdoor, R.drawable.main_indoor, R.drawable.main_find, R.drawable.main_mine};

    private ArrayList<View> views = new ArrayList<>();

    public MainViewPagerAdapter(List<View> view) {
        if (view.size() != titles.length) {
            throw new RuntimeException("views size is not equals titles size");
        }

        views.addAll(view);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position >= views.size()) {
            return null;
        }

        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public View getCustomTabView(ViewGroup parent, int position) {
        View view = View.inflate(parent.getContext(), R.layout.main_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_tab);
        imageView.setImageResource(resId[position]);
        return view;
    }

    @Override
    public void tabSelected(View tab) {





    }

    @Override
    public void tabUnselected(View tab) {

    }
}
