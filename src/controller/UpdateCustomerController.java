package controller;

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



    public void setSelectedCustomer(Customer selectedCustomer) {

        customerIdTF.setText(Integer.toString(selectedCustomer.getCustomerId()));
        customerNameTF.setText(selectedCustomer.getCustomerName());
        addressTF.setText(selectedCustomer.getAddress());
        postalCodeTF.setText(selectedCustomer.getPostalCode());
        phoneNumTF.setText(selectedCustomer.getPhoneNumber());

        for (Country country : CountryDAO.loadAllCountries()) {
            if(country.getCountryId() == selectedCustomer.getCountryId()) {
                System.out.println(selectedCustomer.getCountryId() + " " + country.getCountryId());
                countryComboBox.setValue(country);
                break;
            }
        }

        Country selectedCountry = countryComboBox.getValue();
        ObservableList<Division> DivisionsList = DivisionDAO.getDivisions(selectedCountry);
        divisionComboBox.setItems(DivisionsList);
        for (Division division : DivisionsList) {
            if(division.getDivisionId() == selectedCustomer.getDivisionId()) {
                divisionComboBox.setValue(division);
                break;
            }
        }
    }

    public static void getSelectedCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox();
        setSelectedCustomer(selectedCustomer);

    }

    private boolean validateCustomer() {
        if (customerNameTF.getText().isEmpty()) {
            Messages.validateCustomerError(1);
            return false;
        }
        if(addressTF.getText().isEmpty()) {
            Messages.validateCustomerError(2);
            return false;
        }
        if(postalCodeTF.getText().isEmpty()) {
            Messages.validateCustomerError(3);
            return false;
        }
        if (phoneNumTF.getText().isEmpty()) {
            Messages.validateCustomerError(4);
            return false;
        }
        if(countryComboBox.getValue() == null) {
            Messages.validateCustomerError(5);
            return false;
        }
        if (divisionComboBox.getValue() == null) {
            Messages.validateCustomerError(6);
            return false;
        }

        return true;
    }


    public void cancelToCustomers(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "CustomerView.fxml");
    }

    public void updateCustomer(ActionEvent actionEvent) throws IOException {
        if(validateCustomer()) {

            customerName = customerNameTF.getText();
            address = addressTF.getText();
            postalCode = postalCodeTF.getText();
            phone = phoneNumTF.getText();
            lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            lastUpdatedBy = User.currentUser.getUserName();
            countryId = countryComboBox.getValue().getCountryId();
            divisionId = divisionComboBox.getValue().getDivisionId();
            customerId = Integer.parseInt(customerIdTF.getText());

            boolean updateConfirm = Messages.updateConfirmation(customerNameTF.getText());
            if (updateConfirm) {
                System.out.println(customerName + " updated");
                CustomerDAO.updateCustomer(customerName, address, postalCode, phone, lastUpdate, lastUpdatedBy, divisionId, customerId);
            } else {
                return;
            }
        new ChangeView(actionEvent, "CustomerView.fxml");
        } else {
            return;
        }
    }

    private void setCountryComboBox() {
        ObservableList<Country> countryObservableList = FXCollections.observableList(CountryDAO.loadAllCountries());
        countryComboBox.setItems(countryObservableList);
    }


    @FXML
    public void setDivisionComboBox(ActionEvent event) {
        Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        divisionComboBox.setItems(DivisionDAO.getDivisions(selectedCountry));
    }

}


