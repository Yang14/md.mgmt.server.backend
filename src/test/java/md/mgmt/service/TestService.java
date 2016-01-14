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
public class TestService {
    private static Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private PutMdAttrService putMdAttrService;

    @Autowired
    private GetMdAttrService getMdAttrService;

    private MdAttr mdAttr = new MdAttr();
    private long distrCode = 99999;
    private String fileCode = "backend-fileCode2";

    @Before
    public void setUp() {
        mdAttr.setSize(7878);
        mdAttr.setName("backend.t");
        mdAttr.setAcl((short) 777);
    }

    @Test
    public void testPutMdAttr() {
        PutMdAttrDto putMdAttrDto = new PutMdAttrDto();
        ExactCode exactCode = new ExactCode(distrCode, fileCode);
        putMdAttrDto.setExactCode(exactCode);
        putMdAttrDto.setMdAttr(mdAttr);
        putMdAttrService.putMdAttr(putMdAttrDto);
    }

    @Test
    public void testGetFileMdAttr() {
        logger.info(getMdAttrService.getFileMdAttr(fileCode).toString());
    }

    @Test
    public void testGetDirMdAttrList() {
        logger.info(getMdAttrService.getDirMdAttrList(distrCode+"").toString());
    }
}
