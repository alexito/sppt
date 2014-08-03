package org.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

  Connection connection;
  
  public ConnectDB() {
    connection = null;
    String host = "192.168.2.105",
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

  public Connection getConexion() {
    return connection;
  }

  public void setConexion(Connection connection) {
    this.connection = connection;
  }
}
