package com.fh.shop.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.param.user.UserParam;
import com.fh.shop.param.user.UserPasswordParam;
import com.fh.shop.po.user.User;
import com.fh.shop.po.user.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserMapper extends BaseMapper<User> {
   /*public  void addUser(User user);*/

    Long findCount(UserParam user);

    List<User> findList(UserParam user);

   /* void deleteById(Long id);

   void updateUser(User user);*/

    User queryById(Long id);

    void addUserRole(UserRole userRole);

    List<String> findRoleNames(Long id);

    List<Integer> findRoleId(Long id);

    void deleteRoleId(Long id);

    void deleteUserRole(Long id);

    /*void deleteIds(List list);*/

    void deleteRoleIds(List list);

    User findByUserName(User user);

    void addLoginTime(User user);

    void updateErrorCount(User userInfo);

    List<User> saeshfindList(UserParam user);

    User findByName(String username);

    void updatePassword(UserPasswordParam userPasswordParam);

    void updateLock(long id);

    void resetPassword(@Param("id") Long id,@Param("password") String string);

    User sendPasswordByEmail(String email);
}
