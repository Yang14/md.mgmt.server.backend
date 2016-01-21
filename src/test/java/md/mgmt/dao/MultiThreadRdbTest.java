package md.mgmt.dao;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.md.MdAttr;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mr-yang on 16-1-21.
 */
public class MultiThreadRdbTest implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MultiThreadRdbTest.class);

    private static String path = "/data/test/conn";

    static {
        RocksDB.loadLibrary();
    }

    public void putObj(String key, Object obj) {
        Options options = new Options().setCreateIfMissing(true);
        RocksDB db = null;
        try {
            db = RocksDB.open(options, path);
            db.put(key.getBytes(), JSON.toJSONString(obj).getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        } finally {
            if (db == null) db.close();
            options.dispose();
        }
    }

    public MdAttr getMdAttr(String key) {
        Options options = new Options().setCreateIfMissing(true);
        RocksDB db = null;
        try {
            db = RocksDB.open(options, path);
            byte[] attrBytes = db.get(key.getBytes());
            if (attrBytes != null) {
                return JSON.parseObject(new String(attrBytes), MdAttr.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db == null) db.close();
            options.dispose();
        }
        return null;
    }

    @Override
    public void run() {

    }
}
