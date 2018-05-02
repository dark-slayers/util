package person.liuxx.util.security;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年5月2日 下午1:51:32
 * @since 1.0.0
 */
public class EncryptException extends RuntimeException
{
    private static final long serialVersionUID = -3395870051039508003L;

    public EncryptException(String message)
    {
        super(message);
    }

    public EncryptException(String message, Throwable e)
    {
        super(message, e);
    }
}
