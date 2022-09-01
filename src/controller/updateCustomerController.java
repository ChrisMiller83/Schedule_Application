package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import DAO.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {
    private static Customer selectedCustomer;

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIdTF.setText(Integer.toString(selectedCustomer.getCustomerId()));
        customerNameTF.setText(selectedCustomer.getCustomerName());
        addressTF.setText(selectedCustomer.getAddress());
        postalCodeTF.setText(selectedCustomer.getPostalCode());
        phoneNumTF.setText(selectedCustomer.getPhoneNumber());
        countryComboBox.getSelectionModel().select(selectedCountry);
        divisionComboBox.getSelectionModel().select(selectedDivision);

        setCountryComboBox();
        divisionComboBox.setDisable(true);
        UserDAO.loadAllUsers();

    }


    public void cancelToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customersView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customersView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
