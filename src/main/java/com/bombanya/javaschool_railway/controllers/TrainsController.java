package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.JacksonView;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.trains.Seat;
import com.bombanya.javaschool_railway.entities.trains.WagonType;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.trains.SeatService;
import com.bombanya.javaschool_railway.services.trains.WagonTypeService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainsController {

    private final WagonTypeService wagonTypeService;
    private final SeatService seatService;

    @PostMapping("/wagontype/new/{name}/{toilets}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<WagonType>> saveNewWagonType(@PathVariable String name,
                                                                     @PathVariable Integer toilets){
        return ServiceAnswerHelper.wrapIntoResponse(wagonTypeService.saveNew(name, toilets));
    }

    @GetMapping("/wagontype/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<WagonType>>> getAllWagonTypes(){
        return ServiceAnswerHelper.wrapIntoResponse(wagonTypeService.getAll());
    }

    @PostMapping("/seat/new/single")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Seat>> saveNewSeat(@RequestBody Seat seat){
        return ServiceAnswerHelper.wrapIntoResponse(seatService.saveNew(seat));
    }

    @PostMapping("/seat/new/list")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Seat>>> saveNewListOfSeats(@RequestBody List<Seat> seats){
        return ServiceAnswerHelper.wrapIntoResponse(seatService.saveList(seats));
    }

    @GetMapping("/seat/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Seat>>> getAllSeats(){
        return ServiceAnswerHelper.wrapIntoResponse(seatService.getAll());
    }
}
