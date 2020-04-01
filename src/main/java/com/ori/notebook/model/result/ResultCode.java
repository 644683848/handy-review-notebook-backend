package com.ori.notebook.model.result;

/**
 * 公共的返回码
 */
public enum ResultCode {

    //成功的操作是1开头的
    SUCCESS(true,10000,"操作成功！"),
    //失败的操作大于20000
    FAIL(false,20001,"操作失败"),
    UNAUTHENTICATED(false,20002,"token错误"),
    TOKENTIMEOUT(false, 20003, "token过期"),
    UNAUTHORISE(false,20004,"权限不足"),
    LOGINERR(false, 20005, "用户名或密码错误"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),
    //---用户操作返回码  2xxxx----
    VALID_ERR(false, 40001, "参数验证错误"),
    //---其他操作返回码----
    ARG_ERR(false, 80001, "参数类型错误");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
