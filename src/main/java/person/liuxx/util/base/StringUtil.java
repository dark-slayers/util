package person.liuxx.util.base;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字符串工具类
 * 
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月4日 上午11:53:35
 */
public final class StringUtil
{
    private StringUtil()
    {
        throw new AssertionError("工具类禁止实例化！");
    }

    private static final int[] BLANK_CHARACTER_ARRAY =
    { 0xA0, 0x3000 };
    /**
     * 位于0X20之后的空白字符
     */
    private static final Set<String> BLANK_CHARACTER = Arrays.stream(BLANK_CHARACTER_ARRAY)
            .mapToObj(i -> new String(Character.toChars(i)))
            .collect(Collectors.toSet());

    /**
     * 判断指定字符串是否为空白（字符串对象为null或者字符串除去前后空白之后长度为0）
     * <p>
     * 此方法调用String内置方法trim()后根据剩余字符串长度判断字符串是否为空，<br>
     * 不能识别全角空格等位于ASCII码20（空格字符）之后的空白字符，<br>
     * 如果需要识别全角空格等字符，需要使用方法 {@link #isBlank(String)}
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月4日 下午2:52:50
     * @param word
     *            需要被判断的字符串
     * @return 指定字符串是否为空白
     */
    public static boolean isEmpty(String word)
    {
        return !Optional.ofNullable(word)
                .map(w -> w.trim())
                .map(w -> w.length())
                .filter(i -> i > 0)
                .isPresent();
    }

    /**
     * 判断指定字符串是否为空白，全角空格和0xA0等空白字符也会被识别
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月4日 下午4:34:56
     * @param word
     *            需要被判断的字符串
     * @return 指定字符串是否为空白
     */
    public static boolean isBlank(String word)
    {
        return word.isEmpty() || Stream.of(word.split("")).allMatch(t -> singleStringIsBlank(t));
    }

    /**
     * 判断单一字符(长度为1的String)是否为空白
     * 
     * @author 刘湘湘
     * @since 2019年4月11日 下午4:30:04
     * @param singleString
     *            需要被判断的参数，长度为1的String
     * @return
     */
    private static boolean singleStringIsBlank(String singleString)
    {
        return isEmpty(singleString) || BLANK_CHARACTER.contains(singleString);
    }

    /**
     * 判断指定字符串数组是否全部为空白（字符串对象为null或者字符串除去前后空白之后长度为0）
     * <p>
     * 此方法调用isEmpty(), 不能识别全角空格等位于ASCII码20（空格字符）之后的空白字符<br>
     * {@link #isEmpty(String)}
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月4日 下午2:56:47
     * @param words
     *            需要被判断的字符串数组
     * @return 指定字符串数组是否全部为空白
     */
    public static boolean isAllEmpty(String... words)
    {
        return Stream.of(words).parallel().allMatch(w -> isEmpty(w));
    }

    /**
     * 判断指定字符串数组是否有其中一个为空白（字符串对象对null或者字符串除去前后空白之后长度为0）
     * <p>
     * 此方法调用isEmpty(), 不能识别全角空格等位于ASCII码20（空格字符）之后的空白字符<br>
     * {@link #isEmpty(String)}
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月4日 下午4:35:07
     * @param words
     *            需要被判断的字符串数组
     * @return 指定字符串数组其中一个元素为空白就返回true
     */
    public static boolean isAnyEmpty(String... words)
    {
        return Stream.of(words).parallel().anyMatch(w -> isEmpty(w));
    }
}
