package Database;

import Models.Appointment;
import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;


/** This class is the SQL connection to the appointments table that does the data CRUD stuff. */
public class DBAppointments {

    /** This method retrieves all Appointment data from the database.
     * @return Returns an ObservableList list of appointments.
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")
                );
                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /** This method retrieves a list of Appointments over the previous thirty days.
     * @return Returns an ObservableList list of appointments.
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */
    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * from appointments WHERE Start >=  (CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 MONTH AND Start < LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();


        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")

                );
                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /** This method retrieves a list of Appointments over the previous seven days.
     * @return Returns an ObservableList list of appointments.
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */
    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * from appointments WHERE YEARWEEK(`Start`, 1) = YEARWEEK(CURDATE(), 0);";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /** This method creates a new Appointment in the database with the information from the user input.
     * @param contactName String value of Appointment Contact Name
     * @param title String value of Appointment Title
     * @param description String value of Appointment Description
     * @param location String value of Appointment Location
     * @param type String value of Appointment Type
     * @param start LocalDateTime value of Appointment Start
     * @param end LocalDateTime value of Appointment End
     * @param customerId Int value of Customer ID
     * @param userID Int value of User ID
     * @return Returns Boolean true if the appointment was successfully created and false if not.
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */
    public static boolean createAppointment(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID) throws SQLException {

        Contact contact = DBContacts.getContactId(contactName);

        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, contact.getContactId());
        preparedStatement.setInt(9, userID);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change has occurred.");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /** This method deletes an Appointment by the specified Appointment ID.
     * @param appointmentId Int value of Appointment ID
     * @return Boolean Returns true if the appointment was successfully deleted and false if the appointment deletion failed
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */
    public static boolean deleteAppointment(int appointmentId) throws SQLException {
        String insertStatement = "DELETE from appointments WHERE Appointment_ID=?";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setInt(1, appointmentId);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change has occurred.");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /** This method updates an Appointment by the specified Appointment ID.
     * @param contactName String value of Appointment Contact Name
     * @param title String value of Appointment Title
     * @param description String value of Appointment Description
     * @param location String value of Appointment Location
     * @param type String value of Appointment Type
     * @param start LocalDateTime value of Appointment Start
     * @param end LocalDateTime value of Appointment End
     * @param customerId Int value of Customer ID
     * @param userID Int value of User ID
     * @param appointmentID Int value of Appointment ID
     * @return Boolean Returns true if the appointment was successfully updated and false if the appointment update failed
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */
    public static boolean updateAppointment(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID, Integer appointmentID) throws SQLException {
        Contact contact = DBContacts.getContactId(contactName);

        String updateStatement = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, Contact_ID=?, User_ID=? WHERE Appointment_ID = ?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, contact.getContactId());
        preparedStatement.setInt(9, userID);
        preparedStatement.setInt(10, appointmentID);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change has occurred.");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /** This method gets an Appointment by Customer ID using a join function.
     * @param CustomerID Int value of Customer ID
     * @return ObservableList List of appointments
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */
    public static ObservableList<Appointment> getAppointmentsByCustomerID(int CustomerID) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID=?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setInt(1, CustomerID);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    /** This method gets an Appointment by Appointment ID using a join function.
     * @param AppointmentID Int value of Appointment ID
     * @return Appointment newAppointment
     * @throws SQLException Catches SQLException, prints stacktrace, and error message for debugging. */
    public static Appointment getAppointmentByAppointmentID(int AppointmentID) throws SQLException {

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Appointment_ID=?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setInt(1, AppointmentID);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")
                );

                return newAppointment;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /** This method delivers a report specified by the type and by the month.
     * @return Report of the appointments by type and month */

     // Not the prettiest format. But, you work with what you've got.
    public static String reportAppointmentTypeMonth() {
        try {
            StringBuilder reportAppointmentPerTypeMonth = new StringBuilder("Month          |      Type         |           Total            ");
            reportAppointmentPerTypeMonth.append("\n");
            String sql = "SELECT MONTHNAME(start) as Month, Type, COUNT(*)  as Amount FROM appointments GROUP BY MONTH(start), type";

            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("Month");
                String type = resultSet.getString("Type");
                String amount = resultSet.getString("Amount");

                reportAppointmentPerTypeMonth.append(month + "\t\t\t" + type + "\t\t\t" + amount + "\n");
            }
            return reportAppointmentPerTypeMonth.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Try again";
        }
    }

    /** This method delivers a report specified by the Contact in the database.
     * @return Report that shows amount of schedules by contact */

     //Also did the best I could on the formatting.
    public static String reportAppointmentContact() {
        try {

            StringBuilder reportAppointmentEachContact = new StringBuilder("Contact ID | Appointment ID | Customer ID | Title | Type | Description | Start | End\n");
            String sql = "SELECT Contact_ID, Appointment_ID, Customer_ID, Title, Type, Description, Start, End FROM appointments ORDER BY Contact_ID ";

            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int contactID = resultSet.getInt("Contact_ID");
                int appointmentID = resultSet.getInt("Appointment_ID");
                int customerID = resultSet.getInt("Customer_ID");
                String title = resultSet.getString("Title");
                String type = resultSet.getString("Type");
                String description = resultSet.getString("Description");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();

                reportAppointmentEachContact.append("\n\n" + contactID + "\t" + appointmentID + "\t" + customerID + "\t" + title + "\t" + type + "\t" + description + "\t" + start + "\t" + end + "\n");
            }
            return reportAppointmentEachContact.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Try again";
        }

    }

    /** This method delivers a report specified by the Customer ID in the database.
     * @return Report of amount of types by customerID. */

    //This formatting turned out nice.

    public static String reportAppointmentCustomerId() {
        try {
            StringBuilder reportAppointmentPerTypeLocation = new StringBuilder("Customer ID     |     Total     |    Type   \n");
            reportAppointmentPerTypeLocation.append("\n");

            String sql = "SELECT Customer_ID, Type, COUNT(*)  as Amount FROM appointments GROUP BY Customer_ID, type";

            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String customerId = resultSet.getString("Customer_ID");
                String type = resultSet.getString("Type");
                String amount = resultSet.getString("Amount");
                reportAppointmentPerTypeLocation.append(customerId + "\t\t\t\t" + amount + "\t\t" + type + "\n");
            }
            return reportAppointmentPerTypeLocation.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Try again";
        }
    }


}