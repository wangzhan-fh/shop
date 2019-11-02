package com.fh.shop.biz.user;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.ResponceEnum;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.mapper.user.IUserMapper;
import com.fh.shop.param.user.UserParam;
import com.fh.shop.param.user.UserPasswordParam;
import com.fh.shop.po.user.User;
import com.fh.shop.po.user.UserRole;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.MailUtil;
import com.fh.shop.util.Md5Util;
import com.fh.shop.util.SystemConst;
import com.fh.shop.vo.user.UserVo;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service("userService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    //新增
    @Override
    public void addUser(User user) {
        String s = UUID.randomUUID().toString();
        user.setSalt(s);
        user.setPassword(Md5Util.md5(Md5Util.md5(user.getPassword())+s));
        userMapper.addUser(user);
        addUserRole(user);

    }
    //新增角色信息
    private void addUserRole(User user) {
        String roleIds = user.getRoleIds();
        if(StringUtils.isNotEmpty(roleIds)) {
            String[] split = roleIds.split(",");
            for (String s : split) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Long.parseLong(s));
                userRole.setUserId(user.getId());
                userMapper.addUserRole(userRole);
            }
        }
    }

    //查询
    @Override
    public DataTableResult findList(UserParam user) {
        //构建角色查询id
        buildRoleList(user);
        //查询总条数
        Long count = userMapper.findCount(user);
        //用户列表
        List<User> list =userMapper.findList(user);
        //角色vo
        List<UserVo> dataList = buildUservoList(user, list);
        DataTableResult dataTableResult = new DataTableResult(user.getDraw(), count, count, dataList);
        return dataTableResult;
    }

    private List<UserVo> buildUservoList(UserParam user, List<User> list) {
        List<UserVo> dataList =new ArrayList<>();
        for (User user1 : list) {
            UserVo userInfo = getUserVo(user1);
            List<String> roleNames=  userMapper.findRoleNames(user1.getId());
            if(roleNames!=null && roleNames.size()>0){
                String join = StringUtils.join(roleNames, ",");
                userInfo.setRoleNames(join);
            }
            dataList.add(userInfo);
        }
        buildRoleList(user);
        return dataList;
    }

    private UserVo getUserVo(User user1) {

        UserVo userInfo =new UserVo();
        userInfo.setPhone(user1.getPhone());
        userInfo.setEmail(user1.getEmail());
        userInfo.setSex(user1.getSex());
        userInfo.setAge(user1.getAge());
        userInfo.setId(user1.getId());
        userInfo.setUserName(user1.getUserName());
        userInfo.setRealName(user1.getRealName());
        userInfo.setEntryTime(DateUtil.data2str(user1.getEntryTime(),DateUtil.Y_M_D));
        userInfo.setPay(user1.getPay());
        userInfo.setPhoto(user1.getPhoto());
        userInfo.setStatus(user1.getErrorCount()==SystemConst.ERRORCOUNT  && DateUtil.data2str(user1.getErrorTime(),DateUtil.Y_M_D).equals(DateUtil.data2str(new Date(),DateUtil.Y_M_D)) );
        return userInfo;
    }


    //获取条件的IDS
    private void buildRoleList(UserParam user) {
        String roleIds = user.getRoleIds();
        List<Integer> idsList = user.getIdsList();
        if(StringUtils.isNotEmpty(roleIds)){
            String[] split = roleIds.split(",");
            for (String s : split) {
                idsList.add(Integer.parseInt(s));
            }
            user.setSize(idsList.size());
        }
    }

    //删除
    @Override
    public void deleteById(Long id) {
        userMapper.deleteRoleId(id);
        userMapper.deleteById(id);
    }

    //回显
    @Override
    public UserVo queryById(Long id) {
        User user = userMapper.queryById(id);

        UserVo userInfo = getUserVo(user);
        List<Integer> roleid =userMapper.findRoleId(id);
        if(roleid!=null && roleid.size()>0){
            userInfo.setIds(roleid);
        }
        userInfo.setPassword(user.getPassword());
        return userInfo;
    }

    //修改
    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
        userMapper.deleteRoleId(user.getId());
        addUserRole(user);
    }


    @Override
    public void deleteIds(String ids) {
        List  list =new ArrayList();
        if(StringUtils.isNotEmpty(ids)){
            String[] arr = ids.split(",");
            for (String s : arr) {
                list.add(s);
            }
            userMapper.deleteRoleIds(list);
            userMapper.deleteIds(list);
        }

    }

    @Override
    public User findByName(String userName) {
        return userMapper.findByName(userName);
    }

    //更新登录时间
    @Override
    public void addLoginTime(User userInfo) {
        //登录成功把错误次数清零
        userInfo.setErrorCount(0);
        if(DateUtil.data2str(userInfo.getLoginTime(),DateUtil.Y_M_D).equals(DateUtil.data2str(new Date(),DateUtil.Y_M_D))){
            userInfo.setLoginCount(userInfo.getLoginCount()+1);
        } else {
            userInfo.setLoginCount(1);
        }
        userMapper.addLoginTime(userInfo);
    }

    //更新密码错误信息
    @Override
    public void updateError(User userInfo) {
        //判断登录错误时间是否同一天
        if(DateUtil.data2str(userInfo.getErrorTime(),DateUtil.Y_M_D).equals(DateUtil.data2str(new Date(),DateUtil.Y_M_D))){
            //同一天则加1
            if(userInfo.getErrorCount()==SystemConst.ERRORCOUNT-1){
                MailUtil.sendMail("异常警告",SystemConst.PASSWORD_EMAIL_ERROR,userInfo.getEmail());
            }
            userInfo.setErrorCount(userInfo.getErrorCount()+1);
        }else{
            //第一次或者如果不是则赋值为1，非空在工具类判断过
            userInfo.setErrorCount(1);
        }
        userMapper.updateError(userInfo);
    }

    @Override
    public ServerResponse updatePassword(UserPasswordParam userPasswordParam) {
        //判断参数是否为空
        if(!StringUtils.isNotEmpty(userPasswordParam.getConfirmPassword())
                || !StringUtils.isNotEmpty(userPasswordParam.getOldPassword())
                || !StringUtils.isNotEmpty(userPasswordParam.getNewPassword())){
            return  ServerResponse.error(ResponceEnum.ALL_PASSWORD_IS_NULL);
        }
        //根据用户id查要修改的用户
        User userDB=userMapper.queryById(userPasswordParam.getUserId());
        String salt = userDB.getSalt();

        if(!Md5Util.enCodeString(userPasswordParam.getOldPassword(), salt).equals(userDB.getPassword())){
            return ServerResponse.error(ResponceEnum.OLDPASSWORD_IS_ERROR);
        }
        if (!userPasswordParam.getConfirmPassword().equals(userPasswordParam.getNewPassword())) {
            return ServerResponse.error(ResponceEnum.NEW_OR_CONFIRM_PASSWORD_IS_ERROR);
        }
        userPasswordParam.setNewPassword(Md5Util.enCodeString(userPasswordParam.getNewPassword(),salt));
        userMapper.updatePassword(userPasswordParam);
        return ServerResponse.success();
    }

    @Override
    public void updateLock(Long id) {
        userMapper.updateLock( id);
    }

    @Override
    public User updateEmail(String email) {
       return userMapper.findEmail(email);
    }

    @Override
    public void updatePasswordByEmail(User userDB) {
        userMapper.updatePasswordByEmail(userDB);
    }

    @Override
    public ServerResponse updatePasswordById(Long id) {
        User user = userMapper.queryById(id);
        if(user==null){
            return ServerResponse.error(ResponceEnum.USER_NULL);
        }

        String salt = user.getSalt();
        user.setPassword(Md5Util.enCodeString(SystemConst.UPDATE_PASSWORD_EMAIL,salt));
        userMapper.updatePasswordById(user);
        return ServerResponse.success();
    }
}
