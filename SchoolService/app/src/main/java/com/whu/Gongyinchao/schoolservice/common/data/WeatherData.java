package com.whu.Gongyinchao.schoolservice.common.data;

import java.util.List;

/**
 * Created by panfei on 16/4/14.
 */
public final class WeatherData {

    /**
     * aqi : {"city":{"aqi":"115","co":"1","no2":"56","o3":"91","pm10":"158","pm25":"86","qlty":"轻度污染","so2":"27"}}
     * basic : {"city":"武汉","cnty":"中国","id":"CN101200101","lat":"30.573000","lon":"114.279000","update":{"loc":"2016-04-14 12:49","utc":"2016-04-14 04:49"}}
     * daily_forecast : [{"astro":{"mr":"11:54","ms":"00:54","sr":"05:56","ss":"18:50"},"cond":{"code_d":"305","code_n":"305","txt_d":"小雨","txt_n":"小雨"},"date":"2016-04-14","hum":"52","pcpn":"2.2","pop":"95","pres":"1009","tmp":{"max":"27","min":"16"},"vis":"10","wind":{"deg":"159","dir":"无持续风向","sc":"微风","spd":"8"}},{"astro":{"mr":"12:50","ms":"01:41","sr":"05:54","ss":"18:50"},"cond":{"code_d":"305","code_n":"306","txt_d":"小雨","txt_n":"中雨"},"date":"2016-04-15","hum":"54","pcpn":"9.6","pop":"87","pres":"1006","tmp":{"max":"23","min":"16"},"vis":"10","wind":{"deg":"138","dir":"无持续风向","sc":"微风","spd":"7"}},{"astro":{"mr":"13:46","ms":"02:23","sr":"05:53","ss":"18:51"},"cond":{"code_d":"305","code_n":"101","txt_d":"小雨","txt_n":"多云"},"date":"2016-04-16","hum":"57","pcpn":"4.5","pop":"83","pres":"1009","tmp":{"max":"21","min":"13"},"vis":"10","wind":{"deg":"353","dir":"无持续风向","sc":"微风","spd":"2"}},{"astro":{"mr":"14:39","ms":"03:02","sr":"05:52","ss":"18:52"},"cond":{"code_d":"305","code_n":"305","txt_d":"小雨","txt_n":"小雨"},"date":"2016-04-17","hum":"47","pcpn":"4.5","pop":"70","pres":"1019","tmp":{"max":"20","min":"11"},"vis":"10","wind":{"deg":"27","dir":"无持续风向","sc":"微风","spd":"0"}},{"astro":{"mr":"15:33","ms":"03:39","sr":"05:51","ss":"18:52"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2016-04-18","hum":"36","pcpn":"0.0","pop":"6","pres":"1016","tmp":{"max":"23","min":"14"},"vis":"10","wind":{"deg":"195","dir":"无持续风向","sc":"微风","spd":"8"}},{"astro":{"mr":"16:25","ms":"04:12","sr":"05:50","ss":"18:53"},"cond":{"code_d":"101","code_n":"306","txt_d":"多云","txt_n":"中雨"},"date":"2016-04-19","hum":"34","pcpn":"0.1","pop":"3","pres":"1011","tmp":{"max":"25","min":"17"},"vis":"10","wind":{"deg":"160","dir":"无持续风向","sc":"微风","spd":"6"}},{"astro":{"mr":"17:17","ms":"04:46","sr":"05:49","ss":"18:53"},"cond":{"code_d":"306","code_n":"104","txt_d":"中雨","txt_n":"阴"},"date":"2016-04-20","hum":"98","pcpn":"75.7","pop":"50","pres":"1007","tmp":{"max":"25","min":"14"},"vis":"5","wind":{"deg":"313","dir":"无持续风向","sc":"微风","spd":"2"}}]
     * hourly_forecast : [{"date":"2016-04-14 13:00","hum":"50","pop":"40","pres":"1010","tmp":"29","wind":{"deg":"149","dir":"东南风","sc":"3-4","spd":"20"}},{"date":"2016-04-14 16:00","hum":"60","pop":"83","pres":"1009","tmp":"28","wind":{"deg":"147","dir":"东南风","sc":"微风","spd":"16"}},{"date":"2016-04-14 19:00","hum":"72","pop":"94","pres":"1010","tmp":"25","wind":{"deg":"119","dir":"东南风","sc":"微风","spd":"11"}},{"date":"2016-04-14 22:00","hum":"77","pop":"78","pres":"1011","tmp":"23","wind":{"deg":"97","dir":"东风","sc":"微风","spd":"12"}}]
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"27","hum":"52","pcpn":"0","pres":"1010","tmp":"27","vis":"5","wind":{"deg":"120","dir":"东风","sc":"4-5","spd":"18"}}
     * status : ok
     * suggestion : {"comf":{"brf":"较舒适","txt":"白天虽然有降雨，但会使人们感觉有些热，不过大部分人仍会有比较舒适的感觉。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},"flu":{"brf":"较易发","txt":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"},"sport":{"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意保暖并携带雨具。"},"trav":{"brf":"适宜","txt":"温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！"},"uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}}
     */

