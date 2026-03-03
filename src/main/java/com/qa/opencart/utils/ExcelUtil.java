package com.qa.opencart.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtil {
    private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/opencarttestdata.xlsx";
    private static Workbook book;
    private static Sheet sheet;


    public static Object[][] getTestData(String sheetName)  {
        System.out.println("reading data from sheet: " + sheetName);
        Object data[][] = null;

        try {
            FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH); //Create connection with the Stre
            book = WorkbookFactory.create(ip); //load this excel file in the memory, so that we can read from excel
            sheet = book.getSheet(sheetName); //then go to specific sheet

            data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()]; //with 2DArray, get the rows and columns, no hardcoded value
            for(int i = 0; i < sheet.getLastRowNum(); i++){ //then with 2 for loop get the values of rows and columns(means cells)
                for(int j = 0; j < sheet.getRow(0).getLastCellNum(); j++){
                    data[i][j] = sheet.getRow(i + 1).getCell(j).toString(); //makesure use "i + 1" here, because "i" is header
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return data;
    }
}
