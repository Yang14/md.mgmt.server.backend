package md.mgmt.connPool;

import org.rocksdb.RocksDB;

/**
 * Created by Mr-yang on 16-1-21.
 */
public class Connection {

    /**rocksdb连接对象*/
    private RocksDB db;

    /**连接池*/
    private final ConnectionPool pool;

    public Connection(ConnectionPool pool,RocksDB db) {
        this.pool = pool;
        this.db = db;
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

