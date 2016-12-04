package com.whu.Gongyinchao.schoolservice.common.utils;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.whu.Gongyinchao.schoolservice.common.app.SchoolServiceApplication;
import com.whu.Gongyinchao.schoolservice.common.constant.StringConstant;
import com.whu.Gongyinchao.schoolservice.common.constant.UrlConstant;
import com.whu.Gongyinchao.schoolservice.common.data.CloudPoiResult;
import com.whu.Gongyinchao.schoolservice.common.data.CourseData;
import com.whu.Gongyinchao.schoolservice.common.data.LoginResult;
import com.whu.Gongyinchao.schoolservice.common.data.ShareData;
import com.whu.Gongyinchao.schoolservice.common.data.WeatherData;
import com.whu.Gongyinchao.schoolservice.framework.location.LocationApiProvider;
import com.whu.Gongyinchao.schoolservice.framework.net.NetApiProvider;
import com.whu.Gongyinchao.schoolservice.framework.offlinemap.OfflineMapApiProvider;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by panfei on 16/4/4.
 */
public final class BaseApi {

    private NetApiProvider mNetProxy;
    private LocationApiProvider mLocationProxy;
    private OfflineMapApiProvider mOfflineProxy;

    private volatile static BaseApi instance;

    private BaseApi(Context context){
        mNetProxy = NetApiProvider.getInstance(context);
        mLocationProxy = LocationApiProvider.getInstance();
        mOfflineProxy = OfflineMapApiProvider.get();
    }

    public static BaseApi getInstance() {
        if (instance == null){
            synchronized (BaseApi.class){
                if (instance == null){
                    instance = new BaseApi(SchoolServiceApplication.getInstance());
                }
            }
        }

        return instance;
    }

    public void login(Object TAG, String account, String password, NetApiProvider.UICallBack<LoginResult> uiCallBack) {
//        if (TextUtils.isEmpty(account)) {
//            uiCallBack.onErrorResponse(1001, "账号不能为空,请重新输入");
//            return;
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            uiCallBack.onErrorResponse(1001, "密码不能为空,请重新输入");
//            return;
//        }
//
//        try {
//            if (Integer.valueOf(account) % 2 == 0) {
                LoginResult result = new LoginResult();
                result.setStatus(0);
                result.setMsg("");
                uiCallBack.onResponse(result);
//            }else {
//                uiCallBack.onErrorResponse(1001, "账号或密码错误,请重新输入");
//            }
//        }catch (Exception e){
//            uiCallBack.onErrorResponse(1001, "账号或密码错误,请重新输入");
//        }

    }

    public void getWeather(Object TAG, NetApiProvider.UICallBack<WeatherData> uiCallBack) {
        HashMap<String, String> params = new HashMap<>();
        params.put("city", TextUtils.isEmpty(getCurrentLocCity()) ? "武汉": getCurrentLocCity());
        params.put("key", StringConstant.WeatherKey);
        mNetProxy.get(TAG, UrlConstant.WeatherUrl, params, WeatherData.class, uiCallBack, new NetApiProvider.StringCallBack() {
            @Override
            public String opStr(String s) {
                s = s.replace("HeWeather data service 3.0", "HeWeatherData");
                return s;
            }
        });
    }

