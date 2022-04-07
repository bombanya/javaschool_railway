package com.bombanya.javaschool_railway.services.routes;

import com.bombanya.javaschool_railway.dao.routes.RunUpdateDAO;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.geography.Station;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.routes.RunUpdate;
import com.bombanya.javaschool_railway.entities.routes.RunUpdateId;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.geography.StationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RunUpdateService {

    private final RunUpdateDAO dao;
    private final RunService runService;
    private final StationService stationService;

    @Transactional
    public ServiceAnswer<RunUpdate> updateStationSchedule(int runId, int stationId,
                                                          long arrivalDelta, long departureDelta){
        ServiceAnswer<RunUpdate> runUpdateWrapper = getByIds(runId, stationId);
        if (runUpdateWrapper.isSuccess()){
            runUpdateWrapper.getServiceResult().setArrivalDelta(arrivalDelta);
            runUpdateWrapper.getServiceResult().setDepartureDelta(departureDelta);
            return runUpdateWrapper;
        }
        ServiceAnswer<Run> runWrapper = runService.getById(runId);
        ServiceAnswer<Station> stationWrapper = stationService.getById(stationId);
        if (!runWrapper.isSuccess()) return ServiceAnswerHelper.badRequest(runWrapper.getErrorMessage());
        if (!stationWrapper.isSuccess()) return ServiceAnswerHelper.badRequest(stationWrapper.getErrorMessage());
        Run run = runWrapper.getServiceResult();
        Station station = stationWrapper.getServiceResult();
        if (run.getRoute().getRouteStations()
                .stream()
                .noneMatch(routeStation -> routeStation.getStation().getId().equals(stationId))) {
            return ServiceAnswerHelper.badRequest("bad update");
        }
        RunUpdateId updateId = new RunUpdateId();
        updateId.setRunId(runId);
        updateId.setStationId(stationId);
        RunUpdate runUpdate = RunUpdate.builder()
                .id(updateId)
                .run(run)
                .station(station)
                .arrivalDelta(arrivalDelta)
                .departureDelta(departureDelta)
                .build();
        try{
            dao.save(runUpdate);
            return ServiceAnswerHelper.ok(runUpdate);
        } catch (PersistenceException e) {
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswerHelper.badRequest("bad run update");
        }
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<RunUpdate> getByIds(int runId, int stationId){
        RunUpdateId updateId = new RunUpdateId();
        updateId.setRunId(runId);
        updateId.setStationId(stationId);
        return dao.findById(updateId)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<RunUpdate>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such run update")
                        .build());
    }

    @Transactional(readOnly = true)
    public ServiceAnswer<List<RunUpdate>> getByStationId(int stationId){
        return ServiceAnswerHelper.ok(dao.findByStation(stationId));
    }
}
