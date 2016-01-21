package md.mgmt.connPool;

/**
 * Created by Mr-yang on 16-1-21.
 */
import java.util.Random;

public class ConnectionPoolTest {

    /**
     * 模拟客户端线程不断获取，释放连接的情况
     */
    private static class PoolClient implements Runnable {

        private static int idGenerator = 0;
        private final ConnectionPoolImpl pool;
        private final String threadName;
        private Random random = new Random();

        public PoolClient(ConnectionPoolImpl pool) {
            this.pool = pool;
            threadName = "pool-client-" + (++idGenerator);
            Thread.currentThread().setName(threadName);
        }

        public void run() {

            for (int i = 0; i < 100; i++) {
                Connection conn = pool.getConnection();
                System.out.println("Thread " + threadName
                        + " aquired a connection.");
//                int sleepTime = (3 + random.nextInt(20)) * 1000;
                int sleepTime = 1;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ignore) {
                }
                conn.close();
                System.out.println("Thread " + threadName
                        + " release a connection.");
            }
        }
    }

    public static void main(String[] args) throws Exception {

        ConnectionPoolImpl pool = new ConnectionPoolImpl();
        pool.setMaxSize(100);
        pool.setMinSize(5);
        pool.setDebug(false);
        long start = System.currentTimeMillis();
        //测试连接池，模拟1000个线程不断获取释放连接的情况
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new PoolClient(pool));
            thread.start();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
