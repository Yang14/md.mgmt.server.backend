package md.mgmt.connPool;

/**
 * Created by Mr-yang on 16-1-21.
 */

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPoolImpl implements ConnectionPool {
    private static Logger logger = LoggerFactory.getLogger(ConnectionPoolImpl.class);

    /**
     * 空闲连接
     */
    private List<Connection> freeList = new ArrayList<Connection>();
    /**
     * 等待线程队列，先进去出
     */
    private final LinkedList<Object> waitQueue = new LinkedList<Object>();
    /**
     * 最小连接数
     */
    private int minSize = 0;
    /**
     * 最大连接数
     */
    private int maxSize = 32;
    /**
     * 连接池中的总连接数
     */
    private int totalSize = 0;
    /**
     * 连接池调整大小
     */
    private int step = 1;
    /**
     * 连接池是否已经初始化
     */
    private boolean initialized = false;
    /**
     * 调试模式
     */
    private boolean debug = false;

    private String dbPath;
    private static Options options = new Options().setCreateIfMissing(true);

    static {
        RocksDB.loadLibrary();
    }

    /**
     * 初始化池
     */
    private synchronized void initPool() throws RocksDBException {
        if (initialized) {
            return;
        }
        initialized = true;
        if (debug) debugPrint("Connection pool initialized!");
        for (int i = 0; i < minSize; i++) {
            Connection conn = new Connection(this, RocksDB.open(options, dbPath));
            freeList.add(conn);
            ++totalSize;
            if (debug) {
                debugPrint("Increase a connection, " +
                        "total connections =" + totalSize
                        + ", free connections " + freeList.size());
            }
        }
    }

    /**
     * 获取连接，如果当前没有连接可用，则加入等待队列
     */
    public Connection getConnection() throws RocksDBException {
        Connection result = null;
        while (true) {//直到获取到一条连接为止
            result = internalGetConnection();
            if (result != null) {
                if (debug) {
                    debugPrint("Thread " + Thread.currentThread().getName()
                            + " aquired a connection, " +
                            "total connections =" + totalSize
                            + ", free connections " + freeList.size());
                }
                break;
            } else {
                Object monitor = new Object();
                if (debug) {
                    debugPrint("Thread " + Thread.currentThread().getName()
                            + " wait for a connection.");
                }

                //没有获取到连接，将当前线程加入等待队列
                synchronized (monitor) {
                    synchronized (waitQueue) {
                        waitQueue.add(monitor);
                    }
                    try {
                        monitor.wait();
                    } catch (InterruptedException ignore) {
                    }
                    //唤醒后会继续回到while循环，尝试获取连接
                }

                if (debug) {
                    debugPrint("Thread " + Thread.currentThread().getName()
                            + " wakeup.");
                }
            }
        }
        return result;
    }

    /**
     * 获取连接，如果没有连接，则尝试增加连接池
     */
    private synchronized Connection internalGetConnection() throws RocksDBException {
        if (!initialized) {
            initPool();
        }
        Connection result = null;
        if (!freeList.isEmpty()) {
            result = freeList.remove(0);
        } else {
            if (totalSize < maxSize) {
                if (debug) {
                    debugPrint("Current pool is empty, " +
                            "try to increase connection pool.");
                }
                //当前创建的连接总数小于最大连接数，增加连接池
                result = increasePool();
            }
        }
        return result;
    }

    /**
     * 增加连接池，同时将最后创建的连接返回给当前线程
     */
    private Connection increasePool() throws RocksDBException {
        int localStep = step;
        if (totalSize + step > maxSize) {
            localStep = maxSize - totalSize;
        }
        Connection result = null;
        int lastIndex = localStep - 1;
        for (int i = 0; i < localStep; i++) {
            Connection conn = new Connection(this, RocksDB.open(options, dbPath));
            ++totalSize;
            if (i == lastIndex) {
                result = conn;//最后创建的连接返回给当前线程使用
                if (debug) {
                    debugPrint("Increate a connection, " +
                            "total connections =" + totalSize
                            + ", free connections " + freeList.size());
                }
            } else {
                freeList.add(conn);
                if (debug) {
                    debugPrint("Increate a connection, "
                            + "total connections =" + totalSize
                            + ", free connections " + freeList.size());
                }
                //增加连接后唤醒等待线程
                notifyWaitingThreads();
            }
        }
        return result;
    }

    /**
     * 唤醒等待的线程
     */
    private void notifyWaitingThreads() {
        Object waitMonitor = null;
        synchronized (waitQueue) {
            if (waitQueue.size() > 0) {
                waitMonitor = waitQueue.removeFirst();
            }
        }
        if (waitMonitor != null) {
            synchronized (waitMonitor) {
                waitMonitor.notify();
            }
        }
    }

    /**
     * 释放连接，同时唤醒等待的线程
     */
    public synchronized void releaseConnection(Connection conn) {
        freeList.add(conn);
        if (debug) {
            debugPrint("Release a connection, "
                    + "total connections =" + totalSize
                    + ", free connections " + freeList.size());
        }
        notifyWaitingThreads();
    }

    private void debugPrint(String debugStr) {
        System.out.println(debugStr);
    }

    //省略其他getter, setter ...

    public List<Connection> getFreeList() {
        return freeList;
    }

    public void setFreeList(List<Connection> freeList) {
        this.freeList = freeList;
    }

    public LinkedList<Object> getWaitQueue() {
        return waitQueue;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        dbPath = dbPath;
    }
}


