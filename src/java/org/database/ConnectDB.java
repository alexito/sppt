package org.database;

import java.sql.*;

public class ConnectDB {

  Connection connection;
  
  public ConnectDB() {
    connection = null;
    String host = "192.168.0.4",
            port = "1433",
            db_name = "sistemappt",
            user = "sa",
            pass = "root";

    String connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + db_name + ";user=" + user + "; password=" + pass + ";";
    
    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      connection = DriverManager.getConnection(connectionUrl);
    } catch (ClassNotFoundException | SQLException e) {
      System.out.println("error: " + e.getMessage());
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }
}
