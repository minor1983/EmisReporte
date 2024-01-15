import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XLSWriter {

    private final List <Meter> meterList;
    private final XSSFWorkbook book;
    private final XSSFSheet sheet;
    private final int COLUMNCOUNT=21; //количество колонок выгрузки
    private XSSFCellStyle style;

    public XLSWriter(List<Meter> meterList) throws IOException, ParseException {
        this.meterList=meterList;
        book= new XSSFWorkbook(); //создание книги
        sheet = book.createSheet("Показания"); // создание страницы
        File folder = new File("Reports");
        if (!folder.exists()) {
            folder.mkdir();
        }
       // FileOutputStream fileOut = new FileOutputStream("C://DBSaver//REPORT_" +
       //        new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date())+".xlsx");
        FileOutputStream fileOut = new FileOutputStream("REPORTS//отчет_Эмис_" +new SimpleDateFormat("dd-MM-yyyy HH-mm-ss")
                .format(new Date())+".xlsx");
        setXLSStyle(); //задаем стиль таблице
        createHeadXLSFile(); //формируем шапку
        setXLSColumnWidth(); //устанавливаем ширину столбцов
        XLSValuesFill(); //заполняем таблицу данными
        book.write(fileOut);
        fileOut.close();
    }

    private void setXLSStyle(){
        XSSFFont font = book.createFont(); // Создание шрифта
        font.setFontHeightInPoints((short)9);
        font.setFontName("Arial");
        style = book.createCellStyle(); // Создание стиля с определением в нем шрифта
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderTop   (CellStyle.BORDER_THIN);
        style.setBorderRight (CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft  (CellStyle.BORDER_THIN);

        XSSFRow row;
        XSSFCell cell;
        //отдельный стиль для ячеек с датой
        CreationHelper createHelper = book.getCreationHelper();
        XSSFCellStyle cellStyleDataFormat = book.createCellStyle();
        cellStyleDataFormat.setFont(font);
        cellStyleDataFormat.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleDataFormat.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleDataFormat.setBorderTop   (CellStyle.BORDER_THIN);
        cellStyleDataFormat.setBorderRight (CellStyle.BORDER_THIN);
        cellStyleDataFormat.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleDataFormat.setBorderLeft  (CellStyle.BORDER_THIN);
        cellStyleDataFormat.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

        //предаем общий стиль ячейкам на количество строк выгрузки
        for (int i=1; i<= meterList.size();i++){
            row = sheet.createRow(i);
            for (int j=0; j<COLUMNCOUNT;j++){
                cell = row.createCell(j);
                if (j==17) {                                 //номер колонки с типом дата
                    cell.setCellStyle(cellStyleDataFormat);
                    continue;
                }
                //cell.setCellType(CellType.STRING);
                cell.setCellStyle(style);
            }
        }
    }

    private void setXLSColumnWidth(){
        sheet.setColumnWidth(0, 5000); //Лицевой счет потребителя
        sheet.setColumnWidth(1, 4000); //Лицевой счёт до конвертациии данных в СТЕК
        sheet.setColumnWidth(2, 15000); //Фамилия имя отчество абонента
        sheet.setColumnWidth(3, 5000); //Населенный пункт
        sheet.setColumnWidth(4, 5000); //Район города
        sheet.setColumnWidth(5, 8000); //Улица
        sheet.setColumnWidth(6, 2000); //Номер дома
        sheet.setColumnWidth(7, 2000); //Номер корпуса
        sheet.setColumnWidth(8, 2000); //Номер квартиры
        sheet.setColumnWidth(9, 2000); //Номер комнаты
        sheet.setColumnWidth(10, 6000); //Тип счетчика
        sheet.setColumnWidth(11, 5000); //Номер счетчика
        sheet.setColumnWidth(12, 2000); //Количество зон счётчика
        sheet.setColumnWidth(13, 5000); //Показания текущие(день)
        sheet.setColumnWidth(14, 3000); //Показания текущие(ночь)
        sheet.setColumnWidth(15, 3000); //Показания текущие (полупик)
        sheet.setColumnWidth(16, 2000); //Коэффициент трансформации
        sheet.setColumnWidth(17, 6000); //Дата съема показаний
        sheet.setColumnWidth(18, 3000); //Номер лицевого в сетевой организации
        sheet.setColumnWidth(19, 3000); //ФЛ/ЮЛ/ОДПУ
        sheet.setColumnWidth(20, 3000); //Комментарий
    }

    private void createHeadXLSFile(){

        String [] head=new String[]{
                "Лицевой счет потребителя", "Лицевой счёт до конвертациии данных в СТЕК",
                "Фамилия имя отчество абонента", "Населенный пункт",
                "Район города", "Улица", "Номер дома", "Номер корпуса",
                "Номер квартиры", "Номер комнаты", "Тип счетчика",
                "Номер счетчика", "Количество зон счётчика",
                "Показания текущие(день/пик для 3-хзонных ПУ)(в случае одной зоны текущие показания заносятся в эту колонку)",
                "Показания текущие(ночь)", "Показания текущие (полупик)", "Ктт", "Дата съема показаний",
                "Номер лицевого в сетевой организации", "ФЛ/ЮЛ/ОДПУ", "Коммент"
        };
        XSSFRow row = sheet.createRow(0); // Создание ячейки с определением ее стиля
        sheet.setAutoFilter(CellRangeAddress.valueOf("A:U")); //добавляем фильтр на все колонки выгрузки
        style.setWrapText(true); //разрешаем перенос текста в ячейках

        XSSFCell cell; // создание ячейки

        for (int i=0; i<COLUMNCOUNT;i++){
            cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(head[i]);
            cell.setCellStyle(style);
        }
    }

    private void XLSValuesFill() {
        XSSFRow row;
        XSSFCell cell;
        for (int i=0; i<meterList.size();i++){
            row = sheet.getRow(i+1);
            for (int j=0; j<COLUMNCOUNT;j++){
                cell = row.getCell(j);
                switch (j) {
                    case 0 : cell.setCellValue(meterList.get(i).getLs()); break;
                    case 2 : cell.setCellValue(meterList.get(i).getOwner()); break;
                    case 3 : cell.setCellValue(meterList.get(i).getCity()); break;
                    case 4 : cell.setCellValue(meterList.get(i).getDistrict()); break;
                    case 5 : cell.setCellValue(meterList.get(i).getStreet()); break;
                    case 6 : cell.setCellValue(meterList.get(i).getBuild_num()); break;
                    case 7 : cell.setCellValue(meterList.get(i).getBuild_letter()); break;
                    case 8 : cell.setCellValue(meterList.get(i).getApart_num()); break;
                    case 10 : cell.setCellValue(meterList.get(i).getType()); break;
                    case 11 : cell.setCellValue(meterList.get(i).getSerial()); break;
                    case 12 : cell.setCellValue(meterList.get(i).getTariffCount()); break;
                    case 13 : cell.setCellValue(meterList.get(i).getValue_T1()); break;
                    case 14 : cell.setCellValue(meterList.get(i).getValue_T2()); break;
                    case 15 : cell.setCellValue(meterList.get(i).getValue_T3()); break;
                    case 16 : cell.setCellValue(meterList.get(i).getKtt()); break;
                    case 17 : cell.setCellValue(meterList.get(i).getDateStamp()); break;
                    case 19 : cell.setCellValue(meterList.get(i).getPointType()); break;
                    case 20 : cell.setCellValue(meterList.get(i).getComments()); break;
                }
            }
        }
    }
}
