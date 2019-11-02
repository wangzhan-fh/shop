package com.fh.shop.biz.user;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.param.user.UserParam;
import com.fh.shop.param.user.UserPasswordParam;
import com.fh.shop.po.user.User;
import com.fh.shop.vo.user.UserVo;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface IUserService {

    public void addUser(User user);

    //查询
    DataTableResult findList(UserParam user);

    //删除
    void deleteById(Long id);

    UserVo queryById(Long id);

    void updateUser(User user);



    void deleteIds(String ids);

    User findByUserName(User user);

    void addLoginTime(User  user);


    void updateErrorCount(User userInfo);

    List<User> saeshfindList(UserParam user);

    User findByName(String username);

    ServerResponse updatePassword(UserPasswordParam userPasswordParam);

    void updateLock(long id);

    ServerResponse resetPassword(Long id);

    ServerResponse sendPasswordByEmail(String email);


    File buildWord(List<User> list);

    ByteArrayOutputStream buildPdf(List<User> list);
}
