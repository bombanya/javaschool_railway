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
}
