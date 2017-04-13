package http.utils; /**
 * Created by Chenxj on 16/3/8.
 */

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.Environment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {

    private static Logger log = Logger.getLogger(HttpClientUtil.class);


    public static String sendPost(String url, Map<String, String> map, String encoding) {
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));


            System.out.println("请求地址：" + url);
            System.out.println("请求参数：" + nvps.toString());

            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }


//    public String doPostFromData(String url, Map<String, String> map, String charset) {
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        HttpEntity he = null;
//        CloseableHttpClient httpClient = null;
//        HttpPost httpPost = null;
//        String result = null;
//        try {
//
//            //httpClient = SSLClient.createSSLInsecureClient();
//            httpClient = HttpClients.createDefault();
//
//            httpPost = new HttpPost(url);
//            Iterator iterator = map.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Entry<String, String> elem = (Entry<String, String>) iterator.next();
//                builder.addTextBody(elem.getKey(), elem.getValue());
//                //System.out.println(elem.getKey()+"=="+elem.getValue());
//            }
//            he = builder.build();
//            httpPost.setEntity(he);
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            result = EntityUtils.toString(response.getEntity());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }


    public String doPost(String url, Map<String, String> map, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {

            httpClient = SSLClient.createSSLInsecureClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);

                httpPost.setEntity(entity);
            }
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("X-Deviceid", "EE81B266F2D9AAD0458DECB240246750");
            httpPost.setHeader("X-Appver", "10.4.5.0");
            httpPost.setHeader("X-Platform", "3");
            httpPost.setHeader("X-Mc", "21000020");
            httpPost.setHeader("User-Agent", "WacApp/10.4.5 (iPhone; iOS 9.2.1; Scale/2.00)");
            httpPost.setHeader("X-Uuid", "A24D1C4405FB4C02B5EF9F5C53204733");

            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public String doPostJsonOld(String url, String json, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = SSLClient.createSSLInsecureClient();
            httpPost = new HttpPost(url);
            //设置参数
            StringEntity s = new StringEntity(json);
            httpPost.setEntity(s);

            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public String doPostJson(String url, String json, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = SSLClient.createSSLInsecureClient();
            httpPost = new HttpPost(url);
            //设置参数
            StringEntity s = new StringEntity(json);
            httpPost.setEntity(s);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("X-Deviceid", "EE81B266F2D9AAD0458DECB240246750");
            httpPost.setHeader("X-Appver", "10.4.5.0");
            httpPost.setHeader("X-Platform", "3");
            httpPost.setHeader("X-Mc", "21000020");
            httpPost.setHeader("User-Agent", "WacApp/10.4.5 (iPhone; iOS 9.2.1; Scale/2.00)");
            httpPost.setHeader("X-Uuid", "A24D1C4405FB4C02B5EF9F5C53204733");


            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static String doGetJson(String url, String charset) {
        CloseableHttpClient httpClient = null;
        String result = null;
        try {
            httpClient = SSLClient.createSSLInsecureClient();
            HttpGet get = new HttpGet(url);

            HttpResponse response = httpClient.execute(get);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String doGetJson(String url, String params, String charset) {
        url = url + "/" + params;
        CloseableHttpClient httpClient = null;
        String result = null;
        try {
            httpClient = SSLClient.createSSLInsecureClient();
            HttpGet get = new HttpGet(url);

            HttpResponse response = httpClient.execute(get);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String HeaderGet(String url, Map<String, String> header) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;
        log.info("create httppost:" + url);
        HttpGet get = new HttpGet(url);

        if (header != null) {
            for (Entry<String, String> entry : header.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(get);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String httpGet(String url, HashMap<String, String> params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;
        String str = "";
        List<NameValuePair> list = new ArrayList<NameValuePair>();

        for (Entry<String, String> entry : params.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        try {
            str = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
            log.info("create httpget:" + url + str);


            HttpGet get = new HttpGet(url+str);
            CloseableHttpResponse response = null;
            response = httpclient.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static Header[] testlogin(String url, Map<String, String> header) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;
        Header[] cookie = null;
        log.info("create httppost:" + url);
        HttpGet get = new HttpGet(url);

        if (header != null) {
            for (Entry<String, String> entry : header.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(get);
            cookie = response.getHeaders("Cookies");

            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookie;
    }
}