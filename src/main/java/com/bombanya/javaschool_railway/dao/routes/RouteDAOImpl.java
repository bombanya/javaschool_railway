package com.bombanya.javaschool_railway.dao.routes;

import com.bombanya.javaschool_railway.entities.routes.Route;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class RouteDAOImpl implements RouteDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Route entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Route> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.ofNullable(em.createQuery("select distinct r from Route r " +
                "join fetch r.train t left join fetch r.routeStations rs where r.id = :id",
                Route.class)
                .setParameter("id", integer)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> findAll() {
        return em.createQuery("select distinct r from Route r " +
                        "join fetch r.train t left join fetch r.routeStations rs",
                Route.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }

    @Override
    public List<Route> findByTwoSettlements(int settl1, int settl2) {
        return em.createQuery("select distinct r from Route r join fetch " +
                                "r.routeStations rs join fetch rs.station s " +
                                "join fetch s.settlement where " +
                                "r.id in (select r1.id from Route r1 join r1.routeStations " +
                                "rs1 where rs1.station.settlement.id = :settl1) " +
                                "and r.id in (select r2.id from Route r2 join r2.routeStations " +
                                "rs2 where rs2.station.settlement.id = :settl2)",
                        Route.class)
                .setParameter("settl1", settl1)
                .setParameter("settl2", settl2)
                .getResultList();
    }
}
