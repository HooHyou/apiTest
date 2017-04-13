package dubbo.data;

import com.netease.pbs.api.authentication.dto.OrganizationRemoteDto;
import com.netease.pbs.api.authentication.dto.SignConfirmRemoteDto;
import com.netease.pbs.api.authentication.dto.SignFileRemoteDto;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Chenxj on 2017/2/8.
 */
public class DataForAccountRemoteDtoTest {

    @DataProvider(name = "register")
    public static Object[][] register() {
        return new Object[][]{
                {"1", "正常", "true", "18667150226", "费俊伟", "140502197507176316"},
                {"2", "手机号为空", "false", "", "孔新荣", "370881197807138770"},
                {"3", "姓名为空", "false", "18667150226", "", "370881197807138770"},
                {"4", "身份证为空", "false", "18667150226", "孔欣荣", "" },
                //数据由来钱提供，并且会是实名后的信息，以下情况不会出现。做一个记录
                {"5", "手机号多一位", "false", "186671502267", "孔新荣", "370881197807138770"},
                {"6", "手机号少一位", "false", "1866715022", "孔新荣", "370881197807138770"},
                {"7", "手机号不合法", "false", "1866715022a", "孔新荣", "370881197807138770"},
                {"8", "身份证不合法", "false", "18667150226", "孔新荣", "37088119780713877"},
                {"9", "身份证包含非法字符", "false","1866715022", "孔新荣", "370881197807138770a"},

        };
    }

    @DataProvider(name = "addSeal")
    public static Object[][] addSeal() {
        return new Object[][]{
                {"1", "正常", "true", "A1B57E359E1949779AF3E5F6845DB309", "BLACK", "STLITI_SEAL"},
                {"2", "id为空", "false", "", "BLUE", "STLITI_SEAL"},
                {"3", "重复注册", "true", "A1B57E359E1949779AF3E5F6845DB309", "BLACK", "SXXINGKAI_SEAL"},
        };
    }

    @DataProvider(name = "addOrgSeal")
    public static Object[][] addOrgSeal() {
        return new Object[][]{
                {"1", "正常", "true", "95D2B141F819441D98676067F2B3C5C6", "合同专用章", "310000000146840"},
                {"2", "id为空", "false", "", "合同专用章", "310000000146840"},
                {"3", "htext缺失", "true", "95D2B141F819441D98676067F2B3C5C6", "", "310000000146840"},
                {"4", "qtext缺失(选填项)", "true", "95D2B141F819441D98676067F2B3C5C6", "合同专用章", ""},

        };
    }


    @DataProvider(name = "SignData")
    public static Object[][] signData() {
        return new Object[][]{
                //posPage为空时候，返回系统错误，应该返回参数缺失.
                {"1", "有pos成功", "true", 135, 200, "1", "", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"2", "有key成功(判断key和pos是否为空是根据signType来的，不是文档里写的PosTypeEnum。第三方的锅)", "true", 135, 200, null, "1", SignFileRemoteDto.SignTypeEnum.KEYWORDS, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"3", "key和pos都有成功", "true", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"4", "id缺失", "false", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"5", "文件名缺失", "false", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "", "/Users/Chenxj/Documents/test.pdf"},
                {"6", "签章类型缺失", "false", 135, 200, "1", "1", null, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"7", "定位类型缺失", "false", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, null, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"8", "坐标x缺失", "false", null, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"9", "坐标y缺失", "false", 135, null, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"10", "页码pospage缺失", "false", 135, 200, null, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"1si1", "页码key缺失", "false", 135, 200, "1", "", SignFileRemoteDto.SignTypeEnum.KEYWORDS, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"12", "文件缺失", "false", 135, 200, "1", "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/123.pdf"},

        };
    }

