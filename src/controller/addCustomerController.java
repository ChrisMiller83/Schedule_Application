package controller;

import DAO.CustomerDAO;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

    private String name;
    private String phone;
    private String address;
    private String city;
    private String zip;
    private String state;
    private String fullAddress;
    private Country selectedCountry;
    private Division selectedDivision;


    @FXML private TextField customerIdTF;
    @FXML private TextField customerNameTF;
    @FXML private TextField phoneNumTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private TextField postalCodeTF;
    @FXML private TextField stateTF;
    @FXML private ComboBox<Division> countryComboBox;
    @FXML private ComboBox<Country> stateProvinceComboBox;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;


    @FXML void cancelToCustomer(ActionEvent event) throws IOException {
//        ChangeView.changeView(event, "customersView.fxml");
        Parent root = FXMLLoader.load((getClass().getResource("/view/customersView.fxml")));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveCustomer(ActionEvent event) throws IOException {
        Customer customer = new Customer(
            Integer.parseInt(customerIdTF.getText()),
            customerNameTF.getText(),
            addressTF.getText(),
            postalCodeTF.getText(),
            phoneNumTF.getText(),
            cityTF.getText(),
            countryComboBox.getValue().getDivisionId(),
            stateProvinceComboBox.getValue().getCountryId()
        );

        CustomerDAO.addCustomer(customer);


        Parent root = FXMLLoader.load((getClass().getResource("/view/customersView.fxml")));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox();
        setStateProvinceComboBox();
    }

    private void setCountryComboBox() {
        ObservableList<Division> divisionObservableList = FXCollections.observableArrayList(Division.divisionArrayList);
        countryComboBox.setItems(divisionObservableList);
    }

    private void setStateProvinceComboBox() {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList(Country.countryArrayList);
        stateProvinceComboBox.setItems(countryObservableList);
    }

    private void selectDivision(ActionEvent event) {
        ObservableList<Division> countryPicked = FXCollections.observableArrayList();
        for (Division division : Division.getDivisions()) {
            if(countryComboBox.getValue().getCountryId() == division.getCountryId()) {
                countryPicked.add(division);
            }
        }
        countryComboBox.setItems(countryPicked);
    }


}
