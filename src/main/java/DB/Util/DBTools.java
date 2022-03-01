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
     */
    public static boolean runQuery(String sql) {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            return true;
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            assert connectionPool != null;
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * Generic method for accepting SQL script, parameters and executing query
     *
     * @param sql    SQL script for execution
     * @param params parameter input for SQL script
     * @return true if successful, false if failed
     */
    public static boolean runQuery(String sql, Map<Integer, Object> params) {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
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
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            assert connectionPool != null;
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * Generic method for accepting SQL script, parameters and retrieving ResultSet
     *
     * @param sql    SQL script for execution
     * @param params parameter input for SQL script
     * @return ResultSet if successful, else null
     */
    public static ResultSet runQueryForResult(String sql, Map<Integer, Object> params) {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
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
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            assert connectionPool != null;
            connectionPool.returnConnection(connection);
        }
    }
}