    @DataProvider(name = "confirm")
    public Object[][] confirm() {
        return new Object[][]{

                {"1", "有pos成功", "true", 135, 200, 5, "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "VGNM3UXNHBG0J1XI"},
//                {"2", "id缺失", "false", 135, 200, 5, "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "", "JETYRCNIWCOFK4QN"},
//                {"3", "登记id缺失", "false", 135, 200, 5, "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", ""},
//                {"4", "签章类型缺失", "false", 135, 200, 5, "1", null, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
//                {"5", "定位类型缺失", "false", 135, 200, 5, "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, null, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
//                {"6", "坐标x缺失", "false", null, 200, 5, "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
//                {"7", "坐标y缺失", "false", 135, null, 5, "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
//                {"8", "页码pos缺失", "false", 135, 200, null, "1", SignConfirmRemoteDto.SignTypeEnum.SINGLEPAGE, SignConfirmRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
//                {"9", "页码key缺失", "false", 135, 200, 5, "", SignConfirmRemoteDto.SignTypeEnum.KEYWORDS, SignConfirmRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "JETYRCNIWCOFK4QN"},
                {"10", "有key成功", "true", 135, 200, null, "1", SignConfirmRemoteDto.SignTypeEnum.KEYWORDS, SignConfirmRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "VGNM3UXNHBG0J1XI"},
                {"11", "key和pos都有成功", "true", 135, 200, 5, "1", SignConfirmRemoteDto.SignTypeEnum.KEYWORDS, SignConfirmRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "VGNM3UXNHBG0J1XI"},

        };
    }


    @DataProvider(name = "signAndSave")
    public Object[][] signAndSave() {
        return new Object[][]{
                {"1", "有pos成功", "true", 135, 200, 5, "", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"2", "有key成功", "true", 135, 200, null, "1", SignFileRemoteDto.SignTypeEnum.KEYWORDS, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"3", "key和pos都有成功", "true", 135, 200, 5, "1", SignFileRemoteDto.SignTypeEnum.KEYWORDS, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"4", "id缺失", "false", 135, 200, 5, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"5", "文件名缺失", "false", 135, 200, 5, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "", "/Users/Chenxj/Documents/test.pdf"},
                {"6", "签章类型缺失", "false", 135, 200, 5, "1", null, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"7", "定位类型缺失", "false", 135, 200, 5, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, null, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"8", "坐标x缺失", "false", null, 200, 5, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"9", "坐标y缺失", "false", 135, null, 5, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"10", "页码pospage缺失", "false", 135, 200, null, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"11", "页码key缺失", "false", 135, 200, 5, "", SignFileRemoteDto.SignTypeEnum.KEYWORDS, SignFileRemoteDto.PosTypeEnum.KEYWORDS, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/test.pdf"},
                {"12", "文件缺失", "false", 135, 200, 5, "1", SignFileRemoteDto.SignTypeEnum.SINGLEPAGE, SignFileRemoteDto.PosTypeEnum.COORDINATE, "A1B57E359E1949779AF3E5F6845DB309", "test", "/Users/Chenxj/Documents/123.pdf"},
        };
    }

    @DataProvider(name = "registerOrg")
    public Object[][] registerOrg() {
        return new Object[][]{
                {"1", "legal正常", "true", "上海网易小额贷款有限公司", "蔡安活", "H0851785601","陈潇","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"2", "name缺失", "false", "", "蔡安活", "H0851785601", "陈潇","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"3", "手机缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "陈潇","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"4", "organcode缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "陈潇","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"5", "usertype缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601", "陈潇","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, null, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"6", "legalName缺失", "false", "上海网易小额贷款有限公司", "", "H0851785601", "陈潇","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"7", "legalIdNo缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "", "陈潇","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.LEGAL, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"8", "agent正常", "true", "上海网易小额贷款有限公司", "蔡安活", "H0851785601","陈潇","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.AGENT, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"9", "agentname缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601","","H0851785602", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.AGENT, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},
                {"10", "agentidno缺失", "false", "上海网易小额贷款有限公司", "蔡安活", "H0851785601","陈潇","", OrganizationRemoteDto.AreaEnum.HONGKONG, OrganizationRemoteDto.UserTypeEnum.AGENT, "13064377435", "342339207", OrganizationRemoteDto.OrgTypeEnum.COMMON_ORG, OrganizationRemoteDto.RegTypeEnum.ORG_CODE, "310000000146840"},

        };
    }

}
