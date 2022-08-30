package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Country model class.
 */
public class Country {
    private int countryId;
    private String country;
    public static List<Country> countryList = new ArrayList<>();


    /**
     * Country constructor
     * @param countryId country id
     * @param country country name
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * setCountryId
     * @param countryId sets the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * getCountryId
     * @return returns the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * setCountry
     * @param country sets the country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * getCountry
     * @return returns the country name
     */
    public String getCountry() {
        return country;
    }



    /**
     * getCountry
     * @return returns a list of countries
     */
    public static List<Country> getCountries() {
        return countryList;
    }

    public static int getCountryId(int index) {
        for (Division divisions : Division.getDivisions()) {
            if (index == divisions.getDivisionId()) {
                return divisions.getCountryId();
            }
        }
        return 0;
    }

    /**
     * toString
     * @return Overrides the default toString() method to return the country name.
     */
    @Override
    public String toString() {
        return country;
    }

}
