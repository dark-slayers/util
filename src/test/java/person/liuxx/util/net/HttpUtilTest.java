package person.liuxx.util.net;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 刘湘湘
 * @since 2019年4月8日 下午5:44:21
 */
public class HttpUtilTest
{
    /**
     * @author 刘湘湘
     * @since 2019年4月8日 下午5:44:21
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * @author 刘湘湘
     * @since 2019年4月8日 下午5:44:21
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * {@link person.liuxx.util.net.HttpUtil#simpleGet(java.lang.String)} 的测试方法。
     */
    @Test
    public void testSimpleGet()
    {
        Optional<String> getOptional = HttpUtil.simpleGet("https://www.baidu.com");
        assertTrue(getOptional.isPresent());
    }
}
