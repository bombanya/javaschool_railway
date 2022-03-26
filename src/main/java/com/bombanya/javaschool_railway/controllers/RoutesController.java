package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.routes.RunSearchingResultDTO;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.routes.RouteService;
import com.bombanya.javaschool_railway.services.routes.RouteStationService;
import com.bombanya.javaschool_railway.services.routes.RunSearchingService;
import com.bombanya.javaschool_railway.services.routes.RunService;
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
@CrossOrigin(origins = {"http://localhost:3000"})
public class RoutesController {

    private final RouteService routeService;
    private final RouteStationService routeStationService;
    private final RunService runService;
    private final RunSearchingService runSearchingService;

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

    @GetMapping("/run/search/check/{settlFromId}/{settlToId}/{date}")
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

    @GetMapping("/run/trainschedule/{trainId}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<RunSearchingResultDTO>>> getTrainSchedule(@PathVariable int trainId){
        return ServiceAnswerHelper.wrapIntoResponse(runSearchingService.getTrainSchedule(trainId));
    }
}
