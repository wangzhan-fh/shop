package com.fh.shop.api.conmmons;

public enum ResponceEnum {

     FIND_IS_NULL(1000,"查找数据不存在！"),
     IDS_IS_NULL(1001,"请输入批量删除的数据！"),
     PHONECODE_IS_NULL(1003,"验证码为空！"),
     PHONE_IS_NULL(1004,"手机号为空！"),
     smsCode_IS_ERROR(1005,"验证码发送失败！"),
     PHONE_IS_ERROR(1006,"手机号格式不对！"),
     EMAIL_IS_NULL(1007,"eamil为空！"),
     MEMBER_IS_NULL(1008,"会员名为空！"),
     MEMBER_IS_ERROE(10016,"会员名错误！"),
     PASSWORD_IS_NULL(1015,"密码为空！"),
     PASSWORD_IS_ERROR(1017,"密码错误！"),
     MEMBER_IS_CF(1009,"会员名已存在！"),
     EMAIL_IS_CF(1010,"email已存在！"),
     PHONE_IS_CF(1011,"手机号已存在！"),
     ZHUCE_INFO(1015,"请先注册！"),
     CODE_IS_ERROR(1012,"验证码以失效！"),
     PHONECODE_IS_ERROE(1002,"验证码错误！"),
     HEADER_IS_NULL(2000,"头信息不存在！"),
     HEADER_IS_MISS(2001,"头信息缺失！"),
     CONTEXT_IS_CHANGE(2002,"内容被篡改！"),
     LOGIN_ITIME_OUT(2003,"登录超时！"),

    PRODUCT_IS_NULL(2100,"商品不存在！"),
    SHELVES_IS_DOWN(2101,"商品已下架！"),
    CART_IS_NULL(2102,"购物车为空"),
    ITEM_IS_NULL(2103,"购物车不存在！"),
    CART_ALL_IS_NULL(2188,"所有订单库存量不足！"),


    ZFTYPE_IS_NULL(2104,"请选择支付方式！"),
    A1_IS_NULL(2105,"省份为空！"),
    A2_IS_NULL(2106,"市为空！"),
    A3_IS_NULL(2107,"县份为空！"),
    NAME_IS_NULL(2108,"收货人为空！"),
    INFO_IS_NULL(2109,"详细地址为空！"),
    SHINFO_IS_NULL(2110,"请选择收货地址！"),

    TOKEN_IS_ERROR(2112,"token头信息错误！"),
    TOKEN_IS_CF(2113,"重复提交！"),

    PAY_IS_NULL(3000,"支付数据为空，请添加！"),
    OUTID_PAY_IS_NULL(3001,"没有订单信息！"),




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
