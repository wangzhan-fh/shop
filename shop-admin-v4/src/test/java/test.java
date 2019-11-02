import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class test {


    public class MyConfig implements WXPayConfig {





        public String getAppID() {
            return "wxa1e44e130a9a8eee";
        }

        public String getMchID() {
            return "1507758211";
        }

        public String getKey() {
            return "feihujiaoyu12345678yuxiaoyang123";
        }

        @Override
        public InputStream getCertStream() {
            return null;
        }


        public int getHttpConnectTimeoutMs() {
            return 8000;
        }

        public int getHttpReadTimeoutMs() {
            return 10000;
        }
    }


@Test
    public void aaa() throws Exception {
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", "201609091059590456642");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }






@Test
public void bbb(){


    MyConfig config = new MyConfig();
    WXPay wxpay = new WXPay(config);

    Map<String, String> data = new HashMap<String, String>();
    data.put("out_trade_no", "201609091059590456642");

    try {
        Map<String, String> resp = wxpay.orderQuery(data);
        System.out.println(resp);
    } catch (Exception e) {
        e.printStackTrace();
    }
}







}
