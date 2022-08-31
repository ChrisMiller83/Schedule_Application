package model;


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
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

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

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionName(String division) {
        this.divisionName = divisionName;
    }

    public String getDivisionName() {
        return divisionName;
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



    @Override
    public String toString() {
        return divisionName;
    }

}
