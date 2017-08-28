package db;

public class DBUtil {
	private static final String HOSTNAME = "localhost";
    private static final String PORT_NUM = "3306";// change it to your mysql port number
    public static final String DB_NAME = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    public static final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
   			 + "?user=" + USERNAME + "&password=" + PASSWORD + "&autoreconnect=true";
}
