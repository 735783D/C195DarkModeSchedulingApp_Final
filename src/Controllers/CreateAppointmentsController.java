package Controllers;
import Database.DBAppointments;
import Database.DBContacts;
import Database.DBCustomers;
import Database.DBUsers;
import Models.Appointment;
import Models.Contact;
import Models.Customer;
import Models.User;
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

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/** Create Appointment Controller */
public class CreateAppointmentsController implements Initializable {

    private ZonedDateTime StartDateTimeConversion;
    private ZonedDateTime EndDateTimeConversion;
    @FXML
    private Button ButtonApptCancel;

    @FXML
    private Button ButtonApptMainMenu;

    @FXML
    private Button ButtonApptSave;

    @FXML
    private ComboBox<String > ComboContact;

    @FXML
    private ComboBox<Integer> ComboCustomerId;

    @FXML
    private ComboBox<String> ComboEndTime;

    @FXML
    private ComboBox<String> ComboStartTime;

    @FXML
    private ComboBox<String > ComboType;

    @FXML
    private ComboBox<Integer> ComboUserId;

    @FXML
    private Label ContactLabel;

    @FXML
    private DatePicker EndDatePicker;

    @FXML
    private Label LabelApptId;

    @FXML
    private Label LabelApptTitle;

    @FXML
    private Label LabelCustId;

    @FXML
    private Label LabelDesc;

    @FXML
    private Label LabelEndDate;

    @FXML
    private Label LabelEndTime;

    @FXML
    private Label LabelStartDate;

    @FXML
    private Label LabelStartTime;

    @FXML
    private Label LabelType;

    @FXML
    private Label LabelUserId;

    @FXML
    private DatePicker StartDatePicker;

    @FXML
    private TextField TextAppointmentId;

    @FXML
    private TextField TextDescription;

    @FXML
    private TextField TextLocation;

    @FXML
    private TextField TextTitle;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label TitleLocation;


