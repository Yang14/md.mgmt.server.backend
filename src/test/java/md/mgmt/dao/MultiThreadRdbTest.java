package md.mgmt.dao;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.md.MdAttr;
import md.mgmt.connPool.ConnectionPoolImpl;
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
    private static int idGenerator = 0;
    private final String threadName;

    static {
        RocksDB.loadLibrary();
    }

    public MultiThreadRdbTest() {
        threadName = "pool-client-" + (++idGenerator);
        Thread.currentThread().setName(threadName);
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
        for (int i = 0; i < 10; i++) {
            this.putObj(Thread.currentThread().getName(), getMdAttr());
        }
        for (int i = 0; i < 10; i++) {
            logger.info(i + Thread.currentThread().getName() + "-----" +this.getMdAttr(Thread.currentThread().getName()).toString());
        }
    }

    public static void main(String[] args) throws Exception {
        ConnectionPoolImpl poolImpl = new ConnectionPoolImpl();
        poolImpl.setMinSize(1);
        poolImpl.setMaxSize(100);
        poolImpl.setDbPath("/data/test/conn");
        poolImpl.setDebug(false);

        for (int i=0;i<10;i++){
            new Thread(new MultiThreadRdbTest()).start();
        }
    }

    public static MdAttr getMdAttr() {
        MdAttr mdAttr = new MdAttr();
        mdAttr.setName("backend-fileCode");
        mdAttr.setSize(7878);
        mdAttr.setName("backend.t");
        mdAttr.setAcl((short) 777);
        return mdAttr;
    }
}
