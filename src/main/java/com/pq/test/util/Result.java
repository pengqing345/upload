package com.pq.test.util;

/**
 * 返回结果封装
 * @Author chengtinghua
 * @Date 2017/1/6 9:51
 */
public class Result {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILED = 1;

    private int code;

    private String message;

    private Object content;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.content = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String message(String value) {

        return value;
    }

    public static Result of(boolean b) {
        return b ? success() : failed();
    }

    public static Result success(Object data) {
        return new Result(CODE_SUCCESS, null, data);
    }

    public static Result success() {
        return new Result(CODE_SUCCESS, "成功");
    }

    public static Result success(Object data, String message) {
        return new Result(CODE_SUCCESS, message, data);
    }

    public static Result failed(String message) {
        return new Result(CODE_FAILED, message, null);
    }

    public static Result failed() {
        return new Result(CODE_FAILED, null, null);
    }

    public static Result failed(String message, Object bean) {
        return new Result(CODE_FAILED, message, bean);
    }

    public static Result failed(String message, String bean) {
        return new Result(CODE_FAILED, message, bean);
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
