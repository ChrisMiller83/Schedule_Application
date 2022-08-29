package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    public static final String DB_NAME = "schedule.db";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost/client_schedule/" + DB_NAME;

    /**
     * Instantiates a new appointment
     */
    public AppointmentDAO() {
    }

    /**
     * CONSTANTS used to prevent SQL injection into the appointments table
     */
    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COLUMN_APPT_ID = "Appointment_ID";
    public static final String COLUMN_APPT_TITLE = "Title";
    public static final String COLUMN_APPT_DESCRIPTION = "Description";
    public static final String COLUMN_APPT_LOCATION = "Location";
    public static final String COLUMN_APPT_TYPE = "Type";
    public static final String COLUMN_APPT_START = "Start";
    public static final String COLUMN_APPT_END = "End";
    public static final String COLUMN_APPT_CREATED_DATE = "Create_Date";
    public static final String COLUMN_APPT_CREATED_BY = "Created_By";
    public static final String COLUMN_APPT_LAST_UPDATE = "Last_Update";
    public static final String COLUMN_APPT_LAST_UPDATE_BY = "Last_Updated_By";
    public static final String COLUMN_CUSTOMER_ID = "Customer_ID";
    public static final String COLUMN_USER_ID = "User_ID";
    public static final String COLUMN_CONTACT_ID = "Contact_ID";
    public static final int INDEX_APPT_ID = 1;
    public static final int INDEX_APPT_TITLE = 2;
    public static final int INDEX_APPT_DESCRIPTION = 3;
    public static final int INDEX_APPT_LOCATION = 4;
    public static final int INDEX_APPT_TYPE = 5;
    public static final int INDEX_APPT_START = 6;
    public static final int INDEX_APPT_END = 7;
    public static final int INDEX_APPT_CREATED_DATE = 8;
    public static final int INDEX_APPT_CREATED_BY = 9;
    public static final int INDEX_APPT_LAST_UPDATE = 10;
    public static final int INDEX_APPT_LAST_UPDATED_BY = 11;
    public static final int INDEX_APPT_CUSTOMER_ID = 12;
    public static final int INDEX_APPT_USER_ID = 13;
    public static final int INDEX_APPT_CONTACT_ID = 14;

    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_CUSTOMERS = "customers";

    public static final String TABLE_APPTS_VIEW = "appointments_list";

