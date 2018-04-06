package person.liuxx.util.file;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年11月1日 下午2:51:23
 * @since 1.0.0
 */
public final class FileName
{
    private final String name;
    private final String extension;

    public FileName(String name, String extension)
    {
        this.name = name;
        this.extension = extension;
    }

    public String getName()
    {
        return name;
    }

    public String getExtension()
    {
        return extension;
    }

    @Override
    public String toString()
    {
        return name + "." + extension;
    }
}