package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;

@Service
public class RunUtils {

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

    @Transactional(readOnly = true)
    public Optional<RouteStation> getLastStationOnRun(Run run){
        return run.getRoute()
                .getRouteStations()
                .stream()
                .max(Comparator.comparing(RouteStation::getSerialNumberOnTheRoute));
    }
}