    public void getClass(Object TAG, NetApiProvider.UICallBack<CourseData> uiCallBack) {

        CourseData result = new CourseData();

        CourseData.CoursesBean bean1 = new CourseData.CoursesBean();
        CourseData.CoursesBean bean2 = new CourseData.CoursesBean();
        CourseData.CoursesBean bean3 = new CourseData.CoursesBean();

        bean1.setName("操作系统原理");
        bean1.setTeacher("曾老师");
        bean1.setMember(50);
        CourseData.CoursesBean.CoursetimeBean timeBean1 = new CourseData.CoursesBean.CoursetimeBean();
        timeBean1.setWeek(Arrays.asList(1, 18));
        timeBean1.setDay(2);
        timeBean1.setTime(Arrays.asList(1, 2));
        bean1.setCoursetime(timeBean1);
        CourseData.CoursesBean.AddressBean addressBean1 = new CourseData.CoursesBean.AddressBean();
        addressBean1.setDistrict("信息学部");
        addressBean1.setBuild("青楼");
        addressBean1.setRoom("328");
        bean1.setAddress(addressBean1);

        bean2.setName("计算机网络与通信原理");
        bean2.setTeacher("杜老师");
        bean2.setMember(80);
        CourseData.CoursesBean.CoursetimeBean timeBean2 = new CourseData.CoursesBean.CoursetimeBean();
        timeBean2.setWeek(Arrays.asList(1, 18));
        timeBean2.setDay(2);
        timeBean2.setTime(Arrays.asList(3, 5));
        bean2.setCoursetime(timeBean2);
        CourseData.CoursesBean.AddressBean addressBean2 = new CourseData.CoursesBean.AddressBean();
        addressBean2.setDistrict("信息学部");
        addressBean2.setBuild("青楼");
        addressBean2.setRoom("409");
        bean2.setAddress(addressBean2);

        bean3.setName("数据库系统实现");
        bean3.setTeacher("彭老师");
        bean3.setMember(100);
        CourseData.CoursesBean.CoursetimeBean timeBean3 = new CourseData.CoursesBean.CoursetimeBean();
        timeBean3.setWeek(Arrays.asList(1, 18));
        timeBean3.setDay(2);
        timeBean3.setTime(Arrays.asList(6, 8));
        bean3.setCoursetime(timeBean3);
        CourseData.CoursesBean.AddressBean addressBean3 = new CourseData.CoursesBean.AddressBean();
        addressBean3.setDistrict("信息学部");
        addressBean3.setBuild("青楼");
        addressBean3.setRoom("428");
        bean3.setAddress(addressBean3);

        result.setNearTime(Arrays.asList(2, 10));
        result.setCourses(Arrays.asList(bean1, bean2, bean3));
        uiCallBack.onResponse(result);
    }


