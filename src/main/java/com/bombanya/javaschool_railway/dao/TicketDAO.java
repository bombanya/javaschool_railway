package com.bombanya.javaschool_railway.dao;

import com.bombanya.javaschool_railway.entities.Ticket;

import java.util.List;

public interface TicketDAO extends DAO<Ticket, Integer> {

    List<Ticket> getAllOccupiedOnRange(int runId, int serialFrom, int serialTo);
}
