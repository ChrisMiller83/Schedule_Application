package DAO;

import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DivisionDAO {

    public DivisionDAO() {}

    public static final String TABLE_FIRST_LEVEL_DIVISIONS = "divisions";
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

    public static final String QUERY_ALL_DIVISIONS = "SELECT * FROM " + TABLE_FIRST_LEVEL_DIVISIONS;

    public static void loadAllDivisions() {
        try {
            PreparedStatement loadDivisions = DBConnection.getConnection().prepareStatement(QUERY_ALL_DIVISIONS);
            ResultSet result = loadDivisions.executeQuery();

            while (result.next()) {
                int divisionId = result.getInt(COLUMN_DIVISION_ID);
                String division = result.getString(COLUMN_DIVISION_NAME);
                Timestamp createdDate = result.getTimestamp(COLUMN_DIVISION_CREATED_DATE);
                String createdBy = result.getString(COLUMN_DIVISION_CREATED_BY);
                Timestamp lastUpdated = result.getTimestamp(COLUMN_DIVISION_LAST_UPDATED);
                String lastUpdatedBy = result.getString(COLUMN_DIVISION_LAST_UPDATED_BY);
                int countryId = result.getInt(COLUMN_DIVISION_COUNTRY_ID);
                FirstLevelDivision divisions = new FirstLevelDivision(divisionId, division, createdDate,
                        createdBy, lastUpdated, lastUpdatedBy, countryId);
                FirstLevelDivision.divisionArrayList.add(divisions);

            }
            // TODO: add report for FLD
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
