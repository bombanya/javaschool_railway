package com.bombanya.javaschool_railway.security.dao;

import com.bombanya.javaschool_railway.security.entities.UserAccount;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAccountDAOImpl implements UserAccountDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(UserAccount entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAccount> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return em.createQuery("select distinct u from UserAccount u " +
                        "join fetch u.roles where u.id = :id",
                UserAccount.class)
                .setParameter("id", integer)
                .getResultStream()
                .findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAccount> findAll() {
        return em.createQuery("select distinct u from UserAccount u " +
                "join fetch u.roles",
                UserAccount.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAccount> findByUsername(String username) {
        return em.createQuery("select distinct u from UserAccount u " +
                "join fetch u.roles where u.username = :username",
                        UserAccount.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }
}