    public void getCourse(Object TAG, NetApiProvider.UICallBack<CourseData> uiCallBack) {
        CourseData result = new CourseData();

        CourseData.CoursesBean bean1 = new CourseData.CoursesBean();
        CourseData.CoursesBean bean2 = new CourseData.CoursesBean();
        CourseData.CoursesBean bean3 = new CourseData.CoursesBean();
        CourseData.CoursesBean bean4 = new CourseData.CoursesBean();
        CourseData.CoursesBean bean5 = new CourseData.CoursesBean();

        bean1.setName("自然计算方法导论");
        bean1.setTeacher("梁老师");
        bean1.setMember(50);
        CourseData.CoursesBean.CoursetimeBean timeBean1 = new CourseData.CoursesBean.CoursetimeBean();
        timeBean1.setWeek(Arrays.asList(1, 10));
        timeBean1.setDay(5);
        timeBean1.setTime(Arrays.asList(7, 9));
        bean1.setCoursetime(timeBean1);
        CourseData.CoursesBean.AddressBean addressBean1 = new CourseData.CoursesBean.AddressBean();
        addressBean1.setDistrict("文理学部");
        addressBean1.setBuild("教五楼");
        addressBean1.setRoom("303");
        bean1.setAddress(addressBean1);

        bean2.setName("结构美学－中外桥梁美学赏析");
        bean2.setTeacher("万老师");
        bean2.setMember(80);
        CourseData.CoursesBean.CoursetimeBean timeBean2 = new CourseData.CoursesBean.CoursetimeBean();
        timeBean2.setWeek(Arrays.asList(1, 10));
        timeBean2.setDay(5);
        timeBean2.setTime(Arrays.asList(7, 9));
        bean2.setCoursetime(timeBean2);
        CourseData.CoursesBean.AddressBean addressBean2 = new CourseData.CoursesBean.AddressBean();
        addressBean2.setDistrict("工学部");
        addressBean2.setBuild("十教楼");
        addressBean2.setRoom("201");
        bean2.setAddress(addressBean2);

        bean3.setName("数字媒体技术基础");
        bean3.setTeacher("刘老师");
        bean3.setMember(100);
        CourseData.CoursesBean.CoursetimeBean timeBean3 = new CourseData.CoursesBean.CoursetimeBean();
        timeBean3.setWeek(Arrays.asList(6, 13));
        timeBean3.setDay(5);
        timeBean3.setTime(Arrays.asList(7, 9));
        bean3.setCoursetime(timeBean3);
        CourseData.CoursesBean.AddressBean addressBean3 = new CourseData.CoursesBean.AddressBean();
        addressBean3.setDistrict("信息学部");
        addressBean3.setBuild("青楼");
        addressBean3.setRoom("328");
        bean3.setAddress(addressBean3);

        bean4.setName("《孟子》讲析");
        bean4.setTeacher("吴老师");
        bean4.setMember(100);
        CourseData.CoursesBean.CoursetimeBean timeBean4 = new CourseData.CoursesBean.CoursetimeBean();
        timeBean4.setWeek(Arrays.asList(1, 12));
        timeBean4.setDay(5);
        timeBean4.setTime(Arrays.asList(11, 13));
        bean4.setCoursetime(timeBean4);
        CourseData.CoursesBean.AddressBean addressBean4 = new CourseData.CoursesBean.AddressBean();
        addressBean4.setDistrict("文理学部");
        addressBean4.setBuild("教三楼");
        addressBean4.setRoom("304");
        bean4.setAddress(addressBean4);

        bean5.setName("饮食营养与慢性疾病");
        bean5.setTeacher("汪老师");
        bean5.setMember(100);
        CourseData.CoursesBean.CoursetimeBean timeBean5 = new CourseData.CoursesBean.CoursetimeBean();
        timeBean5.setWeek(Arrays.asList(1, 18));
        timeBean5.setDay(5);
        timeBean5.setTime(Arrays.asList(3, 5));
        bean5.setCoursetime(timeBean5);
        CourseData.CoursesBean.AddressBean addressBean5 = new CourseData.CoursesBean.AddressBean();
        addressBean5.setDistrict("医学部");
        addressBean5.setBuild("一教");
        addressBean5.setRoom("313");
        bean5.setAddress(addressBean5);

        result.setNearTime(Arrays.asList(2, 10));
        result.setCourses(Arrays.asList(bean1, bean2, bean3, bean4, bean5));
        uiCallBack.onResponse(result);
    }

