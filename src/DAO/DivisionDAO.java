package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {

    public DivisionDAO() {}

    public static final String TABLE_DIVISIONS = "first_level_divisions";
    public static final String COLUMN_DIVISION_ID = "Division_ID";
    public static final String COLUMN_DIVISION_NAME = "Division";
    public static final String COLUMN_DIVISION_CREATED_DATE = "Create_Date";
    public static final String COLUMN_DIVISION_CREATED_BY = "Created_By";
    public static final String COLUMN_DIVISION_LAST_UPDATED = "Last_Update";
    public static final String COLUMN_DIVISION_LAST_UPDATED_BY = "Last_Updated_By";
    public static final String COLUMN_DIVISION_COUNTRY_ID = "Country_ID";
    public static final int INDEX_DIVISION_ID = 1;
    public static final int INDEX_DIVISION_NAME = 2;
    public static final int INDEX_DIVISION_CREATED_DATE = 3;
    public static final int INDEX_DIVISION_CREATED_BY = 4;
    public static final int INDEX_DIVISION_LAST_UPDATED = 5;
    public static final int INDEX_DIVISION_LAST_UPDATED_BY = 6;
    public static final int INDEX_DIVISION_COUNTRY_ID = 7;

    public static final String QUERY_ALL_DIVISIONS = "SELECT * FROM " + TABLE_DIVISIONS;

    public static final String QUERY_DIVISION_ID = "SELECT * FROM " + TABLE_DIVISIONS +
            " WHERE " + COLUMN_DIVISION_NAME + " = ?";

    public static final String QUERY_ALL_DIVISION_WITH_COUNTRY_ID = "SELECT * FROM " + TABLE_DIVISIONS +
            " WHERE " + COLUMN_DIVISION_COUNTRY_ID + " = ?";

    public static final String QUERY_DIVISION_ID_FOR_COUNTRY_ID = "SELECT " + COLUMN_DIVISION_COUNTRY_ID +
            " FROM " + TABLE_DIVISIONS + " WHERE " + COLUMN_DIVISION_ID + " = ?";

    public static ObservableList<Division> loadAllDivisions() {
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            PreparedStatement loadDivisions = DBConnection.getConnection().prepareStatement(QUERY_ALL_DIVISIONS);
            ResultSet result = loadDivisions.executeQuery();

            while (result.next()) {
                Division newDivision = new Division(
                        result.getInt(COLUMN_DIVISION_ID),
                        result.getString(COLUMN_DIVISION_NAME),
                        result.getInt(COLUMN_DIVISION_COUNTRY_ID)
                );
                divisions.add(newDivision);
            }
            return divisions;
            } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Division> getDivisions(Country selectedCountry) {
        Country country = CountryDAO.getCountryId(selectedCountry);

        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            PreparedStatement getDivisions = DBConnection.getConnection().prepareStatement(QUERY_ALL_DIVISION_WITH_COUNTRY_ID);
            getDivisions.setInt(1, country.getCountryId());
            ResultSet result = getDivisions.executeQuery();

            while (result.next()) {
                Division divisionSet = new Division(
                        result.getInt(COLUMN_DIVISION_ID),
                        result.getString(COLUMN_DIVISION_NAME),
                        result.getInt(COLUMN_DIVISION_COUNTRY_ID)
                );
                divisions.add(divisionSet);
            }
            return divisions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
