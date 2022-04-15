package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.entities.routes.*;
import com.bombanya.javaschool_railway.services.routes.*;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RoutesController {

    private final RouteService routeService;
    private final RouteStationService routeStationService;
    private final RunService runService;
    private final RunSearchingService runSearchingService;
    private final RunUpdateService runUpdateService;

    //public
    @GetMapping("/public/run/search/check/{settlFromId}/{settlToId}/{date}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<RunSearchingResultDTO>>>
    searchForRuns(@PathVariable int settlFromId,
                  @PathVariable int settlToId,
                  @PathVariable
                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                          LocalDate date){
        return ServiceAnswerHelper.wrapIntoResponse(runSearchingService
                .searchForRuns(settlFromId, settlToId, date));
    }

    //private
    @PostMapping("/route/new")
    @JsonView(JacksonView.RouteFullInfo.class)
    public ResponseEntity<ServiceAnswer<Route>> saveNewRoute(){
        return ServiceAnswerHelper.wrapIntoResponse(routeService.saveNew());
    }

    @GetMapping("/route/all")
    @JsonView(JacksonView.RouteFullInfo.class)
    public ResponseEntity<ServiceAnswer<List<Route>>> getAllRoutes(){
        return ServiceAnswerHelper.wrapIntoResponse(routeService.getAll());
    }

    @GetMapping("/route/{id}")
    @JsonView(JacksonView.RouteFullInfo.class)
    public ResponseEntity<ServiceAnswer<Route>> getRouteById(@PathVariable int id){
        return ServiceAnswerHelper.wrapIntoResponse(routeService.getById(id));
    }

    @PostMapping("/routestation/new/list")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<RouteStation>>> saveNewListOfRouteStations(
            @RequestBody List<RouteStation> routeStations){
        return ServiceAnswerHelper.wrapIntoResponse(routeStationService.saveList(routeStations));
    }

    @GetMapping("/routestation/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<RouteStation>>> getAllRouteStations(){
        return ServiceAnswerHelper.wrapIntoResponse(routeStationService.getAll());
    }

    @PostMapping("/run/new/{routeId}/{trainId}/{startUtc}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Run>> saveNewRun(@PathVariable int routeId,
                                                         @PathVariable int trainId,
                                                         @PathVariable Instant startUtc){
        return ServiceAnswerHelper.wrapIntoResponse(runService.saveNew(routeId, trainId, startUtc));
    }

    @GetMapping("/run/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Run>>> getAllRuns(){
        return ServiceAnswerHelper.wrapIntoResponse(runService.getAll());
    }

    @PostMapping("/run/cancelstation/{runId}/{stationId}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Void>> cancelStationOnRun(@PathVariable int runId,
                                                                  @PathVariable int stationId){
        return ServiceAnswerHelper.wrapIntoResponse(runService.cancelStation(runId, stationId));
    }

    @GetMapping("/run/search/noticketchecking/{settlFromId}/{settleToId}/{date}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Run>>>
    searchForRunsNoTicketsChecking(@PathVariable int settlFromId,
                                   @PathVariable int settleToId,
                                   @PathVariable
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                           LocalDate date){
        return ServiceAnswerHelper.wrapIntoResponse(runSearchingService
                .getByStartAndFinishSettlementsAndStartDay(settlFromId, settleToId, date));
    }

    @GetMapping("/run/trainschedule/{trainId}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<RunSearchingResultDTO>>> getTrainSchedule(@PathVariable int trainId){
        return ServiceAnswerHelper.wrapIntoResponse(runSearchingService.getTrainSchedule(trainId));
    }

    @PostMapping("/run/updatestation/{runId}/{stationId}/{arrivalDelta}/{departureDelta}")
    @JsonView(JacksonView.MinimalInfo.class)
    public ResponseEntity<ServiceAnswer<RunUpdate>> updateStationOnRun(@PathVariable int runId,
                                                                       @PathVariable int stationId,
                                                                       @PathVariable long arrivalDelta,
                                                                       @PathVariable long departureDelta){
        return ServiceAnswerHelper.wrapIntoResponse(runUpdateService.updateStationSchedule(runId,
                stationId, arrivalDelta, departureDelta));
    }
}
