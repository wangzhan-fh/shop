package com.fh.shop.controller.user;

import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.util.FileUtil;
import com.fh.shop.util.SystemConst;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {


    @RequestMapping("/uploadimg")
    public ServerResponse uploadimg(MultipartFile myfile, HttpServletRequest req){
        try {
            InputStream is = myfile.getInputStream();
            String realPath = req.getServletContext().getRealPath(SystemConst.IMAGE_PATH);
            String s = FileUtil.copyFile(is, myfile.getOriginalFilename(), realPath);
            String mainImg = SystemConst.IMAGE_PATH+s;
            return ServerResponse.success(mainImg);
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }


    }

}
