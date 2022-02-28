package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Region;

import java.util.List;

public interface RegionDAO {

    void save(Region region);

    Region findById(int id);

    Region findByNameAndCountryName(String name, String countryName);

    List<Region> findAll();
}
