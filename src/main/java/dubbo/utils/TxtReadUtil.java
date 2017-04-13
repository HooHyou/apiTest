package dubbo.utils;

import org.testng.annotations.Test;

import java.io.*;

/**
 * Created by Guai on 16/10/20.
 */
public class TxtReadUtil {
    public String txtRead(String filename,String  key){
        InputStream is = null;
        String res="";
        try {
            String path=System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator + filename;
            is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            // 读取一行，存储于字符串列表中
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                res=res+line;
            }
            return  res;
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public String txtReadSpilt(String filename,String split){
        InputStream is = null;
        String res="";
        try {
            String path=System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator + filename;
            is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            // 读取一行，存储于字符串列表中
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    res=res+line+split;
            }
            res = res.substring(0,res.length()-1);
            return  res;
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Test
    public void test(){
        TxtReadUtil txtReadUtil = new TxtReadUtil();
        String itemStr = txtReadUtil.txtRead("shulijson.txt", "key");
        System.out.println(itemStr);
    }
}
