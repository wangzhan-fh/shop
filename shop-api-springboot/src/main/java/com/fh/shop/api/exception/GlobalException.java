package com.fh.shop.api.exception;


import com.fh.shop.api.conmmons.ResponceEnum;

public class GlobalException  extends RuntimeException{


    private ResponceEnum responceEnum;

    public GlobalException(ResponceEnum responceEnum){
            this.responceEnum=responceEnum;
    }

    public ResponceEnum getResponceEnum(){
        return responceEnum;
    }


}
