package com.bombanya.javaschool_railway.dao.routes;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.RouteStationId;

import java.util.List;

public interface RouteStationDAO extends DAO<RouteStation, RouteStationId> {

    void saveList(List<RouteStation> routeStations);
}