    public void getShare (Object TAG, NetApiProvider.UICallBack<ShareData> uiCallBack){
        ShareData result = new ShareData();
        ShareData.ContentBean bean1 = new ShareData.ContentBean();
        bean1.setTitle("珞珈讲坛第136讲");
        bean1.setAbstractX("中国科学院院士田刚将于2016年4月22日15:30在武汉大学樱顶老图书馆主讲珞珈讲坛第136讲，题目是：“欧拉公式与计数几何”，届时欢迎广大师生参加。需凭校园卡入场。");
        bean1.setReporter("田刚，中国科学院院士，美国艺术与科学研究院院士，北京国际数学研究中心主任，北京大学数学科学学院院长，全国政协常委，中国民主同盟中央副主席，国家“千人计划”入选者，曾经在2002年国际数学家大会上被邀请作1小时大会报告。\n\n" +
                "田刚教授1982年毕业于南京大学数学系，1984年获北京大学硕士学位，1988年获美国哈佛大学数学系博士学位。1988年至1996年分别于美国普林斯顿大学、纽约大学石溪分校和纽约大学库朗研究所任副教授、教授。1995年至2006年任美国麻省理工学院教授及西蒙讲座教授。2003年至今任美国普林斯顿大学教授，希金斯讲座教授。自1991年起，受聘为北京大学教授及教育部“长江计划”特聘教授。\n\n" +
                "田刚教授是微分几何、几何分析和辛几何领域的专家，在Kaehler-Einstein度量的存在性、量子上同调理论、高维Yang-Mills联络、四维流形的研究、退化复Monge-Ampere方程、Ricci流与庞加莱猜想等问题上做出了一系列的贡献。由于这些学术成就，他2001年被选为中国科学院院士，2004年被选为美国艺术与科学研究院院士，2002年受邀在国际数学家大会上作1小时大会报告。田刚教授曾获Alan Waterman奖（1994年）和Oswald Veblen奖（1996年）并担任Annals of Mathematics（2007至今）等杂志编委，和阿贝尔奖（2012至今）等评奖委员会成员。");
        bean1.setDepartment("科学技术发展研究院");
        bean1.setTime("2016-04-18");
        result.setContent(Arrays.asList(bean1));

        ShareData.ContentBean bean2 = new ShareData.ContentBean();
        bean2.setTitle("普适的计算机图形学");
        bean2.setAbstractX("浙江大学计算机辅助设计与图形学国家重点实验室周昆将于2016年4月22日周五15:00在武汉大学计算机学院B403学术报告，题目是：“普适的计算机图形学”，届时欢迎广大师生参加。需凭校园卡入场。");
        bean2.setReporter("周昆，现任浙江大学计算机辅助设计与图形学国家重点实验室主任，教育部长江学者特聘教授，国家杰出青年科学基金获得者，国际电气电子工程师协会会士（IEEEFellow）。2002年获浙江大学工学博士学位，2002至2008年就职于微软亚洲研究院，2008年全职回到浙江大学工作。\n\n" +
                "研究领域包括计算机图形学、人机交互、虚拟现实和并行计算。近年来在ACM/IEEE Transactions上发表论文70余篇，获得美国发明专利30余项。现担任《ACM Transactions on Graphics》、《The Visual Computer》、《Frontiers of Computer Science》、《计算机研究与发展》等多个期刊编委，担任《IEEE Spectrum》编辑顾问委员会委员。\n\n" +
                "曾获得2010年中国计算机图形学杰出奖、2011年中国青年科技奖、2011年麻省理工学院《技术评论》全球杰出青年创新人物奖(MIT TR35 Award)、2013年国家自然科学二等奖。");
        bean2.setDepartment("计算机学院");
        bean2.setTime("2016-04-22");
        result.setContent(Arrays.asList(bean1, bean2));

        uiCallBack.onResponse(result);
    }

