package com.bombanya.javaschool_railway.dao;

import com.bombanya.javaschool_railway.entities.Ticket;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
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
    @Transactional(readOnly = true)
    public Optional<Ticket> findById(Integer integer) {
        if (integer == null) return Optional.empty();
        return em.createQuery("select t from Ticket t join fetch t.run r " +
                "join fetch t.seat s join fetch t.wagon w join fetch " +
                "t.passenger p join fetch t.startStation join fetch t.finishStation " +
                "join fetch w.wagonType where t.id = :id",
                Ticket.class)
                .setParameter("id", integer)
                .getResultStream()
                .findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        return em.createQuery("select t from Ticket t join fetch t.run r " +
                                "join fetch t.seat s join fetch t.wagon w join fetch " +
                                "t.passenger p join fetch t.startStation join fetch t.finishStation " +
                                "join fetch w.wagonType",
                        Ticket.class)
                .getResultList();
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveList(List<Ticket> tickets) {
        for (Ticket ticket: tickets){
            em.persist(ticket);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getAllRunTickets(int runId) {
        return em.createQuery("select t from Ticket t join fetch t.run r " +
                "join fetch t.seat s join fetch t.wagon w join fetch " +
                "t.passenger p join fetch t.startStation join fetch t.finishStation " +
                "join fetch w.wagonType " +
                "where r.id = :runId",
                        Ticket.class)
                .setParameter("runId", runId)
                .getResultList();
    }
}
