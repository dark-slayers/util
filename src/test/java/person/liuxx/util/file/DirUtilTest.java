package person.liuxx.util.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 刘湘湘
 * @version 1.4.2<br>
 *          创建时间：2018年12月19日 下午12:01:08
 * @since 1.4.2
 */
public class DirUtilTest
{
    private final Path path1 = Paths.get("E:/dshell/test_dir_1");
    private final Path path2 = Paths.get("E:/dshell/test_dir_2");

    /**
     * @author 刘湘湘
     * @version 1.1.0<br>
     *          创建时间：2018年12月19日 下午12:01:08
     * @since 1.1.0
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        Files.createDirectories(path1);
    }

    /**
     * @author 刘湘湘
     * @version 1.1.0<br>
     *          创建时间：2018年12月19日 下午12:01:08
     * @since 1.1.0
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * {@link person.liuxx.util.file.DirUtil#createDirIfNotExists(java.nio.file.Path)}
     * 的测试方法。
     */
    @Test
    public void testCreateDirIfNotExists()
    {
        final int max = 300;
        ExecutorService pool = Executors.newFixedThreadPool(max);
        ExecutorCompletionService<String> service = new ExecutorCompletionService<String>(pool);
        for (int i = 0; i < max; i++)
        {
            service.submit(() ->
            {
                DirUtil.createDirIfNotExists(path1);
                return "A";
            });
            service.submit(() ->
            {
                DirUtil.createDirIfNotExists(path2);
                return "B";
            });
        }
        List<String> list = new ArrayList<>();
        try
        {
            for (int i = 0; i < max; i++)
            {
                Future<String> f = service.take();
                String s = f.get();
                list.add(s);
            }
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        } finally
        {
            pool.shutdown();
        }
        System.out.println("生成对象列表长度：" + list.size());
    }
}