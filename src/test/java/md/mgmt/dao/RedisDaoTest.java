package md.mgmt.dao;

import md.mgmt.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-20.
 */
public class RedisDaoTest extends BaseTest {
    private static Logger logger = LoggerFactory.getLogger(RedisDaoTest.class);
    private static String methodDesc = "RedisDaoTest";

    public RedisDaoTest() {
        super(logger, methodDesc);
    }

    @Autowired
    private RedisDao redisDao;

    @Override
    public long execPut(int hotCount, int count) {
        String key = "list-1";
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            redisDao.setOrCreateHashBucket(key, "fc-" + i);
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }

    @Override
    public long execGet(int hotCount, int count) {
        return 0;
    }

    @Test
    public void testPutList() {
        putMethodPerformance();
    }

    @Test
    public void testGetList() {
        String key = "list-1";
        List<String> list = redisDao.getHashBucket(key);
        logger.info(list.size() + "");
        for (String code : list) {
            System.out.print(code);
        }
    }
}
