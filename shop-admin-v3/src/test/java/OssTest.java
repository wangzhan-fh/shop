import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ObjectMetadata;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class OssTest {

    @Test
public void TestOss() throws FileNotFoundException {
    OSSClient ossClient = new OSSClient("https://oss-cn-beijing.aliyuncs.com", "LTAI4Fh2vVXYfa38x6CRRzxy", "CUcWVhTBgyJECuHtfUObHEaMezPx5u");
    // 上传文件流。
    File file = new File("D:/image/1.jpg");
    InputStream is = new FileInputStream(file);
        //文件名
        String name = file.getName();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("https");
        objectMetadata.setContentDisposition("inline; filename=noavatar_middle.gif");
//第一个参数 是 存储空间的名称  第二个是  文件夹+文件名称  fileName 是 文件名称  is  是文件流
    ossClient.putObject("zhan5897", "img/" + name, is,objectMetadata);
// 关闭OSSClient。
    ossClient.shutdown();
    String str = "img/"+name   ;
}


@Test
public  void delete() {
    // 创建OSSClient实例。
    OSSClient ossClient = new OSSClient("https://oss-cn-beijing.aliyuncs.com", "LTAI4Fh2vVXYfa38x6CRRzxy", "CUcWVhTBgyJECuHtfUObHEaMezPx5u");

// 删除文件。
    ossClient.deleteObject("zhan5897", "img/4.jpg");

// 关闭OSSClient。
    ossClient.shutdown();



}


}
