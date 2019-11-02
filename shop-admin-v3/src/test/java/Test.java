import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Test {



    @org.junit.Test
    public void test1() throws IOException {

        //打开浏览器
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //输入url
        HttpGet httpGet = new HttpGet("http://localhost:8081/brands");
        //敲回车
        HttpResponse httpResponse = client.execute(httpGet);

        HttpEntity entity = httpResponse.getEntity();
        String s = EntityUtils.toString(entity, "utf-8");
        System.out.println(s);
        //关闭浏览器
        client.close();
    }


    @org.junit.Test
    public void test2() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:8081/brands/lists?draw=1&start=0&length=5&brandName=小");
        HttpResponse execute = client.execute(httpGet);
        String s = EntityUtils.toString(execute.getEntity(), "UTF-8");
        System.out.println(s);
        client.close();
    }


}
