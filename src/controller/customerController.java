package controller;

import DAO.CustomerDAO;
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
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

public class customerController implements Initializable {

    private static Customer customerToBeUpdated;
    private static int selectedCustomer;

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
    @FXML private TableColumn<Customer, String > createdByCol;
    @FXML private TableColumn<Customer, Timestamp> lastUpdateCol;
    @FXML private TableColumn<Customer, String > lastUpdatedByCol;
    @FXML private TableColumn<Customer, Integer> divisionIdCol;


    @FXML
    public void toAddCustomer(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "addCustomerView.fxml");
    }

    @FXML
    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Messages.selectAnItemToUpdate("Customer");
            return;
        } else {
            updateCustomerController.getSelectedCustomer(selectedCustomer);

            new ChangeView(actionEvent, "updateCustomerView.fxml");

        }
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            Messages.selectionNeeded();
            return;
        } else {
            // TODO: check if customer has any appointments

            int customerId = selectedCustomer.getCustomerId();
            boolean deleteConfirm = Messages.deleteConfirmation(selectedCustomer.getCustomerName());
            if(deleteConfirm) {
                System.out.println(selectedCustomer.getCustomerName() + " deleted");
                CustomerDAO.deleteCustomer(customerId);
                customersTable.setItems(CustomerDAO.loadAllCustomers());
                customersTable.refresh();
            } else {
                return;
            }
        }
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "mainPageView.fxml");
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
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }



}
