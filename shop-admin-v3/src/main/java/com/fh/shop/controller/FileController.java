package com.fh.shop.controller;

import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.util.FileUtil;
import com.fh.shop.util.OssUtil;
import com.fh.shop.util.SystemConst;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/uploadimg")

    public ServerResponse uploadimg(MultipartFile myfile, HttpServletRequest request){
        try {
            InputStream is = myfile.getInputStream();
            String originalFilename = myfile.getOriginalFilename();
            String mainImg = OssUtil.uploadFile(is, originalFilename);
            return  ServerResponse.success(mainImg);
        } catch (Exception e) {
            e.printStackTrace();
            return  ServerResponse.error();
        }
    }

}
