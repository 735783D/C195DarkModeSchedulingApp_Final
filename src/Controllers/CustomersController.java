package Controllers;

import Database.DBCustomers;
import Models.Customer;
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
import java.util.Optional;
import java.util.ResourceBundle;

/** Customers Controller for Viewing and Manipulating Customers in the database. */
public class CustomersController implements Initializable {

    static ObservableList<Customer> customers;

    @FXML
    private Button CreateCustomer;

    @FXML
    private Button DeleteCustomer;

    @FXML
    private TextField CustSearchText;

    @FXML
    private Button MainMenu;

    @FXML
    private Button CustSearchButton;

    @FXML
    private Button UpdateCustomer;

    @FXML
    private TableColumn<Customer, String > CustomerAddress;

    @FXML
    private TableColumn<Customer, String > CustomerCountry;

    @FXML
    private TableColumn<Customer, String > CustomerFLD;

    @FXML
    private TableColumn<Customer, Integer> CustomerId;

    @FXML
    private TableColumn<Customer, String > CustomerName;

    @FXML
    private TableColumn<Customer, String> CustomerPhone;

    @FXML
    private TableColumn<Customer, String> CustomerPostalCode;

    @FXML
    private Label TitleCustomers;

    @FXML
    private TableView<Customer> Customers;

    /** Navigates to main menu of the application.
     *  Catches Exception, throws alert, and prints a stacktrace for debugging.
     * @param event ActionEvent navigates to Main Menu Screen when clicked
     * Lambda expression was created to combine view movement and alerting in one expression. */
    public void BackToMain(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert!");
        alert.setHeaderText("You sure?");
        alert.setContentText("You can always look more...");

        // Lambda exression
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

    /** Takes the user to the Customer creation screen when clicked.
     * Catches Exception, throws alert, and prints stacktrace to console for debugging.
     * @param event ActionEvent navigates user to Create Customer screen when Create Customer button is clicked. */
    public void CreateCustomers(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/CreateCustomers" +
                    ".fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create Customers!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Takes the user to the Customer update screen when clicked
     * If no customer is clicked, an error will pop up asking the user a question.
     * Catches Exception, throws alert, and prints stacktrace to console for debugging.
     * @param event ActionEvent navigates user to Customer creation screen when Update Customer button is clicked.
     */
    public void UpdateCustomers(ActionEvent event) {
        UpdateCustomersController.receiveSelectedCustomer(Customers.getSelectionModel().getSelectedItem());

        if (Customers.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/UpdateCustomers.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Customers!!");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();
            }
        } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText("Whom did you want to update?");
        alert.showAndWait();
    }

    }

    /** This method deletes the selected customer when clicked .
     * If no customer is clicked, an error will pop up asking the user a question.
     * Calls a validation function to verify Customer is allowed to be deleted by checking for current appointments.
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent deletes customer when clicked if all is correct and no appointments are present.
     */
    public void DeleteCustomer(ActionEvent event) {
        Customer selectedCustomer = Customers.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Whom did you want to delete?");
            alert.showAndWait();
        } else if (Customers.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected customer. Are you sure you want to do this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean deleteSuccessful = DBCustomers.deleteCustomer(Customers.getSelectionModel().getSelectedItem().getCustomerId());

                    if (deleteSuccessful) {
                        customers = DBCustomers.getCustomers();
                        Customers.setItems(customers);
                        Customers.refresh();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("Could not delete customer because they have an appointment.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**This method initializes the combo boxes in the window and allows them to be populated.
     * Catches Exception, throws alert, and prints stacktrace for debugging.
     * @param location This is the locator for relative paths for navigation.
     * @param resources This is the resource bundle that localizes the root objects. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            customers = DBCustomers.getCustomers();

            Customers.setItems(customers);
            CustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            CustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            CustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            CustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            CustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            CustomerFLD.setCellValueFactory(new PropertyValueFactory<>("division"));
            CustomerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
