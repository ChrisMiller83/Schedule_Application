package dao;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;

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
    public static final String COLUMN_APPT_CREATE_DATE = "Create_Date";
    public static final String COLUMN_APPT_CREATED_BY = "Created_By";
    public static final String COLUMN_APPT_LAST_UPDATE = "Last_Update";
    public static final String COLUMN_APPT_LAST_UPDATED_BY = "Last_Updated_By";
    public static final String COLUMN_CUSTOMER_ID = "Customer_ID";
    public static final String COLUMN_USER_ID = "User_ID";
    public static final String COLUMN_CONTACT_ID = "Contact_ID";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_CUSTOMERS = "customers";

    public static final String QUERY_ALL_APPOINTMENTS = "SELECT * FROM " + TABLE_APPOINTMENTS +
            " ORDER BY " + COLUMN_APPT_ID;

    public static final String QUERY_APPOINTMENTS_BY_WEEK_OR_MONTH = "SELECT * FROM " + TABLE_APPOINTMENTS +
            " WHERE " + COLUMN_APPT_START + " > ? AND " + COLUMN_APPT_START + " < ? ;";

    public static final String ADD_NEW_APPOINTMENT = "INSERT INTO " + TABLE_APPOINTMENTS + " (" +
            COLUMN_APPT_TITLE + ", " + COLUMN_APPT_DESCRIPTION + ", " + COLUMN_APPT_LOCATION + ", " +
            COLUMN_APPT_TYPE + ", " + COLUMN_APPT_START + ", " + COLUMN_APPT_END + ", " +
            COLUMN_APPT_CREATE_DATE + ", " + COLUMN_APPT_CREATED_BY + ", " +
            COLUMN_APPT_LAST_UPDATE + ", " + COLUMN_APPT_LAST_UPDATED_BY + ", " +
            COLUMN_CUSTOMER_ID + ", "  + COLUMN_USER_ID + ", " + COLUMN_CONTACT_ID +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String UPDATE_AN_APPOINTMENT = "UPDATE " + TABLE_APPOINTMENTS + " SET " +
            COLUMN_APPT_TITLE + " = ?, " + COLUMN_APPT_DESCRIPTION + " = ?, " +
            COLUMN_APPT_LOCATION + " = ?, " + COLUMN_APPT_TYPE + " = ?, " +
            COLUMN_APPT_START + " = ?, " + COLUMN_APPT_END +" = ?, " +
            COLUMN_APPT_LAST_UPDATE + " = ?, " + COLUMN_APPT_LAST_UPDATED_BY + " = ?, " +
            COLUMN_CUSTOMER_ID + " = ?, " + COLUMN_USER_ID + " = ?, " +
            COLUMN_CONTACT_ID + " = ? WHERE " +
            COLUMN_APPT_ID + " = ?;";

    public static final String DELETE_AN_APPOINTMENT = "DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_APPT_ID + " = ?;";

    public static ObservableList<Appointment> loadAllAppts() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try (PreparedStatement loadAppts = DBConnection.getConnection().prepareStatement(QUERY_ALL_APPOINTMENTS);
            ResultSet result = loadAppts.executeQuery()) {
            while (result.next()) {
                int apptId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description =result.getString("Description");
                String location =result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                Timestamp createDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int customerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment allAppts = new Appointment(apptId, title, description, location, type, start, end,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                allAppointments.add(allAppts);

            }
            // TODO: add report for loaded appointments
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public static ObservableList<Appointment> getApptsThisWeek() throws SQLException {
        ObservableList<Appointment> weeksAppointments = FXCollections.observableArrayList();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextWeek = today.plusDays(7);

        try {
            PreparedStatement getWeeksAppts = DBConnection.getConnection().prepareStatement(QUERY_APPOINTMENTS_BY_WEEK_OR_MONTH);
            getWeeksAppts.setDate(1, java.sql.Date.valueOf(today.toLocalDate()));
            getWeeksAppts.setDate(2, java.sql.Date.valueOf(nextWeek.toLocalDate()));

            ResultSet result = getWeeksAppts.executeQuery();
            while (result.next()) {
                int apptId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description =result.getString("Description");
                String location =result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                Timestamp createDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int customerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment weeklyAppts = new Appointment(apptId, title, description, location, type, start, end,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                weeksAppointments.add(weeklyAppts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeksAppointments;
    }

    public static ObservableList<Appointment> getApptsThisMonth() throws SQLException {
        ObservableList<Appointment> monthsAppointments = FXCollections.observableArrayList();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextMonth = today.plusDays(30);



        try {
            PreparedStatement getMonthsAppts = DBConnection.getConnection().prepareStatement(QUERY_APPOINTMENTS_BY_WEEK_OR_MONTH);
            getMonthsAppts.setDate(1, java.sql.Date.valueOf(today.toLocalDate()));
            getMonthsAppts.setDate(2, java.sql.Date.valueOf(nextMonth.toLocalDate()));
            ResultSet result = getMonthsAppts.executeQuery();
            while (result.next()) {
                int apptId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description =result.getString("Description");
                String location =result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                Timestamp createDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int customerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment monthlyAppts = new Appointment(apptId, title, description, location, type, start, end,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                monthsAppointments.add(monthlyAppts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthsAppointments;
    }

    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start,
                                      LocalDateTime end, Timestamp createDate, User createdBy, Timestamp lastUpdate,
                                      User lastUpdatedBy, int customerId, int userId, int contactId) {
            try {
               PreparedStatement addAppointments = DBConnection.getConnection().prepareStatement(ADD_NEW_APPOINTMENT);

               addAppointments.setString(1, title);
               addAppointments.setString(2, description);
               addAppointments.setString(3, location);
               addAppointments.setString(4, type);
               addAppointments.setTimestamp(5, Timestamp.valueOf(start));
               addAppointments.setTimestamp(6, Timestamp.valueOf(end));
               addAppointments.setTimestamp(7, createDate);
               addAppointments.setString(8, String.valueOf(createdBy));
               addAppointments.setTimestamp(9, lastUpdate);
               addAppointments.setString(10, String.valueOf(lastUpdatedBy));
               addAppointments.setInt(11, customerId);
               addAppointments.setInt(12, userId);
               addAppointments.setInt(13, contactId);

               addAppointments.executeUpdate();
               // TODO: add report statement

            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void updateAppointment(String title, String description, String location, String type, LocalDateTime start,
                                         LocalDateTime end, Timestamp lastUpdate, User lastUpdatedBy, int customerId,
                                         int userId, int contactId, int apptId) {
        try {
            PreparedStatement updateAppointments = DBConnection.getConnection().prepareStatement(UPDATE_AN_APPOINTMENT);

            updateAppointments.setString(1, title);
            updateAppointments.setString(2, description);
            updateAppointments.setString(3, location);
            updateAppointments.setString(4, type);
            updateAppointments.setTimestamp(5, Timestamp.valueOf(start));
            updateAppointments.setTimestamp(6, Timestamp.valueOf(end));
            updateAppointments.setTimestamp(7, lastUpdate);
            updateAppointments.setString(8, String.valueOf(lastUpdatedBy));
            updateAppointments.setInt(9, customerId);
            updateAppointments.setInt(10, userId);
            updateAppointments.setInt(11, contactId);
            updateAppointments.setInt(12, apptId);

            updateAppointments.executeUpdate();

            // TODO: add appointment report

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAppointment(int apptId){

        try {
            PreparedStatement deleteAppt = DBConnection.getConnection().prepareStatement(DELETE_AN_APPOINTMENT);
            deleteAppt.setInt(1, apptId);
            deleteAppt.executeUpdate();

            // TODO: add delete appointment report

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
