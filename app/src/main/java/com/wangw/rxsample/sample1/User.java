package com.wangw.rxsample.sample1;

/**
 * Created by wangw on 2016/3/3.
 */
public class User {


    /**
     * nick_name : 白菜
     * head_photo : http://images.langtianhealth.com:8765/v1/image/6e33afb63112e734329c6de0120bb318
     * gender : true
     * home_address : {"address":"住址不知道"}
     */

    private InfoEntity info;
    /**
     * info : {"nick_name":"白菜","head_photo":"http://images.langtianhealth.com:8765/v1/image/6e33afb63112e734329c6de0120bb318","gender":true,"home_address":{"address":"住址不知道"}}
     * uid : 42
     */

    private int uid;

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public int getUid() {
        return uid;
    }

    public static class InfoEntity {
        private String nick_name;
        private String head_photo;
        private boolean gender;
        /**
         * address : 住址不知道
         */

        private HomeAddressEntity home_address;

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        public void setHome_address(HomeAddressEntity home_address) {
            this.home_address = home_address;
        }

        public String getNick_name() {
            return nick_name;
        }

        public String getHead_photo() {
            return head_photo;
        }

        public boolean isGender() {
            return gender;
        }

        public HomeAddressEntity getHome_address() {
            return home_address;
        }

        public static class HomeAddressEntity {
            private String address;

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddress() {
                return address;
            }
        }
    }
}
