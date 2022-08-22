package utilities;

import DAO.AppointmentDAO;
import javafx.collections.ObservableList;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class ListDAO {

    public ListDAO() {

    }

    public static List<Appointment> appointments = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static List<Customer> customers = new ArrayList<>();
    public static List<Contact> contacts = new ArrayList<>();
    public static List<Country> country = new ArrayList<>();
    public static List<FirstLevelDivision> division = new ArrayList<>();


    public static List<Customer> getCustomers() {
        return customers;
    }

    public static List<Appointment> getAppointments() {
        return appointments;
    }

    public static List<Contact> getContacts() {
        return contacts;
    }

    public static List<Country> getCountry() {
        return country;
    }

    public static List<FirstLevelDivision> getDivision() {
        return division;
    }

    public static User currentUser;

    public static void setListDAO() {
        AppointmentDAO.loadAllAppointments();
        // add more list when DAO files are created
    }

    public static int getCountry(int index) {
        for (FirstLevelDivision division : ListDAO.division) {
            if (index == division.getDivisionId()) {
                return division.getCountryId();
            }
        }
        return 0;
    }

    // add report method


}
