package person.liuxx.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 * 
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年4月6日 上午10:22:15
 * @since 1.0.0
 */
public final class EncryptUtil
{
    private EncryptUtil()
    {
        throw new AssertionError("工具类禁止实例化");
    }

    /**
     * jdk自带的摘要算法，结果中的字母为大写字母
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 上午10:26:57
     * @since 1.0.0
     * @param in
     *            需要使用摘要算法加密的文本
     * @param algorithm
     *            摘要算法名称：MD5、SHA-1、SHA-256等
     * @return 使用摘要算法散列之后的文本，大写
     * @throws NoSuchAlgorithmException
     *             摘要算法不被支持
     */
    public static String jdkSecurity(String in, String algorithm) throws NoSuchAlgorithmException
    {
        StringBuilder result = new StringBuilder();
        MessageDigest currentAlgorithm = MessageDigest.getInstance(algorithm);
        currentAlgorithm.reset();
        currentAlgorithm.update(in.getBytes());
        byte[] hash = currentAlgorithm.digest();
        for (int i = 0, max = hash.length; i < max; i++)
        {
            int v = hash[i] & 0xFF;
            if (v < 16)
            {
                result.append("0");
            }
            result.append(Integer.toString(v, 16).toUpperCase());
        }
        return result.toString();
    }

    /**
     * 对字符串进行SHA-256散列计算，获取到散列后的文本（大写，长度64）
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午5:24:08
     * @since 1.0.0
     * @param in
     *            需要使用摘要算法加密的文本
     * @return 使用SHA-256摘要算法散列之后的文本，大写
     * 
     */
    public static String sha2(String in)
    {
        try
        {
            return jdkSecurity(in, "SHA-256");
        } catch (NoSuchAlgorithmException e)
        {
            throw new EncryptException("使用SHA-256进行hash计算失败！", e);
        }
    }

    /**
     * 对字符串进行SHA-512加密，获取到散列后的文本（大写，长度128）
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午5:29:34
     * @since 1.0.0
     * @param in
     *            需要使用摘要算法加密的文本
     * @return 使用SHA-512摘要算法散列之后的文本，大写
     * 
     */
    public static String sha5(String in)
    {
        try
        {
            return jdkSecurity(in, "SHA-512");
        } catch (NoSuchAlgorithmException e)
        {
            throw new EncryptException("使用SHA-512进行hash计算失败！", e);
        }
    }

    /**
     * 对作为用户名和密码的参数字符串进行复杂运算，获取散列后的字符串（小写，长度128）
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午5:32:46
     * @since 1.0.0
     * @param username
     *            被视为用户名的字符串
     * @param password
     *            被视为密码的字符串
     * @return 使用自定义算法进行散列之后的字符串
     * @throws NoSuchAlgorithmException
     *             摘要算法不被支持
     */
    public static String encrypt(String username, String password) throws NoSuchAlgorithmException
    {
        String in = username + password;
        int key = 7;
        String one = sha5(in);
        String encryOne = one.substring(0, key) + one.substring(one.length() - key) + username;
        String two = sha2(encryOne).toLowerCase();
        String encryTwo = two.substring(0, key) + password + two.substring(two.length() - key);
        String three = sha5(encryTwo);
        return three.toLowerCase();
    }
}
