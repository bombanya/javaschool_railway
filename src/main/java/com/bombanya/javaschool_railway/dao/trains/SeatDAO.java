package com.bombanya.javaschool_railway.dao.trains;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.entities.trains.Seat;

import java.util.List;

public interface SeatDAO extends DAO<Seat, Integer> {

    void saveList(List<Seat> seats);
}
