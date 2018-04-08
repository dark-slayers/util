package person.liuxx.util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import net.jcip.annotations.GuardedBy;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午6:08:58
 * @since 1.0.0
 */
public final class FileUtil
{
    /**
     * 创建文件或者文件夹时使用的锁对象
     */
    private static final Object CREAT_LOCK = new Object();

    private FileUtil()
    {
        throw new AssertionError("工具类禁止实例化！");
    }

    /**
     * 判断指定路径是否存在一个文件，如果指定路径不存在或者指定路径为一个文件夹，返回false
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午7:59:28
     * @since 1.0.0
     * @param path
     *            需要被判断的路径
     * @return 指定路径是否存在一个文件
     */
    public static boolean existsFile(Path path)
    {
        return Objects.nonNull(path) && Files.exists(path) && !Files.isDirectory(path);
    }

    /**
     * 判断指定路径是否存在一个文件夹，如果指定路径不存在或者指定路径为一个文件，返回false
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月8日 下午2:32:46
     * @since 1.0.0
     * @param path
     *            需要被判断的路径
     * @return 指定路径是否存在一个文件夹
     */
    public static boolean existsDir(Path path)
    {
        return Objects.nonNull(path) && Files.exists(path) && Files.isDirectory(path);
    }

    /**
     * 使用指定路径创建一个空文件，如果文件已经存在，会先删除该文件，然后创建一个空文件
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午9:37:32
     * @since 1.0.0
     * @param path
     *            目标路径
     * @throws IOException
     *             目标根路径无法抵达，或者目标路径不是一个文件，或者发生其他IO错误
     */
    @GuardedBy("CREAT_LOCK")
    public static void createEmptyFile(Path path) throws IOException
    {
        synchronized (CREAT_LOCK)
        {
            if (!Files.exists(path.getParent()))
            {
                Files.createDirectories(path);
            }
            if (Files.exists(path))
            {
                Files.delete(path);
            }
            Files.createFile(path);
        }
    }

    /**
     * 如果指定路径不存在，使用指定路径创建一个空文件
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午5:26:16
     * @since 1.0.0
     * @param fileTextItemList
     * @throws IOException
     */
    @GuardedBy("CREAT_LOCK")
    public static void createEmptyFileIfNotExists(Path path) throws IOException
    {
        synchronized (CREAT_LOCK)
        {
            if (!Files.exists(path))
            {
                if (!Files.exists(path.getParent()))
                {
                    Files.createDirectories(path);
                }
                Files.createFile(path);
            }
        }
    }

    /**
     * 获取存在的文件的文件名，如果指定路径不存在，或者路径是一个文件夹，返回Optional.empty()
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午3:09:55
     * @since 1.0.0
     * @param path
     * @return
     */
    public static Optional<FileName> getFileName(Path path)
    {
        if (!existsFile(path))
        {
            return Optional.empty();
        }
        String fileName = path.getFileName().toString();
        if (!fileName.contains(".") || fileName.endsWith("."))
        {
            return Optional.of(new FileName(fileName, ""));
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (Objects.equals(0, dotIndex))
        {
            return Optional.of(new FileName("", fileName));
        }
        return Optional.of(new FileName(fileName.substring(0, dotIndex), fileName.substring(dotIndex
                + 1)));
    }
}
