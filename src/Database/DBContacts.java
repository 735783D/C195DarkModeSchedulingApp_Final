package Database;

import Models.Contact;
import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is the SQL connection to the contacts table for data retrieval. */
public class DBContacts {

    /** This method retrieves a list of Contact information in the database.
     * @return Returns ObservableList list of Contacts
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */

    public static ObservableList<Contact> getContacts() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM contacts";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();;

            while (resultSet.next()) {
                Contact newContact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );
                contacts.add(newContact);
            }
            return contacts;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /** This method retrieves a Contact's information based off of the Contact Name
     * @param contactName String value of Contact Name
     * @return Contact Returns Contact
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */

    public static Contact getContactId(String contactName) throws SQLException {
        String queryStatement = "SELECT * FROM contacts WHERE Contact_Name=?";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, contactName);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();;

            // Forward scroll resultSet
            while (resultSet.next()) {
                Contact newContact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );
                return newContact;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}