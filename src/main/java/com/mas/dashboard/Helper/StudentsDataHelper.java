package com.mas.dashboard.Helper;

import com.mas.dashboard.entity.StudentData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentsDataHelper {

    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();
        //checking that file is of Excel type or not

       return  (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));


    }

    public  static List<StudentData> convertExcelToListOfStudentsData(InputStream is){
        List<StudentData> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet("MasterData");

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
                StudentData data = new StudentData();

                //check for empty cell
                Cell rowCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (rowCell == null || rowCell.getCellType() == CellType.BLANK){
                    break;
                }

                while (cells.hasNext()){
                    Cell cell = cells.next();
                    switch (cellId){
                        case 1:
                            data.setRollNumber( cell.getStringCellValue());
                            break;
                        case 2:
                            data.setStudentName(cell.getStringCellValue());
                            break;
                        case 3:
                            data.setTotalMarks((int) cell.getNumericCellValue() );
                            break;
                        case 4:
                            data.setRank((int) cell.getNumericCellValue());
                            break;
                        case 5:
                            data.setPercentile( cell.getNumericCellValue());
                            break;
                        case 6:
                            data.setTestDate(cell.getDateCellValue());
                            break;
                        case 7:
                            data.setTestName( cell.getStringCellValue());
                            break;
                        case 8:
                            data.setHelper(cell.getStringCellValue());
                            break;
                        case 9:
                            data.setRecentTestName( cell.getStringCellValue());
                            break;
                        case 10:
                            data.setPercentileFinal(cell.getNumericCellValue());
                            break;
                        case 11:
                            data.setMarksFinal((int) cell.getNumericCellValue());
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
