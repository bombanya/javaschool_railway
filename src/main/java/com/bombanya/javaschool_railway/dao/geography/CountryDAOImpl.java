package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Country;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CountryDAOImpl implements CountryDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Country country) {
        em.persist(country);
    }

    @Override
    @Transactional
    public Country findById(int id) {
        return em.find(Country.class, id);
    }

    @Override
    @Transactional
    public Country findByName(String name) {
        return em.unwrap(Session.class).bySimpleNaturalId(Country.class).load(name);
    }

    @Override
    @Transactional
    public List<Country> findAll() {
        return em.createQuery("select c from Country c", Country.class).getResultList();
    }
}
