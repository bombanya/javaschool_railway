package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Region;

import java.util.List;
import java.util.Optional;

public interface RegionDAO {

    void save(Region region);

    Optional<Region> findById(int id);

    Optional<Region> findByNameAndCountryName(String name, String countryName);

    List<Region> findAll();
}
