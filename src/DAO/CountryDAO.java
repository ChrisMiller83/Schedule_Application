package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CountryDAO {

    /**
     * Instantiates a new country dao
     */
    public CountryDAO() {
    }

    /**
     * CONSTANTS used to prevent SQL injection into the countries table
     */
    public static final String TABLE_COUNTRIES = "countries";
    public static final String COLUMN_COUNTRY_ID = "Country_ID";
    public static final String COLUMN_COUNTRY_NAME = "Country";

    public static final int INDEX_COUNTRY_ID = 1;
    public static final int INDEX_COUNTRY_NAME = 2;


    public static final String QUERY_ALL_COUNTRIES = "SELECT * FROM " + TABLE_COUNTRIES;

    public static final String QUERY_COUNTRY_NAME_FOR_ID = "SELECT * FROM " + TABLE_COUNTRIES +
            " WHERE " + COLUMN_COUNTRY_NAME + " = ?";


    public static ObservableList<Country> loadAllCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try {
            PreparedStatement loadCountries = DBConnection.getConnection().prepareStatement(QUERY_ALL_COUNTRIES);
            ResultSet result = loadCountries.executeQuery();

            while (result.next()) {
                Country newCountry = new Country(
                    result.getInt(COLUMN_COUNTRY_ID),
                    result.getString(COLUMN_COUNTRY_NAME)
                );
                countries.add(newCountry);
            }
            return countries;
            // TODO: add countries report

        } catch (SQLException e) {
            e.printStackTrace();
        return null;
        }
    }

    public static Country getCountryId(String country) {
        try {
            PreparedStatement countryId = DBConnection.getConnection().prepareStatement(QUERY_COUNTRY_NAME_FOR_ID);
            countryId.setString(INDEX_COUNTRY_NAME, COLUMN_COUNTRY_NAME);
            ResultSet result = countryId.executeQuery();

            while (result.next()) {
                Country newCountry = new Country(
                        result.getInt(COLUMN_COUNTRY_ID),
                        result.getString(COLUMN_COUNTRY_NAME)
                );
                return newCountry;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

