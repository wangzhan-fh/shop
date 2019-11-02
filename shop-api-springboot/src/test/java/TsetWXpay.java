import com.github.wxpay.sdk.MyConfig;
import com.github.wxpay.sdk.WXPay;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TsetWXpay {

    @Test
    public void hello() {
        System.out.println("hello world");
    }

    @Test
    public void test1(){
        try {
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "飞狐电商——订单支付中心");
        data.put("out_trade_no", "2019102921011189165390670921729");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
       // data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付


            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void test2(){
        MyConfig config = new MyConfig();
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", "2019102921011189165390670921729");
            int count=0;
            while (true){
                Map<String, String> resp = wxpay.orderQuery(data);
                //判断return_code是否成功
                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                System.out.println(resp);
                if(!return_code.equalsIgnoreCase("success")){
                    System.out.println(return_msg);
                    return;
                }

                //判断result_code是否成功
                String result_code = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if(!result_code.equalsIgnoreCase("success")){
                    System.out.println(err_code_des);
                    return;
                }

                //判断支付是否成功
                String trade_state = resp.get("trade_state");
                if(trade_state.equalsIgnoreCase("success")){
                    System.out.println("======支付成功");
                    return;
                }

                count++;
                Thread.sleep(3000);
                if(count>=100){
                    System.out.println("支付失败");
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test3() throws Exception {
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", "2016090910595900000016");

        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
