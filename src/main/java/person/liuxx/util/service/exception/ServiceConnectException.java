package person.liuxx.util.service.exception;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年7月12日 下午3:04:44
 * @since 1.0.0
 */
public class ServiceConnectException extends RuntimeException
{
    private static final long serialVersionUID = -1517954720201293973L;

    public ServiceConnectException(String message)
    {
        super(message);
    }

    public ServiceConnectException(String message, Throwable e)
    {
        super(message, e);
    }
}
