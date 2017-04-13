package dubbo.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;

import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.auth.Credentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.CompleteMultipartUploadRequest;
import com.netease.cloud.services.nos.model.CompleteMultipartUploadResult;
import com.netease.cloud.services.nos.model.InitiateMultipartUploadRequest;
import com.netease.cloud.services.nos.model.InitiateMultipartUploadResult;
import com.netease.cloud.services.nos.model.ListPartsRequest;
import com.netease.cloud.services.nos.model.NOSObject;
import com.netease.cloud.services.nos.model.ObjectMetadata;
import com.netease.cloud.services.nos.model.PartETag;
import com.netease.cloud.services.nos.model.PartListing;
import com.netease.cloud.services.nos.model.PartSummary;
import com.netease.cloud.services.nos.model.PutObjectRequest;
import com.netease.cloud.services.nos.model.UploadPartRequest;
import com.netease.cloud.services.nos.model.UploadPartResult;


/**
 * 类NosHelper.java的实现描述：Nos服务 帮助类
 *
 * @author hzmaqiangjun 2016年12月23日 下午3:33:41
 */

public class NosUtil {

    private volatile static NosUtil instance;

    private NosClient nosClient;

    public static NosUtil getInstance() {
        if (instance == null) {
            synchronized (NosUtil.class) {
                if (instance == null) {
                    instance = new NosUtil();
                }
            }
        }

        return instance;
    }

    private NosUtil() {
        //预发布:967921571806489e8065bf28802d9d6a,64d19464c55d4f80b467d332e7391dc8
        Credentials credentials = new BasicCredentials("64d19464c55d4f80b467d332e7391dc8", "967921571806489e8065bf28802d9d6a");
        nosClient = new NosClient(credentials);
    }

    public String getObject(String url) {
        try {
            NOSObject nosObj = nosClient.getObject("loan-xproject-test", getKey(url));
            // 流式获取文件内容
            InputStream in = nosObj.getObjectContent();
            String obj = getValueFromInputStream(in, "UTF-8");
            // 最后关闭流，不关闭会导致连接泄露
            in.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getValueFromInputStream(InputStream is, String charSet) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(is, charSet));
            char[] temp = new char[2048];
            int charCount = 0;
            while ((charCount = br.read(temp)) != -1) {
                sb.append(new String(temp, 0, charCount));
            }
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }
        return sb.toString();
    }

    public byte[] getObjectByte(String url) {
        try {
            //预发loan-pixiu-prod
            NOSObject nosObj = nosClient.getObject("loan-xproject-test", getKey(url));
            // 流式获取文件内容
            InputStream in = nosObj.getObjectContent();
            byte[] obj = inputStream2Byte(in);
            // 最后关闭流，不关闭会导致连接泄露
            in.close();
            return obj;
        } catch (IOException e) {
        }

        return null;
    }

    private byte[] inputStream2Byte(InputStream in) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int count = -1;
        while ((count = in.read(data, 0, 2048)) != -1)
            outStream.write(data, 0, count);
        return outStream.toByteArray();
    }

    private String getUrl(String key) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("missing required parameter [key]");
        }

        return "https://nos.netease.com/loan-pixiu-prod/" + key;
    }

    private String getKey(String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("missing required parameter [url]");
        }

        return url.substring(url.lastIndexOf("/") + 1);
    }
}
