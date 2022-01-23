package sample.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class AbstractDataBaseHandler extends Configs{
    private static Connection dbConnection;

    Connection getDbConnection() throws SQLException, ClassNotFoundException {
        if(dbConnection == null){
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver");

            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            return dbConnection;
        }else{
            return dbConnection;
        }
    }
}
