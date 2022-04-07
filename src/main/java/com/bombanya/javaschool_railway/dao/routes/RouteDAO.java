package com.bombanya.javaschool_railway.dao.routes;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.routes.Route;

import java.util.List;

public interface RouteDAO extends DAO<Route, Integer> {

    List<Route> findByTwoSettlements(int settl1, int settl2);
      List<Route> findByStation(int station);
}
