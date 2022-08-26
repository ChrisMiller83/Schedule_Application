package controller;

import DAO.CountryDAO;
import DAO.DivisionDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Messages.*;

public class addCustomerController implements Initializable {

    private String name;
    private String phone;
    private String address;
    private String city;
    private String zip;
    private String state;
    private String fullAddress;
    private Country selectedCountry;
    private FirstLevelDivision selectedDivision;


    @FXML private TextField customerIdTF;
    @FXML private TextField customerNameTF;
    @FXML private TextField phoneNumTF;
    @FXML private TextField addressTF;
    @FXML private TextField cityTF;
    @FXML private TextField zipCodeTF;
    @FXML private TextField stateTF;
    @FXML private ComboBox<CountryDAO> countryComboBox;
    @FXML private ComboBox<DivisionDAO> stateProvinceComboBox;
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
            phoneNumTF.getText(),
            addressTF.getText(),
            cityTF.getText(),
            zipCodeTF.getText(),
            countryComboBox.getSelectionModel().getSelectedItem(),
            stateProvinceComboBox.getSelectionModel().getSelectedItem());
        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || city.isEmpty() || zip.isEmpty() || selectedCountry == null || selectedDivision == null) {
            emptyField();
            return;
        }

        if (zip.length() > 5) {
            errorWindow(2);
            return;

        }
//            ChangeView.changeView(event, "customersView.fxml");
        Parent root = FXMLLoader.load((getClass().getResource("/view/customersView.fxml")));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setPromptText("Select a country");
        countryComboBox.setItems(countryComboBox.getItems());
        stateProvinceComboBox.setPromptText("Select a country first");
        stateProvinceComboBox.setItems(null);
    }


}
