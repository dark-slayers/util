package person.liuxx.util.file;

/** 文件或文件夹操作时出现的异常，用来包装IOException
 * @author 刘湘湘
 * @since 2019年4月11日 下午3:33:16
 */
public class FileOperateException extends RuntimeException
{
    private static final long serialVersionUID = -8924495017379651936L;

    public FileOperateException(String message)
    {
        super(message);
    }

    public FileOperateException(String message, Throwable e)
    {
        super(message, e);
    }
}
