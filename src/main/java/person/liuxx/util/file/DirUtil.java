package person.liuxx.util.file;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 上午10:41:09
 * @since 1.0.0
 */
public final class DirUtil
{
    /**
     * 创建文件或者文件夹时使用的锁对象
     */
    private static final CopyOnWriteArrayList<String> CREATE_LOCK_LIST = new CopyOnWriteArrayList<>();

    private DirUtil()
    {
        throw new AssertionError("工具类禁止实例化！");
    }

    /**
     * 判断指定路径是否存在一个文件夹，如果指定路径不存在或者指定路径为一个文件，返回false
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午10:32:23
     * @since 1.0.0
     * @param path
     *            需要被判断的路径
     * @return 指定路径是否存在一个文件夹
     */
    public static boolean exists(Path path)
    {
        return Objects.nonNull(path) && Files.exists(path) && Files.isDirectory(path);
    }

    public static boolean sameOrSub(Path parent, Path sub) throws IOException
    {
        if (Objects.isNull(parent) || Objects.isNull(sub) || (!exists(parent)) || (!Objects.equals(
                parent.getRoot(), sub.getRoot())))
        {
            return false;
        }
        if (Objects.equals(parent, sub))
        {
            return true;
        }
        Path relative = parent.relativize(sub);
        if (Objects.equals(parent.resolve(relative), sub))
        {
            return true;
        }
        return false;
    }

    public static boolean isSub(Path parent, Path sub) throws IOException
    {
        return Objects.isNull(sub) ? false : sameOrSub(parent, sub.getParent());
    }

    public static void deleteIfExists(Path dir) throws IOException
    {
        try
        {
            Files.deleteIfExists(dir);
        } catch (DirectoryNotEmptyException e)
        {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException
                {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                        throws IOException
                {
                    Files.delete(dir);
                    return super.postVisitDirectory(dir, exc);
                }
            });
        }
    }

    /**
     * 移动源文件夹的内容至目标文件夹,移动完成后删除源文件夹<br>
     * <code>move(Paths.get("D:/a"),Paths.get("D:/b"))</code><br>
     * 操作会将a文件夹里面的内容移动到b文件夹中，同时删除a文件夹
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午10:51:46
     * @since 1.0.0
     * @param source
     *            源文件夹
     * @param target
     *            目标文件夹
     * @throws IOException
     */
    public static void move(Path source, Path target) throws IOException
    {
        operateDir(true, source, target);
    }

    /**
     * 复制源文件夹的内容至目标文件夹<br>
     * <code>copy(Paths.get("D:/a"),Paths.get("D:/b"))</code><br>
     * 操作会将a文件夹里面的内容复制到b文件夹中
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午11:01:02
     * @since 1.0.0
     * @param source
     *            源文件夹
     * @param target
     *            目标文件夹
     * @throws IOException
     */
    public static void copy(Path source, Path target) throws IOException
    {
        operateDir(false, source, target);
    }

    public static void operateDir(boolean move, Path source, Path target, CopyOption... options)
            throws IOException
    {
        if (null == source || !Files.isDirectory(source))
            throw new IllegalArgumentException("source must be directory");
        // Path dest = target.resolve(source.getFileName());
        Path dest = target;
        // 如果相同则返回
        if (Files.exists(dest) && Files.isSameFile(source, dest))
            return;
        // 目标文件夹不能是源文件夹的子文件夹
        if (isSub(source, dest))
            throw new IllegalArgumentException("dest must not  be sub directory of source");
        boolean clear = true;
        for (CopyOption option : options)
            if (StandardCopyOption.REPLACE_EXISTING == option)
            {
                clear = false;
                break;
            }
        // 如果指定了REPLACE_EXISTING选项则不清除目标文件夹
        if (clear)
            deleteIfExists(dest);
        Files.walkFileTree(source, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException
            {
                // 在目标文件夹中创建dir对应的子文件夹
                Path subDir = 0 == dir.compareTo(source) ? dest
                        : dest.resolve(dir.subpath(source.getNameCount(), dir.getNameCount()));
                Files.createDirectories(subDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException
            {
                if (move)
                {
                    Files.move(file, dest.resolve(file.subpath(source.getNameCount(), file
                            .getNameCount())), options);
                } else
                {
                    Files.copy(file, dest.resolve(file.subpath(source.getNameCount(), file
                            .getNameCount())), options);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
            {
                // 移动操作时删除源文件夹
                if (move)
                    Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    /**
     * 如果指定的路径不存在文件夹，创建该文件夹
     * 
     * @author 刘湘湘
     * @version 1.1.0<br>
     *          创建时间：2018年12月19日 上午9:29:55
     * @since 1.1.0
     * @param path
     * @throws IOException
     */
    public static void createDirIfNotExists(Path path) throws IOException
    {
        Objects.requireNonNull(path);
        final String lock = path.toString();
        try
        {
            for (;;)
            {
                if (CREATE_LOCK_LIST.addIfAbsent(lock))
                {
                    if (exists(path))
                    {
                        break;
                    } else
                    {
                        Files.createDirectories(path);
                    }
                }
            }
        } finally
        {
            CREATE_LOCK_LIST.remove(lock);
        }
    }
}
