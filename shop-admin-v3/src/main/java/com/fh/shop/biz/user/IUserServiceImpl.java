package com.fh.shop.biz.user;

import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.ResponceEnum;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.controller.user.UserController;
import com.fh.shop.mapper.user.IUserMapper;
import com.fh.shop.param.user.UserParam;
import com.fh.shop.param.user.UserPasswordParam;
import com.fh.shop.po.user.User;
import com.fh.shop.po.user.UserRole;
import com.fh.shop.util.*;
import com.fh.shop.vo.user.UserVo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

@Service("userService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    //新增
    @Override
    public void addUser(User user) {
        String s = UUID.randomUUID().toString();
        user.setSalt(s);
        user.setPassword(Md5Util.enCodeString(user.getPassword(), s));
        userMapper.insert(user);
        addUserRole(user);

    }
    //新增角色信息
    private void addUserRole(User user) {
        String roleIds = user.getRoleIds();
        if(StringUtils.isNotEmpty(roleIds)) {
            String[] split = roleIds.split(",");
            for (String s : split) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(Long.parseLong(s));
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
        userInfo.setStatus(user1.getErrorCount()==SystemConst.REEORCOUNT  && DateUtil.data2str(user1.getErrorTime(),DateUtil.Y_M_D).equals(DateUtil.data2str(new Date(),DateUtil.Y_M_D)));
        userInfo.setAreaName(user1.getAreaName());
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
        String photo = user.getPhoto();
        if(StringUtils.isEmpty(photo)){
            user.setPhoto(user.getOldPath());
        }else {
            OssUtil.deleteFile(user.getOldPath());
        }

        userMapper.updateById(user);
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
            //批量删除及删除中间表
            userMapper.deleteBatchIds(list);
            userMapper.deleteRoleIds(list);
        }

    }

    @Override
    public User findByUserName(User user) {
        return userMapper.findByUserName(user);
    }

    @Override
    public void addLoginTime(User user) {
        //登录错误次数清零
        user.setErrorCount(0);
        //判断是否当天时间 为空通过dateutil   进行判断返回空字符串
        if(DateUtil.data2str(user.getLoginTime(),DateUtil.Y_M_D).equals(DateUtil.data2str(new Date(),DateUtil.Y_M_D))){
          //如果密码输错三次则发送邮箱
            user.setLoginCount(user.getLoginCount()+1);
        }else{
            //不是重新赋值
            user.setLoginCount(1);
        }
        userMapper.addLoginTime(user);
    }


    @Override
    public void updateErrorCount(User userInfo) {
        //判断是否当天时间   为空通过dateutil   进行判断返回空字符串
        if(DateUtil.data2str(userInfo.getErrorTime(),DateUtil.Y_M_D).equals(DateUtil.data2str(new Date(),DateUtil.Y_M_D))&& userInfo.getErrorCount()<3){
            userInfo.setErrorCount(userInfo.getErrorCount()+1);
            if(userInfo.getErrorCount()==SystemConst.REEORCOUNT){
                MailUtil.sendMail("异常警告",SystemConst.PASSWORD_EMAIL_ERROR,userInfo.getEmail());
            }
        }else{
            //不是当天设为1
            userInfo.setErrorCount(1);
        }
        userMapper.updateErrorCount(userInfo);
    }

    @Override
    public List<User> saeshfindList(UserParam user) {
        return userMapper.saeshfindList(user);
    }

    @Override
    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    //修改密码
    @Override
    public ServerResponse updatePassword(UserPasswordParam userPasswordParam) {
        //判断参数是否为空
        if(!StringUtils.isNotEmpty(userPasswordParam.getConfirmPassword())
               || !StringUtils.isNotEmpty(userPasswordParam.getOldPassword())
                || !StringUtils.isNotEmpty(userPasswordParam.getNewPassword())){
                return  ServerResponse.error(ResponceEnum.ALL_PASSWORD_IS_NULL);
        }
        //根据用户id查要修改的用户
        User userDB=userMapper.selectById(userPasswordParam.getUserId());
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
    public void updateLock(long id) {
        userMapper.updateLock(id);
    }

    @Override
    public ServerResponse resetPassword(Long id) {
        User user = userMapper.selectById(id);
        if(user==null){
            return ServerResponse.error();
        }
        String salt = user.getSalt();
        String string = Md5Util.enCodeString(SystemConst.UPDATE_PASSWORD_EMAIL, salt);
        userMapper.resetPassword(id,string);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse sendPasswordByEmail(String email) {
        User userDB=userMapper.sendPasswordByEmail(email);
        if(userDB==null){
            return ServerResponse.error(ResponceEnum.EMAIL_NULL);
        }
        String s = RandomStringUtils.randomAlphanumeric(6);
        String password = Md5Util.enCodeString(s, userDB.getSalt());
        userMapper.resetPassword(userDB.getId(),password);

        MailUtil.sendMail("找回密码","新密码是"+s,email);
        return ServerResponse.success();
    }




    @Override
    public File buildWord(List<User> list) {
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        //创建configuration对象   进行配置
        Configuration configuration = new Configuration();
        //设置编码格式
        configuration.setDefaultEncoding("UTF-8");
        //设置  本方法所在类  最终源代码会根据当前类调方法获取模板所在包路径 注意加‘/’  开头
        configuration.setClassForTemplateLoading(UserController.class, "/word/");
        //获取模板对象    参数模板具体名
        Template template = null;
        FileOutputStream fileOutputStream = null;
        File file =null;
        try {
            template = configuration.getTemplate("word.xml");
            //创建文件对象  临时存放文件位置
            file=new File("D:/"+UUID.randomUUID()+".doc");
            //创建文件输出流   用于写文件
            fileOutputStream = new  FileOutputStream(file);
            //创建写入器   用于将流文件写称utf-8格式
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"utf-8");
            //通过写入器 将map中的值写入到 模板中
            template.process(map, outputStreamWriter);
            //关流
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public ByteArrayOutputStream buildPdf(List<User> list) {
        //创建字节输出流
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {
            buildBody(list, bos);
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bos;
    }

    private void buildBody(List<User> list, ByteArrayOutputStream bos) throws DocumentException, IOException {
        //创建字体样式
        BaseFont bfChinese = BaseFont.createFont("C:\\Windows\\Fonts\\simsun.ttc,1",  BaseFont.IDENTITY_H, 	BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(bfChinese, 10, Font.NORMAL);
        //创建文本对象
        Document document =new Document(PageSize.A4);
        //创建书写器
        PdfWriter.getInstance(document, bos);
        //打开文本
        document.open();
        // 通过 com.lowagie.text.Paragraph 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
        document.add(new Paragraph("用户信息:", fontChinese));

        // document.newPage();
        // 向文档中添加内容
        for (int i = 0; i < list.size(); i++) {
            document.add(new Paragraph("用户名："+list.get(i).getUserName(),fontChinese));
            document.add(new Paragraph("真实姓名："+list.get(i).getRealName(),fontChinese));
            document.add(new Paragraph("年龄："+list.get(i).getAge().toString(),fontChinese));
            document.add(new Paragraph("电话："+list.get(i).getPhone(),fontChinese));
            document.add(new Paragraph("邮箱："+list.get(i).getEmail(),fontChinese));
            document.add(new Paragraph("入职时间："+DateUtil.data2str(list.get(i).getEntryTime(),DateUtil.Y_M_D),fontChinese));
            document.add(new Paragraph("\n"));
        }

        document.close();
    }



}
