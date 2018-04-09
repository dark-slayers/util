package person.liuxx.util.service.exception;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 上午11:33:07
 * @since 1.0.0
 */
public class RemoveException extends RuntimeException
{
    private static final long serialVersionUID = 746072847875051774L;

    public RemoveException(String message)
    {
        super(message);
    }
    public RemoveException(String message,Throwable e)
    {
        super(message,e);
    }
}
