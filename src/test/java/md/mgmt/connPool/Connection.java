package md.mgmt.connPool;

import org.rocksdb.RocksDB;

/**
 * Created by Mr-yang on 16-1-21.
 */
public class Connection extends RocksDB{

    /**
     * 连接池
     */
    private ConnectionPool pool;

    public void setPool(ConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * 关闭连接，将连接返回到连接池中
     */
    public void close() {
        pool.releaseConnection(this);
    }


}

