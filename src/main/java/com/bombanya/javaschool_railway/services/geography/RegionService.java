package com.bombanya.javaschool_railway.services.geography;

import com.bombanya.javaschool_railway.dao.geography.RegionDAO;
import com.bombanya.javaschool_railway.entities.geography.Country;
import com.bombanya.javaschool_railway.entities.geography.Region;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionDAO dao;
    private final CountryService countryService;

    public ServiceAnswer<?> saveNewRegion(String countryName, String name){
        ServiceAnswer<Country> countryCheck = countryService.getCountryByName(countryName);
        if (!countryCheck.isSuccess()) return countryCheck;
        try{
            Region region = new Region();
            region.setName(name);
            region.setCountry(countryCheck.getServiceResult());
            dao.save(region);
            return ServiceAnswer.ok(null);
        } catch (PersistenceException e) {
            if (!e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Region already exists")
                    .build();
        }
    }

    public ServiceAnswer<List<Region>> getAllRegions(){
        return ServiceAnswer.ok(dao.findAll());
    }

    public ServiceAnswer<Region> findByNameAndCountryName(String countryName, String name){
        Region region = dao.findByNameAndCountryName(name, countryName);
        if (region == null){
            return ServiceAnswer.<Region>builder()
                    .success(false)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorMessage("No such region")
                    .build();
        }
        return ServiceAnswer.ok(region);
    }
}
