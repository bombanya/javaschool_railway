package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.geography.Settlement;

import java.util.Optional;

public interface SettlementDAO  extends DAO<Settlement, Integer> {

    Optional<Settlement> findByNames(String countryName, String regionName, String name);
}
