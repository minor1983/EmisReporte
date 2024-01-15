import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class EnterPoint {
    static Connection myConnection;
    static Statement sqlStatement;
    public static Date START;

    public static void main(String[] args) throws SQLException, IOException, ParseException, InterruptedException {
//            String dbURL = "jdbc:oracle:thin:@10.12.57.101:1523:ARGODB2";
//            String strUserID = "POWERDB";
//            String strPassword = "Trailmsg23nigh";
        delayedStart();
//        String dbURL = "jdbc:oracle:thin:@10.12.57.101:1522:EMISDB";
//        String strUserID = "amr_russia";
//        String strPassword = "amr_russia";
        Property property=new Property();
        try {
            myConnection = DriverManager.getConnection(property.getDbURL(), property.getUserID(), property.getPassword());
            sqlStatement = myConnection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        long startTime = System.currentTimeMillis();
        getReport();
        long finishTime = System.currentTimeMillis();
        double processTime = (double) (finishTime - startTime) / (60000);
        System.out.println("Время формирование отчета: " + processTime + " мин");
        System.out.println("Формирование отчета было запущено "+new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(START));
        Scanner scanner=new Scanner(System.in);
        System.out.println("Для завершения нажмите Enter...");
        scanner.nextLine();
        scanner.close();
        sqlStatement.close();
        myConnection.close();
    }

    public static void delayedStart() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите время отложенного запуска как вещественную положительную величину в часах" +
                "\n(например, 0-немедленный запуск, 0.02 = через 1 минуту, 8 = через 8 часов)");
        String input = scanner.nextLine();
        double timeout = 0;
        try{
           timeout=Double.parseDouble(input);
        }
        catch(Exception e){
           e.printStackTrace();
            System.exit(0);
        }
        if (timeout<0) System.exit(0);
        if (timeout>0) {
            System.out.println("Формирование отчета будет запущено "+
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                            .format(new Date((long) (System.currentTimeMillis()+timeout*3600000L))));
            Thread.sleep((long) (timeout*3600000L));
        }
        START=new Date();
    }

    public static void getReport() throws SQLException, IOException, ParseException {
        System.out.println("Формируется список точек для выгрузки");
        DBTable dbTable = new DBTable(myConnection); //считать из базы колонку значений "METER_ID" таблицы "PAR_RESIDENT_METER"
        List<String> meterIdeList = dbTable.getColumnContent("PAR_RESIDENT_METER", "METER_ID");
        System.out.println("Список из " + meterIdeList.size() + " точек учета для для выгрузки сформирован");
        //заполняем коллекцию сущностями Meter со всеми считанными свойствами
        List<Meter> meterList = new ArrayList<>();
        Meter meter;
        String ide;

        for (int i = 0; i < meterIdeList.size(); i++) {
        //for (int i = 0; i < 1000; i++) {
            ide=meterIdeList.get(i);
            meter = getItemContent(ide);
            meterList.add(meter);
            System.out.println("Объект № " + i + " c ID = "+ide+ " считан");
        }

        System.out.println("Формируем отчет XLS...");
        long start = System.currentTimeMillis();
        new XLSWriter(meterList);
        long end = System.currentTimeMillis();
        double work = (double) (end - start) / 60000;
        System.out.println("Время формрования XLS " + work + " мин");
    }

    public static Meter getItemContent(String idMeter) {
        Meter meter = new Meter(idMeter);
        ResultSet resultSet;
        long startTime = System.currentTimeMillis();
        long finishTime;
        long workTime;

        try {
            resultSet = sqlStatement.executeQuery(DBRequest.getItemFromDB(idMeter));
            while (resultSet.next()) {
                meter.setLs(resultSet.getString("LS"));
                meter.setOwner(resultSet.getString("OWNER"));
                meter.setCity(resultSet.getString("CITY"));
                meter.setStreet(resultSet.getString("STREET"));
                meter.setBuild_num(resultSet.getString("BUILDING"));
                meter.setDistrict(resultSet.getString("DISTRICT"));
                meter.setBuild_letter(resultSet.getString("BUILDING_LETTER"));
                meter.setApart_num(resultSet.getString("APART_NUM"));
                meter.setSerial(resultSet.getString("SERIAL"));
                meter.setTariffCount(resultSet.getString("TARIFF_NUM"));
                meter.setPointType(resultSet.getString("SORT"));
                meter.setValue_Sum(resultSet.getString("SUM"));
                meter.setValue_T1(resultSet.getString("T1"));
                meter.setValue_T2(resultSet.getString("T2"));
                meter.setValue_T3(resultSet.getString("T3"));
                meter.setDateStamp(stringToDate(resultSet.getString("TIME")));
                meter.setComments(resultSet.getString("COMMENTS"));
                meter.setKtt(resultSet.getString("KTT"));
            }
            if (meter.getSerial().startsWith("510")) meter.setType("ЭМИС ЭЛЕКТРА 510");
            else if (meter.getSerial().startsWith("975")) meter.setType("ЭМИС ЭЛЕКТРА 975");
            else meter.setType(" ");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        finishTime = System.currentTimeMillis();
        workTime = finishTime - startTime;
        System.out.println("Время обработки запроса  " + workTime + " мс");
        return meter;
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd HH:mm:ss");
        return format.parse(dateString);
    }
}
