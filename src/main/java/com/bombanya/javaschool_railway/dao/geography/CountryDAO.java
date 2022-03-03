package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Country;

import java.util.List;
import java.util.Optional;

public interface CountryDAO {

    void save(Country country);

    Optional<Country> findById(int id);

    Optional<Country> findByName(String name);

    List<Country> findAll();

}
