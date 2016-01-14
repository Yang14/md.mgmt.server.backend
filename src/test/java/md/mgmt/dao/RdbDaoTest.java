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
    public void testSetOrCreateHashBucket() {
        long distrCode = 1000;
        String fileCode = "abcded1";
        if (!rdbDao.setOrCreateHashBucket(distrCode, fileCode)) {
            logger.error(String.format("set hash bucket err"));
        }
        logger.info(String.format("set hash bucket ok"));
    }

    @Test
    public void testGetFileMdAttrList() {
        long distrCode = 1000;
        List<MdAttr> mdAttrs = rdbDao.getDirMdAttrList(distrCode);
        if (mdAttrs == null) {
            logger.error("get File MdAttrs null");
        } else {
            logger.info(mdAttrs.toString());
        }
    }
}
