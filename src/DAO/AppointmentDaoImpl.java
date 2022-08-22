package DAO;

import controller.alertMessage;
import controller.errorMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Appointment;
import main.main;

import javax.management.Query;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

public class AppointmentDaoImpl implements AppointmentDao {
    //private Connection conn = main.conn;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String selectAllAppointments = "SELECT * FROM appointments";

    @FXML
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    @FXML
    ObservableList<Appointment> currentMonthAppointments = FXCollections.observableArrayList();

    public AppointmentDaoImpl() throws SQLException {
    }

    private Appointment getAppointmentInformantion() throws SQLException {
        int appointmentId = resultSet.getInt("Appointment ID");
        String title = resultSet.getString("Title");
        String description = resultSet.getString("Description");
        String location = resultSet.getString("Location");
        String type = resultSet.getString("Type");
        LocalDateTime startDateTime = resultSet.getTimestamp("Start Time").toLocalDateTime();
        LocalDateTime endDateTime =resultSet.getTimestamp("End Time").toLocalDateTime();
        LocalDateTime createdDate = resultSet.getTimestamp("Created Date").toLocalDateTime();
        String createdBy = resultSet.getString("Created By");
        Timestamp lastUpdated = resultSet.getTimestamp("Last Updated");
        String lastUpdatedBy = resultSet.getString("Last Updated By");
        int customerId = resultSet.getInt("Customer ID");
        int userId = resultSet.getInt("User ID");
        int contactId = resultSet.getInt("Contact ID");

        Appointment appointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, createdDate, createdBy, lastUpdated, lastUpdatedBy, customerId, userId, contactId);
        return appointment;
    }

    public void addAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {
        String addAppointment = "INSERT INTO appointments(Appointment ID, Title, Description, Location, Type, Start Time, End Time, Created Date, Created By, Last Updated, Last Updated By, Customer ID, User ID, Contact ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        QueryDatabase.setPreparedStatement(conn, addAppointment);
        preparedStatement = QueryDatabase.getPreparedStatement();

        preparedStatement.setInt(1, appointmentId);
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, location);
        preparedStatement.setString(5, type);
        preparedStatement.setTimestamp(6, Timestamp.valueOf(startDateTime));
        preparedStatement.setTimestamp(7, Timestamp.valueOf(endDateTime));
        preparedStatement.setTimestamp(8, Timestamp.valueOf(createdDate));
        preparedStatement.setString(9, createdBy);
        preparedStatement.setTimestamp(10, lastUpdated);
        preparedStatement.setString(11, lastUpdatedBy);
        preparedStatement.setInt(12, customerId);
        preparedStatement.setInt(13, userId);
        preparedStatement.setInt(14, contactId);

        preparedStatement.execute();

        if (preparedStatement.getUpdateCount() > 0) {
            alertMessage.addSuccessful();
        } else {
            alertMessage.unSuccessful();
        }


    }

    public void updateAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {
        String updateAppointment = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start Time=?, End Time=?, Created Date=?, Created By=?, Last Updated=?, Last Updated By=?, Customer ID=?, User ID=?, Contact ID=? WHERE Appointment ID=?";
        QueryDatabase.setPreparedStatement(conn, updateAppointment);
        Timestamp lastUpdated = Timestamp.now();
        String lastUpdatedBy = loginController.userName;
        try {
            PreparedStatement preparedStatement = QueryDatabase.getPreparedStatement();
            preparedStatement.setInt(1, appointmentId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, location);
            preparedStatement.setString(5, type);
            preparedStatement.setTimestamp(6, Timestamp.valueOf(startDateTime));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(endDateTime));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(createdDate));
            preparedStatement.setString(9, createdBy);
            preparedStatement.setTimestamp(10, lastUpdated);
            preparedStatement.setString(11, lastUpdatedBy);
            preparedStatement.setInt(12, customerId);
            preparedStatement.setInt(13, userId);
            preparedStatement.setInt(14, contactId);

            preparedStatement.execute();

        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
    }

    public void deleteAppointment(int appointmentId) throws SQLException {
        StringBuilder deleteStatement = new StringBuilder();
        deleteStatement.append("DELETE FROM appointments WHERE Appointment ID=?");
        deleteStatement.append(appointmentId);
        QueryDatabase.setPreparedStatement(conn, deleteStatement.toString());

        try {
            PreparedStatement preparedStatement = QueryDatabase.getPreparedStatement();
            preparedStatement.execute();
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
    }

    @Override
    public void deleteAssociatedAppointments(int customerId) throws SQLException {
        String deleteAssociatedAppointmentStatement = "DELETE FROM appointments WHERE Customer ID=?";
        deleteAssociatedAppointmentStatement += customerId;
        QueryDatabase.setPreparedStatement(conn, deleteAssociatedAppointmentStatement);

        try {
            PreparedStatement preparedStatement = QueryDatabase.getPreparedStatement();
            preparedStatement.execute();
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
    }


    @Override
    public ObservableList<Appointment> getAllAppointments() throws SQLException {
        QueryDatabase.setPreparedStatement(conn, selectAllAppointments);
        preparedStatement = QueryDatabase.getPreparedStatement();
        resultSet = preparedStatement.executeQuery();
        try {
            while (resultSet.next()) {
                Appointment appointment = getAppointmentInformantion();
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
        return appointments;
    }

    @Override
    public ObservableList<Appointment> getAllCurrentMonthAppointments() throws SQLException {
        String getAllCurrentMonthAppointments = "SELECT * FROM appointments WHERE MONTH(start)=MONTH(current_date()) and YEAR(start)=YEAR(current_date())";
        QueryDatabase.setPreparedStatement(conn, getAllCurrentMonthAppointments);
        preparedStatement = QueryDatabase.getPreparedStatement;
        resultSet = preparedStatement.executeQuery();
        try {
            while ((resultSet.next())) {
                Appointment appointment = getAppointmentInformation();
                currentMonthAppointments.add(appointment);
            }
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
        return currentMonthAppointments;
    }

    @Override
    public ObservableList<Appointment> getSelectedContactAppointments() throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Appointment> getSelectedUserAppointments() throws SQLException {
        return null;
    }

    @Override
    public Appointment getAppointment(int appointmentId) throws SQLException {
        String selectAppointmentStatement = "SELECT * FROM appointments WHERE Appointment_ID = " + String.valueOf(appointmentId);
        QueryDatabase.setPreparedStatement(conn, selectAppointmentStatement);
        preparedStatement = QueryDatabase.getPreparedStatement;
        resultSet = preparedStatement.executeQuery();
        try {
            while ((resultSet.next())) {
                return getAppointmentInformation();
            }
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
        return null;
    }

    @Override
    public ObservableList<Appointment> getAllAppointmentsByType(String type) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsByType(String type) throws SQLException {
        ObservableList<Appointment> appointmentsByType = FXCollections.observableArrayList();
        String selectAppointmentByType = "SELECT * FROM appointments WHERE type=?";
        QueryDatabase.setPreparedStatement(conn, selectAppointmentByType);
        preparedStatement = QueryDatabase.getPreparedStatement;
        preparedStatement.setString(1, type);
        resultSet = preparedStatement.executeQuery();
        try {
            while ((resultSet.next())) {
                Appointment appointment = getAppointmentInformation();
                appointmentsByType.add(appointment);
            }
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
        return appointmentsByType;
    }

    @Override
    public ObservableList<Appointment> getSelectedContactAppointments(int contactId) throws SQLException {
        ObservableList<Appointment> selectedContactAppointments = FXCollections.observableArrayList();
        String selectedContactAppointment = "SELECT * FROM appointments WHERE Contact_ID=? ORDER BY start ASC";
        QueryDatabase.setPreparedStatement(conn, selectedContactAppointment);
        preparedStatement = QueryDatabase.getPreparedStatement;
        preparedStatement.setInt(1, contactId);
        resultSet = preparedStatement.executeQuery();
        try {
            while ((resultSet.next())) {
                Appointment appointment = getAppointmentInformation();
                selectedContactAppointments.add(appointment);
            }
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
        return selectedContactAppointments;
    }

    @Override
    public ObservableList<Appointment> getSelectedUserAppointments(int userId) throws SQLException {
        ObservableList<Appointment> selectedUserAppointments = FXCollections.observableArrayList();
        String selectedUserAppointment = "SELECT * FROM appointments WHERE User_ID=? ORDER BY start ASC";
        QueryDatabase.setPreparedStatement(conn, selectedUserAppointments);
        preparedStatement = QueryDatabase.getPreparedStatement;
        preparedStatement.setInt(1, userId);
        resultSet = preparedStatement.executeQuery();
        try {
            while ((resultSet.next())) {
                Appointment appointment = getAppointmentInformation();
                selectedUserAppointments.add(appointment);
            }
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
        return selectedUserAppointments;
    }






}
