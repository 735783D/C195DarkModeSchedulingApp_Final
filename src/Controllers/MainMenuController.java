package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/** Main Menu Controller - This is where the journey begins! */
public class MainMenuController implements  Initializable{

    @FXML
    private Button ButtonAppointments;

    @FXML
    private Button ButtonCustomers;

    @FXML
    private Button ButtonLogout;

    @FXML
    private Button ButtonReports;

    @FXML
    private Label LabelMenuTitle;

    /** Takes the user to the Customers screen.
     *  Catches Exception, throws alert, and prints a stacktrace to the console for debugging.
     * @param event ActionEvent takes user to Customers Screen when clicked. */
    @FXML
    void CustomersMenu(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customers!!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Takes the user to the Appointments screen.
     *  Catches Exception, throws alert, and prints a stacktrace to the console for debugging.
     * @param event ActionEvent takes user to Appointments Screen when clicked. */
    @FXML
    void AppointmentsMenu(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointments!!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Takes the user to the Reports screen.
     *  Catches Exception, throws alert, and prints a stacktrace to the console for debugging.
     * @param event ActionEvent takes user to Reports Screen when clicked. */
    @FXML
    void ReportsMenu(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/ReportChoices.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Reports!!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }


    /** This method logs the user out when the Logout button is clicked.
     *  Catches Exception, throws alert, and prints a stacktrace to the console for debugging.
     * @param event ActionEvent Logs user out of application when logout button is clicked. */
    @FXML
    void Logout(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /**This method initializes reports functionality.
     * Catches Exception, throws alert, and prints stacktrace for debugging.
     * @param location This is the locator for relative paths for navigation.
     * @param resources This is the resource bundle that localizes the root objects. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}
