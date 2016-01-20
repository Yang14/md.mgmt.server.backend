package md.mgmt.performance.service;

import md.mgmt.base.BaseTest;
import md.mgmt.base.md.ExactCode;
import md.mgmt.base.md.MdAttr;
import md.mgmt.facade.req.PutMdAttrDto;
import md.mgmt.service.GetMdAttrService;
import md.mgmt.service.PutMdAttrService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mr-yang on 16-1-20.
 */
public class FileServiceTest extends BaseTest {
    private static Logger logger = LoggerFactory.getLogger(FileServiceTest.class);
    private static String methodDesc = "FileServiceTest";

    @Autowired
    private PutMdAttrService putMdAttrService;
    @Autowired
    private GetMdAttrService getMdAttrService;

    public FileServiceTest() {
        super(logger, methodDesc);
    }

    private MdAttr mdAttr = new MdAttr();
    @Before
    public void setUp() {
        mdAttr.setSize(7878);
        mdAttr.setName("backend.t");
        mdAttr.setAcl((short) 777);
        mdAttr.setCreateTime(System.currentTimeMillis());
        mdAttr.setUpdateTime(System.currentTimeMillis());
    }


    @Override
    public long execPut(int hotCount, int count) {
        PutMdAttrDto putMdAttrDto = new PutMdAttrDto();
        for (int i = 1; i < hotCount; i++) {
            putMdAttrDto.setExactCode(new ExactCode((long) i, "be" + i));
            putMdAttrDto.setMdAttr(mdAttr);
            putMdAttrService.putMdAttr(putMdAttrDto);
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            putMdAttrDto.setExactCode(new ExactCode((long) i, "be" + i));
            putMdAttrDto.setMdAttr(mdAttr);
            putMdAttrService.putMdAttr(putMdAttrDto);
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }

    @Override
    public long execGet(int hotCount, int count) {
        long start = System.currentTimeMillis();
        for (int i = 1; i < count; i++) {
            getMdAttrService.getFileMdAttr("be" + i);
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("count %s  use Total time: %s ms, avg time: %sms",
                count, (end - start), (end - start) / (count * 1.0)));
        return end - start;
    }

    @Test
    public void testPutCol(){
        putMethodPerformance();
    }

    @Test
    public void testGetCol(){
        getMethodPerformance();
    }
}
