package com.bombanya.javaschool_railway.services.trains;

import com.bombanya.javaschool_railway.dao.trains.WagonTypeDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Country;
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
public class WagonTypeService {

    private final WagonTypeDAO dao;

    @Transactional
    public ServiceAnswer<WagonType> saveNew(String name, Integer toilets){
        if (name == null || toilets == null) return ServiceAnswerHelper.badRequest("fields cannot be null");
        try{
            WagonType wagonType = new WagonType();
            wagonType.setName(name);
            wagonType.setToilets(toilets);
            dao.save(wagonType);
            return ServiceAnswerHelper.ok(wagonType);
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.<WagonType>builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Wagon type already exists")
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<WagonType>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<WagonType> getByName(String name){
        return dao.findByName(name)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<WagonType>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such wagon type")
                        .build());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<WagonType> getById(Integer id){
        return dao.findById(id)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<WagonType>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such wagon type")
                        .build());
    }
}
