package com.whu.Gongyinchao.schoolservice.common.data;

import java.util.List;

/**
 * Created by panfei on 16/4/25.
 */
public final class CourseData {

    /**
     * name : 税务会计
     * teacher : 孙立
     * member : 50
     * coursetime : {"week":[1,18],"day":2,"time":[8,10]}
     * address : {"district":"1","room":"3-001"}
     */

    private List<CoursesBean> courses;
    private List<Integer> nearTime;
    public List<CoursesBean> getCourses() {
        return courses;
    }

    public void setCourses(List<CoursesBean> courses) {
        this.courses = courses;
    }

    public void setNearTime(List<Integer> nearTime) {
        this.nearTime = nearTime;
    }

    public List<Integer> getNearTime() {
        return nearTime;
    }

    public static class CoursesBean {
        private String name;
        private String teacher;
        private int member;
        /**
         * week : [1,18]
         * day : 2
         * time : [8,10]
         */

        private CoursetimeBean coursetime;
        /**
         * district : 1
         * room : 3-001
         */

        private AddressBean address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public int getMember() {
            return member;
        }

        public void setMember(int member) {
            this.member = member;
        }

        public CoursetimeBean getCoursetime() {
            return coursetime;
        }

        public void setCoursetime(CoursetimeBean coursetime) {
            this.coursetime = coursetime;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class CoursetimeBean {
            private int day;
            private List<Integer> week;
            private List<Integer> time;

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public List<Integer> getWeek() {
                return week;
            }

            public void setWeek(List<Integer> week) {
                this.week = week;
            }

            public List<Integer> getTime() {
                return time;
            }

            public void setTime(List<Integer> time) {
                this.time = time;
            }
        }

        public static class AddressBean {
            private String district;
            private String build;
            private String room;

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public void setBuild(String build) {
                this.build = build;
            }

            public String getBuild() {
                return build;
            }

            public String getRoom() {
                return room;
            }

            public void setRoom(String room) {
                this.room = room;
            }
        }
    }
}
