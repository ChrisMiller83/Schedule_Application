package controller;

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
import model.User;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

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


    @FXML void cancelToCustomer(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "CustomerView.fxml");
    }

    public void saveCustomer(ActionEvent actionEvent) throws IOException {
        if(validateCustomer()) {
            customerName = customerNameTF.getText();
            address = addressTF.getText();
            postalCode = postalCodeTF.getText();
            phone = phoneNumTF.getText();
            createDate = Timestamp.valueOf(LocalDateTime.now());
            createdBy = User.currentUser.getUserName();
            lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            lastUpdatedBy = User.currentUser.getUserName();
            divisionId = divisionComboBox.getValue().getDivisionId();

            boolean addConfirm = Messages.addConfirmation(customerName);
            if (addConfirm) {
                CustomerDAO.addCustomer(customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                System.out.println(customerName + " added");
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
        divisionComboBox.setDisable(false);
        divisionComboBox.setItems(DivisionDAO.getDivisions(selectedCountry));
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

        if(countryComboBox.getSelectionModel().isEmpty()) {
            Messages.validateCustomerError(5);
            return false;
        }

        if (divisionComboBox.getSelectionModel().isEmpty()) {
            Messages.validateCustomerError(6);
            return false;
        }

        if (postalCodeTF.getText().length() != 5) {
            Messages.validateCustomerError(7);
            return false;
        }

        return true;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox();
        divisionComboBox.setDisable(true);
        UserDAO.loadAllUsers();
    }


}
