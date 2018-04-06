package person.liuxx.util.service.reponse;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午4:04:58
 * @since 1.0.0
 */
public final class ErrorResponse
{
    private final boolean errorResponse = true;
    /**
     * status属性仅仅与响应头中的 HTTP 状态码（integer）相同而已。
     */
    private final int status;
    /**
     * code属性是针对你提供的特定 REST API 的异常编码。它通常传达有关问题域的非常具体的信息。
     */
    private final int code;
    /**
     * message属性是一个可读性很好的异常消息，可能会被直接展示给应用程序端的用户
     */
    private final String message;
    /**
     * devMessage属性传达了所有对使用你 REST API 的开发者而言可能有用的技术信息
     */
    private final String devMessage;
    /**
     * moreInfo 属性指定一个 URL， 任何看到该 URL 的人都可在浏览器中点击（或复制粘贴）。<br>
     * URL 的目标网页应该全面的描述出现的异常情况，以及潜在的解决方案，从而帮助他们解决异常情况
     */
    private final String moreInfo;

    public ErrorResponse(int status, int code, String message, String devMessage, String moreInfo)
    {
        this.status = status;
        this.code = code;
        this.message = message;
        this.devMessage = devMessage;
        this.moreInfo = moreInfo;
    }

    public int getStatus()
    {
        return status;
    }

    public int getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public String getDevMessage()
    {
        return devMessage;
    }

    public String getMoreInfo()
    {
        return moreInfo;
    }

    public boolean isErrorResponse()
    {
        return errorResponse;
    }

    @Override
    public String toString()
    {
        return "ErrorResponse [errorResponse=" + errorResponse + ", status=" + status + ", code="
                + code + ", message=" + message + ", devMessage=" + devMessage + ", moreInfo="
                + moreInfo + "]";
    }
}
