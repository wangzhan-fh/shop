package com.fh.shop.conmmons;

public enum ResponceEnum {

     USERNAME_PASSWORD_IS_NULL(1000,"验证码,用户名或密码为空"),
     IMGCODE_ERROR(1008,"验证码错误"),
     USERNAME_IS_ERROR(1001,"用户名错误"),
     PWD_IS_ERROR(1003,"密码错误"),
     PASSWORD_IS_ERROR(1002,"密码连续错误三次，请第二天再次登录"),
     ALL_PASSWORD_IS_NULL(1004,"密码为空"),
     OLDPASSWORD_IS_ERROR(1005,"旧密码有误，请重新输入！"),
     NEW_OR_CONFIRM_PASSWORD_IS_ERROR(1006,"新密码确认密码不一致，请重新输入！"),
     EMAIL_NULL(1007,"邮箱不存在！"),
    ;
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private ResponceEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
