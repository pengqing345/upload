package com.pq.test.util;

public class message {
        //0表示成功;-1表示失败
        int status;
        //向前端返回的内容
        String message;
        public message() {
            super();
        }
        public message(int status, String message) {
            super();
            this.status = status;
            this.message = message;
        }


}
