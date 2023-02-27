package com.mas.dashboard.Helper;

import com.mas.dashboard.entity.CourseData;
import com.mas.dashboard.entity.Leaderboard;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourseDataHelper {
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();
        //checking that file is of Excel type or not

        return  (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));


    }
    public  static List<CourseData> convertExcelToListOfCourseData(InputStream is){
        List<CourseData> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet("CourseData");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while(iterator.hasNext()){
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cellId =1;
                CourseData data = new CourseData();

                while (cells.hasNext()){
                    Cell cell = cells.next();
                    switch (cellId){
                        case 1:
                            data.setCourseName( cell.getStringCellValue());
                            break;
                        case 2:
                            data.setStudentName(cell.getStringCellValue());
                            break;
                        case 3:
                            data.setEmail(cell.getStringCellValue());
                            break;
                        case 4:
                            data.setTimeSpentMins((int) cell.getNumericCellValue());
                            break;
                        case 5:
                            data.setProgress((int) cell.getNumericCellValue());
                            break;
                        case 6:
                            data.setTimeSpentHours(cell.getNumericCellValue());
                            break;
                        case 7:
                            data.setRollNumber(cell.getStringCellValue());
                            break;
                        default:
                            break;

                    }

                    cellId++;


                }
                list.add(data);


            }


        }catch (Exception e){
            e.printStackTrace();

        }
        return list;
    }
}
