package com.bombanya.javaschool_railway.dao.routes;

import com.bombanya.javaschool_railway.entities.geography.Station;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.RouteStationId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class RouteStationDAOImpl implements RouteStationDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(RouteStation entity) {
        entity.setRoute(em.find(Route.class, entity.getRoute().getId()));
        entity.setStation(em.find(Station.class, entity.getStation().getId()));
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RouteStation> findById(RouteStationId routeStationId) {
        if (routeStationId == null) return Optional.empty();
        return Optional.ofNullable(em.find(RouteStation.class, routeStationId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RouteStation> findAll() {
        return em.createQuery("select rs from RouteStation rs",
                RouteStation.class)
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveList(List<RouteStation> routeStations) {
        for (RouteStation routeStation : routeStations) {
            routeStation.setRoute(em.find(Route.class, routeStation.getRoute().getId()));
            routeStation.setStation(em.find(Station.class, routeStation.getStation().getId()));
            em.persist(routeStation);
        }
    }
}
