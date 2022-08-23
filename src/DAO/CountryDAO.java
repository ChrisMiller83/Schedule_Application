package DAO;

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
    public static final String COLUMN_CREATED_DATE = "Created_Date";
    public static final String COLUMN_CREATED_BY = "Created_By";
    public static final String COLUMN_LAST_UPDATED = "Last_Update";
    public static final String COLUMN_LAST_UPDATED_BY = "Last_Updated_By";
    public static final int INDEX_COUNTRY_ID = 1;
    public static final int INDEX_COUNTRY_NAME = 2;
    public static final int INDEX_CREATED_DATE = 3;
    public static final int INDEX_CREATED_BY = 4;
    public static final int INDEX_LAST_UPDATED = 5;
    public static final int INDEX_LAST_UPDATED_BY = 6;

    public static final String QUERY_ALL_COUNTRIES = "SELECT * FROM " + TABLE_COUNTRIES;


    public static void loadAllCountries() {
        try {
            PreparedStatement loadCountries = DBConnection.getConnection().prepareStatement(QUERY_ALL_COUNTRIES);
            ResultSet result = loadCountries.executeQuery();

            while (result.next()) {
                int countryId = result.getInt(COLUMN_COUNTRY_ID);
                String country = result.getString(COLUMN_COUNTRY_NAME);
                Timestamp createdDate = result.getTimestamp(COLUMN_CREATED_DATE);
                String  createdBy = result.getString(COLUMN_CREATED_BY);
                Timestamp lastUpdated = result.getTimestamp(COLUMN_LAST_UPDATED);
                String lastUpdatedBy = result.getString(COLUMN_LAST_UPDATED_BY);

                Country countries = new Country(countryId, country, createdDate, createdBy, lastUpdated, lastUpdatedBy);
                Country.countries.add(countries);
            }

            // TODO: add countries report

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
