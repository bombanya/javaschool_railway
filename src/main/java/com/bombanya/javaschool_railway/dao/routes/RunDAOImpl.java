package com.bombanya.javaschool_railway.dao.routes;

import com.bombanya.javaschool_railway.entities.routes.Run;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class RunDAOImpl implements RunDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Run entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Run> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.ofNullable(em.createQuery("select distinct r from Run r " +
                                "join fetch r.train t " +
                                "join fetch r.route route join fetch route.routeStations rs " +
                                "join fetch rs.station s join fetch s.settlement settl join " +
                                "fetch settl.region reg join fetch reg.country " +
                                "where r.id = :id",
                        Run.class)
                .setParameter("id", integer)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH ,false)
                .getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Run> findAll() {
        return em.createQuery("select r from Run r " +
                        "join fetch r.route join fetch r.train",
                Run.class)
                .getResultList();
    }

    @Override
    public List<Run> findByRouteId(int routeId) {
        return em.createQuery("select distinct run from Run run join fetch run.train " +
                "join fetch run.route r " +
                "join fetch r.routeStations rs join fetch rs.station s join fetch s.settlement settl " +
                "join fetch settl.region reg join fetch reg.country c " +
                "where r.id = :routeId",
                        Run.class)
                .setParameter("routeId", routeId)
                .getResultList();
    }

    @Override
    public List<Run> findByTrainId(int trainId) {
        return em.createQuery("select distinct run from Run run join fetch run.train " +
                                "join fetch run.route r " +
                                "join fetch r.routeStations rs join fetch rs.station s join fetch s.settlement settl " +
                                "join fetch settl.region reg join fetch reg.country c " +
                                "where run.train.id = :trainId",
                        Run.class)
                .setParameter("trainId", trainId)
                .getResultList();
    }
}
