package person.liuxx.util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private static final CopyOnWriteArrayList<String> CREATE_LOCK_LIST = new CopyOnWriteArrayList<>();

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
     * 使用指定路径创建一个空文件，如果文件已经存在，会先删除该文件，然后创建一个空文件
     * <p>
     * 此方法与{@link #createEmptyFileIfNotExists}共用同步条件。
     * <p>
     * 如果多个线程同时执行此方法或其他具有相同同步条件的方法，如果方法参数的路径不同，线程之间互不影响<br>
     * 如果参数路径相同，最先将参数放入锁定列表的线程执行，其他线程不执行操作
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午9:37:32
     * @since 1.0.0
     * @param path
     *            需要创建的文件的完整路径，包含文件名
     * @throws IOException
     *             目标根路径无法抵达，或者目标路径不是一个文件，或者发生其他IO错误
     */
    public static void createEmptyFile(Path path) throws IOException
    {
        Objects.requireNonNull(path);
        Objects.requireNonNull(path.getParent());
        final String lock = path.toString();
        try
        {
            if (CREATE_LOCK_LIST.addIfAbsent(lock))
            {
                DirUtil.createDirIfNotExists(path.getParent());
                if (Files.exists(path))
                {
                    Files.delete(path);
                }
                Files.createFile(path);
            }
        } finally
        {
            CREATE_LOCK_LIST.remove(lock);
        }
    }

    /**
     * 如果指定路径不存在，使用指定路径创建一个空文件
     * <p>
     * 此方法与{@link #createEmptyFile}共用同步条件。
     * <p>
     * 如果多个线程同时执行此方法或其他具有相同同步条件的方法，如果方法参数的路径不同，线程之间互不影响<br>
     * 如果参数路径相同，最先将参数放入锁定列表的线程执行，其他线程不执行操作
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午5:26:16
     * @since 1.0.0
     * @param path
     *            需要创建的文件的完整路径，包含文件名
     * @throws IOException
     */
    public static void createEmptyFileIfNotExists(Path path) throws IOException
    {
        Objects.requireNonNull(path);
        Objects.requireNonNull(path.getParent());
        if (existsFile(path))
        {
            return;
        }
        final String lock = path.toString();
        try
        {
            if (CREATE_LOCK_LIST.addIfAbsent(lock))
            {
                if (!existsFile(path))
                {
                    DirUtil.createDirIfNotExists(path.getParent());
                    Files.createFile(path);
                }
            }
        } finally
        {
            CREATE_LOCK_LIST.remove(lock);
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
