package com.fh.shop.conmmons;

public  enum ResponceEnum {

     USERNAME_PASSWORD_IS_NULL(1000,"用户名或密码为空"),
     USERNAME_IS_ERROR(1001,"用户名错误"),
     PASSWORD_IS_ERROR(1002,"密码错误"),
     IS_ERROR(1003,"当天密码连续错误三次，账户被锁定"),
    ALL_PASSWORD_IS_NULL(1004,"密码为空"),
    OLDPASSWORD_IS_ERROR(1005,"旧密码有误，请重新输入！"),
    NEW_OR_CONFIRM_PASSWORD_IS_ERROR(1006,"新密码确认密码不一致，请重新输入！"),
    EMAIL_IS_NULL(1007,"邮箱不能为空！"),
    EMAIL_IS_ERROR(1008,"邮箱错误！"),
    USER_NULL(1009,"用户不存在！"),
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
