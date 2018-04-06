package person.liuxx.util.service.exception;

/**
 * 解析EXCEL文件时产生的异常
 * 
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午3:02:01
 * @since 1.0.0
 */
public class ExcelParseException extends RuntimeException
{
    private static final long serialVersionUID = -7189359129398244356L;

    public ExcelParseException(String message)
    {
        super(message);
    }

    public ExcelParseException(String message, Throwable e)
    {
        super(message, e);
    }
}
