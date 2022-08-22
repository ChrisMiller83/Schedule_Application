package DAO;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Appointment;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface AppointmentDao {
    @FXML
    public ObservableList<Appointment> getAllAppointments() throws SQLException;

    @FXML
    public Appointment getAppointment(int appointmentID) throws SQLException;

    @FXML
    public ObservableList<Appointment> getAllAppointmentsByType(String type) throws SQLException;

    @FXML
    public ObservableList<Appointment> getAllCurrentMonthAppointments() throws SQLException;

    @FXML
    public ObservableList<Appointment> getSelectedContactAppointments() throws SQLException;

    @FXML
    public ObservableList<Appointment> getSelectedUserAppointments() throws SQLException;

    public void addAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException;

    public void updateAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId) throws  SQLException;

    public void deleteAppointment(int appointmentID) throws SQLException;

    public void deleteAssociatedAppointments(int customerID) throws SQLException;
}
