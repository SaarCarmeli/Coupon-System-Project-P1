package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * Connection pool creates links from the program to MySQL database. Class is singleton.
 */
public class ConnectionPool {
    private static final int NUMBER_OF_CONNECTIONS = 10;
    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

    /**
     * Private constructor to construct new instance of ConnectionPool
     *
     * @throws SQLException Thrown if failed to open connections
     */
    private ConnectionPool() throws SQLException {
        System.out.println("Created new connection pool");
        openAllConnections();
    }

    /**
     * Static method for returning an instance of ConnectionPool.
     * Constructs a new ConnectionPool if not exists, else returns existing instance.
     *
     * @return ConnectionPool instance
     * @throws SQLException Thrown if failed to construct new ConnectionPool
     */
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * Opens all available Connections to MySQL, using DBManager determined SQL_URL, SQL_USER (user name) and SQL_PASS (password),
     * and pushes them into Stack collection. To be used at the beginning of the program.
     *
     * @throws SQLException Thrown if failed to get connection
     */
    private void openAllConnections() throws SQLException {
        for (int counter = 0; counter < NUMBER_OF_CONNECTIONS; counter++) {
            final Connection connection = DriverManager.getConnection(DBManager.SQL_URL, DBManager.SQL_USER, DBManager.SQL_PASS);
            connections.push(connection);
        }
    }

    /**
     * Closes all open Connections and empties Stack collection.
     *
     * @throws InterruptedException Thrown if waiting Connections are interrupted
     */
    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUMBER_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }

    /**
     * Returns a Connection from the Connection Stack, if any are available. To be used when exchanging data with MySQL.
     *
     * @return MySQL Connection
     * @throws InterruptedException Thrown if waiting for Connection is interrupted
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * Returns a Connection to the Connection Stack. To be used at the end of every use of getConnection() method in a "finally" block.
     *
     * @param connection MySQL Connection
     */
    public void returnConnection(final Connection connection) {
        synchronized (connections) {
            if (connection == null) {
                System.out.println("Attempt to return null connection terminated");
                return;
            }
            connections.push(connection);
            connections.notify();
        }
    }
}
