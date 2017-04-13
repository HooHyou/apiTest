package http.elephant;

import http.utils.HttpClientUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chenxj on 2017/3/7.
 */
public class httpApiTest {

    public static Logger log= LogManager.getLogger(httpApiTest.class);

    @Test(dataProvider = "pointModelAnalyze")
    public void pointModelAnalyze(String caseid,String except,String startTime,String endTime,String appId,String modelId,
                                  String stepValue, String startValue,String endValue) {
        log.info("-----------------Start to run test  " + caseid);
        HashMap<String,String> paramlist = new HashMap<String,String>();
        paramlist.put("startTime",startTime);
        paramlist.put("endTime",endTime);
        paramlist.put("appId",appId);
        paramlist.put("modelId",modelId);
        paramlist.put("stepValue",stepValue);
        paramlist.put("startValue",startValue);
        paramlist.put("endValue",endValue);

        String response = HttpClientUtil.httpGet("http://10.165.125.47:8181/modelStatistics/pointModelAnalyze?",paramlist);
        System.out.println(response);

    }

    @DataProvider(name = "pointModelAnalyze")
    public Object[][] pointModelAnalyze() {
        return new Object[][]{
                {"1","成功","2017-03-06","2017-03-07","credit-card","2","80","100","800"},

        };
    }

    @Test(dataProvider = "pointModelDue")
    public void pointModelDue(String caseid,String except,String endTime,String appId,String modelId,
                              String stepValue, String startValue,String endValue) {
        log.info("-----------------Start to run test  " + caseid);
        HashMap<String,String> paramlist = new HashMap<String,String>();
        paramlist.put("endTime",endTime);
        paramlist.put("appId",appId);
        paramlist.put("modelId",modelId);
        paramlist.put("stepValue",stepValue);
        paramlist.put("startValue",startValue);
        paramlist.put("endValue",endValue);


        String response = HttpClientUtil.httpGet("http://10.165.125.47:8181/modelStatistics/pointModelDue?",paramlist);
        System.out.println(response);

    }

    @DataProvider(name = "pointModelDue")
    public Object[][] pointModelDue() {
        return new Object[][]{
                {"1","成功","2017-03-03","credit-card","2","80","100","800"},

        };
    }


    @Test(dataProvider = "decisionTableAnalyze")
    public void decisionTableAnalyze(String caseid,String except,String startTime,String endTime,String appId) {
        log.info("-----------------Start to run test  " + caseid);
        HashMap<String,String> paramlist = new HashMap<String,String>();
        paramlist.put("startTime",startTime);
        paramlist.put("endTime",endTime);
        paramlist.put("appId",appId);
        String response = HttpClientUtil.httpGet("http://10.165.125.47:8181/modelStatistics/decisionTableAnalyze?",paramlist);
        System.out.println(response);

    }


    @DataProvider(name = "decisionTableAnalyze")
    public Object[][] decisionTableAnalyze() {
        return new Object[][]{
                {"1","成功","2017-03-05","2017-03-07","credit-card"},
                {"2","成功","","","credit-card"},

        };
    }




    @Test(dataProvider = "decisionTableDue")
    public void decisionTableDue(String caseid,String except,String endTime,String appId) {
        log.info("-----------------Start to run test  " + caseid);
        HashMap<String,String> paramlist = new HashMap<String,String>();
        paramlist.put("endTime",endTime);
        paramlist.put("appId",appId);

        String response = HttpClientUtil.httpGet("http://10.165.125.47:8181/modelStatistics/decisionTableDue?",paramlist);
        System.out.println(response);

    }

    @DataProvider(name = "decisionTableDue")
    public Object[][] decisionTableDue() {
        return new Object[][]{
                //{"1","成功","2017-03-06","credit-card"},
                {"2","成功","","credit-card"},

        };
    }


}
