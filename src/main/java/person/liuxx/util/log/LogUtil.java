package person.liuxx.util.log;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 日志工具
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午4:37:37
 * @since 1.0.0
 */
public final class LogUtil
{
    private LogUtil()
    {
        throw new AssertionError("工具类禁止实例化");
    }
	public static String errorInfo(Throwable e)
	{
		StringWriter trace = new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		return trace.toString();
	}
}
