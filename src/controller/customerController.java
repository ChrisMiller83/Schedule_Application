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
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
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
    @FXML private TableColumn<Customer, Integer> countryIdCol;
//
//    @FXML
//    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
//
////        customerToBeUpdated = customersTable.getSelectionModel().getSelectedItem();
////        if (customerToBeUpdated == null) {
////            Messages.selectACustomerToUpdate();
////            return;
////        }
////        selectedCustomer  = customersList.indexOf(customerToBeUpdated);
//
//        Parent root = FXMLLoader.load(getClass().getResource("/view/updateCustomerView.fxml"));
//        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

    @FXML
    public void toAddCustomer(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load((getClass().getResource("/view/addCustomerView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            Messages.selectACustomerToDelete();
            return;
        } else {
            // TODO: check if customer has any appointments

            int customerId = selectedCustomer.getCustomerId();
            boolean deleteConfirm = Messages.deleteConfirmation(selectedCustomer.getCustomerName());
            if(deleteConfirm) {
                CustomerDAO.deleteCustomer(customerId);
                customersTable.setItems(CustomerDAO.loadAllCustomers());
                customersTable.refresh();
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
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }


    @FXML
    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Messages.selectACustomerToUpdate();
            return;
        } else {
            updateCustomerController.getSelectedCustomer(customersTable.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/updateCustomerView.fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }
}
