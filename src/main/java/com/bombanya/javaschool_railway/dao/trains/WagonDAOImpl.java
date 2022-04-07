package com.bombanya.javaschool_railway.dao.trains;

import com.bombanya.javaschool_railway.entities.trains.Wagon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class WagonDAOImpl implements WagonDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Wagon entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Wagon> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return em.createQuery("select w from Wagon w " +
                "join fetch w.wagonType wt where w.id = :id",
                Wagon.class)
                .setParameter("id", integer)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Wagon> findAll() {
        return em.createQuery("select w from Wagon w " +
                        "join fetch w.wagonType wt", Wagon.class)
                .getResultList();
    }
}
