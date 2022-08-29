package person.liuxx.util.service.reponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.Getter;

/** 服务器返回结果
 * 字段code：200为正确，500为异常
 * 字段message：
 * 
* @author  
* @version 1.0.0<br>创建时间：2022年8月29日 下午3:18:14
* @since 1.0.0 
*/
@Getter
public class Response {
    private final int code;
    private final String message;
    private final JSONObject data;
    private final static String OK_MESSAGE = "OK";
    private final static String ERROR_MESSAGE = "ERROR";

    Response(int code, String message, JSONObject data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response emptySuccess() {
        return Response.of("OK");
    }

    public static Response of(String text) {
        JSONObject obj = new JSONObject();
        obj.put("data", text);
        return new Response(200, OK_MESSAGE, obj);
    }

    public static <T> Response of(List<T> list) {
        List<T> dataList = Optional.ofNullable(list).orElse(new ArrayList<>());
        JSONObject obj = new JSONObject();
        obj.put("list", JSONArray.parseArray(JSON.toJSONString(dataList)));
        return new Response(200, OK_MESSAGE, obj);
    }

    public static Response of(Object object) {
        JSONObject obj = new JSONObject();
        obj.put("data", JSON.toJSON(object));
        return new Response(200, OK_MESSAGE, obj);
    }

    public static <T> Response of(PageResponse<T> page) {
        JSONObject obj = new JSONObject();
        obj.put("data", JSON.toJSON(page));
        return new Response(200, OK_MESSAGE, obj);
    }

    public static Response error() {
        return new Response(500, ERROR_MESSAGE, null);
    }

    public static Response error(String message2) {
        return new Response(500, message2, null);
    }
}
