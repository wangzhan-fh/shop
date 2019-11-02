package com.fh.shop.api.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static void set(String key,String value){

        Jedis getresource = null;
        try {
            getresource = RedisPool.getresource();
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

    public static Long del (String key){
        Jedis getresource = null;
        Long del =0L;
        try {
            getresource=RedisPool.getresource();
             del = getresource.del(key);

        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        } finally {

            if(null != getresource){
                getresource.close();
            }
        }

        return del;
    }




    public static String get (String key){
        Jedis getresource=null;
        String s=null;
        try {
            getresource = RedisPool.getresource();
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
            getresource = RedisPool.getresource();
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
    public static  boolean exists(String key){
        Jedis getresource = null;
        boolean exists=false;
        try {
            getresource = RedisPool.getresource();
            exists =getresource.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return exists;
    }

    public  static  void exPire(String key,int secounds) {

        Jedis getresource=null;
        try {
            getresource = RedisPool.getresource();
            getresource.expire(key,secounds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(null != getresource){
                getresource.close();
            }
        }
    }

    public  static  void hset(String key,String filed,String value) {

        Jedis getresource=null;
        try {
            getresource = RedisPool.getresource();
            getresource.hset(key,filed,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(null != getresource){
                getresource.close();
            }
        }
    }

    public  static  String hget(String key,String filed) {
        Jedis getresource = null;
        String hgetValue = null;
        try {
            getresource = RedisPool.getresource();
            hgetValue= getresource.hget(key, filed);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (null != getresource) {
                getresource.close();
            }
        }
        return hgetValue;
    }

    public  static  void hdel(String key,String filed) {
        Jedis getresource = null;

        try {
            getresource = RedisPool.getresource();
            getresource.hdel(key, filed);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (null != getresource) {
                getresource.close();
            }
        }
    }
}
