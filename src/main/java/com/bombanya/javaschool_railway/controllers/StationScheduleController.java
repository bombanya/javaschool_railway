package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.stationSchedule.StationScheduleInfo;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.StationScheduleService;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class StationScheduleController {

    private final StationScheduleService stationScheduleService;

    //public
    @GetMapping("/public/{stationId}/{date}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<StationScheduleInfo>>>
    getSchedule(@PathVariable int stationId,
                @PathVariable
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                        LocalDate date){
        return ServiceAnswerHelper.wrapIntoResponse(stationScheduleService.getStationSchedule(stationId, date));
    }
}
