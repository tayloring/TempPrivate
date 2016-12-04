package com.whu.Gongyinchao.schoolservice.findmodule;

import android.content.Context;
import android.util.AttributeSet;

import com.whu.Gongyinchao.schoolservice.common.app.BaseLayout;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;

/**
 * Created by panfei on 16/4/12.
 */
public class FindView extends BaseLayout implements DestoryListener{

  /*  private int[] banners = new int[] { R.drawable.find_main_picture1, R.drawable.find_main_picture2, R.drawable.find_main_picture3 };
    private String[] titles = new String[] {"校园概况", "校园新闻", "吃喝玩乐", "生活黄页"};
    private String[][] details = new String[][]{
            {"学校简介", "机构设置", "师资队伍", "人才培养"},
            {"讲座信息", "珞珈新闻", "学术动态", "通知公告"},
            {"美食", "酒店", "旅游景点", "休闲娱乐"},
            {"查快递", "招聘信息", "客服售后", "公共热线"}
    };

    private int[] images = new int[] {R.drawable.find_school, R.drawable.find_news, R.drawable.find_play, R.drawable.find_book};
    private String[] schoolUrls = new String[]{ "http://www.whu.edu.cn/xxgk/xxjj.htm", "http://www.whu.edu.cn/jgsz/yxsz.htm",
                                                "http://www.whu.edu.cn/szdw/lyys.htm", "http://www.whu.edu.cn/rcpy/bksjy.htm"};

    private String[] newsUrls = new String[] { "http://news.whu.edu.cn/ttxw.htm", "http://news.whu.edu.cn/zhxw.htm",
                                                "http://news.whu.edu.cn/kydt.htm", "http://www.whu.edu.cn/tzgg.htm"};

    private String[] lifeUrls = new String[] { "http://www.kiees.cn/", "http://www.xsjy.whu.edu.cn/default.html"
                                             };

    private SSBanner mPicture;
    private FindGeoView mFind1;
    private FindGeoView mFind2;
    private FindGeoView mFind3;
    private FindGeoView mFind4;*/

    public FindView(Context context) {
        this(context, null);
    }

    public FindView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
      // inflate(getContext(), R.layout.main_find, this);
      /*  mPicture = (SSBanner) findViewById(R.id.find_picture);
        mFind1 = ((FindGeoView)findViewById(R.id.find1)).setMainGeo(titles[0]).setGeos(details[0]).setIcon(images[0]).setUrl(schoolUrls);
        mFind2 = ((FindGeoView)findViewById(R.id.find2)).setMainGeo(titles[1]).setGeos(details[1]).setIcon(images[1]).setUrl(newsUrls);
        mFind3 = ((FindGeoView)findViewById(R.id.find3)).setMainGeo(titles[2]).setGeos(details[2]).setIcon(images[2]).setMapRouteUrl(details[2]);
        mFind4 = ((FindGeoView)findViewById(R.id.find4)).setMainGeo(titles[3]).setGeos(details[3]).setIcon(images[3]).setUrl(lifeUrls);
*/
      //  mPicture.setImagesRes(banners);





    }

    @Override
    public void onDestory() {
       // mPicture.removeCallbacksAndMessages();

      //  mPicture.destroyDrawingCache();
    }
}
