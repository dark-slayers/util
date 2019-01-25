package person.liuxx.util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import person.liuxx.util.base.StringUtil;

/**
 * 用于处理配置文件的工具类
 * 
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 下午1:16:47
 * @since 1.0.0
 */
public final class ConfigFileUtil
{
    private ConfigFileUtil()
    {
        throw new AssertionError("工具类禁止实例化！");
    }

    /**
     * 以UTF-8编码读取一个配置文件，将读取的结果放入一个Map,配置文件逐行读取，不是合法格式的行将被忽略<br>
     * 合法的行忽视前后空白后，起始字符不是"/"，行内包含且只包含一个"="，"="字符分割开的前后部分,忽视前后空白都不能为空字符<br>
     * "="字符分割开的前部分作为键，后部分作为值，存入结果Map
     * <p>
     * 如果不同的行中包含相同的键，Map中的值使用后面行的值
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午1:42:59
     * @since 1.0.0
     * @param path 配置文件路径
     * @return 配置文件解析后的键值对map
     * @throws IOException 读取配置文件时发生IO异常
     */
    public static Map<String, String> read(String path) throws IOException
    {
        Map<String, String> result = Files.lines(Paths.get(path))
                .filter(s -> Objects.nonNull(s))
                .map(s -> s.trim())
                .filter(s -> !StringUtil.isBlank(s))
                .filter(s -> !s.startsWith("/"))
                .filter(s -> !s.startsWith("="))
                .filter(s -> !s.endsWith("="))
                .filter(s -> Arrays.stream(s.split(""))
                        .filter(c -> Objects.equals("=", c))
                        .count() == 1)
                .map(s -> s.split("="))
                .collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()));
        return result;
    }
}
