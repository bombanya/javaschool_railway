package com.bombanya.javaschool_railway.services.geography;

import com.bombanya.javaschool_railway.dao.geography.CountryDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Country;
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
public class CountryService {

    private final CountryDAO dao;

    @Transactional
    public ServiceAnswer<Country> saveNew(String countryName){
        if (countryName == null) return ServiceAnswerHelper.badRequest("name cannot be null");
        try{
            Country newCountry = new Country();
            newCountry.setName(countryName);
            dao.save(newCountry);
            return ServiceAnswer.<Country>builder()
                    .serviceResult(newCountry)
                    .success(true)
                    .httpStatus(HttpStatus.CREATED)
                    .build();
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.<Country>builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Country already exists")
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Country>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<Country> getByName(String name){
        return dao.findByName(name)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Country>builder()
                .success(false)
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessage("No such country")
                .build());
    }
}