    private List<HeWeatherDataBean> HeWeatherData;

    public List<HeWeatherDataBean> getHeWeatherData() {
        return HeWeatherData;
    }

    public void setHeWeatherData(List<HeWeatherDataBean> HeWeatherData) {
        this.HeWeatherData = HeWeatherData;
    }

    public static class HeWeatherDataBean {
        /**
         * city : {"aqi":"115","co":"1","no2":"56","o3":"91","pm10":"158","pm25":"86","qlty":"轻度污染","so2":"27"}
         */

        private AqiBean aqi;
        /**
         * city : 武汉
         * cnty : 中国
         * id : CN101200101
         * lat : 30.573000
         * lon : 114.279000
         * update : {"loc":"2016-04-14 12:49","utc":"2016-04-14 04:49"}
         */

        private BasicBean basic;
        /**
         * cond : {"code":"101","txt":"多云"}
         * fl : 27
         * hum : 52
         * pcpn : 0
         * pres : 1010
         * tmp : 27
         * vis : 5
         * wind : {"deg":"120","dir":"东风","sc":"4-5","spd":"18"}
         */

        private NowBean now;
        private String status;
        /**
         * comf : {"brf":"较舒适","txt":"白天虽然有降雨，但会使人们感觉有些热，不过大部分人仍会有比较舒适的感觉。"}
         * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
         * drsg : {"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"}
         * flu : {"brf":"较易发","txt":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"}
         * sport : {"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意保暖并携带雨具。"}
         * trav : {"brf":"适宜","txt":"温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！"}
         * uv : {"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
         */

        private SuggestionBean suggestion;
        /**
         * astro : {"mr":"11:54","ms":"00:54","sr":"05:56","ss":"18:50"}
         * cond : {"code_d":"305","code_n":"305","txt_d":"小雨","txt_n":"小雨"}
         * date : 2016-04-14
         * hum : 52
         * pcpn : 2.2
         * pop : 95
         * pres : 1009
         * tmp : {"max":"27","min":"16"}
         * vis : 10
         * wind : {"deg":"159","dir":"无持续风向","sc":"微风","spd":"8"}
         */

        private List<DailyForecastBean> daily_forecast;
        /**
         * date : 2016-04-14 13:00
         * hum : 50
         * pop : 40
         * pres : 1010
         * tmp : 29
         * wind : {"deg":"149","dir":"东南风","sc":"3-4","spd":"20"}
         */

        private List<HourlyForecastBean> hourly_forecast;

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public SuggestionBean getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(SuggestionBean suggestion) {
            this.suggestion = suggestion;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<HourlyForecastBean> getHourly_forecast() {
            return hourly_forecast;
        }

        public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
            this.hourly_forecast = hourly_forecast;
        }

        public static class AqiBean {
            /**
             * aqi : 115
             * co : 1
             * no2 : 56
             * o3 : 91
             * pm10 : 158
             * pm25 : 86
             * qlty : 轻度污染
             * so2 : 27
             */

