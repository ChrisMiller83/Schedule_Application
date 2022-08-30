package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * First Level Division Class
 */
public class Division {
    private int divisionId;
    private String division;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int countryId;
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    /**
     * Default Empty Division Constructor
     */
    public Division(){
    }

    /**
     * Division Constructor
     * @param divisionId division/state/province id number
     * @param division state/province name
     * @param createdDate date and time state/province was created
     * @param createdBy user's name who created the state/province
     * @param lastUpdated date and time the state/province was last updated/changed
     * @param lastUpdatedBy user's name who last updated/changed state/province info
     * @param countryId country id number of the state/province
     */
    public Division(int divisionId, String division, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public  String getDivision() {
        return division;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryId() {
        return countryId;
    }

    public static List<Division> getDivisions () {
        return allDivisions;
    }

    public static int findCountryDivision(int index) {
        for (Division division : allDivisions) {
            if (index == division.getDivisionId()) {
                return division.getCountryId();
            }
        }
        return 0;
    }



    @Override
    public String toString() {
        return division;
    }

}
