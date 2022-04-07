package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.dao.routes.RouteDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.trains.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteDAO dao;

    @Transactional
    public ServiceAnswer<Route> saveNew(){
        Route newRoute = new Route();
        newRoute.setRouteStations(new ArrayList<>());
        dao.save(newRoute);
        return ServiceAnswerHelper.ok(newRoute);
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Route>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Route>> getByStationId(int stationId){
        return ServiceAnswerHelper.ok(dao.findByStation(stationId));
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<Route> getById(int id){
        return dao.findById(id)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Route>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such route")
                        .build());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Route>> getByStartAndFinishSettlements(int settlFrom, int settlTo){
        return ServiceAnswerHelper.ok(dao.findByTwoSettlements(settlFrom, settlTo)
                .stream()
                .filter(route -> {
            int serialFrom = -1;
            int serialTo = -1;
            for (RouteStation routeStation : route.getRouteStations()) {
                if (routeStation.getStation().getSettlement().getId().equals(settlFrom)){
                    serialFrom = routeStation.getSerialNumberOnTheRoute();
                }
                if (routeStation.getStation().getSettlement().getId().equals(settlTo)){
                    serialTo = routeStation.getSerialNumberOnTheRoute();
                }
                if (serialFrom != -1 && serialTo != -1) break;
            }
            return serialFrom < serialTo;
        })
                .collect(Collectors.toList()));
    }
}
