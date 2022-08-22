package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * First Level Division Class
 */
public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private LocalDateTime createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int countryId;

    /**
     * Default Empty FirstLevelDivision Constructor
     */
    public FirstLevelDivision(){
    }

    /**
     * FirstLevelDivision Constructor
     * @param divisionId division/state/province id number
     * @param division state/province name
     * @param createdDate date and time state/province was created
     * @param createdBy user's name who created the state/province
     * @param lastUpdated date and time the state/province was last updated/changed
     * @param lastUpdatedBy user's name who last updated/changed state/province info
     * @param countryId country id number of the state/province
     */
    public FirstLevelDivision(int divisionId, String division, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int countryId) {
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

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getCreatedDate() {
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

    @Override
    public String toString() {
        return division;
    }

}
