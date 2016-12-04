package com.whu.Gongyinchao.schoolservice.indoormodule.ui.lecture;

/**
 * Created by 龚银超 on 2016/10/22.
 */

/*public class LecturePoiOverlay extends OverlayManager {


    private SearchResult poiResult=null;

    private PoiSearch poiSearch;


    /**
     * 通过一个BaiduMap 对象构造
     *
     * @param baiduMap
     */
  //*  public LecturePoiOverlay(BaiduMap baiduMap) {
      /*  super(baiduMap);
    }

    public void setData(SearchResult cloudSearchResult) {
        this.poiResult= cloudSearchResult;
    }




    @Override
    public List<OverlayOptions> getOverlayOptions() {

        if ((this.poiResult == null)
                || (this.poiResult == null))
            return null;
        ArrayList<OverlayOptions> arrayList = new ArrayList<OverlayOptions>();
        for (int i = 1; i < this.poiResult.poiList.size(); i++) {
            if (this.poiResult.poiList.get(i) == null)
                continue;
            // 给marker加上标签
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            arrayList.add(new MarkerOptions()
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(setNumToIcon(i))).extraInfo(bundle)
                    .position(this.poiResult.poiList.get(i)));
        }


        return arrayList;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.getExtraInfo() != null) {
            int index = marker.getExtraInfo().getInt("index");
            SearchResult poi = poiResult.poiList.get(index);

            // 详情搜索
            poiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            return true;
        }
        return  false;



    }*/

  /*  @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }


    /**
     * 往图片添加数字
     */
  /*  private Bitmap setNumToIcon(int num) {
        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        Bitmap bitmap = bd.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        int widthX;
        int heightY = 0;
        if (num < 10) {
            paint.setTextSize(30);
            widthX = 8;
            heightY = 6;
        } else {
            paint.setTextSize(20);
            widthX = 11;
        }

        canvas.drawText(String.valueOf(num),
                ((bitmap.getWidth() / 2) - widthX),
                ((bitmap.getHeight() / 2) + heightY), paint);
        return bitmap;
    }*/



//}*/
