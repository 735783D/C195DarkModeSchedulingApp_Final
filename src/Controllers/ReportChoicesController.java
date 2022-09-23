package Controllers;

import Database.DBAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Reports screen Controller. */
public class ReportChoicesController implements Initializable {

    @FXML
    private RadioButton TMReportButton;

    @FXML
    private RadioButton ContactReportButton;

    @FXML
    private RadioButton CustomerIdButton;

    @FXML
    private ToggleGroup viewReportTG;

    @FXML
    private Button GenerateButton;

    @FXML
    private Button ResetButton;

    @FXML
    private Button CancelButton;

    @FXML
    private TextArea ReportTextArea;
    
    /** This onAction generates the reports from the selection chosen.
     * @param event ActionEvent that generates the report from the selected choice. */
    @FXML
    void GenerateReport(ActionEvent event) {
        if (TMReportButton.isSelected()) {
            ReportTextArea.setText(DBAppointments.reportAppointmentTypeMonth());
        }
        if (ContactReportButton.isSelected()) {
            ReportTextArea.setText(DBAppointments.reportAppointmentContact());
        }
        if (CustomerIdButton.isSelected()) {
            ReportTextArea.setText(DBAppointments.reportAppointmentCustomerId());
        }
    }

    /** These actions are the event handlers for the reports by their specificity.
     * @param event ActionEvent that generates the report from the selected choice. */

    @FXML
    void ReportByTypeAndMonth(ActionEvent event) {}
    @FXML
    void ByContactReportId(ActionEvent event) {}
    @FXML
    void ByCustomerId(ActionEvent event) {}

    /** This onAction resets the text field when the Reset button is clicked.
     * @param event ActionEvent that resets the display pane when clicked. */
    @FXML
    void OnActionResetTextField(ActionEvent event) {
        ReportTextArea.clear();
    }

    /** This onAction takes you back to the Main Menu when the Cancel button is clicked
     * @param event ActionEvent that takes the user back to the main menu when clicked.
     * @throws IOException the io exception if something bad happens .
     * This method contains a lambda expression combines an alert with a screen change into one setup. */
    @FXML
    void CancelOut(ActionEvent event) {

        //Lambda expression
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert!");
        alert.setHeaderText("You sure?");
        alert.setContentText("You can always look more...");

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

    /**This method initializes reports functionality.
     * Catches Exception, throws alert, and prints stacktrace for debugging.
     * @param location This is the locator for relative paths for navigation.
     * @param resources This is the resource bundle that localizes the root objects. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}
