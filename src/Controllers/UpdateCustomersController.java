package Controllers;

import Database.DBCountry;
import Database.DBCustomers;
import Database.DBDivision;
import Models.Country;
import Models.Customer;
import Models.Division;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCustomersController implements Initializable {
    private static Customer selectedCustomer;

    @FXML
    private Label AddressLabel;

    @FXML
    private Button homeButton;


    @FXML
    private Button Cancel;

    @FXML
    private ComboBox<String> CountryBox;

    @FXML
    private Label CreateCustomerLabel;

    @FXML
    private TextField CustomerAddress;

    @FXML
    private TextField CustomerID;

    @FXML
    private TextField CustomerName;

    @FXML
    private TextField CustomerPhone;

    @FXML
    private TextField CustomerPostalCode;

    @FXML
    private ComboBox<String> DivisionBox;

    @FXML
    private Label IdLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PhoneLabel;

    @FXML
    private Label PostalCodeLabel;

    @FXML
    private Button Save;



    /** Takes the user back to the Main Menu screen.
     * Catches exception if there is a problem navigating back to the previous screen.
     * @param event ActionEvent navigates to Main Menu screen when clicked
     */
    @FXML
    void home(ActionEvent event) {
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

    /** Cancels customer update and takes user back to Customers display screen.
     * Catches exception if there is a problem navigating back to the previous screen.
     * @param event ActionEvent Navigates to Customer View when cancel button is clicked
     */
    @FXML
    void cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Navigate back to Customers?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
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

    /** This method updates the selected Customer with new paramters as per the user.
     * It calls a validation method to check for paramter errors.
     * Catches Exception, throws alert, and prints stacktrace for debugging.
     * @param event ActionEvent updates Customer if valid when the Save button is clicked
     */
    @FXML
    void Save(ActionEvent event) throws SQLException {
        boolean valid = validateNotEmpty(
                CustomerName.getText(),
                CustomerAddress.getText(),
                CustomerPostalCode.getText(),
                CustomerPhone.getText());

        if (valid) {
            try {
                boolean success = DBCustomers.updateCustomer(
                        Integer.parseInt(CustomerID.getText()),
                        CustomerName.getText(),
                        CustomerAddress.getText(),
                        CustomerPostalCode.getText(),
                        CustomerPhone.getText(),
                        DivisionBox.getValue(),
                        CountryBox.getValue());

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully updated customer");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
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
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update customer");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** Populates Country Combo Box */
    private void setCountryCombo(){
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            ObservableList<Country> countries = DBCountry.getCountries();;
            if (countries != null) {
                for (Country country: countries) {
                    countryList.add(country.getCountry());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CountryBox.setItems(countryList);
    }

    /** Populates Division Combo Box */
    private void setDivisionCombo(){
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        try {
            ObservableList<Division> divisions = DBDivision.getDivisions();;
            if (divisions != null) {
                for (Division division: divisions) {
                    divisionList.add(division.getDivision());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DivisionBox.setItems(divisionList);
    }

    /** Queries database for list of countries and populates the division combo box off of the result.
     * Catches Exception, throws alert, and prints stacktrace for debugging.
     * @param event ActionEvent selects country */
    @FXML
    void SelectCountry(ActionEvent event) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisions = DBDivision.getDivisionsByCountry(CountryBox.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (Division division: divisions) {
                    divisionList.add(division.getDivision());
                }
            }
            DivisionBox.setItems(divisionList);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void SelectDivision(ActionEvent event) {}

    /** Method to help validate input fields are selected and not empty
     * Throws alert if fields are not selected or are empty
     * @param name String value of Customer Name as per user input.
     * @param address String value of Customer Address as per user input.
     * @param postalCode String value of Customer Postal Code as per user input.
     * @param phone String value of Customer Phone Number as per user input.
     * @return Returns a Boolean true if valid and false if not. */
    private boolean validateNotEmpty(String name, String address, String postalCode, String phone){
        if (name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Name is required.");
            alert.showAndWait();
            return false;
        }

        if (address.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Address is required.");
            alert.showAndWait();
            return false;
        }

        if (postalCode.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Postal Code is required.");
            alert.showAndWait();
            return false;
        }

        if (phone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Phone Number is required.");
            alert.showAndWait();
            return false;
        }

        if (DivisionBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Division is required.");
            alert.showAndWait();
            return false;
        }

        if (CountryBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Country is required.");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    /** This method receives the selected customer from the Customer screen.
     * @param customer Selected Customer */
    public static void receiveSelectedCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    /** This method pulls the info from the Customers screen to the Update and initializes the combo boxes in the Update Customers screen.
     * Catches Exception, throws alert, and prints stacktrace for debugging.
     * @param location This is the locator for relative paths for navigation.
     * @param resources This is the resource bundle that localizes the root objects. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDivisionCombo();
        setCountryCombo();

        CustomerID.setText(Integer.toString(selectedCustomer.getCustomerId()));
        CustomerName.setText(selectedCustomer.getCustomerName());
        CustomerPostalCode.setText(selectedCustomer.getPostalCode());
        CustomerAddress.setText(selectedCustomer.getAddress());
        CustomerPhone.setText(selectedCustomer.getPhoneNumber());
        CountryBox.getSelectionModel().select(selectedCustomer.getCountry());
        DivisionBox.getSelectionModel().select(selectedCustomer.getDivision());
    }
}
