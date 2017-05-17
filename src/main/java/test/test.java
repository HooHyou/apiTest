package test;

import http.utils.OracleUtil;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenxj on 2017/3/14.
 */
public class test {


    public static void main(String args[]){
        System.out.println("新分支");

//        String str1 = new StringBuilder("计算机").append("软件").toString();
//        String str3 = new StringBuilder("计算机").append("软件").toString();
//        System.out.println(str1.intern()==str1);
//        System.out.println(str3.intern()==str3);
//
//        String str2 = new StringBuilder("Str").append("ing").toString();
//        System.out.println(str2.intern()==str2);
        // 创建一个数据库连接
        Connection connection = null;
        // 创建预编译语句对象，一般都是用这个而不用Statement
        PreparedStatement pstm = null;
        // 创建一个结果集对象
        ResultSet rs = null;

        OracleUtil ou = new OracleUtil();
        connection = ou.getConnection();
        String sql = "select * from student where 1 = 1";
        try {
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("stu_name");
                String gender = rs.getString("gender");
                String age = rs.getString("age");
                String address = rs.getString("address");
                System.out.println(id + "\t" + name + "\t" + gender + "\t"
                        + age + "\t" + address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ou.ReleaseResource();
        }

    }

    public void test1(List list1){
        list1.add("1");
    }

    @Test
    public void test2(){
        List list1 = new ArrayList();
        test1(list1);
        System.out.println(list1.get(0));
    }


    /**
     * String每次操作都会返回一个新对象，所以str1和str2是不一样的
     * @param str2
     */
    public void test3(String str2){
        str2=str2+"abc";
    }

    @Test
    public void test4(){
        String str = "123";
        test3(str);
        System.out.println(str);

        String str2 = new StringBuilder().append("a").append("b").toString();
        System.out.println(str2);
    }

}
