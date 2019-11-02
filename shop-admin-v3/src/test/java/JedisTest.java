import com.fh.shop.util.RedisUtil;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

public class JedisTest {

    //redis连接池
    static ShardedJedisPool pool;
    static{
        //redis服务器列表 这里可以设置多个redis服务器的地址和端口
        String hostA ="192.168.86.129";
        int port1 =7020;
        List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>();
        JedisShardInfo jedis1 = new JedisShardInfo(hostA, port1);
        jdsInfoList.add(jedis1);
        //池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(1000 * 60);//对象最大空闲时间
        config.setTestOnBorrow(true);

        pool=new ShardedJedisPool(config, jdsInfoList);
    }
    public static void main(String[] args) {
        String username = RedisUtil.get("username");

        System.out.println(username);

    }

}
