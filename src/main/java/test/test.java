package test;

/**
 * Created by Chenxj on 2017/3/14.
 */
public class test {

    public static void main(String args[]){
        String str1 = new StringBuilder("计算机").append("软件").toString();
        String str3 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern()==str1);
        System.out.println(str3.intern()==str3);

        String str2 = new StringBuilder("Str").append("ing").toString();
        System.out.println(str2.intern()==str2);
    }
}
