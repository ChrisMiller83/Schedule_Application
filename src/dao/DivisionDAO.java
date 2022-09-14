package dao;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DivisionDAO -- used to connect to the database and allow sql queries
 */
public class DivisionDAO {

    /**
     * Instantiates a new division
     */
    public DivisionDAO() {}

    /**
     * CONSTANTS used to prevent SQL injection into the divisions table
     */
    public static final String TABLE_DIVISIONS = "first_level_divisions";
    public static final String COLUMN_DIVISION_ID = "Division_ID";
    public static final String COLUMN_DIVISION_NAME = "Division";
    public static final String COLUMN_DIVISION_CREATED_DATE = "Create_Date";
    public static final String COLUMN_DIVISION_CREATED_BY = "Created_By";
    public static final String COLUMN_DIVISION_LAST_UPDATED = "Last_Update";
    public static final String COLUMN_DIVISION_LAST_UPDATED_BY = "Last_Updated_By";
    public static final String COLUMN_DIVISION_COUNTRY_ID = "Country_ID";

    /**
     * QUERY CONSTANTS used to prevent SQL injection into the divisions table
     */
    public static final String QUERY_ALL_DIVISIONS = "SELECT * FROM " + TABLE_DIVISIONS;

    public static final String QUERY_DIVISION_ID = "SELECT * FROM " + TABLE_DIVISIONS +
            " WHERE " + COLUMN_DIVISION_NAME + " = ?";

    public static final String QUERY_ALL_DIVISION_WITH_COUNTRY_ID = "SELECT * FROM " + TABLE_DIVISIONS +
            " WHERE " + COLUMN_DIVISION_COUNTRY_ID + " = ?";

    public static final String QUERY_DIVISION_ID_FOR_COUNTRY_ID = "SELECT " + COLUMN_DIVISION_COUNTRY_ID +
            " FROM " + TABLE_DIVISIONS + " WHERE " + COLUMN_DIVISION_ID + " = ?";

    /**
     * loadAllDivisions -- queries the db and gets all divisions from the divisions table
     * @return -- returns an ObservableList of all divisions and their data from the divisions table.
     */
    public static ObservableList<Division> loadAllDivisions() {
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            PreparedStatement loadDivisions = DBConnection.getConnection().prepareStatement(QUERY_ALL_DIVISIONS);
            ResultSet result = loadDivisions.executeQuery();

            while (result.next()) {
                int divisionId = result.getInt(COLUMN_DIVISION_ID);
                String divisionName = result.getString(COLUMN_DIVISION_NAME);
                int countryId = result.getInt(COLUMN_DIVISION_COUNTRY_ID);

                Division allDivisions = new Division(divisionId, divisionName, countryId);
                divisions.add(allDivisions);
            }
            return divisions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * getDivisions -- queries the db to get all the divisions(state/provices) that match a selectedCountry
     * @param selectedCountry -- country selected in choice box
     * @return -- returns an ObservableList of all divisions that have a country Id that matches the selectedCountry id
     */
    public static ObservableList<Division> getDivisions(Country selectedCountry) {
        Country country = CountryDAO.getCountryId(selectedCountry);

        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            PreparedStatement getDivisions = DBConnection.getConnection().prepareStatement(QUERY_ALL_DIVISION_WITH_COUNTRY_ID);
            getDivisions.setInt(1, country.getCountryId());
            ResultSet result = getDivisions.executeQuery();

            while (result.next()) {
//                Division divisionSet = new Division(
//                        result.getInt(COLUMN_DIVISION_ID),
//                        result.getString(COLUMN_DIVISION_NAME),
//                        result.getInt(COLUMN_DIVISION_COUNTRY_ID)
//                );
                int divisionId = result.getInt(COLUMN_DIVISION_ID);
                String divisionName = result.getString(COLUMN_DIVISION_NAME);
                int countryId = result.getInt(COLUMN_DIVISION_COUNTRY_ID);

                Division divisionSet = new Division(divisionId, divisionName, countryId);

                divisions.add(divisionSet);
            }
            return divisions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getDivisoinId -- queries the db to get the division id that matches a selected division name.
     * @param divisionName -- division name selected from a choice box
     * @return -- returns the divisionId that matches the selected division name or returns 0 if the divisionId was not found
     */
    public static int getDivisionId(String divisionName) {
        try {
            PreparedStatement getId = DBConnection.getConnection().prepareStatement(QUERY_DIVISION_ID);
            getId.setString(1, divisionName);
            ResultSet result = getId.executeQuery();

            int divisionId = result.getInt("Division_ID");

            return divisionId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * getCountryIds -- queries the db to get the country id that matches the given division id
     * @param divisionID -- selected divisionId that is used to find the countryId
     * @return -- returns the countryId of the selected divisionId or returns O if not found
     */
    public static Integer getCountryIds(int divisionID) {
        try {
            PreparedStatement getCountryId = DBConnection.getConnection().prepareStatement(QUERY_DIVISION_ID_FOR_COUNTRY_ID);
            getCountryId.setInt(1, divisionID);
            ResultSet result = getCountryId.executeQuery();

            int countryId = result.getInt("Country_ID");
            return countryId;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
