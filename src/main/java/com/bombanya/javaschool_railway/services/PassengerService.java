package com.bombanya.javaschool_railway.services;

import com.bombanya.javaschool_railway.dao.PassengerDAO;
import com.bombanya.javaschool_railway.entities.Passenger;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerDAO dao;

    @Transactional
    public ServiceAnswer<Passenger> saveNew(Passenger passenger) {
        try{
            dao.save(passenger);
            return ServiceAnswerHelper.ok(passenger);
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswerHelper.badRequest("Constraint violation while saving passenger");
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Passenger>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<Passenger> getById(int id){
        return dao.findById(id)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Passenger>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such passenger")
                        .build());
    }

    @Transactional
    public ServiceAnswer<Integer> getPassengerCode(Passenger passenger){
        Optional<Passenger> fromDb = dao.findByPassport(passenger.getPassportId());
        if (fromDb.isPresent()){
            if (passenger.equals(fromDb.get())){
                return ServiceAnswerHelper.ok(fromDb.get().getId());
            }
            else return ServiceAnswerHelper.badRequest("Incorrect data");
        }
        else{
            ServiceAnswer<Passenger> newPassengerWrapper = saveNew(passenger);
            if (newPassengerWrapper.isSuccess()) return ServiceAnswerHelper.ok(passenger.getId());
            else return ServiceAnswerHelper.badRequest(newPassengerWrapper.getErrorMessage());
        }
    }

}
