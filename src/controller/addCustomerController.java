package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

    private String name;
    private String phone;
    private String address;
    private String city;
    private String zip;
    private String fullAddress;
    private Country selectedCountry;
    private Division selectedDivision;


    @FXML private TextField customerIdTF;
    @FXML private TextField customerNameTF;
    @FXML private TextField phoneNumTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private TextField postalCodeTF;
    @FXML private ComboBox<String> countryComboBox;
    @FXML private ComboBox<String> divisionComboBox;
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
//        Customer customer = new Customer(
//            Integer.parseInt(customerIdTF.getText()),
//            customerNameTF.getText(),
//            addressTF.getText(),
//            postalCodeTF.getText(),
//            phoneNumTF.getText(),
//            divisionComboBox.getSelectionModel().getSelectedItem()
//        );

//        CustomerDAO.addCustomer(customer);


        Parent root = FXMLLoader.load((getClass().getResource("/view/customerView.fxml")));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox();
        setDivisionComboBox();
    }

    private void setCountryComboBox() {
        ObservableList<String> countryList = FXCollections.observableArrayList();
        ObservableList<Country> countries = CountryDAO.loadAllCountries();
        if (countries != null) {
            for (Country country : countries) {
                countryList.add(country.getCountry());
            }
        }
        countryComboBox.setItems(countryList);
    }

    private void setDivisionComboBox() {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        ObservableList<Division> divisions = DivisionDAO.loadAllDivisions();
        if (divisions != null) {
             for (Division division : divisions) {
                 divisionList.add(division.getDivision());
             }
        }
        divisionComboBox.setItems(divisionList);
    }

    @FXML
    void selectDivision(ActionEvent event) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        ObservableList<Division> divisions = DivisionDAO.getDivisionsByCountry(countryComboBox.getSelectionModel().getSelectedItem());
        if(divisions != null) {
            for (Division division : divisions) {
                divisionList.add(division.getDivision());
            }
        }
        divisionComboBox.setItems(divisionList);

    }


}
