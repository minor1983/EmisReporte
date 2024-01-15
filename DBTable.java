import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBTable {
    private final String name;
    private ArrayList<String> columnNames;
    private int rowCount;
    private final Connection DBConnection;

    public DBTable(Connection DBConnection){
        this.DBConnection=DBConnection;
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public Connection getDBConnection() {
        return DBConnection;
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public List<String> getColumnContent(String table, String column) throws SQLException {
        String cell="";
        List<String> result=new ArrayList<>();
        Statement sqlStatement = DBConnection.createStatement();
        ResultSet myResultSet = sqlStatement.executeQuery(DBRequest.getMeterIdeList(table,column));
        int columnCount=myResultSet.getMetaData().getColumnCount();
        while (myResultSet.next()) {
            for (int i = 1; i <=columnCount ; i++) {
                cell = myResultSet.getString(i);
                result.add(cell);
            }
        }
        myResultSet.close();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
