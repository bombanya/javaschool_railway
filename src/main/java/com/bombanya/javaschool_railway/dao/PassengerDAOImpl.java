package com.bombanya.javaschool_railway.dao;

import com.bombanya.javaschool_railway.entities.Passenger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class PassengerDAOImpl implements PassengerDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Passenger entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Passenger> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.ofNullable(em.find(Passenger.class, integer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findAll() {
        return em.createQuery("select p from Passenger p", Passenger.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Passenger> findByPassport(String passportId) {
        return Optional.ofNullable(em.unwrap(Session.class)
                .bySimpleNaturalId(Passenger.class)
                .load(passportId));
    }
}
