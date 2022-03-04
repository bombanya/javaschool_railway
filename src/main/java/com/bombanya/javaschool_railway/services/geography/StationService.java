package com.bombanya.javaschool_railway.services.geography;

import com.bombanya.javaschool_railway.dao.geography.StationDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Settlement;
import com.bombanya.javaschool_railway.entities.geography.Station;
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
public class StationService {

    private final StationDAO dao;
    private final SettlementService settlementService;

    @Transactional
    public ServiceAnswer<Station> saveNewBySettlId(Integer settlId, String name){
        if (name == null) return ServiceAnswerHelper.badRequest("name cannot be null");
        ServiceAnswer<Settlement> settlement = settlementService.getById(settlId);
        if (!settlement.isSuccess()){
            return ServiceAnswer.<Station>builder()
                    .success(false)
                    .httpStatus(settlement.getHttpStatus())
                    .errorMessage(settlement.getErrorMessage())
                    .build();
        }
        try{
            Station station = new Station();
            station.setName(name);
            station.setSettlement(settlement.getServiceResult());
            dao.save(station);
            return ServiceAnswerHelper.ok(station);
        } catch (PersistenceException e) {
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswer.<Station>builder()
                    .success(false)
                    .httpStatus(HttpStatus.CONFLICT)
                    .errorMessage("Station already exists")
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Station>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }
}
