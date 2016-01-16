package md.mgmt.facade;

import md.mgmt.facade.mapper.CommandMapper;
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
public class TestCommand {
    private static Logger logger = LoggerFactory.getLogger(TestCommand.class);

    @Autowired
    private CommandMapper commandMapper;

    @Test
    public void testGetFileMdAttr() {
        String result = commandMapper.selectService("{\"opsContent\":\"f8a8509dc4c9496091ed3d53a55f5ba2\",\"opsType\":3}");
        logger.info(result);
    }
}
