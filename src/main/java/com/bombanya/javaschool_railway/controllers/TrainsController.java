package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.trains.Seat;
import com.bombanya.javaschool_railway.entities.trains.Train;
import com.bombanya.javaschool_railway.entities.trains.Wagon;
import com.bombanya.javaschool_railway.entities.trains.WagonType;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.trains.SeatService;
import com.bombanya.javaschool_railway.services.trains.TrainService;
import com.bombanya.javaschool_railway.services.trains.WagonService;
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
    private final WagonService wagonService;
    private final TrainService trainService;

    @PostMapping("/wagontype/new/{name}/{toilets}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<WagonType>> saveNewWagonType(@PathVariable String name,
                                                                     @PathVariable int toilets){
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

    @PostMapping("/wagon/new/{wagonTypeId}/{number}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Wagon>>> saveNewWagons(@PathVariable int wagonTypeId,
                                                                    @PathVariable int number){
        return ServiceAnswerHelper.wrapIntoResponse(wagonService.saveNewWagons(wagonTypeId, number));
    }

    @GetMapping("/wagon/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Wagon>>> getAllWagons(){
        return ServiceAnswerHelper.wrapIntoResponse(wagonService.getAll());
    }

    @PostMapping("/train/new")
    @JsonView(JacksonView.TrainFullInfo.class)
    public ResponseEntity<ServiceAnswer<Train>> saveNewTrain(@RequestBody Train train){
        return ServiceAnswerHelper.wrapIntoResponse(trainService.saveNew(train));
    }

    @GetMapping("/train/all")
    @JsonView(JacksonView.TrainFullInfo.class)
    public ResponseEntity<ServiceAnswer<List<Train>>> getAllTrains(){
        return ServiceAnswerHelper.wrapIntoResponse(trainService.getAll());
    }
}
