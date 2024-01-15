public class DBRequest {

    public static String getMeterIdeList(String tableName,String columnName){
        return "SELECT "+columnName+" FROM "+tableName;
    }

    public static String getItemFromDBOLD(String id){
        return "SELECT\n" +
                "    p.USER_NO as LS, p.DESCRIBE as OWNER, (SELECT DEPTNO FROM PAR_TERMINAL WHERE TERM_ID=par.TERMINAL_ID) as CITY,\n" +
                "    (SELECT NAME FROM PAR_DISTRICT WHERE ID=p.CTR_ID) as DISTRICT, par.DESCRIBE as STREET, par.BUILDING,\n" +
                "    par.BUILDING_LETTER, par.APART_NUM, par.COMM_ADDRESS as SERIAL, par.TARIFF_NUM,\n" +
                "    data.DATA_VAL_PP as SUM,data.DATA_VAL_PP_1 as T1,data.DATA_VAL_PP_2 as T2, data.DATA_VAL_PP_3 as T3,\n" +
                "    to_char(to_date('01.01.1970 00:00:00','dd.mm.yyyy hh24:mi:ss') + 1/24/60/60 * data.INS_TIME, 'dd.mm.yyyy hh24:mi:ss') as DATE_TIME,\n" +
                "    par.USER_HIGH_SORT,par.COMMENTS\n" +
                "FROM DATA_DAY_FROZEN_PERIOD data\n" +
                "INNER JOIN PAR_RESIDENT_METER par\n" +
                "ON ID=par.METER_ID\n" +
                "LEFT JOIN PAR_RESIDENT p\n" +
                "ON par.USER_ID = p.RESIDENT_ID\n" +
                "WHERE ID="+id+" AND INS_TIME= (SELECT max(data.INS_TIME) FROM DATA_DAY_FROZEN_PERIOD data WHERE data.ID= "+id+")";
    }

    public static String getItemFromDB(String id){
        return "SELECT\n" +
                "    p.USER_NO as LS,\n" +
                "    NVL(p.DESCRIBE,' ') as OWNER,\n" +
                "    NVL((SELECT DEPTNO FROM PAR_TERMINAL WHERE TERM_ID=par.TERMINAL_ID),'Йошкар-Ола') as CITY,\n" +
                "    NVL((SELECT NAME FROM PAR_DISTRICT WHERE ID=p.CTR_ID),' ') as DISTRICT,\n" +
                "    NVL(par.DESCRIBE,' ') as STREET,\n" +
                "    NVL(par.BUILDING,' ') as BUILDING,\n" +
                "    NVL(par.BUILDING_LETTER,' ') as BUILDING_LETTER,\n" +
                "    NVL(par.APART_NUM,' ') as APART_NUM,\n" +
                "    NVL(par.COMM_ADDRESS,' ') as SERIAL,\n" +
                "    NVL(par.TARIFF_NUM,0) as TARIFF_NUM,\n" +
                "    REPLACE(REPLACE(REPLACE(par.USER_HIGH_SORT, 3, 'ЮЛ'), 5, 'ФЛ'), 6, 'ОДПУ') as SORT,\n" +
                "    NVL(par.COMMENTS,' ') as COMMENTS,\n" +
                "    NVL(data.DATA_VAL_PP,0) as SUM,\n" +
                "    NVL(data.DATA_VAL_PP_1,0) as T1,\n" +
                "    NVL(data.DATA_VAL_PP_2,0) as T2,\n" +
                "    NVL(data.DATA_VAL_PP_3,0) as T3,\n" +
                "    par.METER_FACTOR AS KTT,\n"+
                "    NUMBER_TO_DATE(NVL(data.INS_TIME,0)) as TIME\n" +
                "FROM PAR_RESIDENT_METER par\n" +
                "LEFT JOIN PAR_RESIDENT p\n" +
                "ON par.USER_ID = p.RESIDENT_ID\n" +
                "LEFT JOIN DATA_DAY_FROZEN_PERIOD data\n" +
                "ON data.ID = par.METER_ID\n" +
                "WHERE par.METER_ID = "+
                id+ " ORDER BY data.INS_TIME DESC FETCH FIRST 1 ROWS ONLY";
    }
}
