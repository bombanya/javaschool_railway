package com.bombanya.javaschool_railway.services;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.routes.RunUpdate;
import com.bombanya.javaschool_railway.entities.stationSchedule.StationScheduleInfo;
import com.bombanya.javaschool_railway.entities.stationSchedule.TrainStatus;
import com.bombanya.javaschool_railway.services.routes.RouteService;
import com.bombanya.javaschool_railway.services.routes.RunService;
import com.bombanya.javaschool_railway.services.routes.RunUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationScheduleService {

    private final RouteService routeService;
    private final RunService runService;
    private final RunUpdateService runUpdateService;

    @Transactional(readOnly = true)
    public ServiceAnswer<List<StationScheduleInfo>> getStationSchedule(int stationId, LocalDate date){
        List<RunUpdate> runUpdates = runUpdateService.getByStationId(stationId).getServiceResult();
        return ServiceAnswerHelper.ok(routeService.getByStationId(stationId)
                .getServiceResult()
                .stream()
                .flatMap(route -> runService.getAllByRouteId(route.getId()).getServiceResult().stream())
                .map(run -> generateScheduleInfo(run, stationId, runUpdates.stream()
                        .filter(runUpdate -> runUpdate
                                .getRun()
                                .getId()
                                .equals(run.getId()))
                        .findFirst()
                        .orElse(null)))
                .filter(stationScheduleInfo -> stationScheduleInfo.getDeparture() != null &&
                        stationScheduleInfo.getDeparture().toLocalDate().equals(date) ||
                        stationScheduleInfo.getArrival() != null &&
                        stationScheduleInfo.getArrival().toLocalDate().equals(date))
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public StationScheduleInfo generateScheduleInfo(Run run, int stationId, RunUpdate update){
        RouteStation finalStation = runService.getLastStationOnRun(run).get();
        RouteStation station = runService.getRouteStationFromRunByStationId(run, stationId)
                .getServiceResult();
        long arrivalDelta = 0;
        long departureDelta = 0;
        TrainStatus status = TrainStatus.ON_TIME;
        if (update != null) {
            status = TrainStatus.DELAYED;
            arrivalDelta = update.getArrivalDelta();
            departureDelta = update.getDepartureDelta();
        }
        if (run.getCancelledStations()
                .stream()
                .anyMatch(station1 -> station1.getId().equals(stationId))) status = TrainStatus.CANCELLED;
        return StationScheduleInfo.builder()
                .runId(run.getId())
                .trainId(run.getTrain().getId())
                .arrival(station.getStageArrival() == 0 && station.getStageDeparture() == 0
                        ? null
                        : runService.getStationLocalTimeArrival(run, station).plus(arrivalDelta, ChronoUnit.MINUTES))
                .departure(station.getStageArrival() != 0 && station.getStageDeparture() == 0
                        ? null
                        : runService.getStationLocalTimeDeparture(run, station).plus(departureDelta, ChronoUnit.MINUTES))
                .finalStationId(finalStation.getStation().getId())
                .finalStationName(finalStation.getStation().getName())
                .status(status)
                .build();
    }
}
