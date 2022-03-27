package com.bombanya.javaschool_railway.controllers;

import com.bombanya.javaschool_railway.entities.Passenger;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.entities.Ticket;
import com.bombanya.javaschool_railway.entities.geography.Settlement;
import com.bombanya.javaschool_railway.entities.routes.RunSearchingResultDTO;
import com.bombanya.javaschool_railway.services.PassengerService;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.services.TicketService;
import com.bombanya.javaschool_railway.services.geography.SettlementService;
import com.bombanya.javaschool_railway.services.routes.RunSearchingService;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final SettlementService settlementService;
    private final PassengerService passengerService;
    private final RunSearchingService runSearchingService;
    private final TicketService ticketService;

    @GetMapping("/settlement/all/{nameStart}")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<List<Settlement>>> getAllSettlementsByNameStart(
            @PathVariable String nameStart){
        return ServiceAnswerHelper.wrapIntoResponse(settlementService
                .getByNameStartsWith(nameStart));
    }

    @PostMapping("/passenger/code")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Integer>> getPassengerCode(@RequestBody Passenger passenger){
        return ServiceAnswerHelper.wrapIntoResponse(passengerService.getPassengerCode(passenger));
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

    @GetMapping("/tickets/available/{runId}/{stationFrom}/{stationTo}")
    @JsonView(JacksonView.MinimalInfo.class)
    public ResponseEntity<ServiceAnswer<List<Ticket>>> getAvailableTickets(@PathVariable int runId,
                                                                           @PathVariable int stationFrom,
                                                                           @PathVariable int stationTo){
        return ServiceAnswerHelper.wrapIntoResponse(ticketService.getAvailableTickets(runId, stationFrom, stationTo));
    }

    @PostMapping("/tickets/buy")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Integer>> buyTickets(@RequestBody List<Ticket> tickets){
        return ServiceAnswerHelper.wrapIntoResponse(ticketService.buyTickets(tickets));
    }
}
