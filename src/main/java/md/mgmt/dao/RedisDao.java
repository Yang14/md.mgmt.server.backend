package md.mgmt.dao;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-20.
 */
public interface RedisDao {

    public void setOrCreateHashBucket(String key, String fileCode);

    public List<String> getHashBucket(String key);

}
