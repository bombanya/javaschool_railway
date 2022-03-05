package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.dao.routes.RouteStationDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteStationService {

    private final RouteStationDAO dao;

    @Transactional
    public ServiceAnswer<RouteStation> saveNew(RouteStation routeStation){
        try{
            dao.save(routeStation);
            return ServiceAnswerHelper.ok(routeStation);
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswerHelper.badRequest("Could not save such route station");
        }
    }

    @Transactional
    public ServiceAnswer<List<RouteStation>> saveList(List<RouteStation> routeStations){
        try{
            dao.saveList(routeStations);
            return ServiceAnswerHelper.ok(routeStations);
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswerHelper.badRequest("Constraint violation while saving one of the route stations");
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<RouteStation>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }


}
