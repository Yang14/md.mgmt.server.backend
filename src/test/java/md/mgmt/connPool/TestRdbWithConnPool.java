package md.mgmt.connPool;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.md.MdAttr;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mr-yang on 16-1-21.
 */
public class TestRdbWithConnPool implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(TestRdbWithConnPool.class);

    private static int idGenerator = 0;
    private final ConnectionPool pool;
    private final String threadName;

    public TestRdbWithConnPool(ConnectionPool pool) {
        this.pool = pool;
        threadName = "pool-client-" + (++idGenerator);
        Thread.currentThread().setName(threadName);
    }

    public void putObj(String key, Object obj) {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            conn.getDb().put(key.getBytes(), JSON.toJSONString(obj).getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }
    }

    public MdAttr getMdAttr(String key) {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            byte[] attrBytes = conn.getDb().get(key.getBytes());
            if (attrBytes != null) {
                return JSON.parseObject(new String(attrBytes), MdAttr.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        ConnectionPoolImpl poolImpl = new ConnectionPoolImpl();
        poolImpl.setMinSize(5);
        poolImpl.setMaxSize(100);
        poolImpl.setDbPath("/data/test/conn");
        poolImpl.setDebug(true);

        TestRdbWithConnPool testConn = new TestRdbWithConnPool(poolImpl);
        for (int i = 0; i < 100; i++) {
            testConn.putObj(i + "", testConn.getMdAttr());
        }
        for (int i = 0; i < 100; i++) {
            logger.info(testConn.getMdAttr(i + "").toString());
        }
    }

    public MdAttr getMdAttr() {
        MdAttr mdAttr = new MdAttr();
        mdAttr.setName("backend-fileCode");
        mdAttr.setSize(7878);
        mdAttr.setName("backend.t");
        mdAttr.setAcl((short) 777);
        return mdAttr;
    }

    @Override
    public void run() {

    }
}
