package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Region;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class RegionDAOImpl implements RegionDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Region region) {
        em.persist(region);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Region> findById(Integer id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(em.find(Region.class, id,
                Collections.singletonMap("javax.persistence.fetchgraph",
                        em.getEntityGraph("region.fetch_country"))));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Region> findByNameAndCountryName(String name, String countryName) {
        return em.createQuery("select r from Region r join fetch " +
                "r.country c where r.name = :name and c.name = :countryName"
                        , Region.class)
                .setParameter("name", name)
                .setParameter("countryName", countryName)
                .getResultStream()
                .findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAll() {
        return em.createQuery("select r from Region r join fetch r.country"
                , Region.class)
                .getResultList();
    }
}
