package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.dao.routes.RunDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.trains.Train;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.trains.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RunService {

    private final RunDAO dao;
    private final RouteService routeService;
    private final TrainService trainService;

    @Transactional
    public ServiceAnswer<Run> saveNew(int routeId, int trainID, Instant startUtc) {
        ServiceAnswer<Route> route = routeService.getById(routeId);
        ServiceAnswer<Train> train = trainService.getById(trainID);
        if (!route.isSuccess()) return ServiceAnswerHelper.badRequest(route.getErrorMessage());
        if (!train.isSuccess()) return ServiceAnswerHelper.badRequest(train.getErrorMessage());
        if (route.getServiceResult().getRouteStations().size() == 0)
            return ServiceAnswerHelper.badRequest("You cannot plan empty route");
        long finish = route.getServiceResult()
                .getRouteStations()
                .stream()
                .max(Comparator.comparing(RouteStation::getSerialNumberOnTheRoute))
                .get()
                .getStageArrival();
        Run newRun = Run.builder()
                .route(route.getServiceResult())
                .train(train.getServiceResult())
                .startUtc(startUtc)
                .finishUtc(startUtc.plus(finish, ChronoUnit.MINUTES))
                .build();
        dao.save(newRun);
        return ServiceAnswerHelper.ok(newRun);
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Run>> getAll(){
        return ServiceAnswerHelper.ok(dao.findAll());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<Run> getById(int id){
        return dao.findById(id)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<Run>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such run")
                        .build());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Run>> getAllByRouteId(int routeId){
        return ServiceAnswerHelper.ok(dao.findByRouteId(routeId));
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<Run>> getAllByTrainId(int trainId){
        return ServiceAnswerHelper.ok(dao.findByTrainId(trainId));
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<RouteStation> getRouteStationFromRunBySettlId(Run run, int settlId){
        return run.getRoute()
                .getRouteStations()
                .stream()
                .filter(routeStation -> routeStation
                        .getStation()
                        .getSettlement()
                        .getId()
                        .equals(settlId))
                .findFirst()
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() ->
                        ServiceAnswerHelper.badRequest("No such settlement on the run"));
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<RouteStation> getRouteStationFromRunByStationId(Run run, int stationId){
        return run.getRoute()
                .getRouteStations()
                .stream()
                .filter(routeStation -> routeStation
                        .getStation()
                        .getId()
                        .equals(stationId))
                .findFirst()
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() ->
                        ServiceAnswerHelper.badRequest("No such station on the run"));
    }

    @Transactional(readOnly = true)
    public LocalDateTime getStationLocalTimeDeparture(Run run, RouteStation station){
        return run.getStartUtc()
                .plus(station.getStageDeparture(), ChronoUnit.MINUTES)
                .atZone(station.getStation().getSettlement().getTimeZone())
                .toLocalDateTime();
    }

    @Transactional(readOnly = true)
    public LocalDateTime getStationLocalTimeArrival(Run run, RouteStation station){
        return run.getStartUtc()
                .plus(station.getStageArrival(), ChronoUnit.MINUTES)
                .atZone(station.getStation().getSettlement().getTimeZone())
                .toLocalDateTime();
    }


}
