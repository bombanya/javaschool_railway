package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.geography.Region;

import java.util.Optional;

public interface RegionDAO extends DAO<Region, Integer> {

    Optional<Region> findByNameAndCountryName(String name, String countryName);

}
