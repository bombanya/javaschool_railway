package com.bombanya.javaschool_railway.dao.trains;

import com.bombanya.javaschool_railway.entities.trains.Seat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class SeatDAOImpl implements SeatDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Seat entity) {
        em.persist(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveList(List<Seat> seats) {
        for (Seat seat : seats) {
            em.persist(seat);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seat> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return Optional.ofNullable(em.createQuery("select s from Seat s " +
                "join fetch s.wagonType wt where s.id = :id",
                        Seat.class)
                .setParameter("id", integer)
                .getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> findAll() {
        return em.createQuery("select s from Seat s " +
                        "join fetch s.wagonType wt",
                Seat.class)
                .getResultList();
    }
}