            private CityBean city;

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public static class CityBean {
                private String aqi;
                private String co;
                private String no2;
                private String o3;
                private String pm10;
                private String pm25;
                private String qlty;
                private String so2;

                public String getAqi() {
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getCo() {
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public String getNo2() {
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getO3() {
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getPm25() {
                    return pm25;
                }

                public void setPm25(String pm25) {
                    this.pm25 = pm25;
                }

                public String getQlty() {
                    return qlty;
                }

                public void setQlty(String qlty) {
                    this.qlty = qlty;
                }

                public String getSo2() {
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }
            }
        }

        public static class BasicBean {
            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            /**
             * loc : 2016-04-14 12:49
             * utc : 2016-04-14 04:49
             */

            private UpdateBean update;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public static class UpdateBean {
                private String loc;
                private String utc;

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public static class NowBean {
            /**
             * code : 101
             * txt : 多云
             */

            private CondBean cond;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            /**
             * deg : 120
             * dir : 东风
             * sc : 4-5
             * spd : 18
             */

            private WindBean wind;

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class CondBean {
                private String code;
                private String txt;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class WindBean {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }

        public static class SuggestionBean {
            /**
             * brf : 较舒适
             * txt : 白天虽然有降雨，但会使人们感觉有些热，不过大部分人仍会有比较舒适的感觉。
             */

            private ComfBean comf;
            /**
             * brf : 不宜
             * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
             */

            private CwBean cw;
            /**
             * brf : 舒适
             * txt : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
             */

            private DrsgBean drsg;
            /**
             * brf : 较易发
             * txt : 天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。
             */

            private FluBean flu;
            /**
             * brf : 较不宜
             * txt : 有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意保暖并携带雨具。
             */

            private SportBean sport;
            /**
             * brf : 适宜
             * txt : 温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！
             */

            private TravBean trav;
            /**
             * brf : 最弱
             * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
             */

            private UvBean uv;

            public ComfBean getComf() {
                return comf;
            }

            public void setComf(ComfBean comf) {
                this.comf = comf;
            }

            public CwBean getCw() {
                return cw;
            }

            public void setCw(CwBean cw) {
                this.cw = cw;
            }

            public DrsgBean getDrsg() {
                return drsg;
            }

            public void setDrsg(DrsgBean drsg) {
                this.drsg = drsg;
            }

            public FluBean getFlu() {
                return flu;
            }

            public void setFlu(FluBean flu) {
                this.flu = flu;
            }

            public SportBean getSport() {
                return sport;
            }

            public void setSport(SportBean sport) {
                this.sport = sport;
            }

            public TravBean getTrav() {
                return trav;
            }

            public void setTrav(TravBean trav) {
                this.trav = trav;
            }

            public UvBean getUv() {
                return uv;
            }

            public void setUv(UvBean uv) {
                this.uv = uv;
            }

            public static class ComfBean {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class CwBean {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class DrsgBean {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class FluBean {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class SportBean {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class TravBean {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class UvBean {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }
        }

        public static class DailyForecastBean {
            /**
             * mr : 11:54
             * ms : 00:54
             * sr : 05:56
             * ss : 18:50
             */

            private AstroBean astro;
            /**
             * code_d : 305
             * code_n : 305
             * txt_d : 小雨
             * txt_n : 小雨
             */

            private CondBean cond;
            private String date;
            private String hum;
            private String pcpn;
            private String pop;
            private String pres;
            /**
             * max : 27
             * min : 16
             */

            private TmpBean tmp;
            private String vis;
            /**
             * deg : 159
             * dir : 无持续风向
             * sc : 微风
             * spd : 8
             */

            private WindBean wind;

            public AstroBean getAstro() {
                return astro;
            }

            public void setAstro(AstroBean astro) {
                this.astro = astro;
            }

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public TmpBean getTmp() {
                return tmp;
            }

            public void setTmp(TmpBean tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class AstroBean {
                private String mr;
                private String ms;
                private String sr;
                private String ss;

                public String getMr() {
                    return mr;
                }

                public void setMr(String mr) {
                    this.mr = mr;
                }

                public String getMs() {
                    return ms;
                }

                public void setMs(String ms) {
                    this.ms = ms;
                }

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }
            }

            public static class CondBean {
                private String code_d;
                private String code_n;
                private String txt_d;
                private String txt_n;

                public String getCode_d() {
                    return code_d;
                }

                public void setCode_d(String code_d) {
                    this.code_d = code_d;
                }

                public String getCode_n() {
                    return code_n;
                }

                public void setCode_n(String code_n) {
                    this.code_n = code_n;
                }

                public String getTxt_d() {
                    return txt_d;
                }

                public void setTxt_d(String txt_d) {
                    this.txt_d = txt_d;
                }

                public String getTxt_n() {
                    return txt_n;
                }

                public void setTxt_n(String txt_n) {
                    this.txt_n = txt_n;
                }
            }

            public static class TmpBean {
                private String max;
                private String min;

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }
            }

            public static class WindBean {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }

        public static class HourlyForecastBean {
            private String date;
            private String hum;
            private String pop;
            private String pres;
            private String tmp;
            /**
             * deg : 149
             * dir : 东南风
             * sc : 3-4
             * spd : 20
             */

            private WindBean wind;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class WindBean {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }
    }
}
