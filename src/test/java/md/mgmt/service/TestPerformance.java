package md.mgmt.service;

import md.mgmt.base.md.MdAttr;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Mr-yang on 16-1-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestPerformance {
    @Autowired
    private PutMdAttrService putMdAttrService;

    @Autowired
    private GetMdAttrService getMdAttrService;

    private MdAttr mdAttr = new MdAttr();

    @Before
    public void setUp() {
        mdAttr.setSize(7878);
        mdAttr.setName("backend.t");
        mdAttr.setAcl((short) 777);
        mdAttr.setCreateTime();
    }
    @Test
    public void test1dc21fc(){
        int count = 10000;
        System.out.println("\n\n\n" + String.valueOf(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {

        }
        long end = System.currentTimeMillis();
        System.out.println(String.valueOf(System.currentTimeMillis()));
        System.out.println(
                String.format("\nCreate %s dir use Total time: %s ms\navg time: %sms\n\n\n",
                        count, (end - start), (end - start) / (count * 1.0)));
    }
}
