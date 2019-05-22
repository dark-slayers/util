package person.liuxx.util.net;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.config.AuthSchemes;
import org.apache.hc.client5.http.config.CookieSpecs;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.sync.methods.HttpGet;
import org.apache.hc.core5.http.io.ResponseHandler;

/**
 * @author 刘湘湘
 * @since 2019年4月8日 下午5:36:05
 */
public final class HttpUtil
{
    private HttpUtil()
    {
        throw new AssertionError("工具类禁止实例化");
    }

    private static final long TIME_OUT = 15;

    /**
     * 向目标地址发送简单get请求，将结果使用UTF-8编码转为字符串
     * 
     * @author 刘湘湘
     * @since 2019年4月8日 下午5:41:11
     * @param url
     *            目标URL
     * @return get请求获取到文本
     * @throws IOException
     *             请求过程发生IO异常
     */
    public static Optional<String> simpleGet(String url)
    {
        try (CloseableHttpClient httpclient = HttpClients.createDefault())
        {
            final HttpGet httpget = new HttpGet(url);
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,
                            AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                    .build();
            RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                    .setSocketTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .setConnectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .setConnectionRequestTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .build();
            httpget.setConfig(requestConfig);
            httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                    + " AppleWebKit/537.36 (KHTML, like Gecko)"
                    + " Chrome/60.0.3112.78 Safari/537.36");
            final ResponseHandler<Optional<String>> responseHandler = new SimpleResponseHandler();
            Optional<String> responseBody = httpclient.execute(httpget, responseHandler);
            return responseBody;
        } catch (IOException e)
        {
            throw new HttpRequestFailedException("请求失败", e);
        }
    }
}
