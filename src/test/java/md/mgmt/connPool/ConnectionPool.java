package md.mgmt.connPool;

import org.rocksdb.RocksDBException;

/**
 * Created by Mr-yang on 16-1-21.
 */
public interface ConnectionPool {

    /**
     * 获取连接
     */
    public Connection getConnection() throws RocksDBException;

    /**
     * 释放连接
     */
    public void releaseConnection(Connection conn);

}