//    public static final String CREATE_APPTS_VIEW = "CREATE VIEW IF NOT EXISTS " +
//            TABLE_APPTS_VIEW + " AS SELECT " +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_ID + ", " +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_TITLE + ", " +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_DESCRIPTION + ", " +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_LOCATION + ", " +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_TYPE + ", " +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_START + ", " +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_END + ", " +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_CREATED_DATE +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_CREATED_BY +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_LAST_UPDATE +
//            TABLE_APPOINTMENTS + "." + COLUMN_APPT_LAST_UPDATE_BY +
//            TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_ID +
//            TABLE_USERS + "." + COLUMN_USER_ID +
//            TABLE_CONTACTS + "." + COLUMN_CONTACT_ID +
//            " AS " + TABLE_APPOINTMENTS + " FROM " + TABLE_APPOINTMENTS +
//            " INNER JOIN " + TABLE_CUSTOMERS + " ON " + TABLE_CUSTOMERS + "."


    public static final String QUERY_ALL_APPOINTMENTS = "SELECT * FROM " + TABLE_APPOINTMENTS;

    public static final String QUERY_APPOINTMENTS_BY_WEEK_OR_MONTH = "SELECT * FROM " + TABLE_APPOINTMENTS +
            " WHERE " + COLUMN_APPT_START + " < ? AND " + COLUMN_APPT_START + " > ? ";

    public static final String ADD_NEW_APPOINTMENT = "INSERT INTO " + TABLE_APPOINTMENTS + " (" +
            COLUMN_APPT_ID + ", " + COLUMN_APPT_TITLE + ", " + COLUMN_APPT_LOCATION + ", " +
            COLUMN_APPT_TYPE + ", " + COLUMN_APPT_START + ", " + COLUMN_APPT_END + ", " +
            COLUMN_APPT_CREATED_DATE + ", " + COLUMN_APPT_CREATED_BY + ", " + COLUMN_APPT_LAST_UPDATE +
            ", " + COLUMN_APPT_LAST_UPDATE_BY + ", " + COLUMN_CUSTOMER_ID + ", "  + COLUMN_USER_ID +
            ", " + COLUMN_CONTACT_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_AN_APPOINTMENT = "UPDATE " + TABLE_APPOINTMENTS + "SET (" + COLUMN_APPT_TITLE +
            " = ?, " + COLUMN_APPT_DESCRIPTION + " = ?, " + COLUMN_APPT_LOCATION + " = ?, " + COLUMN_APPT_TYPE +
            " = ?, " + COLUMN_APPT_START + " = ?, " + COLUMN_APPT_END +" = ?, " + COLUMN_APPT_LAST_UPDATE +
            " = ?, " + COLUMN_APPT_LAST_UPDATE_BY + " = ?, " + COLUMN_CUSTOMER_ID + " = ?, " + COLUMN_USER_ID +
            " = ?, " + COLUMN_CONTACT_ID + " = ?) ";

    public static final String DELETE_AN_APPOINTMENT = "DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_APPT_ID + " = ?";

    public static ObservableList<Appointment> getAllAppts() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try (PreparedStatement loadAppts = DBConnection.getConnection().prepareStatement(QUERY_ALL_APPOINTMENTS);
            ResultSet result = loadAppts.executeQuery()) {
            while (result.next()) {
                Appointment newAppt = new Appointment(
                    result.getInt("Appointment_ID"),
                    result.getString("Title"),
                    result.getString("Description"),
                    result.getString("Location"),
                    result.getString("Type"),
                    result.getTimestamp("Start"),
                    result.getTimestamp("End"),
                    result.getTimestamp("Create_Date"),
                    result.getString("Created_By"),
                    result.getTimestamp("Last_Update"),
                    result.getString("Last_Updated_By"),
                    result.getInt("Customer_ID"),
                    result.getInt("User_ID"),
                    result.getInt("Contact_ID")
                );

                appointments.add(newAppt);

            }
            return appointments;
            // add report for loaded appointments
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Appointment> getApptsThisWeek() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextWeek = today.plusDays(7);

        PreparedStatement getWeeksAppts = DBConnection.getConnection().prepareStatement(QUERY_APPOINTMENTS_BY_WEEK_OR_MONTH);
        getWeeksAppts.setDate(1, java.sql.Date.valueOf(today.toLocalDate()));
        getWeeksAppts.setDate(2, java.sql.Date.valueOf(nextWeek.toLocalDate()));

        try {
            ResultSet result = getWeeksAppts.executeQuery();
            while (result.next()) {
                Appointment newAppt = new Appointment(
                        result.getInt("Appointment_ID"),
                        result.getString("Title"),
                        result.getString("Description"),
                        result.getString("Location"),
                        result.getString("Type"),
                        result.getTimestamp("Start"),
                        result.getTimestamp("End"),
                        result.getTimestamp("Create_Date"),
                        result.getString("Created_By"),
                        result.getTimestamp("Last_Update"),
                        result.getString("Last_Updated_By"),
                        result.getInt("Customer_ID"),
                        result.getInt("User_ID"),
                        result.getInt("Contact_ID")
                );

                appointments.add(newAppt);
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Appointment> getApptsThisMonth() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextMonth = today.plusDays(30);

        PreparedStatement getWeeksAppts = DBConnection.getConnection().prepareStatement(QUERY_APPOINTMENTS_BY_WEEK_OR_MONTH);
        getWeeksAppts.setDate(1, java.sql.Date.valueOf(today.toLocalDate()));
        getWeeksAppts.setDate(2, java.sql.Date.valueOf(nextMonth.toLocalDate()));

        try {
            ResultSet result = getWeeksAppts.executeQuery();
            while (result.next()) {
                Appointment newAppt = new Appointment(
                        result.getInt("Appointment_ID"),
                        result.getString("Title"),
                        result.getString("Description"),
                        result.getString("Location"),
                        result.getString("Type"),
                        result.getTimestamp("Start"),
                        result.getTimestamp("End"),
                        result.getTimestamp("Create_Date"),
                        result.getString("Created_By"),
                        result.getTimestamp("Last_Update"),
                        result.getString("Last_Updated_By"),
                        result.getInt("Customer_ID"),
                        result.getInt("User_ID"),
                        result.getInt("Contact_ID")
                );

                appointments.add(newAppt);
            }
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addAppointment(int apptId, String title, String description, String location, String type, Timestamp startDateTime,
                                         Timestamp endDateTime, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdated,
                                         String lastUpdatedBy, int customerId, int userId, int contactId) {



            try {
               PreparedStatement addAppointments = DBConnection.getConnection().prepareStatement(ADD_NEW_APPOINTMENT);
               addAppointments.setInt(INDEX_APPT_ID, apptId);
               addAppointments.setString(INDEX_APPT_TITLE, title);
               addAppointments.setString(INDEX_APPT_DESCRIPTION, description);
               addAppointments.setString(INDEX_APPT_LOCATION, location);
               addAppointments.setString(INDEX_APPT_TYPE, type);
               addAppointments.setTimestamp(INDEX_APPT_START, startDateTime);
               addAppointments.setTimestamp(INDEX_APPT_END, endDateTime);
               addAppointments.setTimestamp(INDEX_APPT_CREATED_DATE, Timestamp.valueOf(createdDate));
               addAppointments.setString(INDEX_APPT_CREATED_BY, createdBy);
               addAppointments.setTimestamp(INDEX_APPT_LAST_UPDATE, Timestamp.valueOf(lastUpdated));
               addAppointments.setString(INDEX_APPT_LAST_UPDATED_BY, lastUpdatedBy);
               addAppointments.setInt(INDEX_APPT_CUSTOMER_ID, customerId);
               addAppointments.setInt(INDEX_APPT_USER_ID, userId);
               addAppointments.setInt(INDEX_APPT_CONTACT_ID, contactId);

               ResultSet result = addAppointments.executeQuery();

               return true;

               // TODO: add report statement

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    public static void updateAppointment(Appointment appointment) {
        try {
            PreparedStatement updateAppointments = DBConnection.getConnection().prepareStatement(UPDATE_AN_APPOINTMENT);

            updateAppointments.setString(INDEX_APPT_TITLE, appointment.getApptTitle());
            updateAppointments.setString(INDEX_APPT_DESCRIPTION, appointment.getApptDescription());
            updateAppointments.setString(INDEX_APPT_LOCATION, appointment.getApptLocation());
            updateAppointments.setString(INDEX_APPT_TYPE, appointment.getApptType());
            updateAppointments.setTimestamp(INDEX_APPT_START, appointment.getStartDateTime());
            updateAppointments.setTimestamp(INDEX_APPT_END, appointment.getEndDateTime());
            updateAppointments.setTimestamp(INDEX_APPT_LAST_UPDATE, Timestamp.valueOf(LocalDateTime.now()));
            updateAppointments.setString(INDEX_APPT_LAST_UPDATED_BY, User.currentUser.getUserName());
            updateAppointments.setInt(INDEX_APPT_CUSTOMER_ID, appointment.getCustomerId());
            updateAppointments.setInt(INDEX_APPT_USER_ID, appointment.getUserId());
            updateAppointments.setInt(INDEX_APPT_CONTACT_ID, appointment.getContactId());

            updateAppointments.executeQuery();
            Appointment.updateAppt(appointment.getApptId(), appointment);
            // TODO: add update message window
            // TODO: add appointment report

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAppointment(Appointment appointment){
        List<Appointment> appointmentList = Appointment.appointmentsList;

        try {
            PreparedStatement deleteAppt = DBConnection.getConnection().prepareStatement(DELETE_AN_APPOINTMENT);
            deleteAppt.setInt(INDEX_APPT_ID, appointment.getApptId());
            ResultSet result = deleteAppt.executeQuery();
            appointmentList.remove(appointment);
            //Appointment.getAllAppointmentsList() = appointmentList;

            // TODO: add delete appointment report

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean addAppointment(Appointment appointment) {
        try {
            PreparedStatement addAppointments = DBConnection.getConnection().prepareStatement(ADD_NEW_APPOINTMENT);
            addAppointments.setInt(INDEX_APPT_ID, Integer.parseInt(COLUMN_APPT_ID));
            addAppointments.setString(INDEX_APPT_TITLE, COLUMN_APPT_TITLE);
            addAppointments.setString(INDEX_APPT_DESCRIPTION, COLUMN_APPT_DESCRIPTION);
            addAppointments.setString(INDEX_APPT_LOCATION, COLUMN_APPT_LOCATION);
            addAppointments.setString(INDEX_APPT_TYPE, COLUMN_APPT_TYPE);
            addAppointments.setTimestamp(INDEX_APPT_START, Timestamp.valueOf(COLUMN_APPT_START));
            addAppointments.setTimestamp(INDEX_APPT_END, Timestamp.valueOf(COLUMN_APPT_END));
            addAppointments.setTimestamp(INDEX_APPT_CREATED_DATE, Timestamp.valueOf(COLUMN_APPT_CREATED_DATE));
            addAppointments.setString(INDEX_APPT_CREATED_BY, COLUMN_APPT_CREATED_BY);
            addAppointments.setTimestamp(INDEX_APPT_LAST_UPDATE, Timestamp.valueOf(COLUMN_APPT_LAST_UPDATE));
            addAppointments.setString(INDEX_APPT_LAST_UPDATED_BY, COLUMN_APPT_LAST_UPDATE_BY);
            addAppointments.setInt(INDEX_APPT_CUSTOMER_ID, Integer.parseInt(COLUMN_CUSTOMER_ID));
            addAppointments.setInt(INDEX_APPT_USER_ID, Integer.parseInt(COLUMN_USER_ID));
            addAppointments.setInt(INDEX_APPT_CONTACT_ID, Integer.parseInt(COLUMN_CONTACT_ID));

            try {
                ResultSet result = addAppointments.executeQuery();
                if (addAppointments.getUpdateCount() > 0) {
                    System.out.println("Rows effected: " + addAppointments.getUpdateCount());
                } else {
                    System.out.println("No change");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;

            // TODO: add report statement

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    }
