package controller;

import DAO.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

public class customerController implements Initializable {

    private static Customer selectedCustomer;

    static ObservableList<Customer> customersList;


    @FXML private Button addCustomerBtn;
    @FXML private Button updateCustomerBtn;
    @FXML private Button deleteCustomerBtn;
    @FXML private Button mainMenuBtn;
    @FXML private TableView<Customer> customersTable;
    @FXML private TableColumn<Customer, Integer> customerIdCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> postalCodeCol;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, Timestamp> createDateCol;
    @FXML private TableColumn<Customer, String> createdByCol;
    @FXML private TableColumn<Customer, Timestamp> lastUpdateCol;
    @FXML private TableColumn<Customer, String> lastUpdatedByCol;
    @FXML private TableColumn<Customer, Integer> countryIdCol;

    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load((getClass().getResource("/view/updateCustomerView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toAddCustomer(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/addCustomerView.fxml"))));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) {

        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            Messages.selectACustomerToDelete();
            return;
        } else {
            boolean deleteConfirm = Messages.deleteConfirmation(selectedCustomer.getCustomerName());
            if(deleteConfirm) {
                CustomerDAO.deleteCustomer(selectedCustomer.getCustomerId());
                Customer.deleteCustomer(selectedCustomer);
            }
        }
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load((getClass().getResource("/view/mainPageView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCustomersTable();



    }




    public void setCustomersTable() {
        customersList = CustomerDAO.loadAllCustomers();
        customersTable.setItems(customersList);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }


}
