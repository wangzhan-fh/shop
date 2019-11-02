package com.fh.shop.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static void set (String key , String value){


            Jedis getresource = null;
            try {
                getresource = RedisPool.getResource();
                getresource.set(key,value);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } finally {
                if(null!=getresource){
                    getresource.close();
                }
            }
        }

        public static void del (String key){
            Jedis getresource = null;

            try {
                getresource=RedisPool.getResource();
                getresource.del(key);

            } catch (Exception e) {
                e.printStackTrace();
                throw  new RuntimeException(e.getMessage());
            } finally {

                if(null != getresource){
                    getresource.close();
                }
            }

        }


        public static String get (String key){
            Jedis getresource=null;
            String s=null;
            try {
                getresource = RedisPool.getResource();
                s= getresource.get(key);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } finally {
                if(null != getresource){
                    getresource.close();
                }
            }
            return s;
        }

    public  static  void setEx(String key,int secounds,String value) {

        Jedis getresource=null;
        try {
            getresource = RedisPool.getResource();
            getresource.setex(key,secounds,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(null != getresource){
                getresource.close();
            }
        }
    }

}
