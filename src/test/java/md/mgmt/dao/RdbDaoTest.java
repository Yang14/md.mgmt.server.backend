package md.mgmt.dao;

import md.mgmt.base.md.MdAttr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class RdbDaoTest {
    private static Logger logger = LoggerFactory.getLogger(RdbDaoTest.class);

    @Autowired
    private RdbDao rdbDao;

    @Test
    public void testSingleThreadPut(){
        long start = System.currentTimeMillis();
        for (long i=1;i<1000000;i++){
            rdbDao.putMdAttr(i+"", getMdAttr());
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("total spend:%s, avg %s", (end - start), ((end - start) / 10000*1.0)));
    }

    @Test
    public void testSingleThreadGet(){
        long start = System.currentTimeMillis();
        for (long i=1;i<1000000;i++){
            rdbDao.getFileMdAttr(i+"");
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("total spend:%s, avg %s", (end - start), ((end - start) / 10000*1.0)));
    }

    public MdAttr getMdAttr() {
        MdAttr mdAttr = new MdAttr();
        mdAttr.setName("backend-fileCode");
        mdAttr.setSize(7878);
        mdAttr.setName("backend.t");
        mdAttr.setAcl((short) 777);
        mdAttr.setCreateTime(System.currentTimeMillis());
        return mdAttr;
    }

    @Test
    public void testSetOrCreateHashBucket() {
        String distrCode = 1000 + "";
        String fileCode = "abcded1";
        if (!rdbDao.setOrCreateHashBucket(distrCode, fileCode)) {
            logger.error(String.format("set hash bucket err"));
        }
        logger.info(String.format("set hash bucket ok"));
    }

    @Test
    public void testGetFileMdAttrList() {
        String distrCode = 1000 + "";
        List<MdAttr> mdAttrs = rdbDao.getDirMdAttrList(null);
        if (mdAttrs == null) {
            logger.error("get File MdAttrs null");
        } else {
            logger.info(mdAttrs.toString());
        }
    }
}
