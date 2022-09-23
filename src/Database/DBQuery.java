package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** This class contains a prepared statement object for SQL usage.*/
public class DBQuery {

    private static PreparedStatement statement;

    /** This method sets the Prepared Statement
     * @param connection Database Connection
     * @param sqlStatement SQL Statement string to be replaced in method calls.
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */

    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    /** This method returns the Prepared Statement object that was specified.
     * @return Prepared Statement */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
