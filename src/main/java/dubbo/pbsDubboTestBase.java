package dubbo;


import com.netease.pbs.api.authentication.service.SealRemoteService;
import com.netease.pbs.api.contract.service.ContractRemoteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.annotation.Resource;

/**
 * Created by Chenxj on 2017/1/18.
 */
@ContextConfiguration(locations={"/spring/spring-pbs.xml"})
public class pbsDubboTestBase extends AbstractTestNGSpringContextTests {

    public static Logger log= LogManager.getLogger(pbsDubboTestBase.class);

    @Resource
    protected SealRemoteService sealRemoteService;

    @Resource
    protected ContractRemoteService contractRemoteService;




}

