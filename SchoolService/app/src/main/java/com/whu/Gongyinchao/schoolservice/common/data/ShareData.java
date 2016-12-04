package com.whu.Gongyinchao.schoolservice.common.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by panfei on 16/4/26.
 */
public final class ShareData {

    /**
     * title : 珞珈讲坛第136讲
     * abstract : 中国科学院院士田刚将于2016年4月22日15:30在武汉大学樱顶老图书馆主讲珞珈讲坛第136讲，题目是：“欧拉公式与计数几何”，届时欢迎广大师生参加。需凭校园卡入场。
     * reporter : 田刚，中国科学院院士，美国艺术与科学研究院院士，北京国际数学研究中心主任，北京大学数学科学学院院长，全国政协常委，中国民主同盟中央副主席，国家“千人计划”入选者，曾经在2002年国际数学家大会上被邀请作1小时大会报告。
     田刚教授1982年毕业于南京大学数学系，1984年获北京大学硕士学位，1988年获美国哈佛大学数学系博士学位。1988年至1996年分别于美国普林斯顿大学、纽约大学石溪分校和纽约大学库朗研究所任副教授、教授。1995年至2006年任美国麻省理工学院教授及西蒙讲座教授。2003年至今任美国普林斯顿大学教授，希金斯讲座教授。自1991年起，受聘为北京大学教授及教育部“长江计划”特聘教授。
     田刚教授是微分几何、几何分析和辛几何领域的专家，在Kaehler-Einstein度量的存在性、量子上同调理论、高维Yang-Mills联络、四维流形的研究、退化复Monge-Ampere方程、Ricci流与庞加莱猜想等问题上做出了一系列的贡献。由于这些学术成就，他2001年被选为中国科学院院士，2004年被选为美国艺术与科学研究院院士，2002年受邀在国际数学家大会上作1小时大会报告。田刚教授曾获Alan Waterman奖（1994年）和Oswald Veblen奖（1996年）并担任Annals of Mathematics（2007至今）等杂志编委，和阿贝尔奖（2012至今）等评奖委员会成员。
     * department : 科学技术发展研究院
     * time : 2016-04-18
     */

    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String title;
        @SerializedName("abstract")
        private String abstractX;
        private String reporter;
        private String department;
        private String time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getReporter() {
            return reporter;
        }

        public void setReporter(String reporter) {
            this.reporter = reporter;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
