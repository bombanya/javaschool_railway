package com.bombanya.javaschool_railway.services.trains;

import com.bombanya.javaschool_railway.dao.trains.TrainDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.trains.Train;
import com.bombanya.javaschool_railway.entities.trains.WagonType;
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
public class TrainService {

    private final TrainDAO dao;

    @Transactional
    public ServiceAnswer<Train> saveNew(Train train){
        if (train == null) return ServiceAnswerHelper.badRequest("train cannot be null");
        try{
            dao.save(train);
            return ServiceAnswerHelper.ok(train);
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswerHelper.badRequest("Constraint violation while creating train");
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Train>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }
}
