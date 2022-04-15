package com.bombanya.javaschool_railway.dao.routes;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.routes.RunUpdate;
import com.bombanya.javaschool_railway.entities.routes.RunUpdateId;

import java.util.List;

public interface RunUpdateDAO extends DAO<RunUpdate, RunUpdateId> {
    List<RunUpdate> findByStation(int stationId);
}
