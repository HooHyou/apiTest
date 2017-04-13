package dubbo.shuiyou;

import com.netease.pbs.api.CommonResultDto;
import com.netease.pbs.api.authentication.dto.*;
import com.netease.wyxd.elephant.client.bean.request.shuiyou.ShuiYouDataBean;
import com.netease.wyxd.elephant.client.bean.request.shuiyou.ShuiYouRequestBean;
import com.netease.wyxd.elephant.client.bean.response.base.DataResponse;
import com.netease.wyxd.elephant.client.common.utils.MD5Util;
import com.netease.wyxd.elephant.client.service.ShuiYouService;
import com.wyxd.elephant.beans.shuiyou.ShuiYouIncReportBean;
import com.wyxd.elephant.beans.shuiyou.ShuiYouReportItemBean;
import dubbo.DubboTestBase;
import dubbo.data.DataForAccountRemoteDtoTest;
import dubbo.utils.FileUtils;
import dubbo.utils.NosUtil;
import dubbo.utils.TxtReadUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
     * Created by Chenxj on 2017/1/22.
     */
public class ShuiyouServiceTest extends DubboTestBase {

    private DataResponse<ShuiYouIncReportBean> response;

    @Test
    public void shuiyouTest() {
        ShuiYouRequestBean requestBean = new ShuiYouRequestBean();
        requestBean.setApplyNo("loan_apply-201701131627-000131");
        requestBean.setAppCode("credit-card");
        requestBean.setBid("xxx");
        requestBean.setPassword(MD5Util.md5("123"));


        try {
            DataResponse<ShuiYouIncReportBean> response = shuiYouService.queryIncReportByApplyNO(requestBean);
            log.info("返回的描述：" + response.getRespDesc());
            Assert.assertNotNull(response.getResult());
            Assert.assertEquals(response.getRespCode(), "0", "返回值和期望不符：");
            log.info("Got response:" + response.getResult().getServYouList().toString());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void SaveShuiyou() {
        TxtReadUtil txtReadUtil = new TxtReadUtil();
        ShuiYouDataBean shuiYouDataBean = new ShuiYouDataBean();
        shuiYouDataBean.setOrderSerial("107904test2");
        shuiYouDataBean.setFlag(0l);
        shuiYouDataBean.setData(txtReadUtil.txtRead("shuiyou-json.txt", "key"));
        shuiYouDataBean.setAppCode("credit-card");
        shuiYouDataBean.setBid("xxx");

        shuiYouDataBean.setPassword(MD5Util.md5("123"));

        try {
            DataResponse response = shuiYouService.saveBaseShuiYouData(shuiYouDataBean);
            log.info("返回的描述：" + response.getRespDesc());
            //Assert.assertNotNull(response.getResult());
            Assert.assertEquals(response.getRespCode(), "0", "返回值和期望不符：");
            //log.info("Got response:" + response.getResult().getServYouList().toString());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void queryShuiYouTagDataTest() {
        TxtReadUtil txtReadUtil = new TxtReadUtil();
        ShuiYouRequestBean requestBean = new ShuiYouRequestBean();
        requestBean.setApplyNo("107905");
        requestBean.setOrderSerial("107905");

        //appCode必须有
        requestBean.setAppCode("credit-card");
        //bid必须有，值随便传
        requestBean.setBid("xxx");
        //密码是123
        requestBean.setPassword(MD5Util.md5("123"));

        try {
            DataResponse<ShuiYouReportItemBean> response = shuiYouService.queryShuiYouTagData(requestBean);
            log.info("返回的描述：" + response.getRespDesc());
            //Assert.assertNotNull(response.getResult());
            Assert.assertEquals(response.getRespCode(), "0", "返回值和期望不符：");

            log.info("Got response:" + response.getResult().getNSRMC());
            log.info("Got response:" + response.getResult().getZRRSFZJHM());
            log.info("Got response:" + response.getResult().getZRRSFZJLX_DM());
            log.info("Got response:" + response.getResult().getQYKJXX().get(0).getQYXX().getJYFW());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}
