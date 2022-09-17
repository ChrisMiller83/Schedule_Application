package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.MessageLambdaInterface;
import model.User;
import utilities.ChangeView;
import utilities.Messages;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * AddCustomerController -- adds customers to the db.
 */
public class AddCustomerController implements Initializable {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private Country selectedCountry;
    private Division selectedDivision;

    @FXML private TextField customerIdTF;
    @FXML private TextField customerNameTF;
    @FXML private TextField phoneNumTF;
    @FXML private TextField addressTF;
    @FXML private TextField postalCodeTF;
    @FXML private ComboBox<Country> countryComboBox;
    @FXML private ComboBox<Division> divisionComboBox;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;


    /**
     * cancelToCustomer -- changes view to CustomerView
     * @param actionEvent -- cancel button clicked
     * @throws IOException
     */
    public void cancelToCustomer(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "CustomerView.fxml");
    }

    /**
     * saveCustomer  -- calls validateCustomer method to run validation checks,
     * if validation checks pass, all info from the fields and combo boxes are gathered
     * and customer is added to the db, otherwise error messages are given
     * -- A Lambda expression is used for a console message verifying customer add.
     * @param actionEvent -- save button clicked
     * @throws IOException
     */
    public void saveCustomer(ActionEvent actionEvent) throws IOException {
        /** validateCustomer method called to check for errors */
        if(validateCustomer()) {
            /** if no errors found, gather data from fields */
            customerName = customerNameTF.getText();
            address = addressTF.getText();
            postalCode = postalCodeTF.getText();
            phone = phoneNumTF.getText();
            createDate = Timestamp.valueOf(LocalDateTime.now());
            createdBy = User.currentUser.getUserName();
            lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            lastUpdatedBy = User.currentUser.getUserName();
            divisionId = divisionComboBox.getValue().getDivisionId();
            /** Confirmation message to add customer */
            boolean addConfirm = Messages.addConfirmation(customerName);
            /** If confirmation was ok/yes customer is added to the db. */
            if (addConfirm) {
                CustomerDAO.addCustomer(customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);

                /** Lambda expression -- console message verifying add */
                MessageLambdaInterface message = s -> System.out.println(s + " added.");
                message.displayMessage(customerName);

            } else {
                /** If confirmation was no/cancel, returns to addContactView with current add data in the fields */
                return;
            }
            /** ChangeView brings the user back to the CustomerView page when a customer is added to the db. */
            new ChangeView(actionEvent, "CustomerView.fxml");
        } else {
            /** validateAppt found an error, error message displayed, and user is returned to addCustomer page. */
            return;
        }
    }

    /**
     * setCountryComboBox -- loads all the counties in the db for selection.
     */
    private void setCountryComboBox() {
        ObservableList<Country> countryObservableList = FXCollections.observableList(CountryDAO.loadAllCountries());
        countryComboBox.setItems(countryObservableList);
    }

    /**
     * setDivisionComboBox -- loads all the divisions(states/provinces)
     * once a country is selected from the country combo box
     * @param event -- country is selected from the country combo box
     */
    @FXML
    public void setDivisionComboBox(ActionEvent event) {
        Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        divisionComboBox.setDisable(false);
        divisionComboBox.setItems(DivisionDAO.getDivisions(selectedCountry));
    }

    /**
     * validateCustomer:  Is called in saveCustomer, it checks for empty fields and combo boxes
     * @return -- false if one of the checks are found, otherwise returns true and allows saveCustomer to continue
     */
    private boolean validateCustomer() {
        /** Checker:  customer name text field is empty */
        if (customerNameTF.getText().isEmpty()) {
            Messages.validateCustomerError(1);
            return false;
        }
        /** Checker:  address text field is empty */
        if(addressTF.getText().isEmpty()) {
            Messages.validateCustomerError(2);
            return false;
        }
        /** Checker:  postal code text field is empty */
        if(postalCodeTF.getText().isEmpty()) {
            Messages.validateCustomerError(3);
            return false;
        }
        /** Checker:  phone number text field is empty */
        if (phoneNumTF.getText().isEmpty()) {
            Messages.validateCustomerError(4);
            return false;
        }
        /** Checker: country combo box is empty */
        if(countryComboBox.getValue() == null) {
            Messages.validateCustomerError(5);
            return false;
        }
        /** Checker:  division combo box is empty */
        if (divisionComboBox.getValue() == null) {
            Messages.validateCustomerError(6);
            return false;
        }
        return true;
    }


    /**
     * initialize -- loads combo boxes when page is loaded
     * @param url -- not used
     * @param resourceBundle -- not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox();
        divisionComboBox.setDisable(true);
        UserDAO.loadAllUsers();
    }


}
