package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Region;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RegionDAOImpl implements RegionDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Region region) {
        em.persist(region);
    }

    @Override
    @Transactional
    public Region findById(int id) {
        return em.find(Region.class, id);
    }

    @Override
    @Transactional
    public Region findByNameAndCountryName(String name, String countryName) {
        return em.createQuery("select r from Region r join fetch " +
                "Country c where r.name = :name and c.name = :countryName"
                        , Region.class)
                .setParameter("name", name)
                .setParameter("countryName", countryName)
                .getSingleResult();
    }

    @Override
    @Transactional
    public List<Region> findAll() {
        return em.createQuery("select r from Region r", Region.class).getResultList();
    }
}
