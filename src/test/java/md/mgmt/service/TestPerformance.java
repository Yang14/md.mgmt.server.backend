package md.mgmt.service;

import md.mgmt.base.md.ExactCode;
import md.mgmt.base.md.MdAttr;
import md.mgmt.facade.req.PutMdAttrDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Mr-yang on 16-1-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestPerformance {
    private static Logger logger = LoggerFactory.getLogger(TestPerformance.class);

    @Autowired
    private PutMdAttrService putMdAttrService;

    @Autowired
    private GetMdAttrService getMdAttrService;

    private MdAttr mdAttr = new MdAttr();

    private int count = 10000;

    @Before
    public void setUp() {
        mdAttr.setSize(7878);
        mdAttr.setName("backend.t");
        mdAttr.setAcl((short) 777);
        mdAttr.setCreateTime(System.currentTimeMillis());
        mdAttr.setUpdateTime(System.currentTimeMillis());
    }

    @Test
    public void testPut1dc21fc() {
        logger.info("\n\n\n" + String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        PutMdAttrDto putMdAttrDto = new PutMdAttrDto();
        for (int i = 1; i < count; i++) {
            putMdAttrDto.setExactCode(new ExactCode((long) i, "be" + i));
            putMdAttrDto.setMdAttr(mdAttr);
            putMdAttrService.putMdAttr(putMdAttrDto);
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(
                String.format("\ntestPut1dc21fc %s count use Total time: %s ms\navg time: %sms\n\n\n",
                        count*100, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testPut1dc2fcs() {
        logger.info("\n\n\n" + String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        PutMdAttrDto putMdAttrDto = new PutMdAttrDto();
        for (int i = 1; i < 10 * count; i++) {
            putMdAttrDto.setExactCode(new ExactCode((long) 200002, "be" + i));
            putMdAttrDto.setMdAttr(mdAttr);
            putMdAttrService.putMdAttr(putMdAttrDto);
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(
                String.format("\ntestPut1dc2fcs %s count use Total time: %s ms\navg time: %sms\n\n\n",
                        count, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testGetFile() {
        logger.info("\n\n\n" + String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            getMdAttrService.getFileMdAttr("be" + i);
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(
                String.format("\ntestGetFile %s count use Total time: %s ms\navg time: %sms\n\n\n",
                        count, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testGetDirList() {
        logger.info("\n\n\n" + String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            getMdAttrService.getDirMdAttrList(i+"");
        }
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(
                String.format("\ntestGetDirList %s count use Total time: %s ms\navg time: %sms\n\n\n",
                        count, (end - start), (end - start) / (count * 1.0)));
    }

    @Test
    public void testGet1DirList() {
        logger.info("\n\n\n" + String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        getMdAttrService.getDirMdAttrList("200002");
        long end = System.currentTimeMillis();
        logger.info(String.valueOf(System.currentTimeMillis()));
        logger.info(
                String.format("\ntestGetDirList %s count use Total time: %s ms\navg time: %sms\n\n\n",
                        count, (end - start), (end - start) / (count * 1.0)));
    }

}
