import com.fh.shop.po.user.User;
import org.junit.Test;

import java.awt.print.Book;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

public class FsTest {

    @Test
    public  void BookTest(){
        //第一种方式获取Class对象
        Book book = new Book();//这一new 产生一个Student对象，一个Class对象。
        Class bookClass = book.getClass();//获取Class对象
        System.out.println(bookClass.getName());

        //第二种方式获取Class对象
        Class book2 = Book.class;
        System.out.println(bookClass == book2);

//第三种方式获取Class对象
        try {
            Class bookClass3 = Class.forName("com.book.po.Book");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
            System.out.println(bookClass3 == book2);//判断三种方式是否获取的是同一个Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void methodTest()  {
        //（1）获取class对象
        try {
            Class book = Class.forName("com.book.po.Book");
            Object obj = book.getConstructor().newInstance();
           /* //3) 获取所有的字段(包括私有、受保护、默认的)
            Field[] fieldArray = book.getDeclaredFields();
            for(Field f : fieldArray){
                System.out.println(f);
            }*/
            // (5) 获取私有字段****并调用

            Field bookDeclaredField = book.getDeclaredField("bookName");
            bookDeclaredField.setAccessible(true);
            System.out.println(bookDeclaredField);
            bookDeclaredField.set(obj,"三国"); //为book对象中的name属性赋值-- "三国"

            bookDeclaredField.set(obj, "18888889999");
            //暴力反射，解除私有限定
            System.out.println("验证电话：" + obj);



        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
     /*       //(2)  获取所有公有的字段
            Field[] fieldArray = book.getFields();
            for(Field f : fieldArray){
                System.out.println(f);
            }
            //(4) 获取公有字段**并调用
        Field f = book.getDeclaredFields("name");
        System.out.println(f);
        //获取一个对象
        Object obj = book.getConstructor().newInstance();
        //为字段设置值
        f.set(obj, "刘德华");//为Student对象中的name属性赋值--》stu.name = "刘德华"
        //验证
        Book b1 = (Book) obj;
        System.out.println("验证姓名：" + b1.getBookName());
        */

    }

    @Test
    public void test2(){
        show(User.class);
    }
    public void show(Class clazz){

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println(f.getName());

        }

    }


    @Test
    public void tests(){
        String content = "19323456789";

        String pattern = "^1(3|5|7|8)\\d{9}";//^1(3|5|7|8)\d{9} //java手机号正则

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println(isMatch);
    }


}
