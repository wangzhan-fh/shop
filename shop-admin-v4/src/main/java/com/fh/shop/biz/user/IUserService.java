package com.fh.shop.biz.user;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.param.user.UserParam;
import com.fh.shop.param.user.UserPasswordParam;
import com.fh.shop.po.user.User;
import com.fh.shop.vo.user.UserVo;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    User findByName(String userName);

    void addLoginTime(User userInfo);

    void updateError(User userInfo);

    ServerResponse updatePassword(UserPasswordParam userPasswordParam);

    void updateLock(Long id);

    User updateEmail(String email);


    void updatePasswordByEmail(User userDB);

    ServerResponse updatePasswordById(Long id);
}
