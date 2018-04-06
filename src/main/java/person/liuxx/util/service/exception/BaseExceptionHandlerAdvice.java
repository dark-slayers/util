package person.liuxx.util.service.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import person.liuxx.util.log.LogUtil;
import person.liuxx.util.service.reponse.ErrorResponse;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 上午9:37:02
 * @since 1.0.0
 */
public class BaseExceptionHandlerAdvice
{
    private final String[]  classNameListArray = 
    { "person.liuxx.util.service.exception.SearchException", "java.lang.IllegalArgumentException",
            "java.time.format.DateTimeParseException", };

    protected List<String> getExceptionClassNameList()
    {
        return new ArrayList<>(Arrays.asList(classNameListArray));
    }

    protected ErrorResponse baseExceptionHandler(Exception e)
    {
        String exceptionClassName = e.getClass().getName();
        switch (exceptionClassName)
        {
        case "person.liuxx.util.service.exception.SearchException":
            {
                SearchException e1 = (SearchException) e;
                return new ErrorResponse(801, 80101, e1.getMessage(), "失败信息：" + LogUtil.errorInfo(
                        e), "more info");
            }
        case "java.lang.IllegalArgumentException":
            {
                return new ErrorResponse(400, 40001, "请求参数格式错误", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "java.time.format.DateTimeParseException":
            {
                return new ErrorResponse(400, 40002, "日期格式错误", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        default:
            {
                return new ErrorResponse(500, 50001, "未知错误", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        }
    }
}
