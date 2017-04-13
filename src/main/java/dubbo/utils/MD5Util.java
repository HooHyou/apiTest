package dubbo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * Created by Chenxj on 2017/2/24.
 */
public class MD5Util {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static String md5(String str)
    {
        return md5(str,"UTF-8");
    }

    public static String md5(String str,String charset)
    {
        try {
            byte newByte1[] = str.getBytes(charset);
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            byte newByte2[] = messagedigest.digest(newByte1);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < newByte2.length; i++) {
                String temp = Integer.toHexString(newByte2[i] & 0x000000ff);
                if (temp.length() < 2)
                    sb.append("0");
                sb.append(temp);
            }
            String cryptograph = sb.toString();
            return cryptograph;
        } catch (Exception e) {
            return "error";
        }
    }

}
