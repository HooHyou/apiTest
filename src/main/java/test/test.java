package test;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenxj on 2017/3/14.
 */
public class test {


    public static void main(String args[]){
//        String str1 = new StringBuilder("计算机").append("软件").toString();
//        String str3 = new StringBuilder("计算机").append("软件").toString();
//        System.out.println(str1.intern()==str1);
//        System.out.println(str3.intern()==str3);
//
//        String str2 = new StringBuilder("Str").append("ing").toString();
//        System.out.println(str2.intern()==str2);
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
