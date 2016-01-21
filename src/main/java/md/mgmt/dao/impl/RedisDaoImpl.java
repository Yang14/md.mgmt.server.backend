package md.mgmt.dao.impl;

import md.mgmt.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-20.
 */
@Component
public class RedisDaoImpl implements RedisDao {
    private static final Logger logger = LoggerFactory.getLogger(RedisDaoImpl.class);

    @Autowired
    private Jedis jedis;


    @Override
    public void setOrCreateHashBucket(String key, String fileCode) {
        jedis.rpush(key, fileCode);
    }

    @Override
    public List<String> getHashBucket(String key) {
        long len = jedis.llen(key);
        return jedis.lrange(key, 0, len);
    }
}
