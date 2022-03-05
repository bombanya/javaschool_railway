package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.dao.routes.RunDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RunService {

    private final RunDAO dao;
    private final RouteService routeService;

    @Transactional
    public ServiceAnswer<Run> saveNew(int routeId, Instant startUtc) {
        ServiceAnswer<Route> route = routeService.getById(routeId);
        if (!route.isSuccess()) return ServiceAnswerHelper.badRequest(route.getErrorMessage());
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
    public ServiceAnswer<List<Run>> getByStartAndFinishSettlementsAndStartDay(int settlFrom,
                                                                              int settlTo,
                                                                              LocalDate date){
        return ServiceAnswerHelper.ok(routeService.getByStartAndFinishSettlements(settlFrom, settlTo)
                .getServiceResult()
                .stream()
                .flatMap(route -> dao.findByRouteId(route.getId()).stream())
                .filter(run -> {
                    RouteStation startStation = run.getRoute()
                            .getRouteStations()
                            .stream()
                            .filter(routeStation -> routeStation
                                    .getStation()
                                    .getSettlement()
                                    .getId()
                                    .equals(settlFrom))
                            .findFirst()
                            .get();
                    return run.getStartUtc().plus(startStation
                            .getStageDeparture(), ChronoUnit.MINUTES)
                            .atZone(startStation.getStation().getSettlement().getTimeZone())
                            .toLocalDate()
                            .equals(date);
                })
                .collect(Collectors.toList()));
    }
}
