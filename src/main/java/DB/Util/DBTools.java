package DB.Util;

import DB.ConnectionPool;

import java.sql.*;
import java.util.Map;

public class DBTools {
    /**
     * Generic method for accepting SQL script and executing query
     *
     * @param sql SQL script for execution
     * @return true if successful, false if failed
     * @throws SQLException Thrown if SQL statement not executed
     */
    public static boolean runQuery(String sql) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            return true;
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * Generic method for accepting SQL script, parameters and executing query
     *
     * @param sql    SQL script for execution
     * @param params parameter input for SQL script
     * @return true if successful, false if failed
     * @throws SQLException Thrown if SQL statement not executed
     */
    public static boolean runQuery(String sql, Map<Integer, Object> params) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            params.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (Integer) value);
                    } else if (value instanceof String) {
                        statement.setString(key, String.valueOf(value));
                    } else if (value instanceof Date) {
                        statement.setDate(key, (Date) value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (Double) value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (Float) value);
                    } else if (value instanceof Long) {
                        statement.setLong(key, (Long) value);
                    } else if (value instanceof Byte) {
                        statement.setByte(key, (Byte) value);
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (Boolean) value);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
            statement.execute();
            return true;
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }


    /**
     * Generic method for accepting SQL script, parameters and retrieving ResultSet
     *
     * @param sql    SQL script for execution
     * @param params parameter input for SQL script
     * @return ResultSet if successful, else null
     * @throws SQLException Thrown if SQL statement not executed
     */
    public static ResultSet runQueryForResult(String sql, Map<Integer, Object> params) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            params.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (Integer) value);
                    } else if (value instanceof String) {
                        statement.setString(key, String.valueOf(value));
                    } else if (value instanceof Date) {
                        statement.setDate(key, (Date) value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (Double) value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (Float) value);
                    } else if (value instanceof Long) {
                        statement.setLong(key, (Long) value);
                    } else if (value instanceof Byte) {
                        statement.setByte(key, (Byte) value);
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (Boolean) value);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
            return statement.executeQuery();
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
