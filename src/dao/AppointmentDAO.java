package dao;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.MessageLambdaInterface;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * AppointmentDAO class -- used to connect to the database and allow sql queries, create, update, and deletes.
 */
public class AppointmentDAO {
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


    /**
     * QUERY CONSTANTS used to prevent SQL injection into the appointments table
     */
    public static final String QUERY_ALL_APPOINTMENTS = "SELECT * FROM " + TABLE_APPOINTMENTS +
            " ORDER BY " + COLUMN_APPT_ID;

    public static final String QUERY_APPOINTMENTS_BY_WEEK_OR_MONTH = "SELECT * FROM " + TABLE_APPOINTMENTS +
            " WHERE " + COLUMN_APPT_START + " > ? AND " + COLUMN_APPT_START + " < ? ;";

    public static final String ADD_NEW_APPOINTMENT = "INSERT INTO " + TABLE_APPOINTMENTS + " (" +
            COLUMN_APPT_TITLE + ", " + COLUMN_APPT_DESCRIPTION + ", " + COLUMN_APPT_LOCATION + ", " +
            COLUMN_APPT_TYPE + ", " + COLUMN_APPT_START + ", " + COLUMN_APPT_END + ", " +
            COLUMN_APPT_CREATE_DATE + ", " + COLUMN_APPT_CREATED_BY + ", " +
            COLUMN_APPT_LAST_UPDATE + ", " + COLUMN_APPT_LAST_UPDATED_BY + ", " +
            COLUMN_CUSTOMER_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_CONTACT_ID +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String UPDATE_AN_APPOINTMENT = "UPDATE " + TABLE_APPOINTMENTS + " SET " +
            COLUMN_APPT_TITLE + " = ?, " + COLUMN_APPT_DESCRIPTION + " = ?, " +
            COLUMN_APPT_LOCATION + " = ?, " + COLUMN_APPT_TYPE + " = ?, " +
            COLUMN_APPT_START + " = ?, " + COLUMN_APPT_END + " = ?, " +
            COLUMN_APPT_LAST_UPDATE + " = ?, " + COLUMN_APPT_LAST_UPDATED_BY + " = ?, " +
            COLUMN_CUSTOMER_ID + " = ?, " + COLUMN_USER_ID + " = ?, " +
            COLUMN_CONTACT_ID + " = ? WHERE " +
            COLUMN_APPT_ID + " = ?;";

    public static final String DELETE_AN_APPOINTMENT = "DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_APPT_ID + " = ?;";

    public static final String DELETE_ALL_CUSTOMER_APPOINTMENTS = "DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_CUSTOMER_ID + " = ?;";

    public static final String DELETE_ALL_CONTACT_APPOINTMENTS = "DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_CONTACT_ID + " = ?;";

    public static final String DELETE_ALL_USER_APPOINTMENTS = "DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_USER_ID + " = ?;";

    public static final String QUERY_APPOINTMENTS_BY_CONTACT_ID = "SELECT * FROM " + TABLE_APPOINTMENTS +
            " WHERE " + COLUMN_CONTACT_ID + " = ?;";

    public static final String QUERY_APPOINTMENTS_BY_CUSTOMER_ID = "SELECT * FROM " + TABLE_APPOINTMENTS +
            " WHERE " + COLUMN_CUSTOMER_ID + " = ?;";

    public static final String QUERY_APPT_TOTALS = "SELECT " +
            "MONTHNAME(" + COLUMN_APPT_START + ") AS Month, " +
            COLUMN_APPT_TYPE + ", COUNT(*) AS Total FROM " + TABLE_APPOINTMENTS +
            " GROUP BY " + COLUMN_APPT_TYPE;


