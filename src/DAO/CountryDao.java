package DAO;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Country;

public interface CountryDao {
    @FXML
    public ObservableList<Country> getAllCountries();
}
