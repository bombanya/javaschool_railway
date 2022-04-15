package com.bombanya.javaschool_railway.services.schedule;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.RunUpdate;
import com.bombanya.javaschool_railway.entities.stationSchedule.StationScheduleInfo;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.routes.RouteService;
import com.bombanya.javaschool_railway.services.routes.RunService;
import com.bombanya.javaschool_railway.services.routes.RunUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationScheduleService {

    private final RouteService routeService;
    private final RunService runService;
    private final RunUpdateService runUpdateService;
    private final ScheduleUtils scheduleUtils;

    @Transactional(readOnly = true)
    public ServiceAnswer<List<StationScheduleInfo>> getStationSchedule(int stationId, LocalDate date){
        List<RunUpdate> runUpdates = runUpdateService.getByStationId(stationId).getServiceResult();
        return ServiceAnswerHelper.ok(routeService.getByStationId(stationId)
                .getServiceResult()
                .stream()
                .flatMap(route -> runService.getAllByRouteId(route.getId()).getServiceResult().stream())
                .map(run -> scheduleUtils.generateScheduleInfo(run, stationId, runUpdates.stream()
                        .filter(runUpdate -> runUpdate
                                .getRun()
                                .getId()
                                .equals(run.getId()))
                        .findFirst()))
                .filter(stationScheduleInfo -> stationScheduleInfo.getDeparture() != null &&
                        stationScheduleInfo.getDeparture().toLocalDate().equals(date) ||
                        stationScheduleInfo.getArrival() != null &&
                        stationScheduleInfo.getArrival().toLocalDate().equals(date))
                .collect(Collectors.toList()));
    }
}
