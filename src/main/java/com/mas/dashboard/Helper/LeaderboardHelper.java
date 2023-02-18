package com.mas.dashboard.Helper;

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

public class LeaderboardHelper {

    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();
        //checking that file is of Excel type or not

        return  (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));


    }

    public  static List<Leaderboard> convertExcelToListOfLeaderboard(InputStream is){
        List<Leaderboard> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet("Leaderboard");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while(iterator.hasNext()){
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cellId =0;
                Leaderboard data = new Leaderboard();

                while (cells.hasNext()){
                    Cell cell = cells.next();
                    switch (cellId){
                        case 0:
                            data.setId((long) cell.getNumericCellValue()) ;
                            break;
                        case 1:
                            data.setStudentName( cell.getStringCellValue());
                            break;
                        case 2:
                            data.setTotalMarks((int) cell.getNumericCellValue());
                            break;
                        case 3:
                            data.setRank((int) cell.getNumericCellValue());
                            break;
                        case 4:
                            data.setRollNumber( cell.getStringCellValue() );
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

