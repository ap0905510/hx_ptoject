package com.yw.mvp.model;

/**
 * Created by colin on 2017/7/14.
 */

public class UserInfo {

    protected String s;
    protected String m;
    private DBean d;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {

        private String sex;
        private String nname;
        private String msisdn;
        private String iconUrl;
        private String showType;
        private String type;
        private String version;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNname() {
            return nname;
        }

        public void setNname(String nname) {
            this.nname = nname;
        }

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "DBean{" +
                    "sex='" + sex + '\'' +
                    ", nname='" + nname + '\'' +
                    ", msisdn='" + msisdn + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", showType='" + showType + '\'' +
                    ", type='" + type + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "s='" + s + '\'' +
                ", m='" + m + '\'' +
                ", d=" + d +
                '}';
    }
}
