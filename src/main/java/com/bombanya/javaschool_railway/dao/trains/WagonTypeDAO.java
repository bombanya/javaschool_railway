package com.bombanya.javaschool_railway.dao.trains;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.trains.WagonType;

import java.util.Optional;

public interface WagonTypeDAO extends DAO<WagonType, Integer> {

    Optional<WagonType> findByName(String name);
}
