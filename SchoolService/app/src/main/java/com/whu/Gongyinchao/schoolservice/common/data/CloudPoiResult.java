package com.whu.Gongyinchao.schoolservice.common.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by panfei on 16/4/10.
 */
public final class CloudPoiResult implements Serializable{

    /**
     * status : 0
     * total : 2
     * size : 2
     * contents : [{"tags":"图书馆","uid":1664495035,"province":"湖北省","study_room":[{"classroom":" 3\\u697C","freeTime":"\\u4E0A\\u5348"},{"classroom":"4\\u697C","freeTime":"\\u4E0B\\u5348"}],"geotable_id":137779,"modify_time":1461316792,"district":"武昌区","create_time":1460259047,"city":"武汉市","location":[114.369017,30.541354],"address":"湖北省武汉市武昌区科技路","title":"武汉大学新图书馆","coord_type":3,"direction":"南","type":0,"distance":44,"weight":0},{"tags":"学院","uid":1674047182,"province":"湖北省","geotable_id":137779,"district":"武昌区","create_time":1461296044,"city":"武汉市","location":[114.368635,30.54064],"address":"湖北省武汉市武昌区科技路","title":"武汉大学物理科学与技术学院","coord_type":3,"direction":"东北","type":0,"distance":48,"weight":0}]
     */

    private int status;
    private int total;
    private int size;
    /**
     * tags : 图书馆
     * uid : 1664495035
     * province : 湖北省
     * study_room : [{"classroom":" 3\\u697C","freeTime":"\\u4E0A\\u5348"},{"classroom":"4\\u697C","freeTime":"\\u4E0B\\u5348"}]
     * geotable_id : 137779
     * modify_time : 1461316792
     * district : 武昌区
     * create_time : 1460259047
     * city : 武汉市
     * location : [114.369017,30.541354]
     * address : 湖北省武汉市武昌区科技路
     * title : 武汉大学新图书馆
     * coord_type : 3
     * direction : 南
     * type : 0
     * distance : 44
     * weight : 0
     */

    private List<ContentsBean> contents;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class ContentsBean implements Serializable{
        private String tags;
        private int uid;
        private String province;
        private int geotable_id;
        private int modify_time;
        private String district;
        private int create_time;
        private String city;
        private String address;
        private String title;
        private int coord_type;
        private String direction;
        private int type;
        private int distance;
        private int weight;
        /**
         * classroom :  3\u697C
         * freeTime : \u4E0A\u5348
         */

        private List<StudyRoomBean> study_room;
        private List<Double> location;

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getGeotable_id() {
            return geotable_id;
        }

        public void setGeotable_id(int geotable_id) {
            this.geotable_id = geotable_id;
        }

        public int getModify_time() {
            return modify_time;
        }

        public void setModify_time(int modify_time) {
            this.modify_time = modify_time;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCoord_type() {
            return coord_type;
        }

        public void setCoord_type(int coord_type) {
            this.coord_type = coord_type;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public List<StudyRoomBean> getStudy_room() {
            return study_room;
        }

        public void setStudy_room(List<StudyRoomBean> study_room) {
            this.study_room = study_room;
        }

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
        }

        public static class StudyRoomBean implements Serializable{
            private String classroom;
            private String freeTime;

            public String getClassroom() {
                return classroom;
            }

            public void setClassroom(String classroom) {
                this.classroom = classroom;
            }

            public String getFreeTime() {
                return freeTime;
            }

            public void setFreeTime(String freeTime) {
                this.freeTime = freeTime;
            }
        }
    }
}