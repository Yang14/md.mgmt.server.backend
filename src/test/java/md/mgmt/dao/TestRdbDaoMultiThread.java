package md.mgmt.dao;

import md.mgmt.base.md.MdAttr;
import md.mgmt.dao.impl.RdbDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by yang on 16-1-21.
 */
public class TestRdbDaoMultiThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(TestRdbDaoMultiThread.class);

    private RdbDao rdbDao = new RdbDaoImpl();
    private static int idGenerator = 0;


    private static CountDownLatch latch = new CountDownLatch(10);

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(new TestRdbDaoMultiThread()).start();
        }
        latch.await();
        long end = System.currentTimeMillis();
        logger.info(String.format("total spend:%s, avg %s", (end - start), ((end - start) / 10000*1.0)));
    }

    public static MdAttr getMdAttr() {
        MdAttr mdAttr = new MdAttr();
        mdAttr.setName("backend-fileCode");
        mdAttr.setSize(7878);
        mdAttr.setName("backend.t");
        mdAttr.setAcl((short) 777);
        mdAttr.setCreateTime(System.currentTimeMillis());
        return mdAttr;
    }

    @Override
    public void run() {
        /*for (int i = 0; i < 100000; i++) {
            rdbDao.putMdAttr(Thread.currentThread().getName() + idGenerator++, getMdAttr());
//            logger.info(i + Thread.currentThread().getName() + "-----" + rdbDao.getFileMdAttr(Thread.currentThread().getName()).toString());
        }*/
        for (int i = 0; i < 100000; i++) {
            rdbDao.getFileMdAttr(Thread.currentThread().getName()+i);
        }
        latch.countDown();
    }
}
