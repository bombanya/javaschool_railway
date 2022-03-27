package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.bombanya.javaschool_railway.entities.Passenger;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.services.PassengerService;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping("/new")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Passenger>> saveNewPassenger(@RequestBody Passenger passenger){
        return ServiceAnswerHelper.wrapIntoResponse(passengerService.saveNew(passenger));
    }

    @GetMapping("/all")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Passenger>>> getAllPassengers(){
        return ServiceAnswerHelper.wrapIntoResponse(passengerService.getAll());
    }


}
