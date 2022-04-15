package com.bombanya.javaschool_railway.dao.trains;

import com.bombanya.javaschool_railway.entities.trains.Train;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainDAOImpl implements TrainDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Train entity) {
        em.persist(entity);
    }

    @Override
    public Optional<Train> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return em.createQuery("select distinct t from Train t " +
                "join fetch t.wagons w join fetch w.wagonType wt where t.id = :id",
                        Train.class)
                .setParameter("id", integer)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Train> findAll() {
        return em.createQuery("select distinct t from Train t " +
                        "join fetch t.wagons w join fetch w.wagonType wt",
                Train.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }
}
