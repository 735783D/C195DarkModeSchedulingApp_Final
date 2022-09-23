package Controllers;

import Database.DBAppointments;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller to display and do the CRUD functions on the appointments. */
public class AppointmentsController implements Initializable {

    /** This is the observable list that shows the appointments. */
    static ObservableList<Appointment> appointments;
    @FXML
    private RadioButton AllTimesDisplay;
    @FXML
    private TableView<Appointment> Appointments;

    @FXML
    private Label AppointmentsTitle;

    @FXML
    private Button Cancel;

    @FXML
    private TableColumn<Appointment, Integer> ColumnAppointmentID;

    @FXML
    private TableColumn<Appointment, Integer> ColumnContact;

    @FXML
    private TableColumn<Appointment, Integer> ColumnCustomerID;

    @FXML
    private TableColumn<Appointment, String> ColumnDescription;

    @FXML
    private TableColumn<Appointment, LocalDateTime > ColumnEnd;

    @FXML
    private TableColumn<Appointment, String > ColumnLocation;

    @FXML
    private TableColumn<Appointment, LocalDateTime> ColumnStart;

    @FXML
    private TableColumn<Appointment, String> ColumnTitle;

    @FXML
    private TableColumn<Appointment, String> ColumnType;

    @FXML
    private TableColumn<Appointment, Integer> ColumnUserID;

    @FXML
    private Button CreateAppointment;

    @FXML
    private Button DeleteAppointment;

    @FXML
    private Label Displays;

    @FXML
    private RadioButton MonthDisplay;

    @FXML
    private Button UpdateAppointment;

    @FXML
    private RadioButton WeekDisplay;

    @FXML
    private ToggleGroup ToggleView;


    /** This is the action event handler that takes you back to the main menu.
     * @param event ActionEvent that takes the user back to the main menu.
     * Lambda expression was created to combine view movement and alerting in one expression.
     */
    public void BackToMain(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert!");
        alert.setHeaderText("You sure?");
        alert.setContentText("You can always look more...");

        // Lambda expression
        alert.showAndWait().ifPresent((response -> {
            if (response == ButtonType.OK) {
                System.out.println("Okay. Have fun!");
                Parent main = null;
                try {
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setTitle("Main");
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));
    }

    /** This is the action event handler that takes you to the create appointment menu.
     * @param event ActionEvent that takes the user back to the appointment creation menu.
     */
    public void CreateAppointmentMenu(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/CreateAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointments!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** This is the action event handler that takes you to the update appointment menu.
     * @param event ActionEvent that takes the user back to the ap menu.
     */
    public void UpdateAppointmentMenu(ActionEvent event){
        UpdateAppointmentController.receiveSelectedAppointment(Appointments.getSelectionModel().getSelectedItem());

        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/UpdateAppointment.fxml"));
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

    /**
     * This handler connects to the delete button and allows for the deletion of
     * an appointment when it is selected in the display.
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent deletes appointment when clicked
     */
    @FXML
    void DeleteAppointment(ActionEvent event) {
        Appointment selectedAppointment = Appointments.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select an appointment to delete.");
            alert.showAndWait();
        } else if (Appointments.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected appointment. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean deleteSuccessful = DBAppointments.deleteAppointment(Appointments.getSelectionModel().getSelectedItem().getAppointmentId());

                    if (deleteSuccessful) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Delete");
                        alert.setContentText("Successfully deleted Appointment ID: " + selectedAppointment.getAppointmentId() + " Type: " + selectedAppointment.getType());
                        alert.showAndWait();

                        appointments = DBAppointments.getAppointments();
                        Appointments.setItems(appointments);
                        Appointments.refresh();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("Could not delete appointment.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** Toggle View - All, Week, or Month.
     * The radio buttons dictate the display type.
     * Catches SQL exceptions and prints stacktrace for debugging.
     * @param event ActionEvent updates Appointment Table when Radio Button is selected
     */
    @FXML
    void ViewToggle(ActionEvent event) {

        if (AllTimesDisplay.isSelected()) {
            try {
                appointments = DBAppointments.getAppointments();
                Appointments.setItems(appointments);
                Appointments.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ToggleView.getSelectedToggle().equals(MonthDisplay)) {
            try {
                appointments = DBAppointments.getAppointmentsByMonth();
                Appointments.setItems(appointments);
                Appointments.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ToggleView.getSelectedToggle().equals(WeekDisplay)) {
            try {
                appointments = DBAppointments.getAppointmentsByWeek();
                Appointments.setItems(appointments);
                Appointments.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**This method is the initializer for the Appointments view.
     * It catches a SQL exception and prints it to the console.
     * @param location The location to resolve the relative paths.
     * @param resources The resources that will localize the initialization.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AllTimesDisplay.setToggleGroup(ToggleView);
        WeekDisplay.setToggleGroup(ToggleView);
        MonthDisplay.setToggleGroup(ToggleView);
        try {
            appointments = DBAppointments.getAppointments();

            Appointments.setItems(appointments);
            ColumnAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            ColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            ColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            ColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            ColumnContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            ColumnStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            ColumnEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            ColumnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            ColumnUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
