package person.liuxx.util.log;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 日志工具
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午4:37:37
 * @since 1.0.0
 */
public class LogUtil
{
	public static String errorInfo(Throwable e)
	{
		StringWriter trace = new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		return trace.toString();
	}
}
