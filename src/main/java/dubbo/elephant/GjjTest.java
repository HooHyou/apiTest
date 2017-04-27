package dubbo.elephant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.netease.wyxd.elephant.client.bean.request.gongjijin.GjjDataBean;
import com.netease.wyxd.elephant.client.bean.request.gongjijin.GjjRequestBean;
import com.netease.wyxd.elephant.client.bean.response.base.DataResponse;
import com.netease.wyxd.elephant.client.common.annotion.enums.GjjType;
import com.netease.wyxd.elephant.client.common.utils.MD5Util;
import com.wyxd.elephant.beans.gongjijin.HouseFundInfoResponse;
import com.wyxd.elephant.beans.shandian.ShandianGjjReportBean;
import com.wyxd.elephant.beans.shuli.HouseFundInfoBean;
import dubbo.DubboTestBase;
import dubbo.utils.TxtReadUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Chenxj on 2017/3/20.
 */
public class GjjTest extends DubboTestBase{

    @Test
    public void saveShandianGjjData() {
        Gson gson = new Gson();
        TxtReadUtil txtReadUtil = new TxtReadUtil();
        String itemStr = txtReadUtil.txtRead("shandianjson.txt", "key");
        ShandianGjjReportBean shandianGjjReportBean = gson.fromJson(itemStr,ShandianGjjReportBean.class);

        String json3 = "{\n" +
                "  \"appCode\": \"credit-card\",\n" +
                "  \"appId\": \"credit-card\",\n" +
                "  \"applyNo\": \"2017011814UA00000431\",\n" +
                "  \"eventId\": \"2017011814UA00000432\",\n" +
                "  \"gjjSourceType\": \"SHANDIAN\",\n" +
                "  \"jsonStr\": \"\"\n," +
                "  \"gjjIsVerified\": \"1\"\n" +
                "}";

        GjjDataBean<ShandianGjjReportBean> gjjDataBean = gson.fromJson(json3,GjjDataBean.class);
        gjjDataBean.setData(shandianGjjReportBean);
        gjjDataBean.setPassword(MD5Util.md5("123"));
        gjjDataBean.setGjjLocationCode("330000");
        gjjDataBean.setBid("2017011814UA00000431");

        try {
            gjjService.saveGjjData(gjjDataBean);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Test
    public void saveHouseFundInfo() {
        Gson gson = new Gson();
        TxtReadUtil txtReadUtil = new TxtReadUtil();
        String itemStr = txtReadUtil.txtRead("shulijson.txt", "key");
        //HouseFundInfoBean houseFundInfoBean = gson.fromJson(itemStr,HouseFundInfoBean.class);
        HouseFundInfoBean houseFundInfoBean = JSON.parseObject(itemStr,HouseFundInfoBean.class);

        String json3 = "{\n" +
                "  \"appCode\": \"credit-card\",\n" +
                "  \"appId\": \"credit-card\",\n" +
                "  \"applyNo\": \"2017011814UA00000431\",\n" +
                "  \"eventId\": \"2017011814UA00000431\",\n" +
                "  \"gjjSourceType\": \"SHULI\",\n" +
                "  \"gjjIsVerified\": \"NO\"\n" +
                "}";

        GjjDataBean<HouseFundInfoBean> gjjDataBean = gson.fromJson(json3,GjjDataBean.class);
        gjjDataBean.setData(houseFundInfoBean);
        gjjDataBean.setPassword(MD5Util.md5("123"));
        gjjDataBean.setGjjLocationCode("130100");
        gjjDataBean.setBid("2017011814UA00000431");
        gjjDataBean.setApplyDate(new Date());

        try {
            gjjService.saveGjjData(gjjDataBean);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }



    @Test(dataProvider = "retrieveHouseFundInfo")
    public void retrieveHouseFundInfo(String caseid, String desc, String expect, String accountid, String hText, String qText) {
        log.info("-----------------Start to run test  " + caseid);
        GjjRequestBean gjjRequestBean = new GjjRequestBean();
        gjjRequestBean.setGjjSourceType(GjjType.SHULI);
        gjjRequestBean.setAppId("credit-card");
        gjjRequestBean.setApplyNo("2017011814UA00000430");
        gjjRequestBean.setAppCode("credit-card");
        gjjRequestBean.setPassword(MD5Util.md5("123"));
        gjjRequestBean.setBid("2017011814UA00000430");
        gjjRequestBean.setTag("");
        gjjRequestBean.setStage("");

        try {
            DataResponse<HouseFundInfoResponse> response = gjjService.retrieveHouseFundInfo(gjjRequestBean);
            log.info("Got response:" + response.getResult().getRepaymentRecords());
            Assert.assertNotNull(response);
            System.out.println(response.getResult().getBaseInfo());
            //Assert.assertEquals(response.isSuccessed(), Boolean.valueOf(expect).booleanValue(), "结果返回与预期不符" + response.getErrorMsg());


        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @DataProvider(name = "retrieveHouseFundInfo")
    public static Object[][] retrieveHouseFundInfo() {
        return new Object[][]{
                {"1", "正常", "true", "95D2B141F819441D98676067F2B3C5C6", "合同专用章", "310000000146840"},
                {"2", "id为空", "false", "", "合同专用章", "310000000146840"},
                {"3", "htext缺失", "false", "95D2B141F819441D98676067F2B3C5C6", "", "310000000146840"},
                {"4", "qtext缺失(选填项)", "true", "95D2B141F819441D98676067F2B3C5C6", "合同专用章", ""},

        };
    }

}
