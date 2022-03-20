package com.bombanya.javaschool_railway.services.trains;

import com.bombanya.javaschool_railway.dao.trains.SeatDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.trains.Seat;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatDAO dao;

    @Transactional
    public ServiceAnswer<Seat> saveNew(Seat seat){
        try{
            dao.save(seat);
            return ServiceAnswerHelper.ok(seat);
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.<Seat>builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Could not save such seat")
                    .build();
        }
    }

    @Transactional
    public ServiceAnswer<List<Seat>> saveList(List<Seat> seats){
        try{
            dao.saveList(seats);
            return ServiceAnswerHelper.ok(seats);
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.<List<Seat>>builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Constraint violation while saving one of the objects")
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Seat>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<Seat> getById(int id){
        return dao.findById(id)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Seat>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such seat")
                        .build());
    }
}
