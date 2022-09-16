package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.DivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.*;
import utilities.ChangeView;
import utilities.Messages;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * UpdateCustomerController -- updates customers in the db.
 */
public class UpdateCustomerController implements Initializable {
    private static Customer selectedCustomer;

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId;
    private int divisionId;
    private Country selectedCountry;
    private Division selectedDivision;

    @FXML private TextField customerIdTF;
    @FXML private TextField customerNameTF;
    @FXML private TextField addressTF;
    @FXML private TextField postalCodeTF;
    @FXML private TextField phoneNumTF;
    @FXML private ComboBox<Country> countryComboBox;
    @FXML private ComboBox<Division> divisionComboBox;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;


    /**
     * setSelectedCustomer -- populates all the data fields from the selected customer that was
     * selected in the CustomerController page.
     * @param selectedCustomer -- customer selected to update data.
     */
    public void setSelectedCustomer(Customer selectedCustomer) {

        customerIdTF.setText(Integer.toString(selectedCustomer.getCustomerId()));
        customerNameTF.setText(selectedCustomer.getCustomerName());
        addressTF.setText(selectedCustomer.getAddress());
        postalCodeTF.setText(selectedCustomer.getPostalCode());
        phoneNumTF.setText(selectedCustomer.getPhoneNumber());
        /** loop through the countries list in the db to find the matching countryId for the selected
         * country and displays the selected country name.
         */
        for (Country country : CountryDAO.loadAllCountries()) {
            if(country.getCountryId() == selectedCustomer.getCountryId()) {
                countryComboBox.setValue(country);
            }
        }
        /** gets the country Id from the selected country and loop through the division list in the db
         * to find the matching divisionId for the selected country/division and displays the selected division name.
         */
        Country selectedCountry = countryComboBox.getValue();
        ObservableList<Division> DivisionsList = DivisionDAO.getDivisions(selectedCountry);
        divisionComboBox.setItems(DivisionsList);
        for (Division division : DivisionsList) {
            if(division.getDivisionId() == selectedCustomer.getDivisionId()) {
                divisionComboBox.setValue(division);
            }
        }
    }

    /**
     * getSelectedCustomer -- gets the data from the selected customer from the CustomerController
     * @param customer -- selected customer from the CustomerController
     */
    public static void getSelectedCustomer(Customer customer) {
        selectedCustomer = customer;
    }


    /**
     * validateCustomer:  Is called in updateCustomer, it checks for empty fields and combo boxes
     * @return -- false if one of the checks are found, otherwise returns true and allows updateCustomer to continue
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
     * cancelToCustomer -- changes view to CustomerView
     * @param actionEvent -- cancel button clicked
     * @throws IOException
     */
    public void cancelToCustomers(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "CustomerView.fxml");
    }

    /**
     * updateCustomer  -- calls validateCustomer method to run validation checks,
     * if validation checks pass, all info from the fields and combo boxes are gathered
     * and customer is updated in the db, otherwise error messages are given.
     * @param actionEvent -- save button clicked
     * @throws IOException
     */
    public void updateCustomer(ActionEvent actionEvent) throws IOException {
        /** validateCustomer method called to check for errors */
        if(validateCustomer()) {
            /** if no errors found, gather data from fields */
            customerName = customerNameTF.getText();
            address = addressTF.getText();
            postalCode = postalCodeTF.getText();
            phone = phoneNumTF.getText();
            lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            lastUpdatedBy = User.currentUser.getUserName();
            countryId = countryComboBox.getValue().getCountryId();
            divisionId = divisionComboBox.getValue().getDivisionId();
            customerId = Integer.parseInt(customerIdTF.getText());
            /** Confirmation message to update customer */
            boolean updateConfirm = Messages.updateConfirmation(customerNameTF.getText());
            /** If confirmation was ok/yes customer is updated in the db. */
            if (updateConfirm) {
                CustomerDAO.updateCustomer(customerName, address, postalCode, phone, lastUpdate, lastUpdatedBy, divisionId, customerId);

                /** Lambda expression -- console message verifying update */
                MessageLambdaInterface message = s -> System.out.println(s + " updated.");
                message.displayMessage(customerName);

            } else {
                /** If confirmation was no/cancel, returns to updateContactView with current update data in the fields */
                return;
            }
            /** ChangeView brings the user back to the CustomerView page when a customer is updated in the db. */
            new ChangeView(actionEvent, "CustomerView.fxml");
        } else {
            /** validateAppt found an error, error message displayed, and user is returned to updateCustomer page. */
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
    public void setDivisionComboBox(ActionEvent event) {
        Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        divisionComboBox.setItems(DivisionDAO.getDivisions(selectedCountry));
    }

    /**
     * initialize -- loads combo boxes when page is loaded, set selected customer data in appropriate fields
     * @param url -- not used
     * @param resourceBundle -- not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox();
        setSelectedCustomer(selectedCustomer);

    }

}


