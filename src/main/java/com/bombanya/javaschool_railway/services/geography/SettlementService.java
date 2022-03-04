package com.bombanya.javaschool_railway.services.geography;

import com.bombanya.javaschool_railway.dao.geography.SettlementDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Region;
import com.bombanya.javaschool_railway.entities.geography.Settlement;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementDAO dao;
    private final RegionService regionService;

    @Transactional
    public ServiceAnswer<Settlement> saveNew(String countryName,
                                             String regionName,
                                             String name,
                                             String timeZone){
        if (name == null || timeZone == null) return ServiceAnswerHelper.badRequest("fields cannot be null");
        ServiceAnswer<Region> region =
                regionService.getByNameAndCountryName(countryName, regionName);
        if (!region.isSuccess()){
            return ServiceAnswer.<Settlement>builder()
                    .success(false)
                    .httpStatus(region.getHttpStatus())
                    .errorMessage(region.getErrorMessage())
                    .build();
        }
        try{

            Settlement settlement = Settlement.builder()
                    .name(name)
                    .region(region.getServiceResult())
                    .timeZone(ZoneId.of(timeZone))
                    .build();
            dao.save(settlement);
            return ServiceAnswerHelper.ok(settlement);

        } catch (DateTimeException d){
            return ServiceAnswer.<Settlement>builder()
                    .success(false)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage("Incorrect time zone")
                    .build();
        } catch (PersistenceException e) {
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.<Settlement>builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Settlement already exists")
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Settlement>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<Settlement> getByNames(String countryName,
                                                String regionName,
                                                String name){
        return dao.findByNames(countryName, regionName, name)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Settlement>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such settlement")
                        .build());
    }
    
    @Transactional(readOnly = true)
    public ServiceAnswer<Settlement> getById(Integer id){
        return dao.findById(id)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Settlement>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such settlement")
                        .build());
    }
}
