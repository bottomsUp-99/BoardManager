package lib;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
  private static ConnectionManager instance;
  private Connection connection;

  private ConnectionManager() {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
      if (input == null) {
        throw new IOException("해당 properties 파일을 찾을 수 없습니다..!!");
      }
      properties.load(input);

      String url = properties.getProperty("url");
      String user = properties.getProperty("user");
      String password = properties.getProperty("password");

      this.connection = DriverManager.getConnection(url, user, password);
    } catch (IOException | SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("데이터베이스 연결을 초기화하는 동안 문제가 발생했습니다..!!", e);
    }
  }

  public static synchronized ConnectionManager getInstance() {
    if (instance == null) {
      instance = new ConnectionManager();
    }
    return instance;
  }

  public Connection getConnection() {
    return connection;
  }

  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
