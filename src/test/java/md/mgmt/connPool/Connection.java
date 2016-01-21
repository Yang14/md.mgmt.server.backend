package md.mgmt.connPool;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

/**
 * Created by Mr-yang on 16-1-21.
 */
public class Connection {
    static {
        RocksDB.loadLibrary();
    }

    private static Options options = new Options().setCreateIfMissing(true);

    /**
     * rocksdb连接对象
     */
    private RocksDB db;

    /**
     * 连接池
     */
    private final ConnectionPool pool;

    public Connection(ConnectionPool pool, String dbPath) {
        this.pool = pool;
        try {
            this.db = RocksDB.open(options, dbPath);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接，将连接返回到连接池中
     */
    public void close() {
        pool.releaseConnection(this);
    }

    public RocksDB getDb() {
        return db;
    }

    public void setDb(RocksDB db) {
        this.db = db;
    }

    //省略其他逻辑方法 ... ...
}

