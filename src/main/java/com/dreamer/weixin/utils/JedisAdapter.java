package com.dreamer.weixin.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * jedis
 */
@Slf4j
@Service
public class JedisAdapter implements InitializingBean {

    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/11");
    }

    public long sadd(String key,String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            log.error("发生异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public int setEx(String key,String value,int timeout){
        if(StringUtils.isNoneBlank(key,value)){
            Jedis jedis = null;
            try{
                jedis = pool.getResource();
                if(jedis.setex(key,timeout,value).equalsIgnoreCase("ok")){
                    return 0;
                }else {
                    return 3;
                }
            }catch (Exception e){
                log.error("存储发生异常" + e.getMessage());
            }finally {
                if (jedis != null){
                    jedis.close();
                }
            }
        }else {
            return 1;
        }
        return 2;
    }

    public String getFromRedis(String key){
        if(StringUtils.isNoneBlank(key)){
            Jedis jedis = null;
            try{
                jedis = pool.getResource();
                return jedis.get(key);
            }catch (Exception e){
                log.error(e.getMessage());
            }finally {
                if (jedis != null){
                    jedis.close();
                }
            }
        }
        return null;
    }

    public long srem(String key,String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            log.info("发生异常" + e.getMessage());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }





}
