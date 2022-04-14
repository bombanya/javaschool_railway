package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.dao.routes.RunDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Station;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.trains.Train;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.geography.StationService;
import com.bombanya.javaschool_railway.services.schedule.ScheduleNotifier;
import com.bombanya.javaschool_railway.services.trains.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RunService {

    private final RunDAO dao;
    private final RouteService routeService;
    private final TrainService trainService;
    private final StationService stationService;
    private final ScheduleNotifier notifier;

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
                .cancelledStations(new ArrayList<>())
                .build();
        dao.save(newRun);
        notifier.notifyAllStationsNewRun(newRun);
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

    @Transactional
    public ServiceAnswer<Void> cancelStation(int runId, int stationId){
        ServiceAnswer<Run> runWrapper = getById(runId);
        ServiceAnswer<Station> stationWrapper = stationService.getById(stationId);
        if (!runWrapper.isSuccess()) return ServiceAnswerHelper.badRequest(runWrapper.getErrorMessage());
        if (!stationWrapper.isSuccess()) return ServiceAnswerHelper.badRequest(stationWrapper.getErrorMessage());
        Run run = runWrapper.getServiceResult();
        Station station = stationWrapper.getServiceResult();
        boolean stationIsOnRoute = run.getRoute()
                .getRouteStations()
                .stream()
                .anyMatch(routeStation -> routeStation.getStation().getId().equals(station.getId()));
        boolean isNotAlreadyCancelled = run.getCancelledStations()
                .stream()
                .noneMatch(cancelled -> cancelled.getId().equals(station.getId()));
        if (stationIsOnRoute && isNotAlreadyCancelled){
            run.getCancelledStations().add(station);
            notifier.notifyJmsClients(run, stationId, Optional.empty());
            return ServiceAnswerHelper.ok(null);
        }
        else return ServiceAnswerHelper.badRequest(null);
    }
}
