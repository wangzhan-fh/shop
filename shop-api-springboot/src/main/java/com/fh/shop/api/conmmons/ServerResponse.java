package com.fh.shop.api.conmmons;

import java.io.Serializable;

public class ServerResponse  implements Serializable {

    private int code;

    private String msg;

    private Object data;

    private ServerResponse(){}

    private ServerResponse(int code,String msg,Object data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    public static  ServerResponse success(){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCode(200);
        serverResponse.setMsg("ok");
        return serverResponse;
    }

    public static  ServerResponse success(Object data){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCode(200);
        serverResponse.setMsg("ok");
        serverResponse.setData(data);
        return serverResponse;
    }
    public static  ServerResponse error(){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCode(-1);
        serverResponse.setMsg("操作失败！！");
        return serverResponse;
    }

    public static  ServerResponse error(int code,String msg){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCode(code);
        serverResponse.setMsg(msg);
        return serverResponse;
    }

     public static  ServerResponse error(ResponceEnum responceEnum){

        return new ServerResponse(responceEnum.getCode(),responceEnum.getMsg(),null);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
