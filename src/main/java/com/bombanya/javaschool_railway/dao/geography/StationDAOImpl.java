package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Station;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class StationDAOImpl implements StationDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Station entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Station> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.ofNullable(em.createQuery("select s from Station s " +
                "join fetch s.settlement settl join fetch settl.region r " +
                "join fetch r.country c where s.id = :id",
                Station.class)
                .setParameter("id", integer)
                .getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Station> findAll() {
        return em.createQuery("select s from Station s " +
                                "join fetch s.settlement settl join fetch settl.region r " +
                                "join fetch r.country c",
                        Station.class)
                .getResultList();
    }
}
