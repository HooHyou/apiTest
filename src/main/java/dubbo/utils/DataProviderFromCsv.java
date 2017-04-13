package dubbo.utils;

import au.com.bytecode.opencsv.CSVReader;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Chenxj on 2017/1/22.
 */
public class DataProviderFromCsv {

    // 定义csv文件的分割符
    public static char csvSeprator = ',';
    // 定义@DataProvider的name
    public static DataProviderFromCsv dataProviderFromCsv = new DataProviderFromCsv();

    @DataProvider(name = "ProviderCsvMethod")
    public static Object[][] getCsvCMData(Method method, ITestContext context)
            throws IOException {
        // 获取当前文件编译后的绝对路径

        String retPath = dataProviderFromCsv.getPath();

        // 获取调用方法的方法名
        String methodName = method.getName();

        // 获取调用方法的类名
        String className = null;
        String[] packageName = method.getDeclaringClass().getName().toString()
                .split("\\.");
        className = packageName[packageName.length - 1];

        // 指定当前类当前方法对应的csv文件
        //String csvPath = retPath + className + "." + methodName + ".xls";
        String csvPath = retPath + className + "." + methodName + ".csv";
        return dataProviderFromCsv.readCsvData(csvPath);

    }

    @DataProvider(name = "ProviderXlsMethod")
    public static Object[][] getXlsCMData(Method method, ITestContext context) throws IOException, BiffException {
        // 获取当前文件编译后的绝对路径

        String retPath = dataProviderFromCsv.getPath();

        // 获取调用方法的方法名
        String methodName = method.getName();

        // 获取调用方法的类名
        String className = null;
        String[] packageName = method.getDeclaringClass().getName().toString()
                .split("\\.");
        className = packageName[packageName.length - 1];

        // 指定当前类当前方法对应的csv文件
        String xlsPath = retPath + className + "." + methodName + ".xls";
        return dataProviderFromCsv.readExcelData(xlsPath);
    }

    @DataProvider(name = "ProviderCsvClass")
    public static Object[][] getCsvCData(Method method, ITestContext context)
            throws IOException, BiffException {
        // 获取当前文件编译后的绝对路径

        String retPath = dataProviderFromCsv.getPath();


        // 获取调用方法的类名
        String className = null;
        String[] packageName = method.getDeclaringClass().getName().toString()
                .split("\\.");
        className = packageName[packageName.length - 1];

        // 指定当前类当前方法对应的csv文件
        String csvPath = retPath + className + ".csv";
        //return dataProviderFromCsv.readCsvData(csvPath);
        return dataProviderFromCsv.readCsvData(csvPath);

    }

    @DataProvider(name = "ProviderXlsClass")
    public static Object[][] getXlsCData(Method method, ITestContext context)
            throws IOException, BiffException {
        // 获取当前文件编译后的绝对路径

        String retPath = dataProviderFromCsv.getPath();
;

        // 获取调用方法的类名
        String className = null;
        String[] packageName = method.getDeclaringClass().getName().toString()
                .split("\\.");
        className = packageName[packageName.length - 1];

        // 指定当前类当前方法对应的csv文件
        String xlsPath = retPath + className + ".xls";
        //return dataProviderFromCsv.readCsvData(csvPath);
        return dataProviderFromCsv.readExcelData(xlsPath);

    }

    public Object[][] readCsvData(String filePath) {
        // 从CSV文件中读取数据
        ArrayList<Object[]> csvList = null;
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"), csvSeprator);

            reader.readNext();

            // csv中每行的数据都是一个string数组
            String[] csvRow = null;
            csvList = new ArrayList<Object[]>();
            // 将读取的数据，按行存入到csvList中
            while ((csvRow = reader.readNext()) != null) {
                csvList.add(csvRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(csvList!=null){
            // 定义返回值
            Object[][] results = new Object[csvList.size()][];
            // 设置二维数组每行的值，每行是个object对象
            for (int i = 0; i < csvList.size(); i++) {
                results[i] = csvList.get(i);
            }
            return results;
        }else{
            return  null;
        }

    }

    /**
     * 加上inputdata路径
     * @return
     */
    public String getPath() {
        String absolutePath = null;
        try {
            absolutePath = this.getClass().getResource("/inputdata/").getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return absolutePath;
    }

    public List<Object> parseRow(Cell[] cells) {
        List<Object> cellValueList = new ArrayList<Object>();
        for (int i = 0; i < cells.length; i++) {
            cellValueList.add(cells[i].getContents());
        }
        return cellValueList;
    }

    public Object[][] readExcelData(String fileName) throws IOException, BiffException {
        File filexcel = new File(fileName); // 创建文件对象
        Workbook wb = Workbook.getWorkbook(filexcel); // 从文件流中获取Excel工作区对象（WorkBook）
        Sheet readSheet = wb.getSheet(0);
        List<Object> celllistValue = null;
        int rows = readSheet.getRows();
        int Columns = readSheet.getColumns();
        List newList = new ArrayList();
        for (int j = 1; j < rows; j++) {
            Cell[] cells = readSheet.getRow(j);
            celllistValue = parseRow(cells);
            boolean isSet = true;
            Object[] tmpColumns = new Object[Columns];
            for (int i = 0; i < celllistValue.size(); i++) {
                if("".equals(celllistValue.get(0))||celllistValue.get(0) == null){
                    isSet = false;
                    break;
                }
                tmpColumns[i] = celllistValue.get(i);
            }
            if (isSet) {
                newList.add(tmpColumns);
            }else{
                break;
            }
        }
        Object[][] newExcelData = new Object[newList.size()][Columns];
        for (int i = 0; i < newList.size(); i++) {
            newExcelData[i] = ( Object[])newList.get(i);
            System.out.println(newExcelData[i]);
        }
        return newExcelData;
    }


}
