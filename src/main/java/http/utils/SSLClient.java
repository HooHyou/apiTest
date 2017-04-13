package http.utils; /**
 * Created by Chenxj on 16/3/8.
 */

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;

//用于进行Https请求的HttpClient
public class SSLClient {
    public static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            //信任所有
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true);

            SSLContext sslContext = sslContextBuilder.build();

            SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslFactory).build();

        } catch (GeneralSecurityException e) {
            throw e;
        }
    }
}