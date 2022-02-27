package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Country;

import java.util.List;

public interface CountryDAO {

    void save(Country country);

    Country findById(int id);

    Country findByName(String name);

    List<Country> findAll();

}
