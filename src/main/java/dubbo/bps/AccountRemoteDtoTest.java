package dubbo.bps;

import com.netease.pbs.api.CommonResultDto;
import com.netease.pbs.api.authentication.dto.*;
import com.netease.pbs.api.contract.dto.ContractRemoteDto;
import com.netease.pbs.api.contract.dto.ContractResultDto;
import com.netease.pbs.api.contract.service.ContractRemoteService;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import dubbo.DubboTestBase;
import dubbo.data.DataForAccountRemoteDtoTest;
import dubbo.pbsDubboTestBase;
import dubbo.utils.FileUtils;
import dubbo.utils.NosUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Chenxj on 2017/1/22.
 */
public class AccountRemoteDtoTest extends pbsDubboTestBase {

    @Test(dataProvider = "register", dataProviderClass = DataForAccountRemoteDtoTest.class)
    public void register(String caseid, String desc, String expect, String mobile, String name, String idno) {
        log.info("-----------------Start to run test  " + caseid);
        AccountRemoteDto remoteDto = new AccountRemoteDto();
        remoteDto.setMobile(mobile);
        remoteDto.setName(name);
        remoteDto.setIdNo(idno);

        try {
            CommonResultDto<String> response = sealRemoteService.register(remoteDto);
            log.info("Got response:" + response.getResult() + " : " + response.getErrorMsg());
            Assert.assertNotNull(response);
            Assert.assertEquals(response.isSuccessed(), Boolean.valueOf(expect).booleanValue(), "返回值和期望不符：" + response.getErrorMsg());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test(dataProvider = "addSeal", dataProviderClass = DataForAccountRemoteDtoTest.class)
    public void addSeal(String caseid, String desc, String expect, String id, String Color, String TemplateType) {
        log.info("-----------------Start to run test  " + caseid);
        SealRemoteDto remoteDto = new SealRemoteDto();
        remoteDto.setAccountId(id);
        remoteDto.setColor(SealRemoteDto.ColorEnum.valueOf(Color));
        remoteDto.setTemplateType(SealRemoteDto.TemplateTypeEnum.valueOf(TemplateType));

        try {
            CommonResultDto response = sealRemoteService.addSeal(remoteDto);
            log.info("Got response:" + response.getResult() + " : " + response.getErrorMsg());
            Assert.assertNotNull(response);
            Assert.assertEquals(response.isSuccessed(), Boolean.valueOf(expect).booleanValue(), "返回值和期望不符：" + response.getErrorMsg());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    @Test(dataProvider = "SignData", dataProviderClass = DataForAccountRemoteDtoTest.class)
    public void sign(String caseid, String desc, String expect, Integer PosX, Integer PosY, String PosPage, String key, SignFileRemoteDto.SignTypeEnum SignType, SignFileRemoteDto.PosTypeEnum PosType, String Accoutid, String fileName, String path) {
        log.info("-----------------Start to run test  " + caseid);
        SignFileRemoteDto remoteDto = new SignFileRemoteDto();
        remoteDto.setPosX(PosX);
        remoteDto.setPosY(PosY);
        remoteDto.setPosPage(PosPage);
        remoteDto.setKey(key);
        remoteDto.setSignType(SignType);
        remoteDto.setPosType(PosType);
        remoteDto.setAccountId(Accoutid);//6B17FF7D1F8D40CB9B7054438D132C70
        remoteDto.setFileName(fileName);
        remoteDto.setFile(FileUtils.readFile(path));

        try {
            CommonResultDto<SignFileResultDto> response = sealRemoteService.sign(remoteDto);
            log.info("Got response:" + response.getResult().getSignNo() + " : " + response.getErrorMsg());

            Assert.assertNotNull(response);
            Assert.assertEquals(response.isSuccessed(), Boolean.valueOf(expect).booleanValue(), "返回值和期望不符：" + response.getErrorMsg());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Test(dataProvider = "confirm")
    public void confirm(String caseid, String desc, String expect, Integer PosX, Integer PosY, String PosPage, String key,
                        SignConfirmRemoteDto.SignTypeEnum SignType, SignConfirmRemoteDto.PosTypeEnum PosType, String AccountId, String SignNo) {
        log.info("-----------------Start to run test  " + caseid);
        SignConfirmRemoteDto remoteDto = new SignConfirmRemoteDto();
        remoteDto.setPosX(PosX);
        remoteDto.setPosY(PosY);
        remoteDto.setPosPage(PosPage);
        remoteDto.setKey(key);
        remoteDto.setSignType(SignType);
        remoteDto.setPosType(PosType);
        remoteDto.setAccountId(AccountId);
        remoteDto.setSignNo(SignNo);

        try {
            CommonResultDto<String> response = sealRemoteService.confirm(remoteDto);
            log.info("Got response:" + response.getResult() + " : " + response.getErrorMsg());
            Assert.assertNotNull(response);
            Assert.assertEquals(response.isSuccessed(), Boolean.valueOf(expect).booleanValue(), "返回值和期望不符：" + response.getErrorMsg());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @DataProvider(name = "confirm")
    public Object[][] confirm() {
        return new Object[][]{
                {"1", "有pos成功", "true", 135, 200, "5", "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"2", "id缺失", "false", 135, 200, "5", "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "", "JETYRCNIWCOFK4QN"},
                {"3", "登记id缺失", "false", 135, 200, "5", "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", ""},
                {"4", "签章类型缺失", "false", 135, 200, "5", "1", null, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"5", "定位类型缺失", "false", 135, 200, "5", "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, null, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"6", "坐标x缺失", "false", null, 200, "5", "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"7", "坐标y缺失", "false", 135, null, "5", "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"8", "页码pos缺失", "false", 135, 200, null, "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"9", "页码key缺失", "false", 135, 200, "5", "", SignConfirmRemoteDto.SignTypeEnum.KEYWORDS, SignConfirmRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"10", "有key成功", "true", 135, 200, null, "1", SignConfirmRemoteDto.SignTypeEnum.KEYWORDS, SignConfirmRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"11", "key和pos都有成功", "true", 135, 200, "5", "1", SignConfirmRemoteDto.SignTypeEnum.KEYWORDS, SignConfirmRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},

        };
    }

    @Test(dataProvider = "signAndSave")
    public void signAndSave(String caseid, String desc, String expect, Integer PosX, Integer PosY, String PosPage, String key, SignFileRemoteDto.SignTypeEnum SignType, SignFileRemoteDto.PosTypeEnum PosType, String Accoutid, String fileName, String path) {
        log.info("-----------------Start to run test  " + caseid);
        SignFileRemoteDto remoteDto = new SignFileRemoteDto();

        remoteDto.setPosX(PosX);
        remoteDto.setPosY(PosY);
        remoteDto.setPosPage(PosPage);
        remoteDto.setKey(key);
        remoteDto.setSignType(SignType);
        remoteDto.setPosType(PosType);
        remoteDto.setAccountId(Accoutid);
        remoteDto.setFileName(fileName);
        remoteDto.setFile(FileUtils.readFile(path));
        //FileUtils.writeFile("/Users/Chenxj/Documents/test2.pdf", NosUtil.getInstance().getObjectByte("https://nos.netease.com/loan-pixiu-prod/dx4ijj5i.pdf"));

        try {

            CommonResultDto<SignFileResultDto> response = sealRemoteService.signAndSave(remoteDto);
            log.info("Got SignNo:" + response.getResult().getSignedFile() + " : " + response.getErrorMsg());
            Assert.assertNotNull(response);
            Assert.assertEquals(response.isSuccessed(), Boolean.valueOf(expect).booleanValue(), "返回值和期望不符：" + response.getErrorMsg());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @DataProvider(name = "signAndSave")
    public Object[][] signAndSave() {
        return new Object[][]{
                {"1", "有pos成功", "true", 1, 1, "1", "", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"2", "有key成功", "true", 135, 200, null, "PMO", SignFileRemoteDto.SignTypeEnum.KEYWORDS, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"3", "key和pos都有成功", "true", 135, 200, "1", "PMO", SignFileRemoteDto.SignTypeEnum.KEYWORDS, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"4", "id缺失", "false", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"5", "文件名缺失", "false", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "", "/Users/Chenxj/Documents/test.pdf"},
                {"6", "签章类型缺失", "false", 135, 200, "1", "1", null, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"7", "定位类型缺失", "false", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, null, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"8", "坐标x缺失", "false", null, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"9", "坐标y缺失", "false", 135, null, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"10", "页码pospage缺失", "false", 135, 200, null, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"11", "页码key缺失", "false", 135, 200, "1", "", SignFileRemoteDto.SignTypeEnum.KEYWORDS, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"12", "文件缺失", "false", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/123.pdf"},

        };
    }


    @Test(dataProvider = "registerOrg")
    public void registerOrg(String caseid, String desc, String expect, String name, String legalname, String legalidno, String agentname, String agentidno, OrganizationRemoteDto.AreaEnum legalarea,
                            OrganizationRemoteDto.UserTypeEnum usertype, String mobile, String organcode, OrganizationRemoteDto.OrgTypeEnum organtype, OrganizationRemoteDto.RegTypeEnum regtype, String regcode) {
        log.info("-----------------Start to run test  " + caseid);
        OrganizationRemoteDto remoteDto = new OrganizationRemoteDto();

        remoteDto.setName(name);
        remoteDto.setLegalName(legalname);
        remoteDto.setLegalIdNo(legalidno);
        remoteDto.setAgentName(agentname);
        remoteDto.setAgentIdNo(agentidno);
        remoteDto.setUserType(usertype);
        remoteDto.setMobile(mobile);
        remoteDto.setOrganCode(organcode);
        //以下是选填字段
        remoteDto.setLegalArea(legalarea);
        remoteDto.setOrganCode(organcode);
        remoteDto.setOrganType(organtype);
        remoteDto.setRegCode(regcode);
        remoteDto.setRegType(regtype);

        try {
            CommonResultDto<String> response = sealRemoteService.registerOrg(remoteDto);
            log.info("Got response:" + response.getResult() + " : " + response.getErrorMsg());
            Assert.assertNotNull(response);
            Assert.assertEquals(response.isSuccessed(), Boolean.valueOf(expect).booleanValue(), "返回值和期望不符：" + response.getErrorMsg());


        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @DataProvider(name = "registerOrg")
    public Object[][] registerOrg() {
        return new Object[][]{
                /*
                    agentidno 只能传大陆的身份证号
                 */
                {"1", "legal正常", "true", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "", "", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
//                {"2", "name缺失", "false", "", "蔡安活", "H0851785601", "陈潇", "H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
//                {"3", "手机缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "陈潇", "H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
//                {"4", "organcode缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "陈潇", "H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
//                {"5", "usertype缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "陈潇", "H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, null, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
//                {"6", "legalName缺失", "false", "上海网易小额贷款有限公司", "", "H0851785601", "陈潇", "H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
//                {"7", "legalIdNo缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "", "陈潇", "H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"8", "agent正常", "true", "上海网易小额贷款有限公司", "", "", "陈潇俊", "331082199002268872", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.AGENT, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
//                {"9", "agentname缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "", "H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.AGENT, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
//                {"10", "agentidno缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "陈潇", "", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.AGENT, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},

        };
    }

    @Test(dataProvider = "addOrgSeal")
    public void addOrgSeal(String caseid, String desc, String expect, String accountid, String hText, String qText) {
        log.info("-----------------Start to run test  " + caseid);
        OrgSealRemoteDto remoteDto = new OrgSealRemoteDto();
        remoteDto.setAccountId(accountid);
        remoteDto.sethText(hText);
        remoteDto.setqText(qText);

        try {
            CommonResultDto response = sealRemoteService.addOrgSeal(remoteDto);
            log.info("Got response:" + response.getResult() + " : " + response.getErrorMsg());
            Assert.assertNotNull(response);
            Assert.assertEquals(response.isSuccessed(), Boolean.valueOf(expect).booleanValue(), "结果返回与预期不符" + response.getErrorMsg());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @DataProvider(name = "addOrgSeal")
    public static Object[][] addOrgSeal() {
        return new Object[][]{
                {"1", "正常", "true", "95D2B141F819441D98676067F2B3C5C6", "合同专用章", "310000000146840"},
                {"2", "id为空", "false", "", "合同专用章", "310000000146840"},
                {"3", "htext缺失", "false", "95D2B141F819441D98676067F2B3C5C6", "", "310000000146840"},
                {"4", "qtext缺失(选填项)", "true", "95D2B141F819441D98676067F2B3C5C6", "合同专用章", ""},

        };
    }

    @Test
    public void GenerateContract(){
        ContractRemoteDto remoteDto = new ContractRemoteDto();
        remoteDto.setApplyNo("201703032103");
        remoteDto.setTplId("WYXD20170001");
        remoteDto.setUserSign(true);
        remoteDto.setOrgSign(false);
        remoteDto.setUserName("史雄强");
        remoteDto.setIdNo("511304198801245059");
        remoteDto.setOrgCode("310000000146840");
        remoteDto.setFileName("合同模板电子签章测试");
        remoteDto.setParamJson(null);

        try {
            CommonResultDto<Void> response = contractRemoteService.generateContract(remoteDto);
            System.out.println(response.isSuccessed());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Test
    public void GetContract(){
        String applyno = "201703032100";
        long timeout = 3000;

        try {
            CommonResultDto<ContractResultDto> response = contractRemoteService.getContract(applyno,null);
            System.out.println(response.getResult().getFileName());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void getAccount() {
        log.info("-----------------Start to run test  ");

        try {
            CommonResultDto<List<AccountResultDto>> response = sealRemoteService.getAccount("140502197507176316");
            log.info("Got SignNo:" + response.getResult().get(0).getAccountId() + " : " + response.getErrorMsg());
            Assert.assertNotNull(response);
            Assert.assertTrue(response.isSuccessed(), "错误信息:" + response.getErrorMsg());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void getConfirmFile(){
        FileUtils.writeFile("/Users/Chenxj/Documents/test2.pdf", NosUtil.getInstance().getObjectByte("https://nos.netease.com/loan-xproject-test/k213lll7.pdf"));
    }

}
