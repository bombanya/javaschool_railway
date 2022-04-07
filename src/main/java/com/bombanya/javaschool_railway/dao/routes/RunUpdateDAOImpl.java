package com.bombanya.javaschool_railway.dao.routes;

import com.bombanya.javaschool_railway.entities.routes.RunUpdate;
import com.bombanya.javaschool_railway.entities.routes.RunUpdateId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class RunUpdateDAOImpl implements RunUpdateDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(RunUpdate entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RunUpdate> findById(RunUpdateId runUpdateId) {
        return em.createQuery("select ru from RunUpdate ru join fetch " +
                "ru.station join fetch ru.run where ru.id = :runUpdateId",
                RunUpdate.class)
                .setParameter("runUpdateId", runUpdateId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<RunUpdate> findAll() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RunUpdate> findByStation(int stationId) {
        return em.createQuery("select ru from RunUpdate ru join fetch ru.run " +
                "join fetch ru.station s where s.id = :stationId",
                RunUpdate.class)
                .setParameter("stationId", stationId)
                .getResultList();
    }
}
