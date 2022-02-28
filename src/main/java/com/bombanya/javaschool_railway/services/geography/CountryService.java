package com.bombanya.javaschool_railway.services.geography;

import com.bombanya.javaschool_railway.dao.geography.CountryDAO;
import com.bombanya.javaschool_railway.entities.geography.Country;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryDAO dao;

    public ServiceAnswer<?> saveNewCountry(String countryName){
        try{
            Country newCountry = new Country();
            newCountry.setName(countryName);
            dao.save(newCountry);
            return ServiceAnswer.builder()
                    .success(true)
                    .httpStatus(HttpStatus.CREATED)
                    .build();
        } catch (PersistenceException e){
            if (!e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Country already exists")
                    .build();
        }
    }

    public ServiceAnswer<List<Country>> getAllCountries(){
        return ServiceAnswer.<List<Country>>builder()
                .serviceResult(dao.findAll())
                .success(true)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ServiceAnswer<Country> getCountryByName(String name){
        Country country = dao.findByName(name);
        if (country == null){
            return ServiceAnswer.<Country>builder()
                    .success(false)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorMessage("No such country")
                    .build();
        }
        return ServiceAnswer.ok(country);
    }
}
