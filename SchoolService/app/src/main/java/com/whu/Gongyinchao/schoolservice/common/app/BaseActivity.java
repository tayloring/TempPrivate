package com.whu.Gongyinchao.schoolservice.common.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.whu.Gongyinchao.schoolservice.common.interfaces.LocationCallBack;
import com.whu.Gongyinchao.schoolservice.common.interfaces.PresenterCallBack;
import com.whu.Gongyinchao.schoolservice.common.utils.BaseApi;
import com.whu.Gongyinchao.schoolservice.common.utils.TLog;
import com.whu.Gongyinchao.schoolservice.framework.location.LocationManager;
import com.whu.Gongyinchao.schoolservice.loginmodule.login.MainLoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by panfei on 16/4/7.
 */
public abstract class BaseActivity extends Activity implements Observer, LocationCallBack, PresenterCallBack {

    protected LocationManager mLocationManager;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        mLocationManager = ((SchoolServiceApplication)getApplicationContext()).getLocationManager();
        mLocationManager.registerLocationListener(this);
        mLocationManager.start();
    }

    @Override
    public final void update(Observable observable, Object data) {
        if (data == null) {
            locateFailed();
            return;
        }

        if (data instanceof BDLocation){
            BDLocation location = null;
            try {
                location = (BDLocation) data;
            }catch (ClassCastException e){
                TLog.e("activity", "location can not cast be BDLocation");
            }

            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
                locateSuccess(location);
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
                locateSuccess(location);
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
                locateFailed();
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                locateFailed();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
                locateFailed();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                locateFailed();
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            TLog.i("BaiduLocationApiDem", sb.toString());
            mLocationManager.stop();
        }else if (data instanceof String && data.equals("正在定位")){
            locating();
        }
    }

    @Override
    protected void onPause() {
        mLocationManager.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        BaseApi.getInstance().cacelAll(getRequestTag());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        mLocationManager.unRegisterLocationListener(this);
        super.onDestroy();
    }

    public final LocationManager getLocationManager() {
        return mLocationManager;
    }

    public final Object getRequestTag() {
        return this.toString();
    }

    public void locating() {
        showLoading("定位中, 请等候...");
        if (getRootView(this) instanceof ViewGroup) {
            List<LocationCallBack> list = getCallBackRoot((ViewGroup) getRootView(this));

            for (int i = 0; i < list.size(); i ++) {
                LocationCallBack lcb = list.get(i);
                lcb.locating();
            }
        }
    }

    public void locateFailed() {
        dismissLoading();

        showDialog(null, "定位失败,点击关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissLoading();
                if (BaseActivity.this instanceof MainLoginActivity){
                    finish();
                }else {
                    Intent intent = new Intent(BaseActivity.this, MainLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }, null);

        if (getRootView(this) instanceof ViewGroup) {
            List<LocationCallBack> list = getCallBackRoot((ViewGroup) getRootView(this));

            for (int i = 0; i < list.size(); i ++) {
                LocationCallBack lcb = list.get(i);
                lcb.locateFailed();
            }
        }
    }

    public void locateSuccess(BDLocation location) {
        dismissLoading();
        if (getRootView(this) instanceof ViewGroup) {
            List<LocationCallBack> list = getCallBackRoot((ViewGroup) getRootView(this));

            for (int i = 0; i < list.size(); i ++) {
                LocationCallBack lcb = list.get(i);
                lcb.locateSuccess(location);
            }
        }
    }


    private  View getRootView(Activity context)
    {
        return getWindow().getDecorView();
    }

    private List<LocationCallBack> getCallBackRoot(ViewGroup v) {
        List<LocationCallBack> list = new ArrayList<>();

        int childCount = v.getChildCount();
        for (int i = 0; i < childCount; i ++) {
            View view = v.getChildAt(i);
            if (view instanceof ViewGroup && view instanceof LocationCallBack) {
                list.add((LocationCallBack)view);
            } else if (view instanceof ViewGroup){
                list.addAll(getCallBackRoot((ViewGroup) view));
            }
        }

        return list;
    }

    public void showLoading(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissLoading(){
        progressDialog.dismiss();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showDialog(String titile, String msg, final DialogInterface.OnClickListener positive, final DialogInterface.OnClickListener negative) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        if (TextUtils.isEmpty(titile)) {
            builder.setTitle(titile);
        }

        if (positive != null) {
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    positive.onClick(dialog, which);
                }
            });
        }

        if (negative != null) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    negative.onClick(dialog, which);
                }
            });
        }

        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void showMapNativeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(BaseActivity.this);
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
}
