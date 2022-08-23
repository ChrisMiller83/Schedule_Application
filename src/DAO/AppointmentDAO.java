package DAO;

import model.Appointment;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

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


    public static final String QUERY_ALL_APPOINTMENTS = "SELECT * FROM " + TABLE_APPOINTMENTS;

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

    private PreparedStatement queryAllAppointments;
    private PreparedStatement addAppointments;
    private PreparedStatement updateAppointment;
    private PreparedStatement deleteAppointment;



    public static void loadAllAppointments() {
        try {
            PreparedStatement loadAppts = DBConnection.getConnection().prepareStatement(QUERY_ALL_APPOINTMENTS);
            ResultSet result = loadAppts.executeQuery();

            while (result.next()) {
                int apptId = result.getInt("Appointment_ID");
                String apptTitle = result.getString("Title");
                String apptDescription = result.getString("Description");
                String apptLocation = result.getString("Location");
                String apptType = result.getString("Type");
                Timestamp startDateTime = result.getTimestamp("Start");
                Timestamp endDateTime = result.getTimestamp("End");
                Timestamp createdDate = result.getTimestamp("Create_Date");
                String createdBy = result.getString("Created_By");
                Timestamp lastUpdated = result.getTimestamp("Last_Update");
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int customerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment appointment = new Appointment(apptId, apptTitle, apptDescription, apptLocation,apptType,
                        startDateTime, endDateTime, createdDate, createdBy, lastUpdated, lastUpdatedBy, customerId,
                        userId, contactId);
                Appointment.appointmentArrayList.add(appointment);

            }
            // add report for loaded appointments
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAppointment(Appointment appointment) {
        List<Appointment> appointmentList = Appointment.appointmentArrayList;

        if(Appointment.appointmentArrayList.contains(appointment)) {
            // error alert appointment already scheduled
        } else {
            try {
               PreparedStatement addAppointments = DBConnection.getConnection().prepareStatement(ADD_NEW_APPOINTMENT);
               addAppointments.setInt(INDEX_APPT_ID, appointment.getApptId());
               addAppointments.setString(INDEX_APPT_TITLE, appointment.getApptTitle());
               addAppointments.setString(INDEX_APPT_DESCRIPTION, appointment.getApptDescription());
               addAppointments.setString(INDEX_APPT_LOCATION, appointment.getApptLocation());
               addAppointments.setString(INDEX_APPT_TYPE, appointment.getApptType());
               addAppointments.setTimestamp(INDEX_APPT_START, appointment.getStartDateTime());
               addAppointments.setTimestamp(INDEX_APPT_END, appointment.getEndDateTime());
               addAppointments.setTimestamp(INDEX_APPT_CREATED_DATE, Timestamp.valueOf(LocalDateTime.now()));
               addAppointments.setString(INDEX_APPT_CREATED_BY, User.currentUser.getUserName());
               addAppointments.setTimestamp(INDEX_APPT_LAST_UPDATE, Timestamp.valueOf(LocalDateTime.now()));
               addAppointments.setString(INDEX_APPT_LAST_UPDATED_BY, User.currentUser.getUserName());
               addAppointments.setInt(INDEX_APPT_CUSTOMER_ID, appointment.getCustomerId());
               addAppointments.setInt(INDEX_APPT_USER_ID, appointment.getUserId());
               addAppointments.setInt(INDEX_APPT_CONTACT_ID, appointment.getContactId());

               ResultSet result = addAppointments.executeQuery();
               Appointment.appointmentArrayList.add(appointment);

               // TODO: add report statement

            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        List<Appointment> appointmentList = Appointment.appointmentArrayList;

        try {
            PreparedStatement deleteAppt = DBConnection.getConnection().prepareStatement(DELETE_AN_APPOINTMENT);
            deleteAppt.setInt(INDEX_APPT_ID, appointment.getApptId());
            ResultSet result = deleteAppt.executeQuery();
            appointmentList.remove(appointment);
            Appointment.appointmentArrayList = appointmentList;

            // TODO: add delete appointment report

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