    /**
     * loadAllAppts -- queries the db and gets all appointments from the appointments table
     *
     * @return returns an ObservableList of all appointments and their data from the appointments table.
     */
    public static ObservableList<Appointment> loadAllAppts() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try (PreparedStatement loadAppts = DBConnection.getConnection().prepareStatement(QUERY_ALL_APPOINTMENTS);
             ResultSet result = loadAppts.executeQuery()) {
            while (result.next()) {
                int apptId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * getApptsThisWeek -- queries the db and gets all appointments within the next 7 days from the db appointments table.
     *
     * @return -- Returns an ObservableList of all appts in the next 7 days from the db appointments table.
     * @throws SQLException
     */
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
                String description = result.getString("Description");
                String location = result.getString("Location");
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

    /**
     * getApptsThisMonth -- queries the db and gets all appointments within the next 30 days from the db appointments table.
     *
     * @return -- Returns an ObservableList of all appts in the next 30 days from the db appointments table.
     * @throws SQLException
     */
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
                String description = result.getString("Description");
                String location = result.getString("Location");
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

    /**
     * addAppointment -- Creates a new appointment/entry in the db appointments table.
     *
     * @param title         -- appointment title
     * @param description   -- appointment description
     * @param location      -- appointment location
     * @param type          -- appointment type
     * @param start         -- start date and time
     * @param end           -- end date and time
     * @param createDate    -- Timestamp when appointment is created
     * @param createdBy     -- User name that created the appointment
     * @param lastUpdate    -- Timestamp when appointment is last updated
     * @param lastUpdatedBy -- User name that last updated the appointment
     * @param customerId    -- customer ID
     * @param userId        -- user ID
     * @param contactId     -- contact ID
     */
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updateAppointment -- Updates an existing appointment/entry in the db appointments table that matches the appointment ID.
     *
     * @param title         -- appointment title
     * @param description   -- appointment description
     * @param location      -- appointment location
     * @param type          -- appointment type
     * @param start         -- start date and time
     * @param end           -- end date and time
     * @param lastUpdate    -- Timestamp when appointment is last updated
     * @param lastUpdatedBy -- User name that last updated the appointment
     * @param customerId    -- customer ID
     * @param userId        -- user ID
     * @param contactId     -- contact ID
     * @param apptId        -- appointment Id used to update the appointment entry in the db.
     */
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deleteAppointment -- deletes an appointment from the db appointments table that matches the appointment Id.
     *
     * @param apptId -- appointment ID used to delete an appointment from the db.
     */
    public static void deleteAppointment(int apptId) {
        try {
            PreparedStatement deleteAppt = DBConnection.getConnection().prepareStatement(DELETE_AN_APPOINTMENT);
            deleteAppt.setInt(1, apptId);
            deleteAppt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deleteAllCustomerAppts -- deletes all appts that contain the matching customerId from the db appointments table.
     *
     * @param customerId -- customerId is used to find all appointments that have the customerId
     */
    public static void deleteAllCustomerAppts(int customerId) {
        try {
            PreparedStatement deleteAppt = DBConnection.getConnection().prepareStatement(DELETE_ALL_CUSTOMER_APPOINTMENTS);
            deleteAppt.setInt(1, customerId);
            deleteAppt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deleteAllContactAppts -- deletes all appts that contain the matching contactId from the db appointments table.
     *
     * @param contactId -- contactId is used to find all appointments that have the customerId
     */
    public static void deleteAllContactAppts(int contactId) {
        try {
            PreparedStatement deleteAppt = DBConnection.getConnection().prepareStatement(DELETE_ALL_CONTACT_APPOINTMENTS);
            deleteAppt.setInt(1, contactId);
            deleteAppt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deleteAllUserAppts -- deletes all appts that contain the userId from the db appointments table.
     *
     * @param userId -- userId is used to find all appointments that have the userId
     */
    public static void deleteAllUserAppts(int userId) {
        try {
            PreparedStatement deleteAppt = DBConnection.getConnection().prepareStatement(DELETE_ALL_USER_APPOINTMENTS);
            deleteAppt.setInt(1, userId);
            deleteAppt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * loadContactAppts -- queries the db and gets all appointments from the appointments table that match the contact ID.
     *
     * @param contactId -- contact ID used to get all appointments that match the contact ID.
     * @return -- Returns an ObservableList of all appointments that match the contact ID.
     */
    public static ObservableList<Appointment> loadContactAppts(int contactId) {
        ObservableList<Appointment> contactAppts = FXCollections.observableArrayList();
        try {
            PreparedStatement loadAppts = DBConnection.getConnection().prepareStatement(QUERY_APPOINTMENTS_BY_CONTACT_ID);
            loadAppts.setInt(1, contactId);
            ResultSet result = loadAppts.executeQuery();
            while (result.next()) {
                int apptId = result.getInt("Appointment_ID");
                LocalDate apptDate = result.getTimestamp("Start").toLocalDateTime().toLocalDate();
                LocalTime start = result.getTimestamp("Start").toLocalDateTime().toLocalTime();
                LocalTime end = result.getTimestamp("End").toLocalDateTime().toLocalTime();
                String apptTitle = result.getString("Title");
                String apptType = result.getString("Type");
                String apptDescription = result.getString("Description");
                int customerId = result.getInt("Customer_ID");

                Appointment appts = new Appointment(apptId, apptDate, start, end, apptTitle, apptType, apptDescription, customerId);

                contactAppts.add(appts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactAppts;
    }

    /**
     * loadCustomerAppts -- queries the db and gets all appointments from the appointments table that match the customer ID.
     *
     * @param customerId -- customer ID used to get all appointments that match the customer ID.
     * @return -- Returns an ObservableList of all appointments that match the customer ID.
     */
    public static ObservableList<Appointment> loadCustomerAppts(int customerId) {
        ObservableList<Appointment> customerAppts = FXCollections.observableArrayList();

        try {
            PreparedStatement loadAppts = DBConnection.getConnection().prepareStatement(QUERY_APPOINTMENTS_BY_CUSTOMER_ID);
            loadAppts.setInt(1, customerId);
            ResultSet result = loadAppts.executeQuery();
            while (result.next()) {
                int apptId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                Timestamp createDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment appts = new Appointment(apptId, title, description, location, type, start, end,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                customerAppts.add(appts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerAppts;
    }

    /**
     * loadTotals -- queries the db and gets a total of all appointments from the appointments table by month and type.
     *
     * @return -- Returns an ObservableList of the total appointments by month and type.
     */
    public static ObservableList<Appointment> loadTotals() {
        ObservableList<Appointment> totals = FXCollections.observableArrayList();
        try {
            PreparedStatement load = DBConnection.getConnection().prepareStatement(QUERY_APPT_TOTALS);
            ResultSet result = load.executeQuery();
            while (result.next()) {
                String month = result.getString("Month");
                String apptType = result.getString("Type");
                Integer total = result.getInt("Total");

                Appointment apptTotals = new Appointment(month, apptType, total);

                totals.add(apptTotals);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totals;
    }


}