package controller;

import DAO.CountryDAO;

import DAO.CustomerDAO;
import DAO.DivisionDAO;
import DAO.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.User;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
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


    @FXML void cancelToCustomer(ActionEvent event) throws IOException {
//        ChangeView.changeView(event, "customersView.fxml");
        Parent root = FXMLLoader.load(getClass().getResource("/view/customerView.fxml"));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveCustomer(ActionEvent event) throws IOException {
        if(validateCustomer()) {
            customerId = Customer.getUniqueCustomerId.getAndIncrement();
            customerName = customerNameTF.getText();
            address = addressTF.getText();
            postalCode = postalCodeTF.getText();
            phone = phoneNumTF.getText();
            createDate = Timestamp.valueOf(LocalDateTime.now());
            createdBy = User.currentUser.getUserName();
            lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            lastUpdatedBy = User.currentUser.getUserName();
            divisionId = divisionComboBox.getValue().getDivisionId();

            try {
                CustomerDAO.addCustomer(customerId, customerName, address, postalCode, phone, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, divisionId);

                Messages.addConfirmation(customerName);

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        Parent root = FXMLLoader.load((getClass().getResource("/view/customerView.fxml")));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox();
        divisionComboBox.setDisable(true);
        UserDAO.loadAllUsers();
    }

    private void setCountryComboBox() {
        ObservableList<Country> countryObservableList = FXCollections.observableList(CountryDAO.loadAllCountries());
        countryComboBox.setItems(countryObservableList);
    }


    @FXML
    public void selectDivision(ActionEvent event) {
        Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        int country_ID = selectedCountry.getCountryId();
        divisionComboBox.setDisable(false);
        divisionComboBox.setItems(DivisionDAO.getDivisionsByCountry(country_ID));
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


}
