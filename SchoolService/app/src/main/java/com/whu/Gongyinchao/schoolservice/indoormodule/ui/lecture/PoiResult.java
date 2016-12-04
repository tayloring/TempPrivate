package com.whu.Gongyinchao.schoolservice.indoormodule.ui.lecture;

import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.whu.Gongyinchao.schoolservice.common.overlayutil.PoiOverlay;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by 龚银超 on 2016/10/22.
 */

public class PoiResult implements OnGetPoiSearchResultListener {

    /** 定义 BaiduMap 地图对象的操作方法与接口 */
    private BaiduMap baiduMap;



    @Override
    public void onGetPoiResult(com.baidu.mapapi.search.poi.PoiResult poiResult) {

        if ((poiResult == null)
                || (poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)) {
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            baiduMap.clear();
            PoiOverlay overlay = new PoiOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);

            overlay.setData(poiResult);
            overlay.addToMap();
            // 缩放地图，使所有Overlay都在合适的视野内
            overlay.zoomToSpan();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
        }


    }




    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
        } else {
            Toast.makeText(getContext(),
                    poiDetailResult.getName() + ": " + poiDetailResult.getAddress(), 0)
                    .show();
        }
    }
}
