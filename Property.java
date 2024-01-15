import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property extends Properties {
    private final String dbURL;
    private final String userID;
    private final String password;

    public Property() throws IOException {
        FileInputStream fis = new FileInputStream("config.ini");
        this.load(fis);
        String database = this.getProperty("database");
        String port = this.getProperty("port");
        dbURL = "jdbc:oracle:thin:@"+this.getProperty("host")+":"+ port +":"+ database;
        userID = this.getProperty("login");
        password = this.getProperty("password");
    }

    public String getDbURL() {
        return dbURL;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }
}
