package md.mgmt.performance;

import md.mgmt.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mr-yang on 16-1-20.
 */
public class ColumnTest extends BaseTest {
    private static Logger logger = LoggerFactory.getLogger(ColumnTest.class);
    private static String methodDesc = "GetNewDirTest";
    public ColumnTest(Logger logger, String methodDesc) {
        super(logger, methodDesc);
    }

    @Override
    public long execPut(int hotCount, int count) {
        return 0;
    }

    @Override
    public long execGet(int hotCount, int count) {
        return 0;
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
