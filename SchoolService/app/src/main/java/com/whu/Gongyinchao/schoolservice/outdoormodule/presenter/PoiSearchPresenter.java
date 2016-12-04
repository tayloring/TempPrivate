package com.whu.Gongyinchao.schoolservice.outdoormodule.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.whu.Gongyinchao.schoolservice.common.app.BasePresenter;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces.OnPoiSearchCallBack;

/**
 * Created by panfei on 16/4/15.
 */
public class PoiSearchPresenter<V extends SearchResult> extends BasePresenter<OnPoiSearchCallBack<V>>{

    private PoiSearch poiSearch;
    private OnGetPoiSearchResultListener listener;

    public PoiSearchPresenter(Context context, OnPoiSearchCallBack<V> callBack) {
        super(context, callBack);
        poiSearch = PoiSearch.newInstance();
        listener = new OnGetPoiSearchResultListener(){
            @Override
            public void onGetPoiResult(PoiResult result){
                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR && mCallBack != null) {
                    mCallBack.onPoiSearchSuccess((V)result);
                }else if (mCallBack != null && result != null){
                    mCallBack.onPoiSearchFailed(result.error);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult result){
                //获取Place详情页检索结果
                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR && mCallBack != null) {
                    mCallBack.onPoiSearchSuccess((V)result);
                }else if (mCallBack != null && result != null){
                    mCallBack.onPoiSearchFailed(result.error);
                }
            }
        };

        poiSearch.setOnGetPoiSearchResultListener(listener);
    }

    public void searchInCity(String city,@NonNull String keyword, int pageNum) {
        searchInCity(city, keyword, pageNum, -1);
    }

    public void searchInCity(final String city, @NonNull final String keyword, final int pageNum, final int pageCaptity) {

        final String[] aCity = new String[] {city};
        IPrensenterRequest request = new IPrensenterRequest() {
            @Override
            public void request() {
                if (TextUtils.isEmpty(aCity[0])) {
                    aCity[0] = BaseApi.getInstance().getCurrentLocCity();
                }

                PoiCitySearchOption option = new PoiCitySearchOption();
                option = option.city(aCity[0])
                        .keyword(keyword)
                        .pageNum(pageNum);

                if (pageCaptity != -1) {
                    option.pageCapacity(pageCaptity);
                }

                poiSearch.searchInCity(option);
            }
        };

        if (TextUtils.isEmpty(aCity[0])) {
            doAfterLocate(request);
        }else {
            doDirect(request);
        }

    }

    public void searchNearby(String ak,String ID,LatLng location, @NonNull String keyword, int pageNum, int radius, @Nullable PoiSortType sortType) {
        searchNearby(ak,ID,location, keyword, pageNum, radius, sortType, -1);
    }

    public void searchNearby(final String ak,final String ID,final LatLng location, @NonNull final String keyword, final int pageNum, final int radius, @Nullable final PoiSortType sortType, final int pageCaptity) {

        final LatLng[] locations = new LatLng[]{location};
        IPrensenterRequest request = new IPrensenterRequest() {
            @Override
            public void request() {
                if (locations[0] == null) {
                    locations[0] = new LatLng(BaseApi.getInstance().getCurrentLocLatitude(), BaseApi.getInstance().getCurrentLocLongitude());
                }

                PoiNearbySearchOption option = new PoiNearbySearchOption();

                option = option.keyword(keyword)
                        .location(locations[0])
                        .pageNum(pageNum)
                        .radius(radius);

                if (sortType != null) {
                    option = option.sortType(sortType);
                }

                if (pageCaptity != -1) {
                    option.pageCapacity(pageCaptity);
                }

                poiSearch.searchNearby(option);
            }
        };

        if (locations[0] == null) {
            doAfterLocate(request);
        }else {
            doDirect(request);
        }

    }

    public void searchInBound(LatLngBounds bounds, @NonNull String keyword, int pageNum) {
        searchInBound(bounds, keyword, pageNum, -1);
    }

    public void searchInBound(@NonNull LatLngBounds bounds, @NonNull String keyword, int pageNum, int pageCaptity) {

        PoiBoundSearchOption option = new PoiBoundSearchOption();
        option = option.bound(bounds)
                .keyword(keyword)
                .pageNum(pageNum);

        if (pageCaptity != -1) {
            option.pageCapacity(pageCaptity);
        }

        poiSearch.searchInBound(option);
    }

    @Override
    public void onDestory() {
        poiSearch.destroy();
        super.onDestory();
    }
}
