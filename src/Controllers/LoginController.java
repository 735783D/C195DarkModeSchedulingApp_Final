package Controllers;

import Database.DBAppointments;
import Database.DBUsers;
import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

interface A {
    public String getFileName();
}

/** Controller for login screen
 *  Lambda Expression in this section was created to pass information to a log file for attempts passing or failing.
 *  This allows for better re-usability and reduces the amount of code in application. */

public class LoginController implements Initializable {

    // Lambda expression
    A loginAttempts = () -> {
        return "login_activity.txt";
    };

    private ResourceBundle resourceBundle;

    @FXML
    private Button ButtonCancel;

    @FXML
    private Button ButtonLogin;

    @FXML
    private Label LabelLocation;

    @FXML
    private Label LabelLocationDisplay;

    @FXML
    private Label LabelPassword;

    @FXML
    private Label LabelTimeZone;

    @FXML
    private Label LabelTimeZoneDisplay;

    @FXML
    private Label LabelTitle;

    @FXML
    private Label LabelUsername;

    @FXML
    private TextField TextPassword;

    @FXML
    private TextField TextUsername;

    /** This method creates a file named login_activity.txt file if it doesn't already exist.
     *  Catches Exception and prints stacktrace for debugging.
     *  Retrieves file name value from the Lambda Expression at the top of the file. */
    private void createLogDataFile(){
        try {
            File newfile = new File(loginAttempts.getFileName());
            if (newfile.createNewFile()) {
                System.out.println("Log created:" + newfile.getName());
            } else {
                System.out.println("Log currently exists. Log location: "+ newfile.getPath());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /** This method enables the user to login
     *  Calls assistance methods to create a log activity text file if one isn't present and validates that the login was either successful or not.
     *  Catches Exception, throws alert, and prints stacktrace to the console for debugging.
     * @param event ActionEvent Logs into application when login button is clicked if credentials are in the database. */
    public void  loginButton(ActionEvent event) throws SQLException {

        if (TextUsername.getText().isBlank() == false && TextPassword.getText().isBlank() == false) {}


        String username = TextUsername.getText();
        String password = TextPassword.getText();
        createLogDataFile();

        boolean validLogin = DBUsers.checkUsernamePassword(username, password);
        if(validLogin) {
            successfulLogin();

            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Main Menu!!");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("errorDescription"));
                alert.setContentText(resourceBundle.getString("viewLoadError"));
                alert.showAndWait();
            }

        }  else {

        failedLoginAttempt();

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("errorDescription"));
            alert.setContentText(resourceBundle.getString("wrongUsernamePassword"));
            alert.showAndWait();
        }
    }


    /** This method alerts the user if there is an appointment within 15 minutes or if they are in the clear.
     * Catches Exception, throws alert, and prints a stacktrace to the console for debugging. */

    }
    private void appointmentAlert(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime addFifteen = localDateTime.plusMinutes(15);

        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();


        try {
            ObservableList<Appointment> appointments = DBAppointments.getAppointments();

            if (appointments != null) {
                for (Appointment appointment: appointments) {
                    if (appointment.getStartTime().isAfter(localDateTime) && appointment.getStartTime().isBefore(addFifteen)) {
                        upcomingAppointments.add(appointment);

                        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(resourceBundle.getString("alertForAppointment"));
                            alert.setContentText(
                                    resourceBundle.getString("panicMode") +
                                            "\n" +
                                            resourceBundle.getString("appointmentId") +
                                            " " +
                                            + appointment.getAppointmentId() +
                                            "\n" +
                                            resourceBundle.getString("date") +
                                            " " +
                                            appointment.getStartDate() +
                                            "\n" +
                                            resourceBundle.getString("time") +
                                            " " +
                                            appointment.getStartTime().toLocalTime());
                            alert.setResizable(true);
                            alert.showAndWait();
                        }
                    }
                }

                if (upcomingAppointments.size() < 1) {
                    if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(resourceBundle.getString("alertForAppointment"));
                        alert.setContentText(resourceBundle.getString("inTheClear"));
                        alert.setResizable(true);
                        alert.showAndWait();
                    }
                }
            } else {
                if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(resourceBundle.getString("alertForAppointment"));
                    alert.setContentText(resourceBundle.getString("inTheClear"));
                    alert.setResizable(true);
                    alert.showAndWait();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Assistance function to write successful logins to the login_activity.txt file
     *  Catches IOException, throws alert, and prints a stacktrace to the console for debugging.
     *  Pulls file name value from a Lambda expression. */
    private void successfulLogin() throws SQLException {

       appointmentAlert();

        try {
            FileWriter fileWriter = new FileWriter(loginAttempts.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Successful Login: Username=" + TextUsername.getText() + " Password=" + TextPassword.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Assistance function to write failed logins to the login_activity.txt file
     *  Catches IOException, throws alert, and prints a stacktrace to the console for debugging.
     *  Pulls file name value from a Lambda expression. */
    private void failedLoginAttempt() {
        try {
            FileWriter fileWriter = new FileWriter(loginAttempts.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Failed Login: Username=" + TextUsername.getText() + " Password=" + TextPassword.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Asks the user if they really want to close the application when clicked.
     *  Catches IOException, throws alert, and prints a stacktrace to the console for debugging.
     * @param event ActionEvent prompts the user to close the application by dialog box with some questions. */
    public void cancelButton(ActionEvent event){
            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                // CONFIRMATION is same in French and English
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("confirmLogout"));
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    stage.close();
                }
            }
        }

    /**This method initializes the login screen.
     *  Gets region and converts to French if location is in French Canada.
     * @param location This is the locator for relative paths for navigation.
     * @param resources This is the resource bundle that localizes the root objects. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resourceBundle = ResourceBundle.getBundle("Language/language", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){

            LabelTitle.setText(resourceBundle.getString("title"));
            LabelUsername.setText(resourceBundle.getString("username"));
            LabelPassword.setText(resourceBundle.getString("password"));
            LabelLocation.setText(resourceBundle.getString("location"));
            LabelLocationDisplay.setText(resourceBundle.getString("country"));
            LabelTimeZone.setText(resourceBundle.getString("timezone"));
            LabelTimeZoneDisplay.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            ButtonLogin.setText(resourceBundle.getString("login"));
            ButtonCancel.setText(resourceBundle.getString("cancel"));

        }
    }
}
