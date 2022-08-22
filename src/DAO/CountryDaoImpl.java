package DAO;

import controller.errorMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import main.main;

import javax.management.Query;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CountryDaoImpl implements CountryDao{
    private final Connection conn = main.conn;
    private final String selectAllCountries = "SELECT * FROM countries";
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private ObservableList<Country> countries = FXCollections.observableArrayList();

    public CountryDaoImpl() throws SQLException {
        QueryDatabase.setPreparedStatement(conn, selectAllCountries);
        preparedStatement = QueryDatabase.getPreparedStatement();
        resultSet = preparedStatement.executeQuery();

    }

    @Override
    public ObservableList<Country> getAllCountries() {
        try {
            while (resultSet.next()) {
                int countryId = resultSet.getInt("Country ID");
                String countryName = resultSet.getString("Country Name");
                LocalDateTime createdDate = resultSet.getTimestamp("Created Date").toLocalDateTime();
                String createdBy = resultSet.getString("Created By");
                Timestamp lastUpdated = resultSet.getTimestamp("Last Updated");
                String lastUpdatedBy = resultSet.getString("Last Updated By");
                Country country = new Country(countryId, countryName, createdDate, createdBy, lastUpdated, lastUpdatedBy);
                countries.add(country);
            }
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
        return countries;
    }


    public Country getCountry(int countryId) throws SQLException {
        Country country = null;
        String selectCountry = "SELECT * FROM countries WHERE Country ID=" + countryId;
        QueryDatabase.setPreparedStatement(conn, selectCountry);
        PreparedStatement preparedStatement = QueryDatabase.getPreparedStatement();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String countryName = resultSet.getString("Country");
            LocalDateTime createdDate = resultSet.getTimestamp("Created Date").toLocalDateTime();
            String createdBy = resultSet.getString("Created By");
            Timestamp lastUpdated = resultSet.getTimestamp("Last Updated");
            String lastUpdatedBy = resultSet.getString("Last Updated By");
            country = new Country(countryId, countryName, createdDate, createdBy, lastUpdated, lastUpdatedBy);
        }
        return country;
    }
}
