import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableFileWriter {
    private final DBTable dbTable;
    final String path = "C://DBSaver//";
    private int rowCount;

    public TableFileWriter(DBTable dbTable, int rowCount) {
        this.dbTable = dbTable;
        this.rowCount=rowCount;
        write();
    }

    private void write(){

        try {
            //FileWriter writer = new FileWriter(path+dbTable.getName()+".txt", false);
            PrintWriter writer=new PrintWriter(path+dbTable.getName()+".txt", "cp1251");
            Statement sqlStatement = dbTable.getDBConnection().createStatement();
            ResultSet myResultSet = sqlStatement.executeQuery("select * from " + dbTable.getName());
            StringBuilder requestResult = new StringBuilder();
            for (int i=0; i<dbTable.getColumnNames().size();i++){
                requestResult.append(dbTable.getColumnNames().get(i)).append("\t");
            }
            requestResult.append("\n");
            writer.write(requestResult.toString());
            requestResult=new StringBuilder();
            int count=0;
            while (myResultSet.next()&count<rowCount) {
                for (int i = 0; i < dbTable.getColumnNames().size(); i++) {
                    requestResult.append(myResultSet.getString(dbTable.getColumnNames().get(i))).append("\t");
                }
                requestResult.append("\n");
                writer.write(requestResult.toString());
                count++;
                requestResult=new StringBuilder();
            }
            writer.flush();
            writer.close();
            myResultSet.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
