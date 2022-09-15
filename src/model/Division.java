package model;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * First Level Division Class
 */
public class Division {
    private int divisionId;
    private String divisionName;
    private int countryId;

    /**
     * Default Empty Division Constructor
     */
    public Division(){
    }

    /**
     * Division Constructor
     * @param divisionId division/state/province id number
     * @param divisionName state/province name
     * @param countryId country id number of the state/province
     */
    public Division(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /** divisionId setter */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** divisionId getter */
    public int getDivisionId() {
        return divisionId;
    }

    /** divisionName setter */
    public void setDivisionName(String division) {
        this.divisionName = divisionName;
    }

    /** divisionName getter */
    public String getDivisionName() {
        return divisionName;
    }

    /** countryId setter */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** countryId getter */
    public int getCountryId() {
        return countryId;
    }

    @Override
    public String toString() {
        return divisionName;
    }

}
