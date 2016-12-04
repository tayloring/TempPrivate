package com.whu.Gongyinchao.schoolservice.outdoormodule.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.whu.Gongyinchao.schoolservice.common.app.BaseActivity;
import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.DestoryListener;

/**
 * Created by panfei on 16/4/19.
 */
public class RouteGuidePresenter extends BasePresenter<PresenterCallBack> implements OnGetGeoCoderResultListener, DestoryListener {

    private GeoCoder mSearch;
    private int times = 0;
    private String start;
    private String end;
    private LatLng startLaL;
    private LatLng endLaL;

    public RouteGuidePresenter(Context context, PresenterCallBack mCallBack) {
        super(context, mCallBack);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
    }

    public void guide(String start, String end) {

        this.start = start;
        this.end = end;
        GeoCodeOption option = new GeoCodeOption().city(BaseApi.getInstance().getCurrentLocCity())
                .address(this.start);
        mSearch.geocode(option);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (times == 0) {
            if (geoCodeResult != null && geoCodeResult.error == SearchResult.ERRORNO.NO_ERROR) {
                startLaL = geoCodeResult.getLocation();
                times ++;
                GeoCodeOption option = new GeoCodeOption().city(BaseApi.getInstance().getCurrentLocCity())
                        .address(this.end);
                mSearch.geocode(option);
            }else if ("当前位置".equals(this.start)){
                startLaL = BaseApi.getInstance().getCurrentLocLat();
                times ++;
                GeoCodeOption option = new GeoCodeOption().city(BaseApi.getInstance().getCurrentLocCity())
                        .address(this.end);
                mSearch.geocode(option);
            }else {
                ((BaseActivity)mContext.get()).showToast("没有找到起点");
            }
        }else if (times == 1) {
            if ("当前位置".equals(this.end)) {
                endLaL = BaseApi.getInstance().getCurrentLocLat();
            }
            endLaL = geoCodeResult.getLocation();
            times = 0;

            if (endLaL != null) {
                // 构建 导航参数
                NaviParaOption para = new NaviParaOption()
                        .startPoint(startLaL).endPoint(endLaL)
                        .startName(this.start).endName(this.end);

                try {
                    if (!BaiduMapNavigation.openBaiduMapWalkNavi(para, mContext.get())){
                        ((BaseActivity)mContext.get()).showToast("BaiduMap app is not installed or version is too lowl");
                    }
                } catch (BaiduMapAppNotSupportNaviException e) {
                    e.printStackTrace();
                    showDialog();
                }
            }else {
                ((BaseActivity)mContext.get()).showToast("没有找到终点");
            }
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext.get());
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(mContext.get());
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    @Override
    public void onDestory() {
        mSearch.destroy();
    }
}
