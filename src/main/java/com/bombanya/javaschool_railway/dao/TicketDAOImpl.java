package com.bombanya.javaschool_railway.dao;

import com.bombanya.javaschool_railway.entities.Ticket;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDAOImpl implements TicketDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(noRollbackFor = PersistenceException.class)
    public void save(Ticket entity) {
        em.persist(entity);
    }

    @Override
    public Optional<Ticket> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getAllOccupiedOnRange(int runId, int serialFrom, int serialTo) {
        return em.createQuery("select t from Ticket t join fetch t.seat seat " +
                "join fetch t.wagon wagon where t.run.id = :runId and " +
                "not (t.finishSerial <= :serialFrom or t.startSerial >= :serialTo)",
                        Ticket.class)
                .setParameter("serialFrom", serialFrom)
                .setParameter("serialTo", serialTo)
                .setParameter("runId", runId)
                .getResultList();
    }
}
