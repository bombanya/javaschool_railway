package com.bombanya.javaschool_railway.dao;

import com.bombanya.javaschool_railway.entities.Passenger;

import java.util.Optional;

public interface PassengerDAO extends DAO<Passenger, Integer> {

    Optional<Passenger> findByPassport(String passportId);
}