    /** This method converts the time to EST.
     * @param time The input time that is to be converted to EST.
     * @return Returns ZonedDateTime to the EST. */
    private ZonedDateTime convertToEST(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    /** This method takes the user back the main menu to choose a different path to choose.
     * @param event This ActionEvent takes the user back to the main menu. */
    public void BackToMain(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Menu!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** This method takes the user back to the previous screen as well as alerts the user of the movement.
     * This method throws and error if there is something wrong with the navigation.
     * @param event This ActionEvent takes the user back to the previous screen, as well as alerts the user of the movement. */
    @FXML
     public void cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Navigate back to Appointments?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
                stage.setTitle("Customers!!");
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();
            }
        }
    }

    /** Populates Start Time and End Time defined Combo Boxes in increments of fifteen minutes. */
    private void populateTimeComboBoxes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }

        ComboStartTime.setItems(time);
        ComboEndTime.setItems(time);
    }

    /** Populates Contact defined Combo Box with Contacts list */
    private void populateContactComboBox() {
        ObservableList<String> contactComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Contact> contacts = DBContacts.getContacts();
            if (contacts != null){
                for (Contact contact: contacts) {
                    if (!contactComboList.contains(contact.getContactName())) {
                        contactComboList.add(contact.getContactName());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboContact.setItems(contactComboList);
    }

    /** Populates Customer ID defined Combo Box with Customer ID list */
    private void populateCustomerIDComboBox() {
        ObservableList<Integer> customerIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Customer> customers = DBCustomers.getCustomers();
            if (customers != null) {
                for (Customer customer: customers) {
                    customerIDComboList.add(customer.getCustomerId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboCustomerId.setItems(customerIDComboList);
    }

    /** Populates User ID defined Combo Box with User ID list. */
    private void populateUserIDComboBox() {
        ObservableList<Integer> userIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<User> users = DBUsers.getUsers();
            if (users != null) {
                for (User user: users) {
                    userIDComboList.add(user.getUserId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboUserId.setItems(userIDComboList);
    }

    /** Populates Type defined Combo Box with hardcoded list. */
    private void populateTypeComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        typeList.addAll("Planning Session", "De-Briefing", "Follow-up", "Pre-Briefing", "Open Session");

        ComboType.setItems(typeList);
    }

    /** This method saves the appointment the database.
     * It calls a function that validates that all the fields are filled in.
     * @param event This ActionEvent saves the appointment parameters that were selected to the database. */

    @FXML
    void Save(ActionEvent event) {
        boolean valid = validateAppointment(
                TextTitle.getText(),
                TextDescription.getText(),
                TextLocation.getText(),
                TextAppointmentId.getText()
        );

        if (valid) {
            try {
                boolean success = DBAppointments.createAppointment(
                        ComboContact.getSelectionModel().getSelectedItem(),
                        TextTitle.getText(),
                        TextDescription.getText(),
                        TextLocation.getText(),
                        ComboType.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(StartDatePicker.getValue(), LocalTime.parse(ComboStartTime.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(EndDatePicker.getValue(), LocalTime.parse(ComboEndTime.getSelectionModel().getSelectedItem())),
                        ComboCustomerId.getSelectionModel().getSelectedItem(),
                        ComboUserId.getSelectionModel().getSelectedItem());

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully created new appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
                            stage.setTitle("Appointments!!");
                            stage.setScene(new Scene(scene));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setContentText("Load Screen Error.");
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save new appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** This function is used to validate that all the parameters are in place to save the appointment.
     *  It will throw an alert up to the user if something is a miss in their selections with specifics.
     * @param title The String value of the title of the appointment as specified by the user.
     * @param description The String value of the description of the appointment as specified by the user.
     * @param location The String value of the location of the appointment as specified by the user
     * @param appointmentId This value is greyed out as it is auto-generated by the database.
     * @return A boolean is returned to be true if everything is valid and false if not. */
    private boolean validateAppointment(String title, String description, String location, String appointmentId){
        if (ComboContact.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Contact is required.");
            alert.showAndWait();
            return false;
        }

        if (title.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Title is required.");
            alert.showAndWait();
            return false;
        }

        if (description.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Description is required.");
            alert.showAndWait();
            return false;
        }

        if (location.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Location is required.");
            alert.showAndWait();
            return false;
        }

        if (ComboType.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Type is required.");
            alert.showAndWait();
            return false;
        }

        if (StartDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Start Date is required.");
            alert.showAndWait();
            return false;
        }

        if (ComboStartTime.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Start Time is required.");
            alert.showAndWait();
            return false;
        }

        if (EndDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Date is required.");
            alert.showAndWait();
            return false;
        }

        if (EndDatePicker.getValue().isBefore(StartDatePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Date must be after Start Date.");
            alert.showAndWait();
            return false;
        }

        if (ComboEndTime.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Time is required.");
            alert.showAndWait();
            return false;
        }

        if (ComboCustomerId.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Customer ID is required.");
            alert.showAndWait();
            return false;
        }

        if (ComboUserId.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("User ID is required.");
            alert.showAndWait();
            return false;
        }

        // This is an additional validation for the time and date to make sure it is valid.

        LocalTime startTime = LocalTime.parse(ComboStartTime.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse(ComboEndTime.getSelectionModel().getSelectedItem());

        if (endTime.isBefore(startTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("How do you start an appointment after it ends? Please try again.");
            alert.showAndWait();
            return false;
        };

        LocalDate startDate = StartDatePicker.getValue();
        LocalDate endDate = EndDatePicker.getValue();

        if (!startDate.equals(endDate)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("An appointments must start and end on the same date.");
            alert.showAndWait();
            return false;
        };

        // This is to check to make sure that appointments do not overlap.

        LocalDateTime selectedStart = startDate.atTime(startTime);
        LocalDateTime selectedEnd = endDate.atTime(endTime);

        LocalDateTime possibleAppointmentStart;
        LocalDateTime possibleAppointmentEnd;


        try {
            ObservableList<Appointment> appointments = DBAppointments.getAppointmentsByCustomerID(ComboCustomerId.getSelectionModel().getSelectedItem());
            for (Appointment appointment: appointments) {
                possibleAppointmentStart = appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
                possibleAppointmentEnd = appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());

                if (possibleAppointmentStart.isAfter(selectedStart) && possibleAppointmentStart.isBefore(selectedEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                } else if (possibleAppointmentEnd.isAfter(selectedStart) && possibleAppointmentEnd.isBefore(selectedEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // This checks to make sure that the appointment is between business hours.
        StartDateTimeConversion = convertToEST(LocalDateTime.of(StartDatePicker.getValue(), LocalTime.parse(ComboStartTime.getSelectionModel().getSelectedItem())));
        EndDateTimeConversion = convertToEST(LocalDateTime.of(EndDatePicker.getValue(), LocalTime.parse(ComboEndTime.getSelectionModel().getSelectedItem())));

        if (StartDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (StartDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    void PickStartDate(ActionEvent event) {}
    @FXML
    void PickEndDate(ActionEvent event) {}
    @FXML
    void SelectStartTime(ActionEvent event) {}
    @FXML
    void SelectEndTime(ActionEvent event) {}
    @FXML
    void SelectType(ActionEvent event) {}

    /**This method initializes the combo boxes in the window and allows them to be populated.
     * @param location This is the locator for relative paths for navigation.
     * @param resources This is the resource bundle that localizes the root objects. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTimeComboBoxes();
        populateContactComboBox();
        populateCustomerIDComboBox();
        populateUserIDComboBox();
        populateTypeComboBox();
    }
}
