package http.laiqian;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import http.bean.Lighting;
import http.utils.HttpClientUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chenxj on 2017/4/27.
 */
public class LaiqianApiTest {
    public static Logger log= LogManager.getLogger(LaiqianApiTest.class);

    @Test
    public void lightingTest2(){
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("users", "cxj");
        map1.put("u", "123");
        String jsonStr = new Gson().toJson(map1);
        System.out.println(jsonStr);

    }

    /**
        每个方法进来的参数都不一样，下次可以考虑直接传json字符串
     */
    public Lighting lightningTest(String account,String identityNo,String method) {
        String json = "{\n" +
                "  \t\"accountId\":\""+account+"\",\n" +
                " \t\"userName\":\"test\",\n" +
                "\t\"identityNo\":\""+identityNo+"\",\n" +
                "\t\"phone\":\""+account+"\",\n" +
                "  \n" +
                "\t\"method\":\""+method+"\",\n" +
                "\t\"requestId\":\"2B550A80-D7BB-47E9-B608-170A4663A914\",\n" +
                "\t\"timestamp\":\"2017-04-08 15:59:21\"\n" +
                "}";

        String response = HttpClientUtil.doPostJson("http://10.165.125.54:8181/demo/lightning",json,"utf-8");
        JsonObject returnData = new JsonParser().parse(response).getAsJsonObject().get("data").getAsJsonObject();
        Lighting lighting = new Lighting();
        lighting.setEncrypt(String.valueOf(returnData.get("encrypt")));
        lighting.setDigest(String.valueOf(returnData.get("digest")));
//        System.out.println("encrypt---"+returnData.get("encrypt"));
//        System.out.println("digest----"+returnData.get("digest"));
        return lighting;
    }

    @Test
    public void lightningTest(){
        Lighting lighting = lightningTest("18667150226","231200198301289743","wyxd.api.user.login");
//        System.out.println(new Exception().getStackTrace()[0].getMethodName()+"--"+lighting.getEncrypt());
//        System.out.println(new Exception().getStackTrace()[0].getMethodName()+"--"+lighting.getDigest());
        String json="{\t\n" +
                "  \t\"version\":\"v0.1\",\n" +
                " \t\"requestId\":\"2B550A80-D7BB-47E9-B608-170A4663A914\",\n" +
                " \t\"merchantId\":\"lightning\",\n" +
                " \t\"method\":\"wyxd.api.user.login\",\n" +
                " \t\"timestamp\":\"2017-04-08 15:59:21\",\n" +
                " \t\"format\":\"json\",\n" +
                " \t\"signMethod\":\"hmacsha256\",\n" +
                " \t\"data\": "+lighting.getEncrypt()+",\n" +
                "    \"sign\": "+lighting.getDigest()+"\n" +
                "}";

        String response = HttpClientUtil.doPostJson("http://10.165.125.54:8181/api/user/login",json,"utf-8");
        System.out.println(new JsonParser().parse(response).getAsJsonObject().get("code"));
    }

    @Test
    public void test2(){
        Lighting lighting = lightningTest("18667150227","231200198301289745","wyxd.api.user.login");
        System.out.println(new Exception().getStackTrace()[0].getMethodName()+"--"+lighting.getEncrypt());
        System.out.println(new Exception().getStackTrace()[0].getMethodName()+"--"+lighting.getDigest());
        String json="{\t\n" +
                "  \t\"version\":\"v0.1\",\n" +
                " \t\"requestId\":\"2B550A80-D7BB-47E9-B608-170A4663A914\",\n" +
                " \t\"merchantId\":\"lightning\",\n" +
                " \t\"method\":\"wyxd.api.user.login\",\n" +
                " \t\"timestamp\":\"2017-04-08 15:59:21\",\n" +
                " \t\"format\":\"json\",\n" +
                " \t\"signMethod\":\"hmacsha256\",\n" +
                " \t\"data\": "+lighting.getEncrypt()+",\n" +
                "    \"sign\": "+lighting.getDigest()+"\n" +
                "}";

        System.out.println(json);
        String response = HttpClientUtil.doPostJson("http://10.165.125.54:8181/api/user/login",json,"utf-8");
        System.out.println(response);
        System.out.println(new JsonParser().parse(response).getAsJsonObject().get("code"));
    }

}
