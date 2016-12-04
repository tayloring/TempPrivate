package com.whu.Gongyinchao.schoolservice.outdoormodule.interfaces;

import com.baidu.mapapi.search.core.SearchResult;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;

/**
 * Created by panfei on 16/4/13.
 */
public interface OnPoiSearchCallBack<T extends SearchResult> extends PresenterCallBack{
    
    void onPoiSearchSuccess(T searchResult);

    void onPoiSearchFailed(SearchResult.ERRORNO error);
}
