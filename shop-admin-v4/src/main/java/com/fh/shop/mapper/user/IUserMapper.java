package com.fh.shop.mapper.user;

import com.fh.shop.param.user.UserParam;
import com.fh.shop.param.user.UserPasswordParam;
import com.fh.shop.po.user.User;
import com.fh.shop.po.user.UserRole;

import java.util.List;

public interface IUserMapper {
    public  void addUser(User user);

    Long findCount(UserParam user);

    List<User> findList(UserParam user);

    void deleteById(Long id);

    void updateUser(User user);

    User queryById(Long id);

    void addUserRole(UserRole userRole);

    List<String> findRoleNames(Long id);

    List<Integer> findRoleId(Long id);

    void deleteRoleId(Long id);

    void deleteUserRole(Long id);

    void deleteIds(List list);

    void deleteRoleIds(List list);

    User findByName(String userName);

    void addLoginTime(User userInfo);

    void updateError(User userInfo);

    void updatePassword(UserPasswordParam userPasswordParam);

    void updateLock(Long id);

    User findEmail(String email);

    void updatePasswordByEmail(User userDB);

    void updatePasswordById(User user);
}
