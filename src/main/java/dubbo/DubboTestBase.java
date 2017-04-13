package dubbo;


import com.netease.pbs.api.authentication.service.SealRemoteService;
import com.netease.pbs.api.contract.service.ContractRemoteService;
import com.netease.wyxd.elephant.client.service.ConsumerFinService;
import com.netease.wyxd.elephant.client.service.GjjService;
import com.netease.wyxd.elephant.client.service.ShuiYouService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.annotation.Resource;

/**
 * Created by Chenxj on 2017/1/18.
 */
@ContextConfiguration(locations = {"/spring/spring-client.xml"})
public class DubboTestBase extends AbstractTestNGSpringContextTests {

    public static Logger log = LogManager.getLogger(DubboTestBase.class);

    @Resource
    protected ShuiYouService shuiYouService;

    @Resource
    protected GjjService gjjService;

    @Resource
    protected ConsumerFinService consumerFinService;

}

