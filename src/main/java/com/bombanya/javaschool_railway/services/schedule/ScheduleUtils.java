package com.bombanya.javaschool_railway.services.schedule;

import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.routes.RunUpdate;
import com.bombanya.javaschool_railway.entities.stationSchedule.StationScheduleInfo;
import com.bombanya.javaschool_railway.entities.stationSchedule.TrainStatus;
import com.bombanya.javaschool_railway.services.routes.RunUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleUtils {

    private final RunUtils runUtils;

    @Transactional(readOnly = true)
    public StationScheduleInfo generateScheduleInfo(Run run, int stationId, Optional<RunUpdate> update){
        RouteStation finalStation = runUtils.getLastStationOnRun(run).get();
        RouteStation station = runUtils.getRouteStationFromRunByStationId(run, stationId)
                .getServiceResult();
        long arrivalDelta = 0;
        long departureDelta = 0;
        TrainStatus status = TrainStatus.ON_TIME;
        if (update.isPresent()) {
            status = TrainStatus.DELAYED;
            arrivalDelta = update.get().getArrivalDelta();
            departureDelta = update.get().getDepartureDelta();
        }
        if (checkIfStationCancelled(run, stationId)) status = TrainStatus.CANCELLED;
        return StationScheduleInfo.builder()
                .runId(run.getId())
                .trainId(run.getTrain().getId())
                .arrival(station.getStageArrival() == 0 && station.getStageDeparture() == 0
                        ? null
                        : runUtils.getStationLocalTimeArrival(run, station).plus(arrivalDelta, ChronoUnit.MINUTES))
                .departure(station.getStageArrival() != 0 && station.getStageDeparture() == 0
                        ? null
                        : runUtils.getStationLocalTimeDeparture(run, station).plus(departureDelta, ChronoUnit.MINUTES))
                .finalStationId(finalStation.getStation().getId())
                .finalStationName(finalStation.getStation().getName())
                .status(status)
                .build();
    }

    private boolean checkIfStationCancelled(Run run, int stationId) {
        return run.getCancelledStations()
                .stream()
                .anyMatch(station1 -> station1.getId().equals(stationId));
    }
}
