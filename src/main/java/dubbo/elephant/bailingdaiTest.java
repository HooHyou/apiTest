package dubbo.elephant;

import com.netease.wyxd.elephant.client.bean.request.consumerfin.ConsumerFinRequestBean;
import com.netease.wyxd.elephant.client.bean.request.shuiyou.ShuiYouRequestBean;
import com.netease.wyxd.elephant.client.bean.response.base.DataResponse;
import com.netease.wyxd.elephant.client.common.utils.MD5Util;
import com.wyxd.elephant.beans.shuiyou.ShuiYouIncReportBean;
import dubbo.DubboTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Chenxj on 2017/4/9.
 */
public class bailingdaiTest extends DubboTestBase{


    @Test
    public void bailingdaiTest() {
        ConsumerFinRequestBean requestBean = new ConsumerFinRequestBean();
        requestBean.setApplyNo("107905");
        //requestBean.setIdCardNo("1edc7efeab730176db7e4ed38d642772");

        requestBean.setBid("xxx");
        requestBean.setAppCode("credit-card");
        requestBean.setPassword(MD5Util.md5("123"));

        try {
            DataResponse<Integer> response = consumerFinService.queryWangYiScore(requestBean);
            log.info("返回的描述："+response.getRespDesc());
            log.info("返回的分数："+response.getResult());
            Assert.assertEquals(response.getRespCode(), "0", "返回值和期望不符：");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
