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
     *            需要被加密的文本
     * @param algorithm
     *            摘要算法名称：MD5、SHA-1、SHA-256等
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String jdkSecurity(String in, String algorithm) throws NoSuchAlgorithmException
    {
        StringBuilder result = new StringBuilder();
        MessageDigest currentAlgorithm = MessageDigest.getInstance(algorithm);
        currentAlgorithm.reset();
        currentAlgorithm.update(in.getBytes());
        byte[] hash = currentAlgorithm.digest();
        for (int i = 0; i < hash.length; i++)
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
     * 对字符串进行SHA-256加密，获取到加密后的文本（大写，长度64）
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午5:24:08
     * @since 1.0.0
     * @param in
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String SHA2(String in) throws NoSuchAlgorithmException
    {
        return jdkSecurity(in, "SHA-256");
    }

    /**
     * 对字符串进行SHA-512加密，获取到加密后的文本（大写，长度128）
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午5:29:34
     * @since 1.0.0
     * @param in
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String SHA5(String in) throws NoSuchAlgorithmException
    {
        return jdkSecurity(in, "SHA-512");
    }

    /**
     * 对作为用户名和密码的参数字符串进行复杂运算，获取加密后的字符串（小写，长度128）
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年4月6日 下午5:32:46
     * @since 1.0.0
     * @param username
     *            被视为用户名的字符串
     * @param password
     *            被视为密码的字符串
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encrypt(String username, String password) throws NoSuchAlgorithmException
    {
        String in = username + password;
        int key = 7;
        String one = SHA5(in);
        String encryOne = one.substring(0, key) + one.substring(one.length() - key) + username;
        String two = SHA2(encryOne).toLowerCase();
        String encryTwo = two.substring(0, key) + password + two.substring(two.length() - key);
        String three = SHA5(encryTwo);
        return three.toLowerCase();
    }
}
