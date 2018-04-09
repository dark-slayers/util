package person.liuxx.util.service.reponse;

/**
 * 表示一个操作成功的返回结果，结果只包含一个固定字段succed,字段的值固定为true
 * 
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月9日 上午10:13:00
 * @since 1.0.0
 */
public final class EmptySuccedResponse
{
    private final boolean succed = true;

    public EmptySuccedResponse()
    {
    }

    public boolean isSucced()
    {
        return succed;
    }
}
