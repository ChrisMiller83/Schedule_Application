package DAO;

import com.sun.org.apache.xpath.internal.operations.Div;
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
    public static final Integer COLUMN_DIVISION_ID = "Division_ID";
    public static final String COLUMN_DIVISION_NAME = "Division";
    public static final String COLUMN_DIVISION_CREATED_DATE = "Create_Date";
    public static final String COLUMN_DIVISION_CREATED_BY = "Created_By";
    public static final String COLUMN_DIVISION_LAST_UPDATED = "Last_Update";
    public static final String COLUMN_DIVISION_LAST_UPDATED_BY = "Last_Updated_By";
    public static final Integer COLUMN_DIVISION_COUNTRY_ID = "Country_ID";
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

    public static final String QUERY_DIVISION_BY_COUNTRY = "SELECT * FROM " + TABLE_DIVISIONS +
            " WHERE " + COLUMN_DIVISION_COUNTRY_ID + " = ?";

    public static ObservableList<Division> loadAllDivisions() {
        ObservableList<Division> divisions = FXCollections.checkedObservableList();

        try {
            PreparedStatement loadDivisions = DBConnection.getConnection().prepareStatement(QUERY_ALL_DIVISIONS);
            ResultSet result = loadDivisions.executeQuery();

            while (result.next()) {
                Division newDivision = new Division(
                        result.getInt(COLUMN_DIVISION_ID),
                        result.getString(COLUMN_DIVISION_NAME),
                        result.getTimestamp(COLUMN_DIVISION_CREATED_DATE),
                        result.getString(COLUMN_DIVISION_CREATED_BY),
                        result.getTimestamp(COLUMN_DIVISION_LAST_UPDATED),
                        result.getString(COLUMN_DIVISION_LAST_UPDATED_BY),
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

    public static Division getDivisionId(String division) {
        try {
            PreparedStatement getId = DBConnection.getConnection().prepareStatement(QUERY_DIVISION_ID);
            getId.setString(INDEX_DIVISION_NAME, COLUMN_DIVISION_NAME);
            ResultSet result = getId.executeQuery();

            while (result.next()) {
                Division newDivision = new Division(
                        result.getInt(COLUMN_DIVISION_ID),
                        result.getString(COLUMN_DIVISION_NAME),
                        result.getTimestamp(COLUMN_DIVISION_CREATED_DATE),
                        result.getString(COLUMN_DIVISION_CREATED_BY),
                        result.getTimestamp(COLUMN_DIVISION_LAST_UPDATED),
                        result.getString(COLUMN_DIVISION_LAST_UPDATED_BY),
                        result.getInt(COLUMN_DIVISION_COUNTRY_ID)
                );
                return newDivision;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Division> getDivisionsByCountry(int country) {
        int newCountry = Country.getCountryId(COLUMN_DIVISION_COUNTRY_ID);
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            PreparedStatement getDivision = DBConnection.getConnection().prepareStatement(QUERY_DIVISION_BY_COUNTRY);
            getDivision.setInt(INDEX_DIVISION_COUNTRY_ID, COLUMN_DIVISION_COUNTRY_ID);
            ResultSet result = getDivision.executeQuery();

            while (result.next()) {
                Division newDivision = new Division(
                        result.getInt(COLUMN_DIVISION_ID),
                        result.getString(COLUMN_DIVISION_NAME),
                        result.getTimestamp(COLUMN_DIVISION_CREATED_DATE),
                        result.getString(COLUMN_DIVISION_CREATED_BY),
                        result.getTimestamp(COLUMN_DIVISION_LAST_UPDATED),
                        result.getString(COLUMN_DIVISION_LAST_UPDATED_BY),
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




}
