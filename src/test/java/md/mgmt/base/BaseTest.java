package md.mgmt.base;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Mr-yang on 16-1-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public abstract class BaseTest {
    private Logger logger;
    private String methodDesc;

    private static int[] cycles = new int[]{10, 5, 3};
    private static int[] counts = new int[]{10000, 10000 * 10, 10000 * 100};
    private static int hotCount = 10000;
    private static int cycleLen = 2;

    private String beginTestDesc = "------------------start--------------------";
    private String calResultDesc = "min %s, max %s, total avg time: %s";
    private String endTestDesc = "--------------------end---------------------\n";

    private long min = Integer.MAX_VALUE;
    private long max = Integer.MIN_VALUE;
    private long totalTime = 0;

    public BaseTest(Logger logger, String methodDesc) {
        this.logger = logger;
        this.methodDesc = methodDesc;
    }

    private void initTime() {
        totalTime = 0;
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
    }

    private void caulTime(long time) {
        if (time > max) {
            max = time;
        }
        if (time <= min) {
            min = time;
        }
        totalTime += time;
    }

    public void putMethodPerformance() {
        initTime();
        logger.info(beginTestDesc);
        logger.info(methodDesc);
        for (int i = 0; i < cycleLen; ++i) {
            for (int j = 0; j < cycles[i]; ++j) {
                logger.info(String.format("round %s:", i + 1));
                long execTime = execPut(hotCount, counts[i]);
                caulTime(execTime);
            }
            logger.info(String.format(calResultDesc,
                    min, max, totalTime / (counts[i] * cycles[i] * 1.0)));
        }
        logger.info(endTestDesc);
    }

    public void getMethodPerformance() {
        initTime();
        logger.info(beginTestDesc);
        logger.info(methodDesc);
        for (int i = 0; i < cycleLen; ++i) {
            for (int j = 0; j < cycles[i]; ++j) {
                logger.info(String.format("round %s:", i + 1));
                long execTime = execGet(hotCount, counts[i]);
                caulTime(execTime);
            }
            logger.info(String.format(calResultDesc,
                    min, max, totalTime / (counts[i] * cycles[i] * 1.0)));
        }
        logger.info(endTestDesc);
    }

    public abstract long execPut(int hotCount, int count);

    public abstract long execGet(int hotCount, int count);
}