    public void getPoi(Object TAG,String ak ,String ID,double longitude, double latitude, int radius, NetApiProvider.UICallBack<CloudPoiResult> uiCallBack){
        if (radius < 0)
            throw new RuntimeException("radius can not smaller than 0");

        HashMap<String, String> params = new HashMap<>();
        params.put("ak", ak);
        params.put("geotable_id",ID);
        params.put("location", longitude + "," + latitude);
        params.put("coord_type", "3");
        params.put("sortby", "distance:1");
        params.put("radius", radius + "");
        mNetProxy.get(TAG, "http://api.map.baidu.com/geosearch/v3/nearby", params, CloudPoiResult.class, uiCallBack, new NetApiProvider.StringCallBack() {
            @Override
            public String opStr(String s) {
                int start = s.indexOf("study_room") + 12;
                if (start <= 12) {
                    return s;
                }
                String buffer1 = s.substring(0, start);
                String temp = s.substring(start + 1, s.length());
                int end = temp.indexOf("]");
                String buffer2 = s.substring(start+end+3, s.length());
                temp = temp.substring(0, end + 1);
                temp = temp.substring(1, temp.length() - 1);
                String[] classrooms = temp.split(Pattern.quote("},"));
                JSONArray array = new JSONArray();
                for (String str : classrooms) {
                    if (str.startsWith("{")) {
                        str = str.substring(1, str.length());
                    }

                    if (str.endsWith("}")) {
                        str = str.substring(0, str.length() - 1);
                    }

                    JSONObject obj = new JSONObject();
                    String[] classroom = str.split(",");
                    for(String st : classroom) {
                        String[] aCouple = st.split(":");
                        try {
                            obj.put(aCouple[0], aCouple[1]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    array.put(obj);
                }
                String arr = StringEscapeUtils.unescapeJava(array.toString());
                String result = buffer1 + arr + buffer2;
                return result;
            }
        });
    }

    public void cacelAll(Object o) {
        mNetProxy.cacelAll(o);
    }

    public void cancelAll(RequestQueue.RequestFilter filter) {
        mNetProxy.cancelAll(filter);
    }

    public final String getCurrentLocAddrStrA() {
        return mLocationProxy.getCurrentLocAddrStrA();
    }

    public final String getCurrentLocDescribe() {
        return mLocationProxy.getCurrentLocDescribe();
    }

    public final String getCurrentLocCountry() {
        return mLocationProxy.getCurrentLocCountry();
    }

    public final String getCurrentLocProvince() {
        return mLocationProxy.getCurrentLocProvince();
    }

    public final String getCurrentLocCity() {
        return mLocationProxy.getCurrentLocCity();
    }

    public final String getCurrentLocDirection() {
        return mLocationProxy.getCurrentLocDirection();
    }

    public final String getCurrentLocStreet() {
        return mLocationProxy.getCurrentLocStreet();
    }

    public final String getCurrentLocStreetNumber() {
        return mLocationProxy.getCurrentLocStreetNumber();
    }

    public final double getCurrentLocLatitude() {
        return mLocationProxy.getCurrentLocLatitude();
    }

    public final double getCurrentLocLongitude() {
        return mLocationProxy.getCurrentLocLongitude();
    }

    // 0 - 在国外   1 - 在国内   2 － 未知
    public final int getCurrentLocationWhere(){
        return mLocationProxy.getCurrentLocationWhere();
    }

    public final boolean hasAddr() {
        return mLocationProxy.hasAddr();
    }

    public final float getCurrentSpeed() {
        return mLocationProxy.getCurrentSpeed();
    }

    public final List<Poi> getCurrentLocPoiList() {
        return mLocationProxy.getCurrentLocPoiList();
    }

    public final LatLng getCurrentLocLat() {
        return mLocationProxy.getCurrentLocLat();
    }

    public final List<MKOLUpdateElement> getAllUpdateInfo() {
        if (mOfflineProxy == null) {
            mOfflineProxy = OfflineMapApiProvider.get();
        }

        return mOfflineProxy.getAllUpdateInfo();
    }

    public final MKOLUpdateElement getUpdateInfo(int cityID) {
        if (mOfflineProxy == null) {
            mOfflineProxy = OfflineMapApiProvider.get();
        }
        return mOfflineProxy.getUpdateInfo(cityID);
    }

    public final List<MKOLSearchRecord>	searchCity(String cityName) {
        if (mOfflineProxy == null) {
            mOfflineProxy = OfflineMapApiProvider.get();
        }
        return mOfflineProxy.searchCity(cityName);
    }

    public final List<MKOLSearchRecord>	getOfflineCityList() {
        if (mOfflineProxy == null) {
            mOfflineProxy = OfflineMapApiProvider.get();
        }
        return mOfflineProxy.getOfflineCityList();
    }

    public final void start(int cityID, MKOfflineMapListener listener) {
        mOfflineProxy = OfflineMapApiProvider.get(listener);
        mOfflineProxy.start(cityID);
    }

    public final void update(int cityID, MKOfflineMapListener listener) {
        mOfflineProxy = OfflineMapApiProvider.get(listener);
        mOfflineProxy.update(cityID);
    }

    public final void pause(int cityID) {
        if (mOfflineProxy == null) {
            mOfflineProxy = OfflineMapApiProvider.get();
        }
        mOfflineProxy.pause(cityID);
    }

    public final void remove(int cityID) {
        if (mOfflineProxy == null) {
            mOfflineProxy = OfflineMapApiProvider.get();
        }
        mOfflineProxy.remove(cityID);
    }

    public final void destoryOffline() {
        mOfflineProxy.destory();
        mOfflineProxy = null;
    }
}
