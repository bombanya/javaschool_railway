package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.geography.Station;

import java.util.List;

public interface StationDAO extends DAO<Station, Integer> {

    List<Station> findByNameOrSettlNameStartsWith(String nameStart);
}
