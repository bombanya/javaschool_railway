package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Settlement;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class SettlementDAOImpl implements SettlementDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Settlement settlement) {
        em.persist(settlement);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Settlement> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.ofNullable(em.createQuery("select s from Settlement s " +
                "join fetch s.region r join fetch r.country c " +
                "where s.id = :id",
                        Settlement.class)
                .setParameter("id", integer)
                .getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Settlement> findByNames(String countryName, String regionName, String name) {
        return Optional.ofNullable(em.createQuery("select s from Settlement s " +
                "join fetch s.region r join fetch r.country c " +
                "where s.name = :name and r.name = :regionName " +
                "and c.name = :countryName",
                Settlement.class)
                .setParameter("name", name)
                .setParameter("regionName", regionName)
                .setParameter("countryName", countryName)
                .getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Settlement> findByNameStartsWith(String nameStart) {
        nameStart = (nameStart + "%").toLowerCase();
        return em.createQuery("select s from Settlement s " +
                "join fetch s.region r join fetch r.country c " +
                "where lower(s.name) like :nameStart",
                        Settlement.class)
                .setParameter("nameStart", nameStart)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Settlement> findAll() {
        return em.createQuery("select s from Settlement s " +
                "join fetch s.region r join fetch r.country c ",
                Settlement.class)
                .getResultList();
    }
}
