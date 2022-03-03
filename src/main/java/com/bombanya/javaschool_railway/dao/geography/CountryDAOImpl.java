package com.bombanya.javaschool_railway.dao.geography;

import com.bombanya.javaschool_railway.entities.geography.Country;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class CountryDAOImpl implements CountryDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Country country) {
        em.persist(country);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Country> findById(int id) {
        return Optional.ofNullable(em.find(Country.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Country> findByName(String name) {
        return Optional.ofNullable(em.unwrap(Session.class)
                .bySimpleNaturalId(Country.class)
                .load(name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> findAll() {
        return em.createQuery("select c from Country c",
                        Country.class)
                .getResultList();
    }
}
