package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.JacksonView;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.routes.Route;
import com.bombanya.javaschool_railway.entities.routes.RouteStation;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.routes.RouteService;
import com.bombanya.javaschool_railway.services.routes.RouteStationService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RoutesController {

    private final RouteService routeService;
    private final RouteStationService routeStationService;

    @PostMapping("/route/new/{trainId}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Route>> saveNewRoute(@PathVariable int trainId){
        return ServiceAnswerHelper.wrapIntoResponse(routeService.saveNew(trainId));
    }

    @GetMapping("/route/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Route>>> getAllRoutes(){
        return ServiceAnswerHelper.wrapIntoResponse(routeService.getAll());
    }

    @PostMapping("/routestation/new/list")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<RouteStation>>> saveNewListOfRouteStations(
            @RequestBody List<RouteStation> routeStations){
        return ServiceAnswerHelper.wrapIntoResponse(routeStationService.saveList(routeStations));
    }

    @GetMapping("/routestations/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<RouteStation>>> getAllRouteStations(){
        return ServiceAnswerHelper.wrapIntoResponse(routeStationService.getAll());
    }
}
