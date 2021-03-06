package com.bombanya.javaschool_railway.security.dao;

import com.bombanya.javaschool_railway.security.entities.UserRole;
import com.bombanya.javaschool_railway.security.entities.UserRoleEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(UserRoleEntity entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserRoleEntity> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.ofNullable(em.find(UserRoleEntity.class, integer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRoleEntity> findAll() {
        return em.createQuery("select role from UserRoleEntity role",
                UserRoleEntity.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserRoleEntity> findByName(String name) {
        return Optional.ofNullable(em.unwrap(Session.class)
                .bySimpleNaturalId(UserRoleEntity.class)
                .load(UserRole.valueOf(name)));
    }
}
