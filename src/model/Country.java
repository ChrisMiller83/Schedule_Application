package model;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Country model class.
 */
public class Country {
    private int countryId;
    private String country;

    /** Empty default constructor */
    public Country() {};

    /**
     * Country constructor
     * @param countryId country id
     * @param country country name
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /** countryId setter */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** countryId getter */
    public int getCountryId() {
        return countryId;
    }

    /** country name setter */
    public void setCountry(String country) {
        this.country = country;
    }

    /** country name getter */
    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return country;
    }

}
