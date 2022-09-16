package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.AppointmentDAO;
import dao.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;
import model.MessageLambdaInterface;
import utilities.ChangeView;
import utilities.Messages;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * CustomerController -- Displays a table of customers,  allows customers to be deleted, and redirects to add/update customer page
 */
public class CustomerController implements Initializable {

    static ObservableList<Customer> customersList = CustomerDAO.loadAllCustomers();
    static ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();
    private static Customer selectedCustomer;

    @FXML private Button addCustomerBtn;
    @FXML private Button updateCustomerBtn;
    @FXML private Button deleteCustomerBtn;
    @FXML private Button mainMenuBtn;

    /** customers table components */
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


    /**
     * toAddCustomer -- changes the view to AddCustomerView
     * @param actionEvent -- Add Customer button clicked
     * @throws IOException
     */
    public void toAddCustomer(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "AddCustomerView.fxml");
    }

    /**
     * toUpdateCustomer -- user selects a customer to update and changes the view to UpdateCustomerView
     * @param actionEvent -- customer to update is selected and then Update Customer button is clicked.
     * @throws IOException
     */
    public void toUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        /** if no customer is selected an error message is displayed */
        if (selectedCustomer == null) {
            Messages.selectAnItemToUpdate("Customer");
            return;
        } else {
            /** the selected customer's data is sent to the update customer controller. */
            UpdateCustomerController.getSelectedCustomer(selectedCustomer);
            /** changes the view to the UpdateCustomerView */
            new ChangeView(actionEvent, "UpdateCustomerView.fxml");
        }
    }

    /**
     * noAppointments -- checks if customer to delete has upcoming appointments.
     * @return -- Returns false if customer has upcoming appointments, returns true if customer does not have any appts scheduled.
     */
    private boolean noAppointments() {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        int customerId = selectedCustomer.getCustomerId();
        ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

        /** loops through the appointment db looking for appointments with the customer id, if an appt is found an
         *  error message is displayed telling the user they must delete the appt before they can delete the customer
         */
        for(Appointment appointment : appointments) {
            if(appointment.getCustomerId() == customerId) {
                Messages.hasAppointments(selectedCustomer.getCustomerName());
                return false;
            }
        }
        return true;
    }

    /**
     * deleteCustomer -- deletes selected customer if customer does not have upcoming appointments.
     * @param actionEvent -- customer to be deleted is selected and Delete Customer button is clicked.
     */
    public void deleteCustomer(ActionEvent actionEvent) {
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        /** Displays error message if a customer to delete is not selected. */
        if(selectedCustomer == null) {
            Messages.selectionNeeded();
            return;
        } else {
            /** Checks if customer has an appts by calling the noAppointments method */
            if(noAppointments()) {
                /** if customer did not have any upcoming appts, a delete confirmation is displayed */
                int customerId = selectedCustomer.getCustomerId();


                boolean deleteConfirm = Messages.deleteConfirmation(selectedCustomer.getCustomerName());
                /** if delete is confirmed, delete contact, display console message confirming delete */
                if (deleteConfirm) {
                    // TODO: remove the noAppointments method and method call if auto delete appts is required or delete auto-delete for loop
//                    /** Automatically deletes all appointments from the db that have the selectedCustomer's customerId */
//                    for (Appointment appointment : appointments) {
//                        if(appointment.getCustomerId() == customerId) {
//                            AppointmentDAO.deleteAllCustomerAppts(customerId);
//                        }
//                    }
                    /** customer is deleted from the db */
                    CustomerDAO.deleteCustomer(customerId);

                    /** Lambda expression -- console message verifying delete */
                    MessageLambdaInterface message = s -> System.out.println(s + " deleted.");
                    message.displayMessage(selectedCustomer.getCustomerName());

                    /** the customer table is reloaded and the deleted customer is removed from the table display */
                    customersTable.setItems(CustomerDAO.loadAllCustomers());
                    customersTable.refresh();
                }
            }
        }
    }

    /**
     * toMainMenu -- changes the view to the Main Page View
     * @param actionEvent -- Main Menu button clicked
     * @throws IOException
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "MainPageView.fxml");
    }

    /**
     * setCustomerTable -- populates customer data into appropriate columns in the customer table
     */
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

    /**
     * initialize -- calls the setCustomersTable method to load the customer table when page is loaded
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCustomersTable();
    }
}
