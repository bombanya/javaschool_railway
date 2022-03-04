package com.bombanya.javaschool_railway.dao.trains;

import com.bombanya.javaschool_railway.entities.trains.WagonType;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class WagonTypeDAOImpl implements WagonTypeDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(WagonType entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WagonType> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.ofNullable(em.find(WagonType.class, integer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WagonType> findAll() {
        return em.createQuery("select wt from WagonType wt",
                WagonType.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WagonType> findByName(String name) {
        return Optional.ofNullable(em.unwrap(Session.class)
                .bySimpleNaturalId(WagonType.class)
                .load(name));
    }
}
