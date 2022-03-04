package com.bombanya.javaschool_railway.services.geography;

import com.bombanya.javaschool_railway.dao.geography.RegionDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Country;
import com.bombanya.javaschool_railway.entities.geography.Region;
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
public class RegionService {

    private final RegionDAO dao;
    private final CountryService countryService;

    @Transactional
    public ServiceAnswer<Region> saveNew(String countryName, String name){
        ServiceAnswer<Country> countryCheck = countryService.getByName(countryName);
        if (!countryCheck.isSuccess()) return ServiceAnswer.<Region>builder()
                .success(false)
                .errorMessage(countryCheck.getErrorMessage())
                .httpStatus(countryCheck.getHttpStatus())
                .build();
        try{
            Region region = new Region();
            region.setName(name);
            region.setCountry(countryCheck.getServiceResult());
            dao.save(region);
            return ServiceAnswerHelper.ok(region);
        } catch (PersistenceException e) {
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.<Region>builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Region already exists")
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Region>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<Region> getByNameAndCountryName(String countryName, String name){
        return dao.findByNameAndCountryName(name, countryName)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Region>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such region")
                        .build());
    }
}
