package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.geography.Country;

import java.util.Optional;

public interface CountryDAO extends DAO<Country, Integer> {

    Optional<Country> findByName(String name);

}
