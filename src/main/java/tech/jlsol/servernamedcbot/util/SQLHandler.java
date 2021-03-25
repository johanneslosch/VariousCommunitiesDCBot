package tech.jlsol.servernamedcbot.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class SQLHandler {

  private static final String PORT = Config.readConfig(
    "data",
    "credentials",
    "databasePORT"
  );
  private static final String DBNAME = Config.readConfig(
    "data",
    "credentials",
    "databaseUSER"
  );
  private static final String USER = DBNAME;
  private static final String PASSWORD = Config.readConfig(
    "data",
    "credentials",
    "databasePASSWORD"
  );
  private static final String URL = Config.readConfig(
    "data",
    "credentials",
    "databaseURL"
  );

  private static final String DATABASE_URL =
    "jdbc:mysql://" + URL + ":" + PORT + "/" + DBNAME;
  private static Connection connection;
  private static PreparedStatement prepareStatement;

  /**
   * @return                  the connection
   */
  public static Connection connect() {
    if (connection == null) {
      System.out.println(System.nanoTime() + " Connecting database...");

      try {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setDatabaseName(USER);
        dataSource.setServerName(URL);
        connection = dataSource.getConnection();
        System.out.println(System.nanoTime() + " Database connected!");
      } catch (SQLException e) {
        Logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
    return connection;
  }

  /**
   * sets preparedStatement for Message-Recive event
   * @param time          time
   * @param channelID     channel ID
   * @param length        length
   * @param bot           is bot?
   * @param roles         number of roles
   * @param guild         guild ID
   * @return              preparedStatement
   * @throws SQLException then connection error?
   */
  private static PreparedStatement preparedStatementMessageRecive(
    String time,
    String channelID,
    int length,
    int bot,
    int roles,
    String guild
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        "INSERT INTO `messagerecive`(`time`, `channel_id`, `length`, `bot`, `roles`, `guild`) VALUES ('" +
        time +
        "','" +
        channelID +
        "','" +
        length +
        "','" +
        bot +
        "','" +
        roles +
        "','" +
        guild +
        "')"
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  /**
   * sets prepared statement for User-update event
   * @param time          time
   * @param bot           is bot?
   * @param roles         role count
   * @param guild         guild id
   * @param oldStatus     old Status
   * @param newStatus     new Status
   * @return              prepared statement
   * @throws SQLException then connection error?
   */
  private static PreparedStatement preparedStatementUserUpdate(
    String time,
    boolean bot,
    int roles,
    String guild,
    String oldStatus,
    String newStatus
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        String.format(
          "INSERT INTO `userupdate`(`time`, `bot`, `roles`, `guild`, `oldStatus`, `newStatus`) VALUES (`%s`,`%s`,`%s`,`%s`,`%s`,`%s`)",
          time,
          bot,
          roles,
          guild,
          oldStatus,
          newStatus
        )
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  /**
   * sets prepared statement for member change event
   * @param time          time
   * @param bot           is bot?
   * @param roles         role count
   * @param guild         guild id
   * @param event         event name
   * @return              prepared Statement
   * @throws SQLException then connection error?
   */
  private static PreparedStatement preparedStatementMemberChange(
    String time,
    boolean bot,
    int roles,
    String guild,
    String event
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        String.format(
          "INSERT INTO `memberchange`(`time`, `bot`, `roles`, `guild`, `event`) VALUES (`%s`,`%s`,`%s`,`%s`,`%s`)",
          time,
          bot,
          roles,
          guild,
          event
        )
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  /**
   * sets prepared statement for message react event
   * @param time          time
   * @param channelID     channel id
   * @param bot           is bot?
   * @param roles         role count
   * @param guild         guild id
   * @param emoji         emoji name
   * @return              prepared statement
   * @throws SQLException then connection error?
   */
  private static PreparedStatement preparedStatementMessageReact(
    String time,
    String channelID,
    boolean bot,
    int roles,
    String guild,
    String emoji
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        String.format(
          "INSERT INTO `messagereact`(`time`, `channel_id`, `bot`, `roles`, `guild`, `emoji`)  VALUES ('%s','%s','%s','%s','%s','%s')",
          time,
          channelID,
          bot,
          roles,
          guild,
          emoji
        )
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  private static PreparedStatement preparedStatementFeatureRequest(
    String time,
    String message,
    String user
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        "INSERT INTO `featurerequests`(`id`, `time`, `request`, `requested by`) VALUES (NULL,'" +
        time +
        "','" +
        message +
        "','" +
        user +
        "')"
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  private static PreparedStatement preparedStatementBirthday(
    String userID,
    String creationDate
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        "INSERT INTO `birthday`(`id`, `userID`, `accountCreateDate`) VALUES (NULL,'" +
        userID +
        "','" +
        creationDate +
        "')"
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  private static PreparedStatement preparedStatementTicket(
    String createUser,
    String status,
    String workingUser,
    String ticketMessage
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        "INSERT INTO `ticket`(`id`, `ticketCreateUser`, `status`, `ticketWorkingUser`, `ticketMessage`) VALUES (NULL,'" +
        createUser +
        "','" +
        status +
        "','" +
        workingUser +
        "','" +
        ticketMessage +
        "')"
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  private static PreparedStatement preparedStatementTicketHelper(
    Integer ticketId,
    String messageID
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        "INSERT INTO `tickethelper`(`ticketId`, `messageId`) VALUES ('" +
        ticketId +
        "','" +
        messageID +
        "')"
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  private static ResultSet getTicketHelper(String messageID)
    throws SQLException {
    connect();
    Statement statement = null;
    statement = connection.createStatement();
    String sql =
      ("SELECT * FROM `tickethelper` WHERE `messageId`='" + messageID + "'");
    assert statement != null;

    ResultSet resultSet = statement.executeQuery(sql);
    resultSet.next();
    return resultSet;
  }

  private static PreparedStatement preparedStatementFindTicket(
    String createUser
  ) throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        "SELECT * FROM `ticket` WHERE `ticketCreateUser` = '" + createUser + "'"
      );
    prepareStatement.execute();
    return prepareStatement;
  }

  private static ResultSet preparedStatementFindBirthday(String userID)
    throws SQLException {
    connect();
    Statement statement = null;
    statement = connection.createStatement();
    String sql = ("SELECT * FROM `birthday` WHERE `userID` = '" + userID + "'");
    assert statement != null;

    ResultSet resultSet = statement.executeQuery(sql);
    resultSet.next();
    return resultSet;
  }

  private static ResultSet getTicket(Integer id) throws SQLException {
    connect();
    Statement statement = null;
    statement = connection.createStatement();
    String sql = ("SELECT * FROM `ticket` WHERE `id` = '" + id + "'");
    assert statement != null;

    ResultSet resultSet = statement.executeQuery(sql);
    resultSet.next();
    return resultSet;
  }

  private static ResultSet getTicketIdMax() throws SQLException {
    connect();
    Statement statement = null;
    statement = connection.createStatement();
    String sql = ("select MAX(id) from ticket");
    assert statement != null;

    ResultSet resultSet = statement.executeQuery(sql);
    resultSet.next();
    return resultSet;
  }

  private static void updateTicket(
    String status,
    String workingUser,
    Integer ticketId
  ) throws SQLException {
    connect();
    Statement statement;
    statement = connection.createStatement();
    String sql =
      (
        "UPDATE `ticket` SET `status`='" +
        status +
        "',`ticketWorkingUser`='" +
        workingUser +
        "' WHERE `id`=" +
        ticketId
      );
    assert statement != null;
    statement.execute(sql);
  }

  /**
   * Deletes Data from Database
   * @param id        number of Element
   * @param table     table name
   * @return          a Statement of delete
   * @throws SQLException
   */
  static PreparedStatement deleteData(int id, String table)
    throws SQLException {
    connect();
    prepareStatement =
      connection.prepareStatement(
        "DELETE FROM " + table + " WHERE `id` = " + id + ")"
      );
    prepareStatement.execute(); //
    return prepareStatement;
  }

  /**
   * Helps to get data from DataBase
   * @param category              category in DataBase
   * @param table                 a mySQL table
   * @return                      a resultSet for usage
   * @throws SQLException         throws in Error
   */
  private static ResultSet getData(String category, String table)
    throws SQLException {
    connect();
    Statement statement = null;
    statement = connection.createStatement();
    String sql = ("SELECT " + category + " FROM `" + table + "`;");
    assert statement != null;

    ResultSet resultSet = statement.executeQuery(sql);
    resultSet.next();
    return resultSet;
  }

  private static ResultSet getBirthday(String userID) throws SQLException {
    connect();
    Statement statement = null;
    statement = connection.createStatement();
    String sql = ("SELECT * FROM `birthday` WHERE `userID` = '" + userID + "'");
    assert statement != null;

    ResultSet resultSet = statement.executeQuery(sql);
    resultSet.next();
    return resultSet;
  }

  /**
   * Class to get/set Data from Database
   */
  public static class MySQLUseDataManager {

    public static String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
      .format(Calendar.getInstance().getTime());

    public static String getInt(String category, String table)
      throws SQLException {
      return String.valueOf(getData(category, table).getInt(1));
    }

    public static String getString(String category, String table)
      throws SQLException {
      return getData(category, table).getString(1);
    }

    public static void setUserUpdate(
      String time,
      boolean bot,
      int roles,
      String guild,
      String oldStatus,
      String newStatus
    ) throws SQLException {
      SQLHandler.preparedStatementUserUpdate(
        time,
        bot,
        roles,
        guild,
        oldStatus,
        newStatus
      );
    }

    public static void setFeatureRequest(
      String time,
      String message,
      String user
    ) throws SQLException {
      SQLHandler.preparedStatementFeatureRequest(time, message, user);
    }

    public static void setMemberChange(
      String time,
      boolean bot,
      int roles,
      String guild,
      String event
    ) throws SQLException {
      SQLHandler.preparedStatementMemberChange(time, bot, roles, guild, event);
    }

    public static void setMessageReact(
      String time,
      String channelID,
      boolean bot,
      int roles,
      String guild,
      String emoji
    ) throws SQLException {
      SQLHandler.preparedStatementMessageReact(
        time,
        channelID,
        bot,
        roles,
        guild,
        emoji
      );
    }

    public static void setMessageReceive(
      String time,
      String channelID,
      int length,
      boolean bot,
      int roles,
      String guild
    ) throws SQLException {
      if (bot) {
        SQLHandler.preparedStatementMessageRecive(
          time,
          channelID,
          length,
          1,
          roles,
          guild
        );
      } else {
        SQLHandler.preparedStatementMessageRecive(
          time,
          channelID,
          length,
          0,
          roles,
          guild
        );
      }
    }

    public static void setBirthday(String userID, OffsetDateTime date)
      throws SQLException {
      SQLHandler.preparedStatementBirthday(userID, date.toString());
    }

    public static void setTicket(
      String createUser,
      String status,
      String workingUser,
      String ticketMessage
    ) throws SQLException {
      SQLHandler.preparedStatementTicket(
        createUser,
        status,
        workingUser,
        ticketMessage
      );
    }

    public static void setTicketHelper(Integer ticketID, String messageID)
      throws SQLException {
      SQLHandler.preparedStatementTicketHelper(ticketID, messageID);
    }

    public static Integer getTicketHelper(String messageID)
      throws SQLException {
      return SQLHandler.getTicketHelper(messageID).getInt(1);
    }

    public static ArrayList getTicket(Integer id) throws SQLException {
      ArrayList<String> test1 = new ArrayList<>();
      test1.add(String.valueOf(SQLHandler.getTicket(id).getObject(1)));
      test1.add(String.valueOf(SQLHandler.getTicket(id).getObject(2)));
      test1.add(String.valueOf(SQLHandler.getTicket(id).getObject(3)));
      test1.add(String.valueOf(SQLHandler.getTicket(id).getObject(4)));
      test1.add(String.valueOf(SQLHandler.getTicket(id).getObject(5)));
      return test1;
    }

    public static Integer getTicketIdMax() throws SQLException {
      return SQLHandler.getTicketIdMax().getInt(1);
    }

    public static void updateTicket(
      String status,
      String workingUser,
      Integer ticketId
    ) throws SQLException {
      SQLHandler.updateTicket(status, workingUser, ticketId);
    }

    public static Object getBirthday(String userID) throws SQLException {
      return SQLHandler.getBirthday(userID).getObject(3);
    }
  }
}
