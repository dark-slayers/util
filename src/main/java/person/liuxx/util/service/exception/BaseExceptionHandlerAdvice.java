package person.liuxx.util.service.exception;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import person.liuxx.util.service.reponse.Response;

/**
 * @author 刘湘湘
 * 
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 上午9:37:02
 * 
 * @since 1.0.0
 */
public class BaseExceptionHandlerAdvice {
    private final String[] classNameListArray = {
            "person.liuxx.util.service.exception.SearchException",
            "java.lang.IllegalArgumentException", "java.time.format.DateTimeParseException", };

    protected List<String> getExceptionClassNameList() {
        return new ArrayList<>(Arrays.asList(classNameListArray));
    }

    protected Response baseExceptionHandler(Exception e) {
        String exceptionClassName = e.getClass().getName();
        switch (exceptionClassName) {
            case "person.liuxx.util.service.exception.SearchException": {
                SearchException e1 = (SearchException) e;
                return Response.error(e1.getMessage());
            }
            case "java.lang.IllegalArgumentException": {
                IllegalArgumentException e1 = (IllegalArgumentException) e;
                return Response.error(e1.getMessage());
            }
            case "java.time.format.DateTimeParseException": {
                DateTimeParseException e1 = (DateTimeParseException) e;
                return Response.error(e1.getMessage());
            }
            default: {
                return Response.error();
            }
        }
    }
}
