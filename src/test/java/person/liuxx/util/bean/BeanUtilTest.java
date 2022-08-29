package person.liuxx.util.bean;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 刘湘湘
 * @version 1.1.2<br>
 *          创建时间：2018年9月14日 下午12:26:30
 * @since 1.1.2
 */
public class BeanUtilTest
{
    private TestObject obj1;
    private TestObject2 obj2;

    /**
     * @author 刘湘湘
     * @version 1.1.2<br>
     *          创建时间：2018年9月14日 下午12:26:30
     * @since 1.1.2
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        obj1 = new TestObject();
        obj1.setT1(LocalDateTime.of(2018, 7, 12, 13, 30));
        obj1.setD1(LocalDate.of(2018, 7, 12));
        obj1.setD2(LocalDate.of(2018, 10, 12));
        obj1.setS1("2018-07-12");
        obj2 = new TestObject2();
    }

    /**
     * @author 刘湘湘
     * @version 1.1.2<br>
     *          创建时间：2018年9月14日 下午12:26:30
     * @since 1.1.2
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * {@link person.liuxx.util.bean.BeanUtil#copyProperties(java.lang.Object, java.lang.Object)}
     * 的测试方法。
     */
    @Test
    public void testCopyProperties()
    {
        System.out.println(obj1);
        System.out.println(obj2);
        BeanUtil.copyProperties(obj2, obj1);
        System.out.println(obj1);
        System.out.println(obj2);
        assertTrue(Objects.equals(obj2.getS1(), LocalDate.of(2018, 7, 12)));
    }

    /**
     * {@link person.liuxx.util.bean.BeanUtil#copyAppointProperties(java.lang.Object, java.lang.Object, java.lang.String[])}
     * 的测试方法。
     */
    @Test
    public void testCopyAppointProperties()
    {
        BeanUtil.copyProperties(obj2, obj1);
        assertTrue(Objects.equals(obj2.getS1(), LocalDate.of(2018, 7, 12)));
    }

    /**
     * {@link person.liuxx.util.bean.BeanUtil#copyIgnoreProperties(java.lang.Object, java.lang.Object, java.lang.String[])}
     * 的测试方法。
     */
    @Test
    public void testCopyIgnoreProperties()
    {
        BeanUtil.copyProperties(obj2, obj1);
        assertTrue(Objects.equals(obj2.getS1(), LocalDate.of(2018, 7, 12)));
    }
}
