package com.bombanya.javaschool_railway.dao.routes;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.routes.Run;

import java.util.List;

public interface RunDAO extends DAO<Run, Integer> {

    List<Run> findByRouteId(int routeId);

    List<Run> findByTrainId(int trainId);
}
