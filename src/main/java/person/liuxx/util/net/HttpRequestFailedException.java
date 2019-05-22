package person.liuxx.util.net;

/**
 * @author 刘湘湘
 * @since 2019年5月22日 下午5:40:42
 */
public class HttpRequestFailedException extends RuntimeException
{
    private static final long serialVersionUID = -7963263499914868175L;

    public HttpRequestFailedException(String message)
    {
        super(message);
    }

    public HttpRequestFailedException(String message, Throwable e)
    {
        super(message, e);
    }
}
