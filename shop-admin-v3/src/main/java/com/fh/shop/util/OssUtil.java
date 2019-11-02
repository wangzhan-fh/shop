package com.fh.shop.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public class OssUtil {
    public static String ENDPOINT="https://oss-cn-beijing.aliyuncs.com";
    public static String ACCESSKEYID="LTAI4Fh2vVXYfa38x6CRRzxy";
    public static String SECRETACCESSKEY="CUcWVhTBgyJECuHtfUObHEaMezPx5u";
    public static String BUCKET_NAME="zhan5897";
    public static String OSS_PATH="https://zhan5897.oss-cn-beijing.aliyuncs.com/";

    public static String uploadFile(InputStream is,String fileName){
        OSSClient ossClient = null;
        String dateStr = null;
        try {
            //创建ossclient实例  第一个参数 是你的域名  第二个参数 是产品app  第三个是 对应的密匙
            ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, SECRETACCESSKEY);
            dateStr = DateUtil.data2str(new Date(), DateUtil.Y_M_D);
            //UUID
            if(StringUtils.isEmpty(fileName)){
                return "";
            }
            String uuid = UUID.randomUUID().toString();
            fileName = uuid+fileName.substring(fileName.lastIndexOf("."));
            //第一个参数 是 存储空间的名称  第二个是  文件夹+文件名称  fileName 是 文件名称  is  是文件流
            ossClient.putObject(BUCKET_NAME, dateStr+"/"+ fileName, is);

        } catch (OSSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ossClient!=null){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }

     return OSS_PATH+dateStr+"/"+fileName;
}

    public static void  deleteFile(String pathImg){
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, SECRETACCESSKEY);
       if(pathImg!=null){
           pathImg=pathImg.replace(OSS_PATH,"");
       }
        ossClient.deleteObject(BUCKET_NAME, pathImg);
    }

}
